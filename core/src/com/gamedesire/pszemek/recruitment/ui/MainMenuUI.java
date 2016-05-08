package com.gamedesire.pszemek.recruitment.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.gamedesire.pszemek.recruitment.utilities.AssetRouting;

/**
 * Created by Ciemek on 30/04/16.
 */
public class MainMenuUI extends AbstractBaseUI {

    private Label.LabelStyle    labelStyle;

    private Table               mainMenuTable;
    private TextButton          startGameButton;
    private Image               gamedesireLogo;
    private Image               invadersLogo;

    private Table               tutorialTable;
    private TextArea            tutorialTitle;
    private TextArea            placeFingerText;
    private TextArea            holdOrTapText;
    private TextArea            shootEnemiesText;
    private TextArea            shootSpaceDeersText;
    private TextArea            haveFunText;

    public MainMenuUI(SpriteBatch spriteBatch) {
        super(spriteBatch);
    }

    @Override
    public void create() {
        BitmapFont bitmapFont = new BitmapFont();
        bitmapFont.getData().setScale(3f);
        labelStyle = new Label.LabelStyle(bitmapFont, new Color(1f, 1f, 1f, 1f));

        mainMenuTable = new Table();
        mainMenuTable.setDebug(true);

        startGameButton = new TextButton("START GAME", new TextButton.TextButtonStyle());
        gamedesireLogo = new Image(AssetRouting.getUiLogoGanymede());
        invadersLogo = new Image(AssetRouting.getUiLogoInvaders());

        mainMenuTable.row();
        mainMenuTable.add(invadersLogo).center();
        mainMenuTable.row();
        mainMenuTable.add(gamedesireLogo).center();
        mainMenuTable.row();
        mainMenuTable.add(startGameButton);
        mainMenuTable.row();

        stage.addActor(mainMenuTable);

        update();
    }

    @Override
    public void update() {

    }

    @Override
    public void render(float deltaTime) {
        stage.act(deltaTime);
        stage.draw();
    }
}
