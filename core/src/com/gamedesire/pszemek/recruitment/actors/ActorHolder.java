package com.gamedesire.pszemek.recruitment.actors;

import java.util.LinkedList;
import java.util.Random;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.gamedesire.pszemek.recruitment.actors.archetypes.ActorType;
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
public class ActorHolder {

    DelayedRemovalArray<SpaceInvadersActor> actors;

    //todo: fix this workaround as soon as possible!
    LinkedList<EnemyActor> temporaryEnemyList;
    LinkedList<BulletProjectileActor> temporaryProjectileList;

    short           actualLevel;
    short           remainingActorsInWave;
    short           remainingActorsInLevel;
    short           aliveNonHeroActorsInWave;
    TouchProcessor  touchProcessor;
    HeroActor heroActor;
    Random          random;


    public ActorHolder(TouchProcessor touchProcessor) {
        this.touchProcessor = touchProcessor;
        actors = new DelayedRemovalArray<SpaceInvadersActor>();
        random = new Random();
        actualLevel = 0;
        remainingActorsInWave = 0;
        aliveNonHeroActorsInWave = remainingActorsInWave;
    }

    public HeroActor spawnHero() {
        if (actors.size != 0) return null;
        else
            actors.add(new HeroActor(Constants.PREF_HEIGHT / 3f, Constants.PREF_WIDTH / 3f, 0f, 0f));

        heroActor = (HeroActor)actors.get(0);
        return heroActor;
    }

    public void beginNewLevel() {
        ++actualLevel;
//        remainingActorsInWave = (short)(actualLevel * 10);

        //debug only (or maybe not):
        remainingActorsInWave = 10;
        for (int i=0; i < 30; ++i) {
            addActor(
                    new EnemyActor(
                            MathUtils.random(Constants.SPAWN_MARGIN_HORIZONTAL_STANDARD + AssetRouting.getEnemy001Texture().getWidth()/2, Constants.PREF_WIDTH - AssetRouting.getEnemy001Texture().getWidth()/2 - Constants.SPAWN_MARGIN_HORIZONTAL_STANDARD),
                            MathUtils.random((float) (Constants.PREF_HEIGHT + AssetRouting.getEnemy001Texture().getHeight() / 2), Constants.PREF_HEIGHT * 2f),
                            Constants.VECTOR_DIRECTION_DOWN
                    ));
        }

    }

    public void updateAll() {
        actors.begin();

        //todo: move it to another method?
        if(touchProcessor.isTouchPressedDown())
            playerSpawnProjectile();


        for (SpaceInvadersActor actor : actors) {
            actor.update();

            if (disposeActor(actor))
                break;

            if (actor instanceof EnemyActor)
                enemySpawnProjectile((EnemyActor)actor);
        }

        checkProjectileHitAll();


        for (SpaceInvadersActor actor : actors) {
            if(actor instanceof EnemyActor) {
                if (((EnemyActor)actor).isDead())
                    actors.removeValue(actor, false);
            }
        }

        actors.end();
    }

    //todo: optimise traversal through actors list
    public void renderAll(SpriteBatch spriteBatch) {
        //rendering enemies on screen, as one layer
        for (SpaceInvadersActor actor : actors)
            if (actor instanceof EnemyActor)
                actor.render(spriteBatch);

        for (SpaceInvadersActor actor : actors)
            if (actor instanceof BulletProjectileActor)
                actor.render(spriteBatch);

        //rendering player on screen on layer above enemies, for image clarity
        if (actors.get(0) instanceof HeroActor)
                actors.get(0).render(spriteBatch);
        else
            System.out.print("COULD NOT FIND HERO AT POSITION 0.");
    }


    public void addActor(SpaceInvadersActor actor) {
        actors.add(actor);
        remainingActorsInWave = 0;
    }


    public HeroActor getHero() {
        return heroActor;
    }


    public boolean playerSpawnProjectile() {
        long timeDiff = System.currentTimeMillis() - heroActor.getLastFiredMillis();

        if (timeDiff > heroActor.getRateOfFireIntervalMillis()) {
            addActor(new BulletProjectileActor(heroActor.getActorPosition(), ActorType.HERO));
            heroActor.setLastFiredMillis(System.currentTimeMillis());
            return true;
        }

        return false;
    }

    public boolean enemySpawnProjectile(EnemyActor enemy) {
        if (enemy.isShoot()) {
            enemy.setShot();
            addActor(new BulletProjectileActor(enemy.getActorPosition(), Constants.VECTOR_DIRECTION_DOWN, ActorType.ENEMY));
            return true;
        }

        return false;
    }


    private boolean disposeActor(SpaceInvadersActor actor) {
        if (actor.getActorPosition().y < -actor.getBoundingRectangle().getHeight()) {
            System.err.println("REMOVAL-> actor pos: " + actor.getActorPosition() + " (too low in scene) ");
            actors.removeValue(actor, false);
            return true;
        }

        if (actor instanceof BulletProjectileActor
        && actor.getActorPosition().y > Gdx.graphics.getHeight() + actor.getBoundingRectangle().getHeight()) {
            System.err.println("REMOVAL-> missle pos: " + actor.getActorPosition() + " (too high in scene) ");
            actors.removeValue(actor, false);
            return true;
        }

        return false;
    }


    private boolean checkProjectileHitAll() {

        temporaryCreateEnemyList();
        temporaryCreateProjectileList();
//        for (BulletProjectileActor projectile : temporaryProjectileList)
//            for (EnemyActor enemy : temporaryEnemyList)
//                if (enemy.getBoundingRectangle().contains(projectile.getBoundingRectangle())
//                        || enemy.getBoundingRectangle().overlaps(projectile.getBoundingRectangle())) {
//                    System.err.println("COLLISION-> " + enemy.getActorPosition() + " (collided with projectile at " + projectile.getActorPosition() + ") ");
////                    actors.removeValue(enemy, false);
//
//                    //todo: WHAT THE FUCK, THIS IS LOOP INSIDE LOOP INSIDE LOOP (O^3), REWRITE THAT ASAP
//                    for (SpaceInvadersActor actor : actors)
//                        if (actor.equals(enemy)) {
//                            ((EnemyActor) actor).onHit(projectile.getDamageValue());
//                        }
//
//                    actors.removeValue(projectile, false);
//                    return true;
//                }

        return false;
    }

    private LinkedList<EnemyActor> temporaryCreateEnemyList() {

        temporaryEnemyList = new LinkedList<EnemyActor>();
        for (SpaceInvadersActor enemy : actors) {
            if (enemy instanceof EnemyActor)
                temporaryEnemyList.add((EnemyActor)enemy);
        }

        return temporaryEnemyList;
    }

    public LinkedList<BulletProjectileActor> temporaryCreateProjectileList() {

        temporaryProjectileList = new LinkedList<BulletProjectileActor>();
        for (SpaceInvadersActor projectile : actors) {
            if (projectile instanceof BulletProjectileActor)
                temporaryProjectileList.add((BulletProjectileActor)projectile);
        }

        return temporaryProjectileList;
    }


}
