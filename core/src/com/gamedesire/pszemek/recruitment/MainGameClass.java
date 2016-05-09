package com.gamedesire.pszemek.recruitment;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gamedesire.pszemek.recruitment.screens.GameOverScreen;
import com.gamedesire.pszemek.recruitment.screens.MainMenuScreen;
import com.gamedesire.pszemek.recruitment.screens.SpaceInvadersScreen;
import com.gamedesire.pszemek.recruitment.utilities.GameStateEnum;

/**
 * Main starting class for a game. Serves as a container for Screens (android activities equivalent)
 */
public class MainGameClass extends Game {

	private SpriteBatch     spriteBatch;
    public GameStateEnum    gameState;


	@Override
	public void create () {
		spriteBatch = new SpriteBatch();

        gameState = GameStateEnum.MAIN_MENU;
        setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}


	@Override
	public void dispose() {
		super.dispose();
		spriteBatch.dispose();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}

    public void stateChanged() {
        getScreen().dispose();

        if (gameState == GameStateEnum.SPACE_INVADERS)
            setScreen(new SpaceInvadersScreen(this));

        else if (gameState == GameStateEnum.MAIN_MENU)
            setScreen(new MainMenuScreen(this));

        else if (gameState == GameStateEnum.GAME_OVER)
            setScreen(new GameOverScreen(this));
    }

	public SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}
}
