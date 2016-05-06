package com.gamedesire.pszemek.recruitment.actors.projectiles;

import com.badlogic.gdx.math.Vector2;
import com.gamedesire.pszemek.recruitment.actors.archetypes.ActorType;
import com.gamedesire.pszemek.recruitment.actors.archetypes.ProjectileActor;
import com.gamedesire.pszemek.recruitment.utilities.AssetRouting;
import com.gamedesire.pszemek.recruitment.utilities.Constants;


/**
 * Created by Ciemek on 03/05/16.
 */
public class BulletProjectileActor extends ProjectileActor {

    public BulletProjectileActor(Vector2 location, ActorType actorType) {
        this(location, Constants.VECTOR_DIRECTION_UP, actorType);
    }

    public BulletProjectileActor(Vector2 location, Vector2 direction, ActorType actorType) {
        super(AssetRouting.getProjectileRocketSprite(), location, direction, actorType);
    }

    @Override
    public void create() {
        if (getActorType() == ActorType.ENEMY) {
            actorSprite.flip(true, true);
        }
        velocityValue = Constants.VELOCITY_VALUE_PROJECTILE_BULLET;
        damageValue = 8;
    }

    @Override
    public void update() {
        updatePosition();
    }

    @Override
    public void dispose() {

    }


}
