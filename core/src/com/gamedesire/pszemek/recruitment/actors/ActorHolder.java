package com.gamedesire.pszemek.recruitment.actors;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gamedesire.pszemek.recruitment.utilities.Constants;
import com.gamedesire.pszemek.recruitment.utilities.AssetRouting;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by Ciemek on 02/05/16.
 */
public class ActorHolder {

    //todo: double check if LinkedList of actors is thread-safe (just in case)
    List<SpaceInvadersActor> actors;

    short actualLevel;
    short remainingActorsInWave;
    short remainingActorsInLevel;
    short aliveNonHeroActorsInWave;
    Random random;


    public ActorHolder() {
        actors = new LinkedList<SpaceInvadersActor>();
        random = new Random();
        actualLevel = 0;
        remainingActorsInWave = 0;
        aliveNonHeroActorsInWave = remainingActorsInWave;
    }

    public void devStart(){
        spawnHero();

    }

    public HeroActor spawnHero() {
        if (actors.size() != 0) {
            System.out.println("ERROR: ACTOR LIST IS NOT EMPTY");
            return null;
        }
        else
            actors.add(new HeroActor(Constants.PREF_HEIGHT / 3f, Constants.PREF_WIDTH / 3f, 0f, 0f));

        return (HeroActor)actors.get(0);
    }

    public void beginNewLevel() {
        ++actualLevel;
//        remainingActorsInWave = (short)(actualLevel * 10);

        //debug only (or maybe not):
        remainingActorsInWave = 10;
        for (int i=0; i < 10; ++i) {
            addActor(
                    new EnemyActor(
//                        (float)random.nextInt(Constants.PREF_WIDTH - AssetRouting.getEnemy001Texture().getWidth()) + AssetRouting.getEnemy001Texture().getWidth(),
//                        (float)(random.nextInt(Constants.PREF_HEIGHT*2) + Constants.PREF_HEIGHT),
                        (float) random.nextInt(Constants.PREF_WIDTH - AssetRouting.getEnemy001Texture().getWidth() - Constants.SPAWN_MARGIN_HORIZONTAL_STANDARD) + AssetRouting.getEnemy001Texture().getWidth() + Constants.SPAWN_MARGIN_HORIZONTAL_STANDARD,
                        (float) random.nextInt(Constants.PREF_HEIGHT - AssetRouting.getEnemy001Texture().getHeight() - Constants.SPAWN_MARGIN_HORIZONTAL_STANDARD) + AssetRouting.getEnemy001Texture().getHeight() + Constants.SPAWN_MARGIN_HORIZONTAL_STANDARD,
                        0f,
                        0f
            ));

        }

    }

    public void updateAll() {
        for (SpaceInvadersActor actor : actors)
            actor.update();
    }

    public void renderAll(SpriteBatch spriteBatch) {
        //rendering enemies on screen, as one layer
        for (SpaceInvadersActor actor : actors)
            if (actor instanceof EnemyActor)
                actor.render(spriteBatch);

        //rendering player on screen on layer above enemies, for image clarity
        if (actors.get(0) instanceof HeroActor)
                actors.get(0).render(spriteBatch);
        else
            System.out.print("COULD NOT FIND HERO AT POSITION 0.");
    }


    public void addActor(SpaceInvadersActor actor) {
        actors.add(actor);
        remainingActorsInWave = 0;
    }


    public HeroActor getHero() {
        if (!(actors.get(0) instanceof HeroActor)) return null;
        return (HeroActor)actors.get(0);
    }



}
