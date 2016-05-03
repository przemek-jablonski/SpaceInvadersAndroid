package com.gamedesire.pszemek.recruitment.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Ciemek on 29/04/16.
 */

/**
 * Utils:
 *  Bunch of convenience methods for keeping rest of the code cleaner and easier to read.
 *  Includes methods for getting center points of the screen, converting two vector
 *  difference into direction, creating color in format (255, 255, 255, 1) etc.
 *
 */
public class Utils {

    //todo: this all is kinda stupid, consider throwing out to a bin
    private static int SCREEN_WIDTH;
    private static int SCREEN_HEIGHT;


    //todo: converter from libgdx screen coordinate system to actual good coordinates


    public static void initialize() {
        SCREEN_WIDTH = Gdx.graphics.getWidth();
        SCREEN_HEIGHT = Gdx.graphics.getHeight();
    }

    public static int getScreenCenterWidth() {
        return SCREEN_WIDTH/2;
    }

    public static int getScreenCenterHeight() {
        return SCREEN_HEIGHT/2;
    }

    //todo: fix this, it's not good i guess
    public static Color getColorFrom255(int red, int green, int blue, int alpha) {
        return new Color(MathUtils.lerp(0, 255, red), MathUtils.lerp(0, 255, green), MathUtils.lerp(0, 255, blue), MathUtils.lerp(0, 1, alpha));
    }

    public static Vector2 getCenterPosition(Sprite sprite){
        return new Vector2(sprite.getX() + (sprite.getWidth()/2), sprite.getY() + (sprite.getHeight()/2));
    }

    public static Vector2 transformVectorToDirection(Vector2 vector) {
        return transformVectorToDirection(vector.x, vector.y);
    }

    public static Vector2 transformVectorToDirection(float x, float y) {
        return new Vector2(transformValueToDirection(x), transformValueToDirection(y));
    }

    //this transformation doesn't make any sense, but works in a game
    private static float transformValueToDirection(float value) {
        if (value > 0) return -1;
        if (value < 0) return +1;
        return value;
    }

    private static float transformValueToDirection(float value, float margin) {
        if (value > margin) return -1;
        if (value < -margin) return +1;
        return 0;
    }

}
