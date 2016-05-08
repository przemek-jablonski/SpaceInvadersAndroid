package com.gamedesire.pszemek.recruitment.screens;


import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gamedesire.pszemek.recruitment.MainGameClass;
import com.gamedesire.pszemek.recruitment.mvc.models.SpaceInvadersSceneModel;
import com.gamedesire.pszemek.recruitment.mvc.views.SpaceInvadersScreenRenderer;
import com.gamedesire.pszemek.recruitment.utilities.AssetRouting;
import com.gamedesire.pszemek.recruitment.utilities.Const;
import com.gamedesire.pszemek.recruitment.actors.ActorHolder;
import com.gamedesire.pszemek.recruitment.mvc.controllers.AbstractTouchProcessor;
import com.gamedesire.pszemek.recruitment.mvc.controllers.TouchProcessorDesktop;
import com.gamedesire.pszemek.recruitment.mvc.controllers.TouchProcessorMobile;
import com.gamedesire.pszemek.recruitment.ui.SpaceInvadersUI;

/**
 * Created by Ciemek on 30/04/16.
 */
public class SpaceInvadersScreen implements Screen {

    ActorHolder actorHolder;

    private SpriteBatch spriteBatch;

    private AbstractTouchProcessor  touchProcessor;
    private Viewport                viewport;
    private SpaceInvadersUI         spaceInvadersUI;
    private OrthographicCamera      camera;

    private Sprite          backgroundSprite;
    private ShapeRenderer   backgroundGradient;


    //view:
    private SpaceInvadersScreenRenderer sceneRenderer;

    //model:
    private SpaceInvadersSceneModel sceneModel;


    Texture vlt = AssetRouting.getVignetteLTTexture();
    Texture vlb = AssetRouting.getVignetteLBTexture();
    Texture vrt = AssetRouting.getVignetteRTTexture();
    Texture vrb = AssetRouting.getVignetteRBTexture();



    public SpaceInvadersScreen(MainGameClass game) {
        actorHolder = new ActorHolder();
        spriteBatch = game.getSpriteBatch();

        sceneRenderer = new SpaceInvadersScreenRenderer(spriteBatch, actorHolder);
        sceneModel = new SpaceInvadersSceneModel(actorHolder);
        instantiateInputProcessor();
        touchProcessor.registerModel(sceneModel);
    }


    @Override
    public void show() {

        actorHolder.spawnHero();

        backgroundSprite = new Sprite(new Texture("ui_bg_main_tile.png"));


        camera = new OrthographicCamera();

        viewport = new ScalingViewport(Scaling.stretch, Const.PREF_WIDTH, Const.PREF_HEIGHT, camera);
        viewport.apply();
        camera.translate(camera.viewportWidth / 2, camera.viewportHeight / 2);

        backgroundGradient = new ShapeRenderer();
        backgroundSprite.setPosition(10, Gdx.graphics.getHeight() - backgroundSprite.getHeight() - 10);
        spaceInvadersUI = new SpaceInvadersUI(spriteBatch);
        spaceInvadersUI.create();


        touchProcessor.registerCamera(camera);
        touchProcessor.registerControlledActor(actorHolder.getHero());
        Gdx.input.setInputProcessor(touchProcessor);

    }



    @Override
    public void render(float delta) {
//        if (actorHolder.getActualActorsSize() <= 1) {
//            actorHolder.spawnLevel();
//        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

//        spaceInvadersUI.updateHeroPoints(actorHolder.getHeroPoints());
//        spaceInvadersUI.updateLevel(actorHolder.getActualLevel());
//        spaceInvadersUI.updateHP(actorHolder.getHero().getActualHealthPoints());
//        spaceInvadersUI.updateSP(actorHolder.getHero().getActualShieldPoints());


        spriteBatch.begin();
        setRenderBackground();

        spriteBatch.setProjectionMatrix(spaceInvadersUI.getStage().getCamera().combined);

//        actorHolder.updateAll();

        sceneModel.update(delta);

        actorHolder.renderAll(spriteBatch);

        Color col = spriteBatch.getColor();
        spriteBatch.setColor(new Color(0f, 0f, 0f, 0.5f));

        spriteBatch.draw(vlb, 0f, 0f, vlb.getWidth(), vlb.getHeight());
        spriteBatch.draw(vlt, 0f, Gdx.graphics.getHeight() - vlt.getHeight(), vlt.getWidth(), vlt.getHeight());
        spriteBatch.draw(vrt, Gdx.graphics.getWidth() - vrt.getWidth(), Gdx.graphics.getHeight() - vlt.getHeight(), vlt.getWidth(), vlt.getHeight());
        spriteBatch.draw(vrb, Gdx.graphics.getWidth() - vrt.getWidth(), 0f, vlt.getWidth(), vlt.getHeight());

        spriteBatch.setColor(col);


        sceneRenderer.render();
        spriteBatch.end();


        spaceInvadersUI.update();
        spaceInvadersUI.render();
        spaceInvadersUI.getStage().draw();
    }

    private void setRenderBackground() {
        //      todo: figure out why setting projection matrix destroys gradient (???)
//        backgroundGradient.setProjectionMatrix(camera.combined);
//
//        mainGameClass.getSpriteBatch().setProjectionMatrix(camera.combined);
//        backgroundGradient.begin(ShapeRenderer.ShapeType.Filled);
//        backgroundGradient.rect(
//                0,
//                0,
//                Gdx.graphics.getWidth(),
//                Gdx.graphics.getHeight(),
//                Utils.getColorFrom255(200, 0, 150, 1),
//                Utils.getColorFrom255(60, 0, 40, 1),
//                Utils.getColorFrom255(100, 0, 100, 1),
//                Utils.getColorFrom255(80, 0, 40, 1));
//        backgroundGradient.end();

        spriteBatch.draw(backgroundSprite.getTexture(), 0f, 0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }


    private AbstractTouchProcessor instantiateInputProcessor() {
        if (Gdx.app.getType() == Application.ApplicationType.Android)
            touchProcessor = new TouchProcessorMobile();
        else
            touchProcessor = new TouchProcessorDesktop();
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

    public Viewport getViewport() {
        return viewport;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
}
