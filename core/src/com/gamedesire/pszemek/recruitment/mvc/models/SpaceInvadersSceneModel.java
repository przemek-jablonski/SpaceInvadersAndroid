package com.gamedesire.pszemek.recruitment.mvc.models;

import com.badlogic.gdx.math.MathUtils;
import com.gamedesire.pszemek.recruitment.actors.archetypes.ActorType;
import com.gamedesire.pszemek.recruitment.actors.archetypes.BonusItemActor;
import com.gamedesire.pszemek.recruitment.actors.primary.HeroActor;
import com.gamedesire.pszemek.recruitment.utilities.Constants;

/**
 * Created by Ciemek on 08/05/16.
 */
public class SpaceInvadersSceneModel extends AbstractSceneModel {


    private long        startTimeMillis;
    private long        gameTimeSecs;
    private long        currentTimeMillis;
    private long        lastHPRegeneration;
    private float       tickRandomnessFactor;
    private int         actualLevel;
    private int         heroPoints;
    private boolean     gameOver;
    private HeroActor   heroActor;


    public SpaceInvadersSceneModel() {
        super();
    }

    @Override
    public void create() {
        startTimeMillis = System.currentTimeMillis();
        lastHPRegeneration = startTimeMillis;
        gameTimeSecs = 0;
        actualLevel = 0;
        heroPoints = 0;
        heroActor = actorHolder.spawnHero();
        gameOver = false;
        touchRequest = false;
    }

    @Override
    public void update(float deltaTime) {
        currentTimeMillis = System.currentTimeMillis();
        gameTimeSecs = (int)((currentTimeMillis - startTimeMillis)/1000f);
        tickRandomnessFactor = MathUtils.random(0.7f, 1.3f);

        actorHolder.updateAllActors(currentTimeMillis);

        if (currentTimeMillis - lastHPRegeneration > Constants.HP_REGEN_PLAYER_MILLIS) {
            actorHolder.getHero().updateHealthAdd(1);
            lastHPRegeneration = currentTimeMillis;
        }

        if (touchRequest) {
            actorHolder.spawnProjectile(actorHolder.getHero(), ActorType.HERO);
        }

        //// TODO: 08/05/16  it seems that level progression is not working anymore - investigate
        if (actorHolder.getActors().size <= 1) {
            updateOnNextLevel();
        }

        checkHeroColliding();
        heroPoints = actorHolder.getHeroPoints();
    }


    @Override
    public void dispose() {
        super.dispose();
    }

    private void updateOnNextLevel() {
        ++actualLevel;
        actorHolder.spawnLevel(actualLevel);
        actorHolder.getHero().updateHealthPercentage(50);
        for (int a = 1; a < actorHolder.getActors().size; a++) {
            actorHolder.getActors().get(a).updateVelocity(19 * actualLevel);
            actorHolder.getActors().get(a).updateRateOfFireInterval(6 * actualLevel);
        }
        actorHolder.getHero().updateRateOfFireInterval(2 * actualLevel);
    }

    private void checkHeroColliding() {
        for (int a = 1; a < actorHolder.getActors().size; a++) {

            if (actorHolder.checkCollision(heroActor, actorHolder.getActors().get(a))) {
                if (actorHolder.getActors().get(a) instanceof BonusItemActor) {
                    heroActor.upgradeWeapon();
                    ((BonusItemActor)actorHolder.getActors().get(a)).setDead();
                }
                else {
                    heroActor.updateHealthAdd(-2);
                }

            }
        }

    }


    public int getHeroPoints() { return heroPoints; }

    public int getActualLevel() { return actualLevel; }

    public long getGameTimeSecs() { return gameTimeSecs; }

}
