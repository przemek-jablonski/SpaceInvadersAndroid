package com.gamedesire.pszemek.recruitment.mvc.models;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;
import com.gamedesire.pszemek.recruitment.actors.ActorHolder;
import com.gamedesire.pszemek.recruitment.actors.archetypes.ActorType;

/**
 * Created by Ciemek on 08/05/16.
 */
public class SpaceInvadersSceneModel implements Disposable{

    private ActorHolder actorHolder;
    private long        startTimeMillis;
    private long        gameTimeMillis;
    private long        currentTimeMillis;
    private float       tickRandomnessFactor;
    private int         actualLevel;
    private int         heroPoints;
    private boolean     levelCleared;


    private boolean touchRequest;


    public SpaceInvadersSceneModel(ActorHolder actorHolder) {
        this.actorHolder = actorHolder;
        touchRequest = false;
        create();
    }

    protected void create() {
        startTimeMillis = System.currentTimeMillis();
        gameTimeMillis = 0;
        actualLevel = 0;
        heroPoints = 0;
    }

    public void update(float deltaTime) {
        gameTimeMillis = System.currentTimeMillis();
        tickRandomnessFactor = MathUtils.random(0.7f, 1.3f);

        actorHolder.updateAll(deltaTime, gameTimeMillis);

//        if(!debugTestOne) {
//            actorHolder.spawnLevel3();
//            debugTestOne = true;
//        }

        if (touchRequest) {
            actorHolder.spawnProjectile(actorHolder.getHero(), ActorType.HERO);
        }

        //// TODO: 08/05/16  it seems that level progression is not working anymore - investigate
        if (actorHolder.isLevelCleared()) {
            ++actualLevel;
            actorHolder.spawnLevel(actualLevel);
            actorHolder.setLevelCleared(false);
        }




    }


    @Override
    public void dispose() {

    }


    public void switchTouchRequest() {
        touchRequest = (!touchRequest);
    }

    public boolean getTouchRequest() {
        return touchRequest;
    }

}
