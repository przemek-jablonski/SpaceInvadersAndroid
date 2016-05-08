package com.gamedesire.pszemek.recruitment.actors.primary;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.gamedesire.pszemek.recruitment.actors.archetypes.EnemyActor;
import com.gamedesire.pszemek.recruitment.utilities.AssetRouting;
import com.gamedesire.pszemek.recruitment.utilities.Const;

/**
 * Created by Ciemek on 07/05/16.
 */
public class EnemyActor002 extends EnemyActor{



    public EnemyActor002(Vector2 location, Vector2 directionVector) {
        super(AssetRouting.getEnemy002Sprite(), location, directionVector);
    }

    public EnemyActor002(float locationX, float locationY, Vector2 directionVector) {
        this(new Vector2(locationX, locationY), directionVector);
    }

    public EnemyActor002(float locationX, float locationY, Vector2 directionVector, int velocity) {
        super(AssetRouting.getEnemy002Sprite(), locationX, locationY, directionVector, velocity);
    }

    @Override
    public void create() {
        super.create();
        velocityValue = Const.VELOCITY_VALUE_ENEMY_002;
        rateOfFireIntervalMillis = (long)(Const.RATEOFFIRE_INTERVAL_ENEMY002 * Const.RATEOFFIRE_RANDOM_OFFSET_HIGH_EASY);
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
        super.onSpawn();
        maxHealthPoints = Const.HP_AMOUNT_ENEMY002;
        maxShieldPoints = Const.SP_AMOUNT_ENEMY002;
        actualHealthPoints = maxHealthPoints;
        actualShieldPoints = maxShieldPoints;
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
