package com.gamedesire.pszemek.recruitment.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.gamedesire.pszemek.recruitment.utilities.AssetRouting;
import com.gamedesire.pszemek.recruitment.utilities.Constants;


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
    private Label            placeFingerText;
    private Label            holdOrTapText;
    private Label            shootEnemiesText;
    private Label            shootSpaceDeersText;
    private Label           haveFunText;
    private TextButton          tutorialEndButton;

    private SpriteBatch         spriteBatch;
    private Sprite              backgroundSprite;
    private ParticleEffect      backgroundParticle;
    private ParticleEffect      mainMenuParticle;

    private boolean             tutorialStart = false;
    private boolean             tutorialCompleted = false;

    public MainMenuUI(SpriteBatch spriteBatch) {
        super(spriteBatch);
        this.spriteBatch = spriteBatch;
    }

    @Override
    public void create() {
        BitmapFont bitmapFont = new BitmapFont();
        bitmapFont.getData().setScale(3f);
        labelStyle = new Label.LabelStyle(bitmapFont, new Color(1f, 1f, 1f, 1f));

        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

        mainMenuTable = new Table(skin);
        mainMenuTable.setFillParent(true);

        tutorialTable = new Table(skin);
        tutorialTable.setFillParent(true);
//        tutorialTable.setDebug(true);

        startGameButton = new TextButton("START GAME", skin);
        startGameButton.setScale(3f);
        startGameButton.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                stage.clear();
                stage.addActor(tutorialTable);
                tutorialStart = true;
                return true;
            }
        });
        gamedesireLogo = new Image(AssetRouting.getUiLogoGanymede());
        invadersLogo = new Image(AssetRouting.getUiLogoInvaders());

        placeFingerText = new Label("PLACE finger on a screen to move your ship", skin);
        holdOrTapText = new Label("TAP or HOLD your finger on a screen to shoot", skin);
        shootEnemiesText = new Label("SHOOT enemies to stay alive and score points", skin);
        shootSpaceDeersText = new Label("GRAB or SHOOT space deers to receive bonuses", skin);
        haveFunText = new Label("Have fun!", skin);
        tutorialEndButton = new TextButton("GOT IT!", skin);
        tutorialEndButton.setScale(3f);
        tutorialEndButton.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                tutorialCompleted = true;
                return true;
            }
        });


        mainMenuTable.row();
        mainMenuTable.add(invadersLogo).center().padBottom(10);
        mainMenuTable.row();
        mainMenuTable.add(gamedesireLogo).center().padBottom(20);
        mainMenuTable.row();
        mainMenuTable.add(startGameButton).fillY().height(75).width(200);
        mainMenuTable.row();

        stage.addActor(mainMenuTable);

        tutorialTable.row();
        tutorialTable.add(placeFingerText).center().height(35).fill().padBottom(25);
        tutorialTable.row();
        tutorialTable.add(holdOrTapText).center().height(35).fill().padBottom(25);
        tutorialTable.row();
        tutorialTable.add(shootEnemiesText).center().height(35).fill().padBottom(25);
        tutorialTable.row();
        tutorialTable.add(shootSpaceDeersText).center().height(35).fill().padBottom(25);
        tutorialTable.row();
//        tutorialTable.add(haveFunText).center().height(35).fill().padBottom(25);
        tutorialTable.row();
        tutorialTable.add(tutorialEndButton).center().fillY().height(75).width(300);

        backgroundSprite = AssetRouting.getBackgroundSprite();
        mainMenuParticle = new ParticleEffect();
        backgroundParticle = new ParticleEffect();
        backgroundParticle.load(Gdx.files.internal(AssetRouting.PARTICLE_SLIDINGSTARS), Gdx.files.internal(""));
        mainMenuParticle.load(Gdx.files.internal("particle_mainmenu.p"), Gdx.files.internal(""));
        backgroundParticle.setPosition(0f, 0f);
        mainMenuParticle.setPosition(Constants.CAMERA_WIDTH/2, Constants.CAMERA_HEIGHT/2);
        for (ParticleEmitter emitter : mainMenuParticle.getEmitters()) {
            emitter.setContinuous(true);
        }
        for (ParticleEmitter emitter : backgroundParticle.getEmitters()) {
            emitter.setContinuous(true);
        }
        backgroundParticle.start();
        mainMenuParticle.start();

        Gdx.input.setInputProcessor(stage);
        update();
    }

    @Override
    public void update() {

    }

    @Override
    public void render(float deltaTime) {
        if (backgroundParticle.isComplete()) backgroundParticle.reset();
        if (mainMenuParticle.isComplete()) mainMenuParticle.reset();
        spriteBatch.begin();
        backgroundSprite.draw(spriteBatch);
        backgroundParticle.draw(spriteBatch, deltaTime);
        mainMenuParticle.draw(spriteBatch, deltaTime);
        spriteBatch.end();
        stage.act(deltaTime);
        stage.draw();
    }

    public boolean isTutorialCompleted() {
        return tutorialCompleted;
    }
}
