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

/**
 * Created by Ciemek on 30/04/16.
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
    }


    @Override
    public void show() {
        sceneModel.create();
        sceneUI.create();

        touchProcessor.registerCamera(sceneRenderer.getCamera());
        touchProcessor.registerControlledActor(sceneModel.getActorHolder().getHero());
        Gdx.input.setInputProcessor(touchProcessor);

        Constants.updateCameraData(sceneRenderer.getCamera().viewportWidth, sceneRenderer.getCamera().viewportHeight);
    }



    @Override
    public void render(float deltaTime) {
        sceneModel.update(deltaTime);
        sceneRenderer.render(deltaTime);

        ((SpaceInvadersUI) sceneUI).updateUIData(
                ((SpaceInvadersSceneModel) sceneModel).getGameTimeSecs(),
                ((SpaceInvadersSceneModel) sceneModel).getHeroPoints(),
                ((SpaceInvadersSceneModel) sceneModel).getActualLevel(),
                sceneModel.getActorHolder().getHero().getActualHealthPoints());

        sceneUI.render(deltaTime);
        ((SpaceInvadersSceneRenderer) sceneRenderer).updateLevel(((SpaceInvadersSceneModel) sceneModel).getActualLevel());
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
