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
 */
public class Utils {




    public static Vector2 getCenterPosition(Sprite sprite){
        return new Vector2(sprite.getX() + (sprite.getWidth()/2), sprite.getY() + (sprite.getHeight()/2));
    }

    public static Vector2 transformVectorToDirection(Vector2 vector) {
        return transformVectorToDirection(vector.x, vector.y);
    }

    public static Vector2 transformVectorToDirection(float x, float y) {
        return new Vector2(transformValueToDirection(x), transformValueToDirection(y));
    }

    public static Vector2 transformVectorToDirection(Vector2 vector, float margin) {
        return transformVectorToDirection(vector.x, vector.y, margin);
    }

    public static Vector2 transformVectorToDirection(float x, float y, float margin) {
        return new Vector2(transformValueToDirection(x, margin), transformValueToDirection(y, margin));
    }

    //todo: not lerping properly, need fix
    public static Color getColorFrom255(int red, int green, int blue, float alpha) {
        return new Color(MathUtils.lerp(0, 255, red), MathUtils.lerp(0, 255, green), MathUtils.lerp(0, 255, blue), MathUtils.lerp(0, 1, alpha));
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
