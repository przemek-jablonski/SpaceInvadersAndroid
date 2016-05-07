package com.gamedesire.pszemek.recruitment.actors.primary;

import com.badlogic.gdx.math.Vector2;
import com.gamedesire.pszemek.recruitment.actors.archetypes.EnemyActor;
import com.gamedesire.pszemek.recruitment.utilities.AssetRouting;
import com.gamedesire.pszemek.recruitment.utilities.Const;

/**
 * Created by Ciemek on 30/04/16.
 */
public class EnemyActor001 extends EnemyActor {


    public EnemyActor001(Vector2 location, Vector2 direction) {
        super(AssetRouting.getEnemy001Sprite(), location, direction);
    }

    public EnemyActor001(float locationX, float locationY, Vector2 direction) {
        this(new Vector2(locationX, locationY), direction);
    }



    @Override
    public void create() {
        super.create();
        velocityValue = Const.VELOCITY_VALUE_ENEMY_001;
        rateOfFireIntervalMillis = (long)(Const.RATEOFFIRE_INTERVAL_ENEMY001 * Const.RATEOFFIRE_RANDOM_OFFSET_HIGH_EASY);
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
        maxHealthPoints = Const.HP_AMOUNT_ENEMY001;
        maxShieldPoints = Const.SP_AMOUNT_ENEMY001;
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
