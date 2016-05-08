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
import com.gamedesire.pszemek.recruitment.mvc.controllers.AbstractTouchProcessor;
import com.gamedesire.pszemek.recruitment.mvc.controllers.TouchProcessorDesktop;
import com.gamedesire.pszemek.recruitment.mvc.controllers.TouchProcessorMobile;
import com.gamedesire.pszemek.recruitment.ui.SpaceInvadersUI;

/**
 * Created by Ciemek on 30/04/16.
 */
public class SpaceInvadersScreen implements Screen {


    //model:
    private SpaceInvadersSceneModel sceneModel;

    //view:
    private SpaceInvadersScreenRenderer sceneRenderer;

    //controller:
    private AbstractTouchProcessor  touchProcessor;


    private SpriteBatch spriteBatch;


    public SpaceInvadersScreen(MainGameClass game) {
        spriteBatch = game.getSpriteBatch();

        sceneModel = new SpaceInvadersSceneModel();
        sceneRenderer = new SpaceInvadersScreenRenderer(spriteBatch, sceneModel.getActorHolder());
        instantiateInputProcessor();
        touchProcessor.registerModel(sceneModel);
    }


    @Override
    public void show() {
        sceneModel.create();

        touchProcessor.registerCamera(sceneRenderer.getCamera());
        touchProcessor.registerControlledActor(sceneModel.getActorHolder().getHero());
        Gdx.input.setInputProcessor(touchProcessor);
    }



    @Override
    public void render(float deltaTime) {

        sceneModel.update(deltaTime);
        sceneRenderer.render(deltaTime);


        //// TODO: 08/05/16 maybe UI must be rendered OUT OF SPRITE BATCH BOUNDARIES (after end)?
//        spaceInvadersUI.updateHeroPoints(actorHolder.getHeroPoints());
//        spaceInvadersUI.updateLevel(actorHolder.getActualLevel());
//        spaceInvadersUI.updateHP(actorHolder.getHero().getActualHealthPoints());
//        spaceInvadersUI.updateSP(actorHolder.getHero().getActualShieldPoints());

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

//        spriteBatch.draw(backgroundSprite.getTexture(), 0f, 0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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
        //// TODO: 08/05/16 check if now everything works on different devices
//        viewport.update(width, height);
        sceneRenderer.resize(width, height);
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
        sceneModel.dispose();
        sceneRenderer.dispose();
    }
}
