package com.gamedesire.pszemek.recruitment.mvc.models;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;
import com.gamedesire.pszemek.recruitment.actors.ActorHolder;
import com.gamedesire.pszemek.recruitment.actors.archetypes.ActorType;

/**
 * Created by Ciemek on 08/05/16.
 */
public class SpaceInvadersSceneModel extends AbstractSceneModel {


    private long        startTimeMillis;
    private long        gameTimeMillis;
    private long        currentTimeMillis;
    private float       tickRandomnessFactor;
    private int         actualLevel;
    private int         heroPoints;
    private boolean     levelCleared;
//    private boolean     touchRequest;


    public SpaceInvadersSceneModel() {
        super();
    }

    @Override
    public void create() {
        startTimeMillis = System.currentTimeMillis();
        gameTimeMillis = 0;
        actualLevel = 0;
        heroPoints = 0;
        actorHolder.spawnHero();
        touchRequest = false;
    }

    @Override
    public void update(float deltaTime) {
        currentTimeMillis = System.currentTimeMillis();
        tickRandomnessFactor = MathUtils.random(0.7f, 1.3f);

        actorHolder.updateAll(
                deltaTime,
                currentTimeMillis);


        if (touchRequest) {
            actorHolder.spawnProjectile(actorHolder.getHero(), ActorType.HERO);
        }

        //// TODO: 08/05/16  it seems that level progression is not working anymore - investigate
        if (actorHolder.getActors().size <= 1) {
            ++actualLevel;
            actorHolder.spawnLevel(actualLevel);
        }
    }
    

    @Override
    public void dispose() {
        super.dispose();
    }
}
