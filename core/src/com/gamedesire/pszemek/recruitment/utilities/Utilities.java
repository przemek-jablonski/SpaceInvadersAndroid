package com.gamedesire.pszemek.recruitment.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by Ciemek on 29/04/16.
 */
public class Utilities {

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

    public static Color getColorFrom255(int red, int green, int blue, int alpha) {
        return new Color(MathUtils.lerp(0, 255, red), MathUtils.lerp(0, 255, green), MathUtils.lerp(0, 255, blue), MathUtils.lerp(0, 1, alpha));
    }


}
