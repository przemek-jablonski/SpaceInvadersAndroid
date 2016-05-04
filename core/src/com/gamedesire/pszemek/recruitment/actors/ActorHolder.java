package com.gamedesire.pszemek.recruitment.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.gamedesire.pszemek.recruitment.actors.archetypes.ActorType;
import com.gamedesire.pszemek.recruitment.actors.archetypes.ProjectileActor;
import com.gamedesire.pszemek.recruitment.actors.archetypes.SpaceInvadersActor;
import com.gamedesire.pszemek.recruitment.actors.primary.EnemyActor;
import com.gamedesire.pszemek.recruitment.actors.primary.HeroActor;
import com.gamedesire.pszemek.recruitment.actors.projectiles.BulletProjectileActor;
import com.gamedesire.pszemek.recruitment.input.TouchProcessor;
import com.gamedesire.pszemek.recruitment.utilities.Constants;
import com.gamedesire.pszemek.recruitment.utilities.AssetRouting;


/**
 * Created by Ciemek on 02/05/16.
 */

/**
 * Handles operations on objects inside a screen during gameplay, which are not already covered by
 * Actors' classes themselves.
 *
 * This includes spawning enemies {@link #spawnEnemiesTest()}, player avatar {@link #spawnHero()},
 * referencing all existing actors inside a scene and performing modifications on them as well ( {@link #disposeActor(SpaceInvadersActor)},
 * {@link #renderAll(SpriteBatch)}, {@link #renderAll(SpriteBatch)}) etc.
 */
public class ActorHolder {

    //todo: check if after optimisation, it is possible to change arrays to simpler lists
    DelayedRemovalArray<SpaceInvadersActor> actors;
    DelayedRemovalArray<ProjectileActor>    projectiles;

    TouchProcessor  touchProcessor;
    short           actualLevel;
    short           remainingActorsInWave;



    public ActorHolder(TouchProcessor touchProcessor) {
        this.touchProcessor = touchProcessor;

        actors = new DelayedRemovalArray<SpaceInvadersActor>();
        projectiles = new DelayedRemovalArray<ProjectileActor>();
        actualLevel = 0;
        remainingActorsInWave = 0;
    }


    public void updateAll() {
        actors.begin();
        projectiles.begin();

        //todo: move it to another method?
        if(touchProcessor.isTouchPressedDown())
            spawnProjectile(getHero(), ActorType.HERO);


        //iterating actors
        for (SpaceInvadersActor actor : actors) {
            actor.update();

            if(checkActorOutOfScreen(actor))
                disposeActor(actor);

            if (actor instanceof EnemyActor) {
                if (checkEnemySpawnProjectile((EnemyActor) actor))
                    spawnProjectile(actor, ActorType.ENEMY);

                if (((EnemyActor)actor).isDead())
                    disposeActor(actor);
            }

        }

        //iterating projectiles
        for (ProjectileActor projectile : projectiles) {
            projectile.update();

            if(checkActorOutOfScreen(projectile))
                disposeActor(projectile);

            checkProjectileHit(projectile);
        }

        projectiles.end();
        actors.end();

    }

    /**
     * Calling all available renderers.
     *
     * @param spriteBatch
     */
    //todo: optimise traversal through actors list
    public void renderAll(SpriteBatch spriteBatch) {
        renderAllActors(spriteBatch);
        renderAllProjectiles(spriteBatch);
        renderHero(spriteBatch);

    }

    /**
     * Rendering all actors referenced by this class (enemies, obstacles, specials) onto screen.
     *
     * @param spriteBatch Rendering batch.
     */
    private void renderAllActors(SpriteBatch spriteBatch) {
        for (int a = 1; a < actors.size; ++a)
            actors.get(a).render(spriteBatch);
    }

    /**
     * Rendering all projectiles referenced by this class (bullets, rockets etc.) onto screen.
     *
     * @param spriteBatch Rendering batch.
     */
    private void renderAllProjectiles(SpriteBatch spriteBatch) {
        for (int p = 0; p < projectiles.size; ++p)
            projectiles.get(p).render(spriteBatch);
    }

    /**
     * Rendering hero on screen.
     *
     * @param spriteBatch Rendering batch.
     */
    private void renderHero(SpriteBatch spriteBatch) {
        //todo: some exceptions, for example here: if actor here isn't instance of HeroActor
        actors.get(0).render(spriteBatch);
    }


    public HeroActor spawnHero() {
        actors.add(new HeroActor(Constants.PREF_HEIGHT / 5f, Constants.PREF_WIDTH / 2f, 0f, 0f));
        return (HeroActor)actors.get(0);
    }


    public void spawnEnemiesTest() {
        ++actualLevel;

        for (int i=0; i < 30; ++i)
            actors.add(
                    new EnemyActor(
                            MathUtils.random(Constants.SPAWN_MARGIN_HORIZONTAL_STANDARD + AssetRouting.getEnemy001Texture().getWidth() / 2, Constants.PREF_WIDTH - AssetRouting.getEnemy001Texture().getWidth() / 2 - Constants.SPAWN_MARGIN_HORIZONTAL_STANDARD),
                            MathUtils.random((float) (Constants.PREF_HEIGHT + AssetRouting.getEnemy001Texture().getHeight() / 2), Constants.PREF_HEIGHT * 2f),
                            Constants.VECTOR_DIRECTION_DOWN
                    ));


    }

//    public boolean playerSpawnProjectile() {
//        long timeDiff = System.currentTimeMillis() - heroActor.getLastFiredMillis();
//
//        if (timeDiff > heroActor.getRateOfFireIntervalMillis()) {
//            addActor(new BulletProjectileActor(heroActor.getActorPosition(), ActorType.HERO));
//            heroActor.setLastFiredMillis(System.currentTimeMillis());
//            return true;
//        }
//
//        return false;
//    }

    /**
     * Performing simple check whether given enemy is requesting for projectile spawn on it's behalf.
     *
     * @param enemy Enemy requesting (perhaps) for projectile spawn.
     * @return true, if enemy is actually requesting for projectile spawn.
     */
    //todo: make this method not only for enemy, but for player as well (make 'boolean shoot' part of SpaceInvActor, not only EnemyActor)
    //todo: check time here?
    public boolean checkEnemySpawnProjectile(EnemyActor enemy) {
        if (enemy.isShoot()) {
            enemy.setShot();
            return true;
        }
        return false;
    }


    /**
     * Spawning projectile on screen.
     * Setting up and storing projectile reference in proper list as well.
     *
     * @param actor Actor, which requested projectile spawn (needed for position fetch)
     * @param actorType ActorType, passed as projectile constructor parameter.
     */
    private void spawnProjectile(SpaceInvadersActor actor, ActorType actorType) {
//        if (actorType == ActorType.ENEMY)
//            projectiles.add(new BulletProjectileActor(actor.getActorPosition(), Constants.VECTOR_DIRECTION_DOWN, ActorType.ENEMY));
//        else {
//            checkIntervalForProjectile(actor);
//        }

        if (System.currentTimeMillis() - actor.getLastFiredMillis() > actor.getRateOfFireIntervalMillis()) {
            if (actorType == ActorType.HERO)
                projectiles.add(new BulletProjectileActor(actor.getActorPosition(), ActorType.HERO));
            else
                projectiles.add(new BulletProjectileActor(actor.getActorPosition(), Constants.VECTOR_DIRECTION_DOWN, ActorType.ENEMY));
            actor.setLastFiredMillis(System.currentTimeMillis());
        }

    }


    /**
     * Checks whether actor is inside bounds of a screen.
     *
     * @param actor Actor for which checks are performed on
     * @return boolean - if Actor is out of bounds or not
     */
    private boolean checkActorOutOfScreen(SpaceInvadersActor actor) {

        if (actor.getActorPosition().y < -actor.getBoundingRectangle().getHeight())
            return true;

        if (actor.getActorPosition().y > Gdx.graphics.getHeight() + actor.getBoundingRectangle().getHeight())
            return true;

        //needed ?
//        if (actor instanceof BulletProjectileActor
//        && actor.getActorPosition().y > Gdx.graphics.getHeight() + actor.getBoundingRectangle().getHeight()) {
//            return true;
//        }

        return false;
    }


    /**
     * Performs disposal of given actor (removal and dereferencial / setting invisibility)
     *
     * @param actor Actor to dispose
     * @return true if disposal action(s) were executed properly.
     */
    private boolean disposeActor(SpaceInvadersActor actor) {
        return actors.removeValue(actor, false);
    }


    private boolean checkProjectileHit(ProjectileActor projectile) {
//        temporaryCreateEnemyList();
//        temporaryCreateProjectileList();
////        for (BulletProjectileActor projectile : temporaryProjectileList)
////            for (EnemyActor enemy : temporaryEnemyList)
////                if (enemy.getBoundingRectangle().contains(projectile.getBoundingRectangle())
////                        || enemy.getBoundingRectangle().overlaps(projectile.getBoundingRectangle())) {
////                    System.err.println("COLLISION-> " + enemy.getActorPosition() + " (collided with projectile at " + projectile.getActorPosition() + ") ");
//////                    actors.removeValue(enemy, false);
////
////                    //todo: WHAT THE FUCK, THIS IS LOOP INSIDE LOOP INSIDE LOOP (O^3), REWRITE THAT ASAP
////                    for (SpaceInvadersActor actor : actors)
////                        if (actor.equals(enemy)) {
////                            ((EnemyActor) actor).onHit(projectile.getDamageValue());
////                        }
////
////                    actors.removeValue(projectile, false);
////                    return true;
////                }
//        return false;

        for (SpaceInvadersActor actor : actors)
            if ((actor instanceof EnemyActor && projectile.getActorType() == ActorType.HERO) || (actor instanceof HeroActor && projectile.getActorType() == ActorType.ENEMY)) {
                if (checkCollision(actor, projectile))
                    //todo: actors need to have their indexes inside them, so this STUPID LOOP can be thrashed out.
                    for (SpaceInvadersActor actorInList : actors) {
                        if (actorInList.equals(actor)) {
                            //todo: move away this shit to projectileCollided().
                            if (actor instanceof EnemyActor)
                                ((EnemyActor) actor).onHit(projectile.getDamageValue());
                            if (actor instanceof HeroActor)
                                ((HeroActor) actor).onHit(projectile.getDamageValue());
                            return true;
                        }
                    }


            }

        return false;
    }


    private boolean checkCollision(SpaceInvadersActor actor1, SpaceInvadersActor actor2) {
        if (actor1.getBoundingRectangle().contains(actor2.getBoundingRectangle())
                ||actor1.getBoundingRectangle().overlaps(actor2.getBoundingRectangle()))
            return true;
        return false;
    }

    //todo:
//    private boolean projectileCollided(SpaceInvadersActor actor, ProjectileActor projectile) {
//
//    }

//    private LinkedList<EnemyActor> temporaryCreateEnemyList() {
//
//        temporaryEnemyList = new LinkedList<EnemyActor>();
//        for (SpaceInvadersActor enemy : actors) {
//            if (enemy instanceof EnemyActor)
//                temporaryEnemyList.add((EnemyActor)enemy);
//        }
//
//        return temporaryEnemyList;
//    }
//
//    public LinkedList<BulletProjectileActor> temporaryCreateProjectileList() {
//
//        temporaryProjectileList = new LinkedList<BulletProjectileActor>();
//        for (SpaceInvadersActor projectile : actors) {
//            if (projectile instanceof BulletProjectileActor)
//                temporaryProjectileList.add((BulletProjectileActor)projectile);
//        }
//
//        return temporaryProjectileList;
//    }

    public HeroActor getHero() {
        return (HeroActor)actors.get(0);
    }

}
