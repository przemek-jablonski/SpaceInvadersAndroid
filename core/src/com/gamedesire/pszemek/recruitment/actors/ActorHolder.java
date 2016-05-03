package com.gamedesire.pszemek.recruitment.actors;

import java.util.LinkedList;
import java.util.Random;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.gamedesire.pszemek.recruitment.actors.archetypes.SpaceInvadersActor;
import com.gamedesire.pszemek.recruitment.actors.projectiles.BulletProjectileActor;
import com.gamedesire.pszemek.recruitment.input.TouchProcessor;
import com.gamedesire.pszemek.recruitment.utilities.Constants;
import com.gamedesire.pszemek.recruitment.utilities.AssetRouting;


/**
 * Created by Ciemek on 02/05/16.
 */
public class ActorHolder {

    //todo: double check if LinkedList of actors is thread-safe (just in case)
//    List<SpaceInvadersActor> actors;
    DelayedRemovalArray<SpaceInvadersActor> actors;

    //todo: fix this workaround as soon as possible!
    LinkedList<EnemyActor> temporaryEnemyList;
    LinkedList<BulletProjectileActor> temporaryProjectileList;

    short           actualLevel;
    short           remainingActorsInWave;
    short           remainingActorsInLevel;
    short           aliveNonHeroActorsInWave;
    TouchProcessor  touchProcessor;
    HeroActor       heroActor;
    Random          random;


    public ActorHolder(TouchProcessor touchProcessor) {
//        actors = new LinkedList<SpaceInvadersActor>();
        this.touchProcessor = touchProcessor;
        actors = new DelayedRemovalArray<SpaceInvadersActor>();
        random = new Random();
        actualLevel = 0;
        remainingActorsInWave = 0;
        aliveNonHeroActorsInWave = remainingActorsInWave;
    }

    public HeroActor spawnHero() {
        if (actors.size != 0) {
            System.out.println("ERROR: ACTOR LIST IS NOT EMPTY");
            return null;
        }
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
        for (int i=0; i < 10; ++i) {
            addActor(
                    new EnemyActor(
                        (float) random.nextInt(Constants.PREF_WIDTH - AssetRouting.getEnemy001Texture().getWidth() - Constants.SPAWN_MARGIN_HORIZONTAL_STANDARD) + AssetRouting.getEnemy001Texture().getWidth() + Constants.SPAWN_MARGIN_HORIZONTAL_STANDARD,
                        (float) random.nextInt(Constants.PREF_HEIGHT - AssetRouting.getEnemy001Texture().getHeight() - Constants.SPAWN_MARGIN_HORIZONTAL_STANDARD) + AssetRouting.getEnemy001Texture().getHeight() + Constants.SPAWN_MARGIN_HORIZONTAL_STANDARD,
                        Constants.VECTOR_DIRECTION_DOWN
            ));

        }

    }

    public void updateAll() {
        actors.begin();

        if(touchProcessor.isTouchPressedDown())
            playerSpawnProjectile();

        for (SpaceInvadersActor actor : actors) {
            actor.update();

            if (disposeActor(actor))
                break;

//            if (actor instanceof BulletProjectileActor)
//                checkProjectileHit( (BulletProjectileActor)actor );
        }

//        for (SpaceInvadersActor projectile : actors) {
////            if (projectile instanceof BulletProjectileActor)
////                checkProjectileHit( (BulletProjectileActor) projectile);
//            getHero();
//        }

        checkProjectileHitAll();

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
        System.err.println("PLAYER SPAWN PROJECTILE TEST, diff: " + timeDiff + ", rate: " + heroActor.getRateOfFireIntervalMillis());

        if (timeDiff > heroActor.getRateOfFireIntervalMillis()) {
            addActor(new BulletProjectileActor(heroActor.getActorPosition()));
            heroActor.setLastFiredMillis(System.currentTimeMillis());
            System.err.println("PLAYER SPAWN PROJECTILE TEST - TRUE");
            return true;
        }

        System.err.println("PLAYER SPAWN PROJECTILE TEST - FALSE");
        return false;
    }


    private boolean disposeActor(SpaceInvadersActor actor) {
        if (actor.getActorPosition().y < -actor.getBoundingRectangle().getHeight()) {
            System.err.println("REMOVAL-> actor pos: " + actor.getActorPosition() + " (too low in scene) ");
//            actors.remove(actor);
            actors.removeValue(actor, false);
            return true;
        }

        if (actor instanceof BulletProjectileActor
        && actor.getActorPosition().y > Gdx.graphics.getHeight() + actor.getBoundingRectangle().getHeight()) {
            System.err.println("REMOVAL-> missle pos: " + actor.getActorPosition() + " (too high in scene) ");
//            actors.remove(actor);
            actors.removeValue(actor, false);
            return true;
        }

        return false;
    }


    private boolean checkProjectileHitAll() {

        temporaryCreateEnemyList();
        temporaryCreateProjectileList();

//        for (int i=0; i < temporaryEnemyList.size(); ++i)
//                if (temporaryEnemyList.get(i).getBoundingRectangle().contains(projectile.getBoundingRectangle())
//                        || temporaryEnemyList.get(i).getBoundingRectangle().overlaps(projectile.getBoundingRectangle())) {
//                    System.err.println("COLLISION-> " + temporaryEnemyList.get(i).getActorPosition() + " (collided with projectile at " + projectile.getActorPosition() + ") ");
//                    actors.removeValue(projectile, false);
//                    actors.removeValue(temporaryEnemyList.get(i), false);
//                    return true;
//                }

        for (BulletProjectileActor projectile : temporaryProjectileList)
            for (EnemyActor enemy : temporaryEnemyList)
                if (enemy.getBoundingRectangle().contains(projectile.getBoundingRectangle())
                        || enemy.getBoundingRectangle().overlaps(projectile.getBoundingRectangle())) {
                    System.err.println("COLLISION-> " + enemy.getActorPosition() + " (collided with projectile at " + projectile.getActorPosition() + ") ");
                    actors.removeValue(projectile, false);
                    actors.removeValue(enemy, false);
                    return true;
                }

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
