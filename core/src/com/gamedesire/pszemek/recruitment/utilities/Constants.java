package com.gamedesire.pszemek.recruitment.utilities;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Ciemek on 30/04/16.
 */
public class Constants {

    public static final int PREF_WIDTH = 540;
    public static final int PREF_HEIGHT = 960;

    public static final Vector2 VECTOR_DIRECTION_DOWN = new Vector2(0, -1);
    public static final Vector2 VECTOR_DIRECTION_DOWN_RIGHTBOTTOM = new Vector2(1, -1);
    public static final Vector2 VECTOR_DIRECTION_DOWN_LEFTBOTTOM = new Vector2(-1, -1);
    public static final Vector2 VECTOR_DIRECTION_UP = new Vector2(0, 1);
    public static final Vector2 VECTOR_DIRECTION_STOP = Vector2.Zero;

    public static final int SPAWN_MARGIN_HORIZONTAL_SMALL = 4; //px in PREF_ dimensions
    public static final int SPAWN_MARGIN_HORIZONTAL_STANDARD = 10; //px in PREF_ dimensions
    public static final int SPAWN_MARGIN_HORIZONTAL_HUGE = 24; //px in PREF_ dimensions

    public static final int VELOCITY_VALUE_PLAYER = 1200;
    public static final int VELOCITY_VALUE_ENEMY_SLOW = 50;
    public static final int VELOCITY_VALUE_PROJECTILE_BULLET = 400;

    public static final int RATEOFFIRE_INTERVAL_PLAYER_BASE = 400;
    public static final int RATEOFFIRE_INTERVAL_ENEMY001_BASE = 2900;
    public static final int TIMEINTERVAL_SECOND_INMILLIS = 1000;


    public static final float RATEOFFIRE_RANDOM_OFFSET_LOW = MathUtils.random(0.95f, 1.05f);
    public static final float RATEOFFIRE_RANDOM_OFFSET_STANDARD = MathUtils.random(0.85f, 1.25f);
    public static final float RATEOFFIRE_RANDOM_OFFSET_HIGH = MathUtils.random(0.70f, 1.60f);
    public static final float RATEOFFIRE_RANDOM_OFFSET_LOW_EASY = MathUtils.random(0.95f, 1.15f);
    public static final float RATEOFFIRE_RANDOM_OFFSET_STANDARD_EASY = MathUtils.random(0.90f, 1.50f);
    public static final float RATEOFFIRE_RANDOM_OFFSET_HIGH_EASY = MathUtils.random(0.85f, 2.00f);

    public static final float RATEOFFIRE_RANDOM_BINARY = MathUtils.random(0f, 1f);

    public static final int BASE_POINTS_FOR_ENEMY = 50;
}
