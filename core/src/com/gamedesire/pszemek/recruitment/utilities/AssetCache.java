package com.gamedesire.pszemek.recruitment.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Ciemek on 01/05/16.
 */
public class AssetCache {

    private static final String HERO_ASSET = "game_character_hero";
    private static final String SPACE_DEER = "game_character_special_spacedeer";
    private static final String ENEMY_ASSET_001 = "game_character_enemy_001";
    private static final String ENEMY_ASSET_002 = "game_character_enemy_002";
    private static final String ENEMY_ASSET_003 = "game_character_enemy_003";
    private static final String ENEMY_ASSET_004 = "game_character_enemy_004";
    private static final String BACKGROUND_ASSET = "ui_bg_main_tile";
    private static final String PNG_FORMAT = ".png";
    private static final String JPG_FORMAT = ".jpg";
    private static final String OGG_FORMAT = ".ogg";
    private static final String MP3_FORMAT = ".mp3";


    public static final Sprite getHeroSprite() {return new Sprite(getHeroTexture());}

    public static final Sprite getBackgroundSprite() {return new Sprite(getBackgroundTexture());}

    public static final Sprite getEnemy001Sprite() {return new Sprite(getEnemy001Texture());}

    public static final Sprite getEnemy002Sprite() {return new Sprite(getEnemy002Texture());}

    public static final Sprite getEnemy003Sprite() {return new Sprite(getEnemy003Texture());}

    public static final Sprite getEnemy004Sprite() {return new Sprite(getEnemy004Texture());}

    public static final Texture getHeroTexture() {return new Texture(getAssetFullLocation(HERO_ASSET, PNG_FORMAT));}

    public static final Texture getBackgroundTexture() {return new Texture(getAssetFullLocation(BACKGROUND_ASSET, PNG_FORMAT));}

    public static final Texture getEnemy001Texture() {return new Texture(getAssetFullLocation(ENEMY_ASSET_001, PNG_FORMAT));}

    public static final Texture getEnemy002Texture() {return new Texture(getAssetFullLocation(ENEMY_ASSET_002, PNG_FORMAT));}

    public static final Texture getEnemy003Texture() {return new Texture(getAssetFullLocation(ENEMY_ASSET_003, PNG_FORMAT));}

    public static final Texture getEnemy004Texture() {return new Texture(getAssetFullLocation(ENEMY_ASSET_004, PNG_FORMAT));}

    private static final FileHandle getAssetFullLocation(final String location, final String format) {
        return Gdx.files.internal(location + format);
    }

}
