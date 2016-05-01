package com.gamedesire.pszemek.recruitment;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gamedesire.pszemek.recruitment.screens.SpaceInvadersScreen;

public class MainGameClass extends Game {

	private SpriteBatch spriteBatch;


	//todo: camera and handling multiple device resolutions and aspect ratios (important!)

	@Override
	public void create () {
		spriteBatch = new SpriteBatch();
		setScreen(new SpaceInvadersScreen(this));
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


	//accessors:
	public SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}
}
