package com.gamedesire.pszemek.recruitment.actors.primary;


import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.gamedesire.pszemek.recruitment.actors.archetypes.SpaceInvadersActor;
import com.gamedesire.pszemek.recruitment.actors.interfaces.IDamageable;
import com.gamedesire.pszemek.recruitment.actors.projectiles.ProjectileType;
import com.gamedesire.pszemek.recruitment.utilities.AssetRouting;
import com.gamedesire.pszemek.recruitment.utilities.Const;

/**
 * Created by Ciemek on 30/04/16.
 */
public class HeroActor extends SpaceInvadersActor implements IDamageable{


    protected boolean           dead;
    protected ProjectileType    weaponType;

    public HeroActor(Vector2 location, Vector2 direction) {
        super(AssetRouting.getHeroSprite(), location, direction);

    }

    public HeroActor(float locationX, float locationY, float directionX, float directionY) {
        this(new Vector2(locationX, locationY), new Vector2(directionX, directionY));
    }

    @Override
    public void create() {
        rateOfFireIntervalMillis = Const.RATEOFFIRE_INTERVAL_PLAYER_BASE;
        velocityValue = Const.VELOCITY_VALUE_PLAYER;
        onSpawn();
    }

    @Override
    public void update() {
        if (actualHealthPoints <= 0)
            if (!dead)
                onDeath();

        updatePosition();
    }

    @Override
    public void dispose() {

    }

    @Override
    public void onSpawn() {
        System.err.println("HERO SPAWNED");
        maxHealthPoints = Const.HP_AMOUNT_PLAYER;
        maxShieldPoints = 0;
        actualHealthPoints = maxHealthPoints;
        actualShieldPoints = maxShieldPoints;
        weaponType = ProjectileType.ROCKET;
        dead = false;
    }

    @Override
    public float onHit(float damageDealt) {
        actualShieldPoints -= damageDealt;

        if (actualShieldPoints <= 0) {
            actualHealthPoints += actualShieldPoints;
            actualShieldPoints = 0;
            if(actualHealthPoints <= 0)
                actualHealthPoints = 0;
        }

        return 0;
    }

    @Override
    public void onDeath() {
        dead = true;
    }

    public void updateHealthPercentage(int percentage) {
        if (actualHealthPoints <= 0)
            return;

        float percent = percentage / 100f;
        if (actualHealthPoints * (1 + percent) >= maxHealthPoints)
            actualHealthPoints = maxHealthPoints;
        else
            actualHealthPoints += actualHealthPoints * percent;
    }

    public void updateHealthAdd(int value) {
        if (actualHealthPoints <= 0)
            return;

        if (actualHealthPoints + value >= maxHealthPoints)
            actualHealthPoints = maxHealthPoints;
        else
            actualHealthPoints += value;
    }

    public void upgradeWeapon() {
        System.err.println("UPGRADING WEAPON");
        if (weaponType == ProjectileType.ROCKET)
            weaponType = ProjectileType.BOLT;
        else
            rateOfFireIntervalMillis /= Const.PLAYER_RATEOFFIRE_UPGRADE_PERCENT;
    }

    public ProjectileType getWeaponType() {
        return weaponType;
    }
}
