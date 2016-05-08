package com.gamedesire.pszemek.recruitment.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.gamedesire.pszemek.recruitment.utilities.AssetRouting;

/**
 * Created by Ciemek on 30/04/16.
 */
public class SpaceInvadersUI extends AbstractBaseUI {


    private LabelStyle labelStyle;

    private Label   labelLeftHPText;
    private Label   labelLeftHP;
    private Label   labelRightPointsText;
    private Label   labelRightTimeText;
    private Label   labelRightLevelText;
    private Label   labelRightPoints;
    private Label   labelRightTime;
    private Label   labelRightLevel;
    private Image   imageBottomGameLogo;
    private Image   imageBottomCompanyLogo;

    private long   actualTime;
    private int    heroHP;
    private int    heroPoints;
    private int    gameLevel;



    public SpaceInvadersUI(SpriteBatch batch) {
        super(batch);
    }


    @Override
    public void create() {
        BitmapFont bitmapFont = new BitmapFont();
        bitmapFont.getData().setScale(2f);
        labelStyle = new LabelStyle(bitmapFont, new Color(1f, 1f, 1f, 0.60f));


        Table leftTable = new Table();
        Table rightTable = new Table();
        Table bottomTableCompanyLogo = new Table();
        Table bottomTableInvadersLogo = new Table();

        leftTable.setFillParent(true);
        rightTable.setFillParent(true);
        bottomTableCompanyLogo.setFillParent(true);
        bottomTableInvadersLogo.setFillParent(true);

//        leftTable.setDebug(true);
//        rightTable.setDebug(true);
//        bottomTableCompanyLogo.setDebug(true);
//        bottomTableInvadersLogo.setDebug(true);

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

        leftTable.add(labelLeftHPText).left().padRight(10f);
        leftTable.add(labelLeftHP).left();
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


    public void updateUIData(long gameTimeSecs, int heroPoints, int gameLevel, int actualHP) {
        actualTime = gameTimeSecs;
        heroHP = actualHP;
        this.heroPoints = heroPoints;
        this.gameLevel = gameLevel;
        update();
    }


    @Override
    public void update() {
        labelRightTime.setText(Long.toString(actualTime));
        labelRightPoints.setText(Integer.toString(heroPoints));
        labelRightLevel.setText(Integer.toString(gameLevel));
        labelLeftHP.setText(Integer.toString(heroHP));
    }


    @Override
    public void render(float deltaTime) {
        stage.act(deltaTime);
        stage.draw();
    }

}
