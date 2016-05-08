package com.gamedesire.pszemek.recruitment.actors.primary;

import com.badlogic.gdx.math.Vector2;
import com.gamedesire.pszemek.recruitment.actors.archetypes.BonusItemActor;
import com.gamedesire.pszemek.recruitment.utilities.AssetRouting;
import com.gamedesire.pszemek.recruitment.utilities.Constants;

/**
 * Created by Ciemek on 01/05/16.
 */
public class SpaceDeerActor extends BonusItemActor {

    public SpaceDeerActor(float locationX, float locationY, Vector2 directionVector) {
        super(AssetRouting.getSpaceDeerSprite(), locationX, locationY, directionVector);
    }

    @Override
    public void create() {
        super.create();
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void onSpawn() {
        velocityValue = Constants.VELOCITY_VALUE_SPACE_DEER;
        super.onSpawn();
    }

    @Override
    public float onHit(float damageDealt) {
        return super.onHit(damageDealt);
    }

    @Override
    public void onDeath() {
        super.onDeath();
    }
}
