package com.gamedesire.pszemek.recruitment.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.Disposable;
import com.gamedesire.pszemek.recruitment.actors.archetypes.ActorType;
import com.gamedesire.pszemek.recruitment.actors.archetypes.ProjectileActor;
import com.gamedesire.pszemek.recruitment.actors.archetypes.SpaceInvadersActor;
import com.gamedesire.pszemek.recruitment.actors.primary.EnemyActor001;
import com.gamedesire.pszemek.recruitment.actors.primary.HeroActor;
import com.gamedesire.pszemek.recruitment.actors.projectiles.RocketProjectileActor;
import com.gamedesire.pszemek.recruitment.mvc.controllers.AbstractTouchProcessor;
import com.gamedesire.pszemek.recruitment.utilities.Const;
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
public class ActorHolder implements Disposable{

    //todo: check if after optimisation, it is possible to change arrays to simpler lists
    DelayedRemovalArray<SpaceInvadersActor> actors;
    DelayedRemovalArray<ProjectileActor>    projectiles;

    long            currentTimeMillis;

    //dev only, refactor!:
    ParticleEffect exploParticle;

//    private  boolean levelCleared = true;

//    private int heroPoints;


    public ActorHolder() {
        actors = new DelayedRemovalArray<SpaceInvadersActor>();
        projectiles = new DelayedRemovalArray<ProjectileActor>();

        exploParticle = new ParticleEffect();
        exploParticle.load(Gdx.files.internal("particle_explosion_alllayers.p"), Gdx.files.internal(""));
        exploParticle.scaleEffect(2.75f);
    }


    public void updateAll(float deltaTime, long currentTimeMillis) {
        this.currentTimeMillis = currentTimeMillis;
        actors.begin();
        projectiles.begin();


        //iterating over actors
        for (SpaceInvadersActor actor : actors) {
            actor.update();

            if(checkActorOutOfScreen(actor))
                if (disposeActor(actor)) {
                    System.err.println("ACTOR (actor) DISPOSED, pos: " + actor.getActorPosition());
                }

            //spawn projectile, if applicable
            if (actor instanceof EnemyActor001) {
                if (checkEnemySpawnProjectile((EnemyActor001) actor)) {
                    spawnProjectile(actor, ActorType.ENEMY);
                }

            //check if actor is dead
            if (((EnemyActor001)actor).isDead())
                if (disposeActor(actor))
                    System.err.println("ACTOR (actor) DEAD, pos: " + actor.getActorPosition());
            }

        }

        //iterating over projectiles
        for (ProjectileActor projectile : projectiles) {
            //update actor
            projectile.update();

            //check if is out of screen
            if(checkActorOutOfScreen(projectile))
                if (disposeActor(projectile)) {
                    System.err.println("ACTOR (projectile) DISPOSED, pos: " + projectile.getActorPosition() + ", act size: " + actors.size);
                }

            //check for collisions
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

        exploParticle.update(Gdx.graphics.getDeltaTime());
        exploParticle.draw(spriteBatch);
        if(exploParticle.isComplete())
            exploParticle.reset();
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
        for (int p = 0; p < projectiles.size; ++p) {
            if (projectiles.get(p).getActorType() == ActorType.ENEMY)
                projectiles.get(p).render(spriteBatch);
            projectiles.get(p).render(spriteBatch);
        }
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
        actors.add(new HeroActor(Const.PREF_HEIGHT / 5f, Const.PREF_WIDTH / 2f, 0f, 0f));
        return (HeroActor)actors.get(0);
    }

    public void spawnLevel(int actualLevel) {
//        ++actualLevel;
        if (actualLevel == 1) {
            spawnLevel1();
            return;
        }
        if (actualLevel == 2) {
            spawnLevel2();
            return;
        }
        if (actualLevel == 3) {
            spawnLevel3();
            return;
        }
        if (actualLevel == 4) {
            spawnLevel4();
            return;
        }
        if (actualLevel == 5) {
            spawnLevel5();
            return;
        }

        spawnLevelProcedural(actualLevel);
    }

    public void spawnLevel1(){
        spawnEnemyWave(2, 1);
        spawnEnemyWave(3, 2);
    }

    public void spawnLevel2(){
        spawnEnemyWave(1, 1);
        spawnEnemyWave(3, 2);
        spawnEnemyWave(4, 3);
    }

    public void spawnLevel3() {
        spawnEnemyWave(1, 1); //todo: here should be spawned enemy002
        spawnEnemyWave(3, 2);
        spawnEnemyWave(5, 3); //todo: here as well (in the middle)
    }

    public void spawnLevel4() {
        spawnEnemyWave(2, 1);
        spawnEnemyWave(2, 2);
        spawnEnemyWave(3, 3); //todo: here on the sides
        spawnEnemyWave(4, 4);
        spawnEnemyWave(5, 5); //todo: here as well (in the middle)
    }

    public void spawnLevel5() {
        spawnEnemyWave(4, 1);
        spawnEnemyWave(3, 2); //todo: all enemies002
        spawnEnemyWave(3, 3); //todo: 002 + 003 in the middle
        spawnEnemyWave(4, 4); //todo: 2x 003
        spawnEnemyWave(5, 5); //todo: all 001
    }

    public void spawnLevelProcedural(int levelNumber) {
        int waveHeight = 0;
        for (int i=0; i < levelNumber + MathUtils.random(-1, 3); ++i) {
            waveHeight += i;
            waveHeight += MathUtils.random(0f, 0.5f);
            spawnEnemyWave(MathUtils.clamp(MathUtils.random(0,3) + MathUtils.random(1, 4), 1, 5), waveHeight);
        }

    }

    private void spawnEnemyWave(int enemyCount, int heightLine) {
        for (int e=0; e<enemyCount; ++e)
            actors.add(
                    new EnemyActor001(
                            (Const.PREF_WIDTH / (enemyCount + 1)) * (e+1),
                            AssetRouting.getEnemy001Texture().getHeight() * (heightLine * 2) + Const.PREF_HEIGHT + AssetRouting.getEnemy001Texture().getHeight() * 2,
                            Const.VECTOR_DIRECTION_DOWN
                    ));
    }

    /**
     * Performing simple check whether given enemy is requesting for projectile spawn on it's behalf.
     *
     * @param enemy Enemy requesting (perhaps) for projectile spawn.
     * @return true, if enemy is actually requesting for projectile spawn.
     */
    //todo: make this method not only for enemy, but for player as well (make 'boolean shoot' part of SpaceInvActor, not only EnemyActor001)
    //todo: check time here?
    public boolean checkEnemySpawnProjectile(EnemyActor001 enemy) {
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
    //todo: this random shit should be here - players rate of fire shouldn't be affected
    public void spawnProjectile(SpaceInvadersActor actor, ActorType actorType) {
        if (currentTimeMillis - actor.getLastFiredMillis() > actor.getRateOfFireIntervalMillis() * MathUtils.random(0.8f, 1.5f)) {
            if (actorType == ActorType.HERO)
                projectiles.add(new RocketProjectileActor(actor.getActorPosition(), ActorType.HERO));
            else
                projectiles.add(new RocketProjectileActor(actor.getActorPosition(), Const.VECTOR_DIRECTION_DOWN, ActorType.ENEMY));
            actor.setLastFiredMillis(currentTimeMillis);
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

        if (actor instanceof ProjectileActor && actor.getActorPosition().y > Gdx.graphics.getHeight() + actor.getBoundingRectangle().getHeight())
            return true;

        return false;
    }

    /**
     * Performs disposal of given actor (removal and dereferencial / setting invisibility)
     *
     * @param actor Actor to dispose
     * @return true if disposal action(s) were executed properly.
     */
    private boolean disposeActor(SpaceInvadersActor actor) {
        if (actor instanceof ProjectileActor)
            return projectiles.removeValue((ProjectileActor) actor, false);
        if (actor instanceof EnemyActor001) {
            for (ParticleEmitter emitter : exploParticle.getEmitters())
                emitter.setPosition(actor.getActorPosition().x, actor.getActorPosition().y);

            exploParticle.start();

            //// FIXME: 06/05/16 should be multiplied by level value and healthpoints
//            heroPoints += Const.BASE_POINTS_FOR_ENEMY * (actualLevel * 0.25f + 1f) + actor.getMaxHealthPoints() + actor.getMaxShieldPoints();

//            ((EnemyActor001) actor).getSprite().setAlpha(0f);
//            ((EnemyActor001) actor).getSprite().setColor(1f, 1f, 1f, 0.5f);

//            actor.setPosition(0, Const.PREF_HEIGHT * 3);
//            actor.setDirection(0f, 0f);

            return actors.removeValue(actor, false);

        }
        return false;
    }

    private boolean checkProjectileHit(ProjectileActor projectile) {
        for (SpaceInvadersActor actor : actors)
            if ((actor instanceof EnemyActor001 && projectile.getActorType() == ActorType.HERO) || (actor instanceof HeroActor && projectile.getActorType() == ActorType.ENEMY)) {
                if (checkCollision(actor, projectile))
                    //todo: actors need to have their indexes inside them, so this STUPID LOOP can be thrashed out.
                    for (SpaceInvadersActor actorInList : actors) {
                        if (actorInList.equals(actor)) {
                            //todo: move away this shit to projectileCollided().
                            if (actor instanceof EnemyActor001)
                                ((EnemyActor001) actor).onHit(projectile.getDamageValue());
                            else if (actor instanceof HeroActor)
                                ((HeroActor) actor).onHit(projectile.getDamageValue());
                            projectiles.removeValue(projectile, false);
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

    public HeroActor getHero() {
        return (HeroActor)actors.get(0);
    }

    public DelayedRemovalArray<SpaceInvadersActor> getActors() {
        return actors;
    }

    @Override
    public void dispose() {
        actors.clear();
        actors = null;
        projectiles.clear();
        projectiles = null;
    }
}
