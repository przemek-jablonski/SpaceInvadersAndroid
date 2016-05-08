package com.gamedesire.pszemek.recruitment.mvc.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gamedesire.pszemek.recruitment.actors.ActorHolder;
import com.gamedesire.pszemek.recruitment.ui.SpaceInvadersUI;
import com.gamedesire.pszemek.recruitment.utilities.AssetRouting;
import com.gamedesire.pszemek.recruitment.utilities.Const;

/**
 * Created by Ciemek on 08/05/16.
 */
public class SpaceInvadersScreenRenderer implements Disposable{

    private SpriteBatch         spriteBatch;
    private SpaceInvadersUI     sceneUI;
    private ActorHolder         actorHolder;

    //// TODO: 08/05/16 CHECK IF THIS SHIT WORKS FOR DIFFERENT DEVICES NOW (konrad?)
    private Camera              camera;
    private Viewport            viewport;

    Texture vlt = AssetRouting.getVignetteLTTexture();
    Texture vlb = AssetRouting.getVignetteLBTexture();
    Texture vrt = AssetRouting.getVignetteRTTexture();
    Texture vrb = AssetRouting.getVignetteRBTexture();


    public SpaceInvadersScreenRenderer(SpriteBatch spriteBatch, ActorHolder actorHolder) {
        this.spriteBatch = spriteBatch;
        this.actorHolder = actorHolder;
        create();
    }

    protected void create() {
        sceneUI = new SpaceInvadersUI(spriteBatch);
        sceneUI.create();


        camera = new OrthographicCamera();
        viewport = new ScalingViewport(Scaling.stretch, Const.PREF_WIDTH, Const.PREF_HEIGHT, camera);
        viewport.apply();
        ((OrthographicCamera) camera).translate(
                camera.viewportWidth / 2,
                camera.viewportHeight / 2);
    }

    public void render(float deltaTime) {
        camera.update();

//        sceneModel.getActorHolder().renderAll(spriteBatch);
        actorHolder.renderAll(spriteBatch);
        renderLayerVignettes();
//        Gdx.gl.glClearColor(1, 0, 0, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        spriteBatch.end();
//        spriteBatch.begin();
//        spriteBatch.setProjectionMatrix(sceneUI.getStage().getCamera().combined);
//
//        updateUI();
//        renderUI();
//
//        spriteBatch.end();

    }

    private void renderLayerVignettes() {
        Color col = spriteBatch.getColor();
        spriteBatch.setColor(new Color(0f, 0f, 0f, 0.5f));
        spriteBatch.draw(vlb, 0f, 0f, vlb.getWidth(), vlb.getHeight());
        spriteBatch.draw(vlt, 0f, Gdx.graphics.getHeight() - vlt.getHeight(), vlt.getWidth(), vlt.getHeight());
        spriteBatch.draw(vrt, Gdx.graphics.getWidth() - vrt.getWidth(), Gdx.graphics.getHeight() - vlt.getHeight(), vlt.getWidth(), vlt.getHeight());
        spriteBatch.draw(vrb, Gdx.graphics.getWidth() - vrt.getWidth(), 0f, vlt.getWidth(), vlt.getHeight());
        spriteBatch.setColor(col);
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    private void updateUI() {
//        sceneUI.updateHeroPoints(actorHolder.getHeroPoints());
//        sceneUI.updateLevel(actorHolder.getActualLevel());
        sceneUI.updateHP(actorHolder.getHero().getActualHealthPoints());
        sceneUI.updateSP(actorHolder.getHero().getActualShieldPoints());
    }

    private void renderUI() {
        sceneUI.update();
        sceneUI.render();
        sceneUI.getStage().draw();
    }

    public Camera getCamera() {
        return camera;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }

    @Override
    public void dispose() {
//        spriteBatch.dispose();
//        sceneUI.dispose();
    }
}
