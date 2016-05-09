package com.gamedesire.pszemek.recruitment.screens;


import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.gamedesire.pszemek.recruitment.MainGameClass;
import com.gamedesire.pszemek.recruitment.mvc.models.AbstractSceneModel;
import com.gamedesire.pszemek.recruitment.mvc.models.SpaceInvadersSceneModel;
import com.gamedesire.pszemek.recruitment.mvc.views.AbstractSceneRenderer;
import com.gamedesire.pszemek.recruitment.mvc.views.SpaceInvadersSceneRenderer;
import com.gamedesire.pszemek.recruitment.mvc.controllers.AbstractTouchProcessor;
import com.gamedesire.pszemek.recruitment.mvc.controllers.TouchProcessorDesktop;
import com.gamedesire.pszemek.recruitment.mvc.controllers.TouchProcessorMobile;
import com.gamedesire.pszemek.recruitment.ui.AbstractBaseUI;
import com.gamedesire.pszemek.recruitment.ui.SpaceInvadersUI;
import com.gamedesire.pszemek.recruitment.utilities.Constants;
import com.gamedesire.pszemek.recruitment.utilities.GameStateEnum;

/**
 * Created by Ciemek on 30/04/16.
 *
 * Screen for game scene, which is basically  a container for MVC components.
 * Decouples model, rendering and input concerns.
 * Passes callbacks such as render, update, resize, dispose, etc. to specific classes.
 *
 */
public class SpaceInvadersScreen implements Screen {


    //model:
    private AbstractSceneModel      sceneModel;

    //view:
    private AbstractSceneRenderer   sceneRenderer;

    //view ui:
    private AbstractBaseUI          sceneUI;

    //controller:
    private AbstractTouchProcessor  touchProcessor;

    private MainGameClass           game;
    private boolean                 gameOverRequested;


    public SpaceInvadersScreen(MainGameClass game) {

        //model instantiation:
        sceneModel = new SpaceInvadersSceneModel();

        //view instantiation:
        sceneRenderer = new SpaceInvadersSceneRenderer(game.getSpriteBatch(), sceneModel.getActorHolder());

        //view ui instantiation:
        sceneUI = new SpaceInvadersUI(game.getSpriteBatch());

        //controller instantiation:
        instantiateInputProcessor();
        touchProcessor.registerModel(sceneModel);
        gameOverRequested = false;
        this.game = game;
    }


    @Override
    public void show() {
        //called once, on start
        sceneModel.create();
        sceneUI.create();

        touchProcessor.registerCamera(sceneRenderer.getCamera());
        touchProcessor.registerControlledActor(sceneModel.getActorHolder().getHero());
        Gdx.input.setInputProcessor(touchProcessor);
        Constants.updateCameraData(sceneRenderer.getCamera().viewportWidth, sceneRenderer.getCamera().viewportHeight);
    }



    @Override
    public void render(float deltaTime) {
        //logic
        sceneModel.update(deltaTime);

        //rendering
        sceneRenderer.render(deltaTime);

        //logic update for UI
        ((SpaceInvadersUI) sceneUI).updateUIData(
                ((SpaceInvadersSceneModel) sceneModel).getGameTimeSecs(),
                ((SpaceInvadersSceneModel) sceneModel).getHeroPoints(),
                ((SpaceInvadersSceneModel) sceneModel).getActualLevel(),
                sceneModel.getActorHolder().getHero().getActualHealthPoints());

        //rendering UI
        sceneUI.render(deltaTime);
        ((SpaceInvadersSceneRenderer) sceneRenderer).updateLevel(((SpaceInvadersSceneModel) sceneModel).getActualLevel());

        //listen on state change
        if(((SpaceInvadersSceneModel)sceneModel).isGameOver()) {
            game.gameState = GameStateEnum.GAME_OVER;
            game.stateChanged();
        }
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
