package com.gamedesire.pszemek.recruitment.utilities;

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

    public static final int RATEOFFIRE_INTERVAL_BASE_PLAYER = 400;
    public static final int RATEOFFIRE_INTERVAL_BASE_ENEMY001 = 1000;



}
