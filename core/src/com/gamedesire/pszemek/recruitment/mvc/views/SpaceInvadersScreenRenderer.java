package com.gamedesire.pszemek.recruitment.mvc.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.gamedesire.pszemek.recruitment.actors.ActorHolder;
import com.gamedesire.pszemek.recruitment.ui.SpaceInvadersUI;

/**
 * Created by Ciemek on 08/05/16.
 */
public class SpaceInvadersScreenRenderer implements Disposable{

    private SpriteBatch         spriteBatch;
    private SpaceInvadersUI     sceneUI;
    private ActorHolder         actorHolder;

    public SpaceInvadersScreenRenderer(SpriteBatch spriteBatch, ActorHolder actorHolder) {
        this.spriteBatch = spriteBatch;
        this.actorHolder = actorHolder;
        create();
    }

    protected void create() {
        sceneUI = new SpaceInvadersUI(spriteBatch);
        sceneUI.create();
    }

    public void render() {
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

    @Override
    public void dispose() {
//        spriteBatch.dispose();
//        sceneUI.dispose();
    }
}
