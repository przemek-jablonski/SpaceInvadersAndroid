package com.gamedesire.pszemek.recruitment;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gamedesire.pszemek.recruitment.input.TouchProcessor;
import com.gamedesire.pszemek.recruitment.input.TouchProcessorDesktop;
import com.gamedesire.pszemek.recruitment.input.TouchProcessorMobile;
import com.gamedesire.pszemek.recruitment.utilities.Utilities;

public class MainGameClass extends ApplicationAdapter {

	TouchProcessor touchProcessor;
	TouchProcessorMobile touchProcessorMobile;

	SpriteBatch spriteBatch;
	Texture img;
	Sprite sprite;
	Sprite backgroundSprite;


	//todo: camera and handling multiple device resolutions and aspect ratios (important!)

	@Override
	public void create () {

		spriteBatch = new SpriteBatch();
		img = new Texture("game_character_hero.png");
		sprite = new Sprite(img);
		backgroundSprite = new Sprite(new Texture("ui_bg_main_tile.png"));
		touchProcessorMobile = new TouchProcessorMobile(sprite);
		Utilities.initialize();

//		sprite.setPosition(
//				Utilities.getScreenCenterWidth() - sprite.getWidth() / 2,
//				Utilities.getScreenCenterHeight() - sprite.getHeight() / 2);
		sprite.setCenter(Utilities.getScreenCenterWidth(), Utilities.getScreenCenterHeight());

//		backgroundSprite.setCenter(Utilities.getScreenCenterWidth(), Utilities.getScreenCenterHeight());
		backgroundSprite.setPosition(10, Gdx.graphics.getHeight() - backgroundSprite.getHeight() - 10);

		Gdx.input.setInputProcessor(getValidInputProcessor());
	}


	private TouchProcessor getValidInputProcessor() {
		if (Gdx.app.getType() == Application.ApplicationType.Android)
			touchProcessor = new TouchProcessorMobile(sprite);
		else
			touchProcessor = new TouchProcessorDesktop(sprite);
		return touchProcessor;
	}



	@Override
	public void render () {

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//todo: converter from libgdx screen coordinate system to actual good coordinates
//		if(Gdx.input.isTouched())
//				sprite.setPosition(Gdx.input.getX() - sprite.getWidth()/2, Gdx.graphics.getHeight() - Gdx.input.getY() - sprite.getHeight()/2);

		spriteBatch.begin();
		spriteBatch.draw(backgroundSprite, backgroundSprite.getX(), backgroundSprite.getY());
		spriteBatch.draw(sprite, sprite.getX(), sprite.getY());
		spriteBatch.end();

	}


	@Override
	public void dispose() {

		super.dispose();
		img.dispose();
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

}
