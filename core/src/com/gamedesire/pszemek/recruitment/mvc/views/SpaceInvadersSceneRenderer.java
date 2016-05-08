package com.gamedesire.pszemek.recruitment.mvc.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.gamedesire.pszemek.recruitment.actors.ActorHolder;
import com.gamedesire.pszemek.recruitment.actors.archetypes.SpaceInvadersActor;
import com.gamedesire.pszemek.recruitment.ui.SpaceInvadersUI;
import com.gamedesire.pszemek.recruitment.utilities.AssetRouting;
import com.gamedesire.pszemek.recruitment.utilities.Const;
import com.gamedesire.pszemek.recruitment.utilities.Utils;

/**
 * Created by Ciemek on 08/05/16.
 */
public class SpaceInvadersSceneRenderer extends AbstractSceneRenderer {

//    private SpriteBatch         spriteBatch;
    private SpaceInvadersUI     sceneUI;
    private ActorHolder         actorHolder;

    //// TODO: 08/05/16 CHECK IF THIS SHIT WORKS FOR DIFFERENT DEVICES NOW (konrad?)
//    private Camera              camera;
//    private Viewport            viewport;

    private Texture             vignetteLeftTop;
    private Texture             vignetteLeftBottom;
    private Texture             vignetteRightTop;
    private Texture             vignetteRightBottom;

    private Sprite              backgroundSprite;
    private ShapeRenderer       backgroundGradient;

    private ParticleEffect      particleExplosionOnDeath;
    private ParticleEffectPool  particleExplosionOnDeathPool;
    private ParticleEffectPool.PooledEffect particleExplosionOnDeathPooled;
    private Array<ParticleEffectPool.PooledEffect> particleExplosionOnDeathArray;


    public SpaceInvadersSceneRenderer(SpriteBatch spriteBatch, ActorHolder actorHolder) {
        super(spriteBatch);
        this.actorHolder = actorHolder;
        create();
    }

    @Override
    protected void create() {
        spriteBatch.enableBlending();
        sceneUI = new SpaceInvadersUI(spriteBatch);
        sceneUI.create();


        camera = new OrthographicCamera();
        viewport = new ScalingViewport(Scaling.stretch, Const.PREF_WIDTH, Const.PREF_HEIGHT, camera);
        viewport.apply();
        ((OrthographicCamera) camera).translate(
                camera.viewportWidth / 2,
                camera.viewportHeight / 2);

        vignetteLeftTop = AssetRouting.getVignetteLTTexture();
        vignetteLeftBottom = AssetRouting.getVignetteLBTexture();
        vignetteRightTop = AssetRouting.getVignetteRTTexture();
        vignetteRightBottom = AssetRouting.getVignetteRBTexture();

        backgroundSprite = AssetRouting.getBackgroundSprite();
        backgroundGradient = new ShapeRenderer();

        particleExplosionOnDeath = new ParticleEffect();
        particleExplosionOnDeath.load(Gdx.files.internal(
                        AssetRouting.PARTICLE_EXPLOSION_ONDEATH),
                Gdx.files.internal(""));
        particleExplosionOnDeath.scaleEffect(2.80f);
        particleExplosionOnDeathPool = new ParticleEffectPool(particleExplosionOnDeath, 2, 10);
        particleExplosionOnDeathArray = new Array<ParticleEffectPool.PooledEffect>();
    }

    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        renderLayerBackgroundGradient();

        spriteBatch.begin();
        spriteBatch.setProjectionMatrix(sceneUI.getStage().getCamera().combined);

        camera.update();


        renderLayerBackgroundSprite();
        renderLayerActors();
        renderLayerProjectiles();
        renderLayerHero();
        updateLayerExplosionParticles();
        renderLayerExplosionParticles(deltaTime);
        renderLayerVignettes();


        spriteBatch.end();

    }



    private void renderLayerBackgroundSprite() {
        Color col = spriteBatch.getColor();
        spriteBatch.setColor(col.r, col.g, col.b, 0.9f);
        spriteBatch.draw(backgroundSprite.getTexture(), 0f, 0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        spriteBatch.setColor(col);
    }

    private void renderLayerBackgroundGradient() {

        backgroundGradient.setProjectionMatrix(camera.combined);

        backgroundGradient.begin(ShapeRenderer.ShapeType.Filled);
        backgroundGradient.rect(
                0,
                0,
                Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight(),
                Utils.getColorFrom255(0, 0, 0, 0.4f),
                Utils.getColorFrom255(1, 0, 0, 0.4f),
                Utils.getColorFrom255(0, 0, 1, 0.4f),
                Utils.getColorFrom255(0, 1, 0, 0.4f));
                backgroundGradient.end();



//        spriteBatch.draw(backgroundSprite.getTexture(), 0f, 0f, camera.viewportWidth, camera.viewportHeight);
    }


    private void renderLayerActors() {
        for (int a = 1; a < actorHolder.getActors().size; ++a) {
//            actorHolder.getActors().get(a).render(spriteBatch);
            renderActor(actorHolder.getActors().get(a));
        }
    }

    private void renderLayerProjectiles() {
        for (int p = 1; p < actorHolder.getProjectiles().size; ++p) {
//            actorHolder.getProjectiles().get(p).render(spriteBatch);
            renderActor(actorHolder.getProjectiles().get(p));
        }
    }

    private void renderLayerHero() {
//        actorHolder.getHero().render(spriteBatch);
        renderActor(actorHolder.getHero());
    }

    private void updateLayerExplosionParticles() {
        if(actorHolder.enemyDeathOnHit) {
            actorHolder.enemyDeathOnHit = false;
            particleExplosionOnDeathPooled = particleExplosionOnDeathPool.obtain();
            for (ParticleEmitter emitter : particleExplosionOnDeathPooled.getEmitters()){
                emitter.setPosition(
                        actorHolder.enemyDeathOnHitLocation.x,
                        actorHolder.enemyDeathOnHitLocation.y);
            }

            particleExplosionOnDeathArray.add(particleExplosionOnDeathPooled);

//            particleExplosionOnDeathPooled.getEmitters()
//            particleExplosionOnDeathPooled.setPosition(
//                    actorHolder.enemyDeathOnHitLocation.x,
//                    actorHolder.enemyDeathOnHitLocation.y);

//            particleExplosionOnDeathArray.add(particleExplosionOnDeathPool.obtain());
//            particleExplosionOnDeathArray.get(particleExplosionOnDeathArray.size-1)
//                    .setPosition(
//                            actorHolder.enemyDeathOnHitLocation.x,
//                            actorHolder.enemyDeathOnHitLocation.y);

//            particleExplosionOnDeathArray.get(particleExplosionOnDeathArray.size-1).start();
        }
    }

    private void renderLayerExplosionParticles(float deltaTime) {
        for (int i = 0; i < particleExplosionOnDeathArray.size; i++) {
            particleExplosionOnDeathPooled  = particleExplosionOnDeathArray.get(i);
            particleExplosionOnDeathPooled.draw(spriteBatch, Gdx.graphics.getDeltaTime());

            if (particleExplosionOnDeathPooled.isComplete()) {
                particleExplosionOnDeathArray.removeIndex(i);
                particleExplosionOnDeathPooled.free();
            }
        }
    }


    private void renderLayerVignettes() {
        Color col = spriteBatch.getColor();
        spriteBatch.setColor(new Color(0f, 0f, 0f, 0.5f));
        spriteBatch.draw(vignetteLeftBottom, 0f, 0f, vignetteLeftBottom.getWidth(), vignetteLeftBottom.getHeight());
        spriteBatch.draw(vignetteLeftTop, 0f, Gdx.graphics.getHeight() - vignetteLeftTop.getHeight(), vignetteLeftTop.getWidth(), vignetteLeftTop.getHeight());
        spriteBatch.draw(vignetteRightTop, Gdx.graphics.getWidth() - vignetteRightTop.getWidth(), Gdx.graphics.getHeight() - vignetteLeftTop.getHeight(), vignetteLeftTop.getWidth(), vignetteLeftTop.getHeight());
        spriteBatch.draw(vignetteRightBottom, Gdx.graphics.getWidth() - vignetteRightTop.getWidth(), 0f, vignetteLeftTop.getWidth(), vignetteLeftTop.getHeight());
        spriteBatch.setColor(col);
    }

    private void renderActor(SpaceInvadersActor actor) {
        spriteBatch.draw(
                actor.getActorSprite().getTexture(),
                actor.getActorSprite().getX(),
                actor.getActorSprite().getY());
    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    private void updateUI() {
//        sceneUI.updateHeroPoints(actorHolder.getHeroPoints());
//        sceneUI.updateLevel(actorHolder.getActualLevel());
//        sceneUI.updateHP(actorHolder.getHero().getActualHealthPoints());
//        sceneUI.updateSP(actorHolder.getHero().getActualShieldPoints());
    }

    private void renderUI(float deltaTime) {
        sceneUI.update();
        sceneUI.render(deltaTime);
        sceneUI.getStage().draw();
    }

    @Override
    public void dispose() {
        super.dispose();
        sceneUI.dispose();
    }
}
