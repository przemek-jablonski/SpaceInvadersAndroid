package com.gamedesire.pszemek.recruitment.screens;


import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gamedesire.pszemek.recruitment.MainGameClass;
import com.gamedesire.pszemek.recruitment.input.TouchProcessor;
import com.gamedesire.pszemek.recruitment.input.TouchProcessorDesktop;
import com.gamedesire.pszemek.recruitment.input.TouchProcessorMobile;
import com.gamedesire.pszemek.recruitment.ui.DebugUI;
import com.gamedesire.pszemek.recruitment.utilities.Utilities;

/**
 * Created by Ciemek on 30/04/16.
 */
public class SpaceInvadersScreen implements Screen {

    private MainGameClass   mainGameClass;
    private TouchProcessor  touchProcessor;
    private Viewport        viewport;
    private DebugUI         debugUI;
    private OrthographicCamera camera;

    private Sprite heroSprite;
    private Sprite backgroundSprite;
    private ShapeRenderer backgroundGradient;



    public SpaceInvadersScreen(MainGameClass game) {

        mainGameClass = game;
        heroSprite = new Sprite(new Texture("game_character_hero.png"));
        backgroundSprite = new Sprite(new Texture("ui_bg_main_tile.png"));
        //todo: figure out why this camera shit is not working
//        camera = new OrthographicCamera();
//        viewport = new StretchViewport(Preferences.PREF_WIDTH, Preferences.PREF_HEIGHT, camera);
//        viewport = new FitViewport(Preferences.PREF_WIDTH, Preferences.PREF_HEIGHT, camera);

        Utilities.initialize();
        backgroundGradient = new ShapeRenderer();


        heroSprite.setCenter(Utilities.getScreenCenterWidth(), Utilities.getScreenCenterHeight());
		backgroundSprite.setPosition(10, Gdx.graphics.getHeight() - backgroundSprite.getHeight() - 10);

        debugUI = new DebugUI(game.getSpriteBatch());

        Gdx.input.setInputProcessor(getValidInputProcessor());
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

//        camera.update();
//        backgroundGradient.setProjectionMatrix(camera.combined);
//        mainGameClass.getSpriteBatch().setProjectionMatrix(camera.combined);

        //todo: fix this background colours shit
        backgroundGradient.begin(ShapeRenderer.ShapeType.Filled);
        backgroundGradient.rect(
                0,
                0,
                Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight(),
                Utilities.getColorFrom255(200, 0, 150, 1),
                Utilities.getColorFrom255(60, 0, 40, 1),
                Utilities.getColorFrom255(100, 0, 100, 1),
                Utilities.getColorFrom255(80, 0, 40, 1));
        backgroundGradient.end();


        mainGameClass.getSpriteBatch().begin();

        mainGameClass.getSpriteBatch().draw(backgroundSprite, backgroundSprite.getX(), backgroundSprite.getY());
        mainGameClass.getSpriteBatch().draw(heroSprite, heroSprite.getX(), heroSprite.getY());
        mainGameClass.getSpriteBatch().setProjectionMatrix(debugUI.getStage().getCamera().combined);

        mainGameClass.getSpriteBatch().end();

        debugUI.getStage().draw();
    }


    private TouchProcessor getValidInputProcessor() {
        if (Gdx.app.getType() == Application.ApplicationType.Android)
            touchProcessor = new TouchProcessorMobile(heroSprite);
        else
            touchProcessor = new TouchProcessorDesktop(heroSprite);
        return touchProcessor;
    }


    @Override
    public void resize(int width, int height) {
//        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
