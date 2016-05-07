package com.gamedesire.pszemek.recruitment.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gamedesire.pszemek.recruitment.utilities.AssetRouting;
import com.gamedesire.pszemek.recruitment.utilities.Constants;

import net.dermetfan.gdx.assets.AnnotationAssetManager;

/**
 * Created by Ciemek on 30/04/16.
 */
public class DebugUI extends AbstractBaseUI {

    private Viewport viewport;
    private Stage   stage;

    //// FIXME: 06/05/16 store this in some logic (model) class
    private long    actualTime;
    private long    startTime;

    private long    heroHP = 50;
    private long    heroSP = 0;

    private int    heroPoints;
    private long    gameTime;
    private long    gameLevel;


//    private Label   labelFrames;
//    private Label   labelDelta;
//    private Label   labelTimer;


    private Label   labelLeftHPText;
    private Label   labelLeftSPText;
    private Label   labelLeftHP;
    private Label   labelLeftSP;

    private Label   labelRightPointsText;
    private Label   labelRightTimeText;
    private Label   labelRightLevelText;
    private Label   labelRightPoints;
    private Label   labelRightTime;
    private Label   labelRightLevel;

    private Image   imageBottomGameLogo;
    private Image   imageBottomCompanyLogo;

    private LabelStyle labelStyle;



    public DebugUI(SpriteBatch batch) {
        super(batch);
        viewport = new FitViewport(Constants.PREF_WIDTH, Constants.PREF_HEIGHT);
        stage = new Stage(viewport, batch);
    }


    @Override
    public void create() {
        //// FIXME: 06/05/16 this should be done in logic (model) class, not in UI class, come on...
        startTime = System.currentTimeMillis();
        BitmapFont bitmapFont = new BitmapFont();
        bitmapFont.getData().setScale(2f);
        labelStyle = new LabelStyle(bitmapFont, new Color(1f, 1f, 1f, 0.45f));


        Table leftTable = new Table();
        Table rightTable = new Table();
        Table bottomTableCompanyLogo = new Table();
        Table bottomTableInvadersLogo = new Table();

        leftTable.setFillParent(true);
        rightTable.setFillParent(true);
        bottomTableCompanyLogo.setFillParent(true);
        bottomTableInvadersLogo.setFillParent(true);

        leftTable.setDebug(true);
        rightTable.setDebug(true);
        bottomTableCompanyLogo.setDebug(true);
        bottomTableInvadersLogo.setDebug(true);

        //// FIXME: 06/05/16 USAGE OF PIXELS INSTEAD OF GETTING CONSTANTS.GETCAMERA.GETWIDTH * 0.05f
        leftTable.pad(15f);
        rightTable.pad(15f);
        bottomTableCompanyLogo.pad(4f);
        bottomTableInvadersLogo.pad(5f);

        leftTable.top().left();
        rightTable.top().right();
        bottomTableCompanyLogo.bottom().left();
        bottomTableInvadersLogo.bottom().right();


        labelLeftHPText = new Label("HP:", labelStyle);
        labelLeftHP = new Label(Long.toString(heroHP), labelStyle);
        labelLeftSPText = new Label("SP:", labelStyle);
        labelLeftSP = new Label(Long.toString(heroSP), labelStyle);

        leftTable.add(labelLeftHPText).left().padRight(10f);
        leftTable.add(labelLeftHP).left();
        leftTable.row();

        leftTable.add(labelLeftSPText).left().padRight(10f);
        leftTable.add(labelLeftSP).left();
        leftTable.row();


        labelRightPointsText = new Label("POINTS", labelStyle);
        labelRightPoints = new Label(Long.toString(heroPoints), labelStyle);
        labelRightLevelText = new Label("LEVEL", labelStyle);
        labelRightLevel = new Label(Long.toString(gameLevel), labelStyle);
        labelRightTimeText = new Label("TIME", labelStyle);
        labelRightTime = new Label(Long.toString(actualTime), labelStyle);

        rightTable.add(labelRightPoints).right().padRight(10f);
        rightTable.add(labelRightPointsText).right();
        rightTable.row();

        rightTable.add(labelRightTime).right().padRight(10f);
        rightTable.add(labelRightTimeText).right();
        rightTable.row();

        rightTable.add(labelRightLevel).right().padRight(10f);
        rightTable.add(labelRightLevelText).right();
        rightTable.row();


        imageBottomCompanyLogo = new Image(AssetRouting.getUiLogoGanymede());
        imageBottomGameLogo = new Image(AssetRouting.getUiLogoInvaders());

        bottomTableCompanyLogo.add(imageBottomGameLogo).left();
        bottomTableInvadersLogo.add(imageBottomCompanyLogo).right();


        stage.addActor(leftTable);
        stage.addActor(rightTable);
        stage.addActor(bottomTableCompanyLogo);
        stage.addActor(bottomTableInvadersLogo);

        update();
    }

    @Override
    public void update() {
        actualTime = (long)((System.currentTimeMillis() - startTime) / 1000f);

        //// FIXME: 07/05/16 Longs here are not nessesary, refactor variables type to Integer
        labelRightTime.setText(Long.toString(actualTime));
        labelRightPoints.setText(Integer.toString(heroPoints));
        labelRightLevel.setText(Long.toString(gameLevel));
        labelLeftHP.setText(Long.toString(heroHP));
        labelLeftHP.setText(Long.toString(heroSP));

//        labelFrames.setText(Float.toString(Gdx.graphics.getFramesPerSecond()));
//        labelDelta.setText(Float.toString(Gdx.graphics.getDeltaTime()));
//        labelTimer.setText(Float.toString(actualTime));
    }


    @Override
    public void render() {
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }



    public void updateHP(int actualHealthPoints) {
        heroHP = actualHealthPoints;
    }

    public void updateSP(int actualShieldPoints) {
        heroSP = actualShieldPoints;
    }

    public void updateHeroPoints(int heroPoints) {
        this.heroPoints = heroPoints;
    }

    public void updateLevel(int gameLevel) {
        this.gameLevel = gameLevel;
    }



    public Stage getStage() {
        return stage;
    }

    public Viewport getViewport() {
        return viewport;
    }
}
