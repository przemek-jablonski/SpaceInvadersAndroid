package com.gamedesire.pszemek.recruitment.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.particles.emitters.Emitter;
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

import net.dermetfan.gdx.assets.AnnotationAssetManager;


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
    long            currentTimeMillis;
    float           randomnessFactor;

    Texture vlt = AssetRouting.getVignetteLTTexture();
    Texture vlb = AssetRouting.getVignetteLBTexture();
    Texture vrt = AssetRouting.getVignetteRTTexture();
    Texture vrb = AssetRouting.getVignetteRBTexture();

    //dev only, refactor!:
    ParticleEffect exploParticle;

    public boolean levelCleared = true;

    private int heroPoints;


    public ActorHolder(TouchProcessor touchProcessor) {

        heroPoints = 0;

        this.touchProcessor = touchProcessor;

        actors = new DelayedRemovalArray<SpaceInvadersActor>();
        projectiles = new DelayedRemovalArray<ProjectileActor>();
        actualLevel = 0;
        remainingActorsInWave = 0;

        exploParticle = new ParticleEffect();
        exploParticle.load(Gdx.files.internal("particle_explosion_alllayers.p"), Gdx.files.internal(""));
        exploParticle.scaleEffect(2.75f);
    }


    public void updateAll() {
        currentTimeMillis = System.currentTimeMillis();
        randomnessFactor = MathUtils.random(0.7f, 1.3f);
        actors.begin();
        projectiles.begin();

        //todo: move it to another method?
        if(touchProcessor.isTouchPressedDown())
            spawnProjectile(getHero(), ActorType.HERO);

        if (actors.size <= 1)
            if (!levelCleared)
                levelCleared = true;


        //iterating over actors
        for (SpaceInvadersActor actor : actors) {
            actor.update();

            if(checkActorOutOfScreen(actor))
                if (disposeActor(actor)) {
                    System.err.println("ACTOR (actor) DISPOSED, pos: " + actor.getActorPosition());
                }

            //spawn projectile, if applicable
            if (actor instanceof EnemyActor) {
                if (checkEnemySpawnProjectile((EnemyActor) actor)) {
                    spawnProjectile(actor, ActorType.ENEMY);
                }

            //check if actor is dead
            if (((EnemyActor)actor).isDead())
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

        Color col = spriteBatch.getColor();
        spriteBatch.setColor(new Color(0f, 0f, 0f, 0.5f));

        spriteBatch.draw(vlb, 0f, 0f, vlb.getWidth(), vlb.getHeight());
        spriteBatch.draw(vlt, 0f, Gdx.graphics.getHeight() - vlt.getHeight(), vlt.getWidth(), vlt.getHeight());
        spriteBatch.draw(vrt, Gdx.graphics.getWidth() - vrt.getWidth(), Gdx.graphics.getHeight() - vlt.getHeight(), vlt.getWidth(), vlt.getHeight());
        spriteBatch.draw(vrb, Gdx.graphics.getWidth() - vrt.getWidth(), 0f, vlt.getWidth(), vlt.getHeight());

        spriteBatch.setColor(col);
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


    public void spawnLevel() {
        ++actualLevel;
        System.err.println("SPAWNING: SPAWNLEVEL / actual level: " + actualLevel);
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
        System.err.println("SPAWNING: SPAWNLEVEL 1/ actual level: " + actualLevel);
        spawnEnemyWave(2, 1);
        spawnEnemyWave(3, 2);
    }

    public void spawnLevel2(){
        System.err.println("SPAWNING: SPAWNLEVEL 2/ actual level: " + actualLevel);
        spawnEnemyWave(1, 1);
        spawnEnemyWave(3, 2);
        spawnEnemyWave(4, 3);
    }

    public void spawnLevel3() {
        System.err.println("SPAWNING: SPAWNLEVEL 3/ actual level: " + actualLevel);
        spawnEnemyWave(1, 1); //todo: here should be spawned enemy002
        spawnEnemyWave(3, 2);
        spawnEnemyWave(5, 3); //todo: here as well (in the middle)
    }

    public void spawnLevel4() {
        System.err.println("SPAWNING: SPAWNLEVEL 4/ actual level: " + actualLevel);
        spawnEnemyWave(2, 1);
        spawnEnemyWave(2, 2);
        spawnEnemyWave(3, 3); //todo: here on the sides
        spawnEnemyWave(4, 4);
        spawnEnemyWave(5, 5); //todo: here as well (in the middle)
    }


    public void spawnLevel5() {
        System.err.println("SPAWNING: SPAWNLEVEL 5/ actual level: " + actualLevel);
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
                    new EnemyActor(
                            (Constants.PREF_WIDTH / (enemyCount + 1)) * (e+1),
                            AssetRouting.getEnemy001Texture().getHeight() * (heightLine * 2) + Constants.PREF_HEIGHT + AssetRouting.getEnemy001Texture().getHeight() * 2,
                            Constants.VECTOR_DIRECTION_DOWN
                    ));
    }


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
    //todo: this random shit should be here - players rate of fire shouldn't be affected
    private void spawnProjectile(SpaceInvadersActor actor, ActorType actorType) {
        if (currentTimeMillis - actor.getLastFiredMillis() > actor.getRateOfFireIntervalMillis() * MathUtils.random(0.8f, 1.5f)) {
            if (actorType == ActorType.HERO)
                projectiles.add(new BulletProjectileActor(actor.getActorPosition(), ActorType.HERO));
            else
                projectiles.add(new BulletProjectileActor(actor.getActorPosition(), Constants.VECTOR_DIRECTION_DOWN, ActorType.ENEMY));
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
        if (actor instanceof EnemyActor) {
            for (ParticleEmitter emitter : exploParticle.getEmitters())
                emitter.setPosition(actor.getActorPosition().x, actor.getActorPosition().y);

            exploParticle.start();

            //// FIXME: 06/05/16 should be multiplied by level value and healthpoints
            heroPoints += Constants.BASE_POINTS_FOR_ENEMY * (actualLevel * 0.25f + 1f) + actor.getMaxHealthPoints() + actor.getMaxShieldPoints();

//            ((EnemyActor) actor).getSprite().setAlpha(0f);
//            ((EnemyActor) actor).getSprite().setColor(1f, 1f, 1f, 0.5f);

//            actor.setPosition(0, Constants.PREF_HEIGHT * 3);
//            actor.setDirection(0f, 0f);

            return actors.removeValue(actor, false);

        }
        return false;
    }


    private boolean checkProjectileHit(ProjectileActor projectile) {
        for (SpaceInvadersActor actor : actors)
            if ((actor instanceof EnemyActor && projectile.getActorType() == ActorType.HERO) || (actor instanceof HeroActor && projectile.getActorType() == ActorType.ENEMY)) {
                if (checkCollision(actor, projectile))
                    //todo: actors need to have their indexes inside them, so this STUPID LOOP can be thrashed out.
                    for (SpaceInvadersActor actorInList : actors) {
                        if (actorInList.equals(actor)) {
                            //todo: move away this shit to projectileCollided().
                            if (actor instanceof EnemyActor)
                                ((EnemyActor) actor).onHit(projectile.getDamageValue());
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

    public int getHeroPoints() {
        return heroPoints;
    }

    public short getActualLevel() {
        return actualLevel;
    }

    public int getActualActorsSize() {
        return actors.size;
    }
}
