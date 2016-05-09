package com.gamedesire.pszemek.recruitment.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.gamedesire.pszemek.recruitment.MainGameClass;
import com.gamedesire.pszemek.recruitment.ui.GameOverUI;
import com.gamedesire.pszemek.recruitment.utilities.GameStateEnum;

/**
 * Created by Ciemek on 09/05/16.
 *
 * Screen for GameOver screen. Rendering and handling events for single UI element.
 */
public class GameOverScreen implements Screen{

    private GameOverUI      sceneUI;
    private MainGameClass   game;

    public GameOverScreen(MainGameClass game) {
        this.game = game;
        sceneUI = new GameOverUI(game.getSpriteBatch());
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(sceneUI.isRestartRequested()) {
            game.gameState = GameStateEnum.SPACE_INVADERS;
            game.stateChanged();
        }

        sceneUI.update();
        sceneUI.render(delta);
    }

    @Override
    public void resize(int width, int height) {

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
