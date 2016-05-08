package com.gamedesire.pszemek.recruitment.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.Disposable;
import com.gamedesire.pszemek.recruitment.actors.archetypes.ActorType;
import com.gamedesire.pszemek.recruitment.actors.archetypes.BonusItemActor;
import com.gamedesire.pszemek.recruitment.actors.archetypes.EnemyActor;
import com.gamedesire.pszemek.recruitment.actors.archetypes.ProjectileActor;
import com.gamedesire.pszemek.recruitment.actors.archetypes.SpaceInvadersActor;
import com.gamedesire.pszemek.recruitment.actors.interfaces.IDamageable;
import com.gamedesire.pszemek.recruitment.actors.primary.EnemyActor001;
import com.gamedesire.pszemek.recruitment.actors.primary.EnemyActor002;
import com.gamedesire.pszemek.recruitment.actors.primary.HeroActor;
import com.gamedesire.pszemek.recruitment.actors.primary.SpaceDeerActor;
import com.gamedesire.pszemek.recruitment.actors.projectiles.BoltProjectileActor;
import com.gamedesire.pszemek.recruitment.actors.projectiles.ProjectileType;
import com.gamedesire.pszemek.recruitment.actors.projectiles.RocketProjectileActor;
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

    DelayedRemovalArray<SpaceInvadersActor> actors;
    DelayedRemovalArray<ProjectileActor>    projectiles;
    long            currentTimeMillis;

    //// TODO: 08/05/16 refactor it to observer pattern!
    public boolean enemyDeathOnHit;
    public Vector2 enemyDeathOnHitLocation;
    private int actualLevel;



    public ActorHolder() {
        actors = new DelayedRemovalArray<SpaceInvadersActor>();
        projectiles = new DelayedRemovalArray<ProjectileActor>();

        enemyDeathOnHit = false;
        enemyDeathOnHitLocation = Vector2.Zero;
    }


    public void updateAllActors(long currentTimeMillis) {
        this.currentTimeMillis = currentTimeMillis;
        actors.begin();
        projectiles.begin();


        //iterating over actors
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

            if (actor instanceof BonusItemActor) {
                if (actor.getActorPosition().x > Const.CAMERA_WIDTH + Const.SPAWN_MARGIN_HORIZONTAL_STANDARD)
                    disposeActor(actor);
                else if (((BonusItemActor)actor).isDead()) {
                    disposeActor(actor);
                    getHero().upgradeWeapon();
                }
            }
        }

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
     * Performing simple check whether given enemy is requesting for projectile spawn on it's behalf.
     *
     * @param enemy Enemy requesting (perhaps) for projectile spawn.
     * @return true, if enemy is actually requesting for projectile spawn.
     */
    //todo: make this method not only for enemy, but for player as well (make 'boolean shoot' part of SpaceInvActor, not only EnemyActor001)
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
    public void spawnProjectile(SpaceInvadersActor actor, ActorType actorType) {
        if (System.currentTimeMillis() - actor.getLastFiredMillis() > actor.getRateOfFireIntervalMillis() * MathUtils.random(0.8f, 1.5f)) {
            if (actorType == ActorType.HERO) {
                if (((HeroActor)actor).getWeaponType() == ProjectileType.ROCKET)
                    projectiles.add(new RocketProjectileActor(actor.getActorPosition(), ActorType.HERO));
                else
                    projectiles.add(new BoltProjectileActor(actor.getActorPosition(), ActorType.HERO));
            }
            else {
                if (actor instanceof EnemyActor002)
                    projectiles.add(new BoltProjectileActor(actor.getActorPosition(), Const.VECTOR_DIRECTION_DOWN, ActorType.ENEMY));
                else if (actor instanceof EnemyActor001)
                    projectiles.add(new RocketProjectileActor(actor.getActorPosition(), Const.VECTOR_DIRECTION_DOWN, ActorType.ENEMY));
            }
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
        if (actor.getActorPosition().y < -actor.getBoundingRectangle().getHeight()) {
//            System.err.println("actor OUT OF SCREEN, pos: " + actor.getActorPosition() +", y less than rectangle height: " + -actor.getBoundingRectangle().getHeight());
            return true;
        }

        if (actor instanceof ProjectileActor && actor.getActorPosition().y > Const.CAMERA_HEIGHT + actor.getBoundingRectangle().getHeight()/2) {
//            System.err.println("projectile OUT OF SCREEN, pos: " + actor.getActorPosition() + ", y more than gdx.height: " + Const.CAMERA_HEIGHT + ", rect height/2: " + actor.getBoundingRectangle().getHeight() / 2);
            return true;
        }

        return false;
    }


    //// TODO: 08/05/16 cause of disposal!
    private boolean disposeActor(SpaceInvadersActor actor) {
        if (actor instanceof ProjectileActor)
            return projectiles.removeValue((ProjectileActor) actor, false);
        if (actor instanceof EnemyActor) {
            enemyDeathOnHit = true;
            enemyDeathOnHitLocation = actor.getActorPosition();
            //// FIXME: 06/05/16 should be multiplied by level value and healthpoints
//            heroPoints += Const.BASE_POINTS_FOR_ENEMY * (actualLevel * 0.25f + 1f) + actor.getMaxHealthPoints() + actor.getMaxShieldPoints();

            return actors.removeValue(actor, false);
        }


        return actors.removeValue(actor, false);
    }

    private boolean checkProjectileHit(ProjectileActor projectile) {
        for (SpaceInvadersActor actor : actors)
            if ((actor instanceof EnemyActor && projectile.getActorType() == ActorType.HERO) ||
                    (actor instanceof BonusItemActor && projectile.getActorType() == ActorType.HERO) ||
                    (actor instanceof HeroActor && projectile.getActorType() == ActorType.ENEMY)) {
                if (checkCollision(actor, projectile))
                    for (SpaceInvadersActor actorInList : actors) {
                        if (actorInList.equals(actor)) {

                            try {
                                ((IDamageable) actor).onHit(projectile.getDamageValue());
                            } catch (ClassCastException exception) {
                                System.err.println("Attempted to damage actor, which is not damageable by default.");
                            }

                            projectiles.removeValue(projectile, false);
                            return true;
                        }
                    }
            }

        return false;
    }

    public boolean checkCollision(SpaceInvadersActor actor1, SpaceInvadersActor actor2) {
        if (actor1.getBoundingRectangle().contains(actor2.getBoundingRectangle())
                ||actor1.getBoundingRectangle().overlaps(actor2.getBoundingRectangle()))
            return true;
        return false;
    }

    public HeroActor spawnHero() {
        actors.add(new HeroActor(Const.CAMERA_HEIGHT / 5f, Const.CAMERA_WIDTH / 2f, 0f, 0f));
        return (HeroActor)actors.get(0);
    }

    public void spawnLevel(int actualLevel) {
        this.actualLevel = actualLevel;
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
        spawnEnemyWaveEnemy002Alone(1, 1);
        spawnEnemyWave(3, 2);
        spawnEnemyWave(5, 3);
    }

    public void spawnLevel4() {
        spawnSpaceDeer();
        spawnEnemyWaveEnemy002Alone(1, 1);
        spawnEnemyWaveEnemy002All(2, 2);
        spawnEnemyWave(3, 3);
        spawnEnemyWave(4, 4);
        spawnEnemyWave(5, 5);
    }

    public void spawnLevel5() {
        spawnEnemyWave(1, 1);
        spawnEnemyWave(1, 2);
        spawnEnemyWave(1, 3);
        spawnEnemyWaveEnemy002Sides(4, 4);
        spawnEnemyWaveEnemy002All(5, 5);
    }

    public void spawnLevelProcedural(int levelNumber) {
        int waveHeight = 0;
        for (int i=0; i < levelNumber + MathUtils.random(-1, 3); ++i) {
            waveHeight += i;
            waveHeight += MathUtils.random(0f, 0.15f);
            spawnEnemyWave(MathUtils.clamp(MathUtils.random(0,3) + MathUtils.random(1, 4), 1, 5), waveHeight);
        }
    }

    private void spawnEnemyWave(int enemyCount, int heightLine) {
        for (int e=0; e<enemyCount; ++e) {
            if (actualLevel > 5 && MathUtils.randomBoolean(MathUtils.clamp(actualLevel * 8f, 0f, 100f)/100f)) {
                actors.add(
                        new EnemyActor002(
                                (Const.CAMERA_WIDTH / (enemyCount + 1)) * (e + 1),
                                AssetRouting.getEnemy001Texture().getHeight() * (heightLine * 2) + Const.CAMERA_HEIGHT + AssetRouting.getEnemy001Texture().getHeight() * 2,
                                Const.VECTOR_DIRECTION_DOWN,
                                (int)(Const.VELOCITY_VALUE_ENEMY_002 * MathUtils.random(0.65f, 0.89f))
                        ));
            } else {
                actors.add(
                        new EnemyActor001(
                                (Const.CAMERA_WIDTH / (enemyCount + 1)) * (e + 1),
                                AssetRouting.getEnemy001Texture().getHeight() * (heightLine * 2) + Const.CAMERA_HEIGHT + AssetRouting.getEnemy001Texture().getHeight() * 2,
                                Const.VECTOR_DIRECTION_DOWN,
                                (int)(Const.VELOCITY_VALUE_ENEMY_001 * MathUtils.random(0.85f, 1.05f))
                        ));
            }
        }
    }

    private void spawnEnemyWaveEnemy002Alone(int enemyCount, int heightLine) {
        actors.add(
                new EnemyActor002(
                        Const.CAMERA_WIDTH / 2f,
                        AssetRouting.getEnemy001Texture().getHeight() * (heightLine * 2) + Const.CAMERA_HEIGHT + AssetRouting.getEnemy001Texture().getHeight() * 2,
                        Const.VECTOR_DIRECTION_DOWN,
                        Const.VELOCITY_VALUE_ENEMY_001
                ));
    }

    private void spawnEnemyWaveEnemy002All(int enemyCount, int heightLine) {
        for (int e=0; e<enemyCount; ++e) {
            actors.add(
                    new EnemyActor002(
                            (Const.CAMERA_WIDTH / (enemyCount + 1)) * (e + 1),
                            AssetRouting.getEnemy001Texture().getHeight() * (heightLine * 2) + Const.CAMERA_HEIGHT + AssetRouting.getEnemy001Texture().getHeight() * 2,
                            Const.VECTOR_DIRECTION_DOWN,
                            Const.VELOCITY_VALUE_ENEMY_001
                    ));
        }
    }

    private void spawnEnemyWaveEnemy002Sides(int enemyCount, int heightLine) {
        for (int e=0; e<enemyCount; ++e)
            if (e==0 || e == enemyCount -1) {
                actors.add(
                        new EnemyActor002(
                                (Const.CAMERA_WIDTH / (enemyCount + 1)) * (e + 1),
                                AssetRouting.getEnemy001Texture().getHeight() * (heightLine * 2) + Const.CAMERA_HEIGHT + AssetRouting.getEnemy001Texture().getHeight() * 2,
                                Const.VECTOR_DIRECTION_DOWN,
                                Const.VELOCITY_VALUE_ENEMY_001
                        ));
            } else {
                actors.add(
                        new EnemyActor001(
                                (Const.CAMERA_WIDTH / (enemyCount + 1)) * (e + 1),
                                AssetRouting.getEnemy001Texture().getHeight() * (heightLine * 2) + Const.CAMERA_HEIGHT + AssetRouting.getEnemy001Texture().getHeight() * 2,
                                Const.VECTOR_DIRECTION_DOWN
                        ));
            }
    }


    private void spawnSpaceDeer() {
        actors.add(
                new SpaceDeerActor(
                        -Const.SPAWN_MARGIN_HORIZONTAL_STANDARD,
                        Const.CAMERA_HEIGHT * MathUtils.random(0.3f, 0.6f),
                        Const.VECTOR_DIRECTION_RIGHT)
        );
    }



    public HeroActor getHero() {
        return (HeroActor)actors.get(0);
    }

    public DelayedRemovalArray<SpaceInvadersActor> getActors() { return actors; }

    public DelayedRemovalArray<ProjectileActor> getProjectiles() { return projectiles; }

    @Override
    public void dispose() {
        actors.clear();
        actors = null;
        projectiles.clear();
        projectiles = null;
    }
}
