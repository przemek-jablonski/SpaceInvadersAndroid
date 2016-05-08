package com.gamedesire.pszemek.recruitment.mvc.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.gamedesire.pszemek.recruitment.actors.ActorHolder;
import com.gamedesire.pszemek.recruitment.actors.archetypes.SpaceInvadersActor;
import com.gamedesire.pszemek.recruitment.ui.SpaceInvadersUI;
import com.gamedesire.pszemek.recruitment.utilities.AssetRouting;
import com.gamedesire.pszemek.recruitment.utilities.Const;

/**
 * Created by Ciemek on 08/05/16.
 */
public class SpaceInvadersSceneRenderer extends AbstractSceneRenderer {

//    private SpriteBatch         spriteBatch;
    private SpaceInvadersUI     sceneUI;
    private ActorHolder         actorHolder;

    //// TODO: 08/05/16 CHECK IF THIS SHIT WORKS FOR DIFFERENT DEVICES NOW (konrad?)
//    private Camera              camera;
//    private Viewport            viewport;

    private Texture vignetteLeftTop = AssetRouting.getVignetteLTTexture();
    private Texture vignetteLeftBottom = AssetRouting.getVignetteLBTexture();
    private Texture vignetteRightTop = AssetRouting.getVignetteRTTexture();
    private Texture vignetteRightBottom = AssetRouting.getVignetteRBTexture();

    private Sprite backgroundSprite;


    public SpaceInvadersSceneRenderer(SpriteBatch spriteBatch, ActorHolder actorHolder) {
        super(spriteBatch);
        this.actorHolder = actorHolder;
        create();
    }

    @Override
    protected void create() {
        spriteBatch.enableBlending();
        sceneUI = new SpaceInvadersUI(spriteBatch);
        sceneUI.create();


        camera = new OrthographicCamera();
        viewport = new ScalingViewport(Scaling.stretch, Const.PREF_WIDTH, Const.PREF_HEIGHT, camera);
        viewport.apply();
        ((OrthographicCamera) camera).translate(
                camera.viewportWidth / 2,
                camera.viewportHeight / 2);

        vignetteLeftTop = AssetRouting.getVignetteLTTexture();
        vignetteLeftBottom = AssetRouting.getVignetteLBTexture();
        vignetteRightTop = AssetRouting.getVignetteRTTexture();
        vignetteRightBottom = AssetRouting.getVignetteRBTexture();
        backgroundSprite = AssetRouting.getBackgroundSprite();

    }

    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

//        updateUI();

        spriteBatch.begin();

        spriteBatch.setProjectionMatrix(sceneUI.getStage().getCamera().combined);

        camera.update();

        renderLayerBackgroundGradient();
        renderLayerBackgroundSprite();
//        renderLayerAllActors();
        renderLayerActors();
        renderLayerProjectiles();
        renderLayerHero();
        renderLayerVignettes();

        spriteBatch.end();

//
//        sceneUI.update();
//        sceneUI.render(deltaTime);
//        sceneUI.getStage().draw();
    }


    private void renderLayerBackgroundSprite() {
        spriteBatch.draw(backgroundSprite.getTexture(), 0f, 0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    private void renderLayerBackgroundGradient() {
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

//    private void renderLayerAllActors() {
//        actorHolder.renderAll(spriteBatch);
//    }

    private void renderLayerActors() {
        for (int a = 1; a < actorHolder.getActors().size; ++a) {
//            actorHolder.getActors().get(a).render(spriteBatch);
            renderActor(actorHolder.getActors().get(a));
        }
    }

    private void renderLayerProjectiles() {
        for (int p = 1; p < actorHolder.getProjectiles().size; ++p) {
//            actorHolder.getProjectiles().get(p).render(spriteBatch);
            renderActor(actorHolder.getProjectiles().get(p));
        }
    }

    private void renderLayerHero() {
//        actorHolder.getHero().render(spriteBatch);
        renderActor(actorHolder.getHero());
    }


    private void renderLayerVignettes() {
        Color col = spriteBatch.getColor();
        spriteBatch.setColor(new Color(0f, 0f, 0f, 0.5f));
        spriteBatch.draw(vignetteLeftBottom, 0f, 0f, vignetteLeftBottom.getWidth(), vignetteLeftBottom.getHeight());
        spriteBatch.draw(vignetteLeftTop, 0f, Gdx.graphics.getHeight() - vignetteLeftTop.getHeight(), vignetteLeftTop.getWidth(), vignetteLeftTop.getHeight());
        spriteBatch.draw(vignetteRightTop, Gdx.graphics.getWidth() - vignetteRightTop.getWidth(), Gdx.graphics.getHeight() - vignetteLeftTop.getHeight(), vignetteLeftTop.getWidth(), vignetteLeftTop.getHeight());
        spriteBatch.draw(vignetteRightBottom, Gdx.graphics.getWidth() - vignetteRightTop.getWidth(), 0f, vignetteLeftTop.getWidth(), vignetteLeftTop.getHeight());
        spriteBatch.setColor(col);
    }

    private void renderActor(SpaceInvadersActor actor) {
        spriteBatch.draw(
                actor.getActorSprite().getTexture(),
                actor.getActorSprite().getX(),
                actor.getActorSprite().getY());
    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    private void updateUI() {
//        sceneUI.updateHeroPoints(actorHolder.getHeroPoints());
//        sceneUI.updateLevel(actorHolder.getActualLevel());
//        sceneUI.updateHP(actorHolder.getHero().getActualHealthPoints());
//        sceneUI.updateSP(actorHolder.getHero().getActualShieldPoints());
    }

    private void renderUI(float deltaTime) {
        sceneUI.update();
        sceneUI.render(deltaTime);
        sceneUI.getStage().draw();
    }

    @Override
    public void dispose() {
        super.dispose();
        sceneUI.dispose();
    }
}
