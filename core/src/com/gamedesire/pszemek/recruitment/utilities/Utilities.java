package com.gamedesire.pszemek.recruitment.utilities;

import com.badlogic.gdx.Gdx;

/**
 * Created by Ciemek on 29/04/16.
 */
public class Utilities {

    //todo: this all is kinda stupid, consider throwing out to a bin
    private static int SCREEN_WIDTH;
    private static int SCREEN_HEIGHT;

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


}
