package com.gamedesire.pszemek.recruitment.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.gamedesire.pszemek.recruitment.actors.ActorHolder;
import com.gamedesire.pszemek.recruitment.screens.SpaceInvadersScreen;
import com.gamedesire.pszemek.recruitment.utilities.Utils;

/**
 * Created by Ciemek on 29/04/16.
 */
public class TouchProcessorMobile extends TouchProcessor {

    //todo: ship should not TELEPORT to finger's position, but rather MOVE in this directionVector.

    Vector2             differenceVector;
    SpaceInvadersScreen screen;
    boolean             printed = false;


    @Override
    public void attachActorSpawner(ActorHolder actorHolder) {
        this.actorHolder = actorHolder;
        controlledActor = this.actorHolder.getHero();
    }

    public void attachScreen(SpaceInvadersScreen screen) {
        this.screen = screen;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (!touchPressedDown)
            touchPressedDown = true;
//        actorHolder.playerSpawnProjectile();
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (touchPressedDown) touchPressedDown = false;
        return true;
    }

    //origin of screenX and screenY is in TOP LEFT CORNER (...goddamnit)
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

        if (!printed) {
            System.err.println("SCREEN ( ACTUAL ):\t" + Gdx.graphics.getWidth() + ", " + Gdx.graphics.getHeight());
            System.err.println("SCREEN (VIEWPORT OFFSET):\t" + screen.getViewport().getScreenX() + ", " + screen.getViewport().getScreenY());
            System.err.println("SCREEN (VIEWPORT SIZES):\t" + screen.getViewport().getScreenWidth() + ", " + screen.getViewport().getScreenHeight());
            //this shit below works
            System.err.println("SCREEN (CAMERA, VIEWPORT SIZ):\t" + screen.getCamera().viewportWidth + ", " + screen.getCamera().viewportHeight);
            printed = true;
        }
        //v1:
        if (controlledActor == null) return false;
//        controlledActor.setPosition(screenX, Gdx.graphics.getHeight() - screenY);
//        return true;

        //v2:
//        if (controlledActor == null) return false;

//        System.err.print("TOUCHDIFF: ");
        differenceVector = controlledActor.getActorPosition();
//        System.err.print("pos: " + differenceVector + ", ");
//        Vector2 screenVector = new Vector2(screenX, Gdx.graphics.getHeight() - screenY);
        Vector3 screenVectorInput = screen.getCamera().unproject(new Vector3(screenX,screenY,0f));
        Vector2 screenVector = new Vector2(screenVectorInput.x, screenVectorInput.y);
//        Vector2 screenVector = new Vector2(screenX, (Gdx.graphics.getHeight() * (Const.PREF_HEIGHT / Gdx.graphics.getHeight())) - screenY);
//        Vector2 screenVector = new Vector2(screenX, screen.getCamera().viewportHeight - screenY);
//        Vector2 screenVector = new Vector2(screenX, screen.getViewport().getScreenHeight()- screenY);
//        System.err.print("screen: " + "(" + screenVector + ", ");
        differenceVector.sub(screenVector);
//        System.err.print("diff: " + differenceVector);
        differenceVector = Utils.transformVectorToDirection(differenceVector, 10);
//        System.err.print("dir: " + differenceVector + "\n");

        controlledActor.setDirection(differenceVector);
//        System.err.println("touchpos: " + screenVector);

        Vector3 worldCoordinates = screen.getCamera().unproject(new Vector3(screenX,screenY,0));
//        worldCoordinates = new Vector3(worldCoordinates.x, worldCoordinates.y, 0);
//        System.err.println("touchpos, projected at: " + worldCoordinates.x + "," + worldCoordinates.y);

        Vector2 stackCoordinates = new Vector2(screenX, screenY);
//        screen.

        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

}
