package com.gamedesire.pszemek.recruitment;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gamedesire.pszemek.recruitment.screens.MainMenuScreen;
import com.gamedesire.pszemek.recruitment.screens.SpaceInvadersScreen;
import com.gamedesire.pszemek.recruitment.utilities.GameStateEnum;

public class MainGameClass extends Game {

	private SpriteBatch     spriteBatch;
    private Game            game;
    public GameStateEnum    gameState;

	//todo: camera and handling multiple device resolutions and aspect ratios (important!)

	@Override
	public void create () {
        game = this;
		spriteBatch = new SpriteBatch();
        gameState = GameStateEnum.MAIN_MENU;
        setScreen(new MainMenuScreen(this));
//		setScreen(new SpaceInvadersScreen(this));
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
    }

	//accessors:
	public SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}
}
