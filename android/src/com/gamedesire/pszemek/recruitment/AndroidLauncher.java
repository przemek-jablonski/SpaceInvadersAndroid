package com.gamedesire.pszemek.recruitment;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.gamedesire.pszemek.recruitment.MainGameClass;

public class AndroidLauncher extends AndroidApplication {

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		//todo: get this info from SharedPreferences (and create encapsulating class)
		config.useAccelerometer = true;

		initialize(new MainGameClass(), config);
	}


}
