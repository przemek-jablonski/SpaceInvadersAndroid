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
import com.badlogic.gdx.math.MathUtils;
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

    private SpaceInvadersUI     sceneUI;
    private ActorHolder         actorHolder;
    public int                  actualLevel;

    private Texture             vignetteLeftTop;
    private Texture             vignetteLeftBottom;
    private Texture             vignetteRightTop;
    private Texture             vignetteRightBottom;

    private Sprite              backgroundSprite;
    private ShapeRenderer       backgroundGradient;

    private ParticleEffectPool  particleExplosionOnDeathPool;
    private ParticleEffectPool.PooledEffect particleExplosionOnDeathPooled;
    private Array<ParticleEffectPool.PooledEffect> particleExplosionOnDeathArray;

    private ParticleEffectPool  particleProjectileBoltPool;
    private ParticleEffectPool.PooledEffect particleProjectileBoltPooled;
    private Array<ParticleEffectPool.PooledEffect> particleProjectileBoltArray;

    private ParticleEffect      slidingStarsBackground1;
    private ParticleEffect      slidingStarsBackground2;



    public SpaceInvadersSceneRenderer(SpriteBatch spriteBatch, ActorHolder actorHolder) {
        super(spriteBatch);
        this.actorHolder = actorHolder;
        actualLevel = 0;
        create();
    }

    @Override
    protected void create() {
        spriteBatch.enableBlending();
        sceneUI = new SpaceInvadersUI(spriteBatch);
        sceneUI.create();


        camera = new OrthographicCamera();
        viewport = new ScalingViewport(Scaling.stretch, Const.CAMERA_WIDTH, Const.CAMERA_HEIGHT, camera);
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


        createParticlePoolExplosionOnDeath();
        createParticlePoolProjectileBolt();
        createParticleSlidingStars();
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
        renderParticleSlidingStars();

        renderLayerActors();
        renderLayerProjectiles();
        renderLayerHero();
        updateLayerExplosionParticles();
        renderLayerExplosionParticles(deltaTime);
//        updateLayerProjectileBoltParticles();
//        renderLayerProjectileBoltParticles(deltaTime);
        renderLayerVignettes();


        spriteBatch.end();
    }



    private void renderLayerBackgroundSprite() {
        Color col = spriteBatch.getColor();
        spriteBatch.setColor(col.r, col.g, col.b, 0.9f);
        spriteBatch.draw(backgroundSprite.getTexture(), 0f, 0f, Const.CAMERA_WIDTH, Const.CAMERA_HEIGHT);
        spriteBatch.setColor(col);
    }

    private void renderLayerBackgroundGradient() {

        backgroundGradient.setProjectionMatrix(camera.combined);
        backgroundGradient.begin(ShapeRenderer.ShapeType.Filled);
        backgroundGradient.rect(
                0,
                0,
                Const.CAMERA_WIDTH,
                Const.CAMERA_HEIGHT,
                Utils.getColorFrom255(0, 0, 0, 0.4f),
                Utils.getColorFrom255(1, 0, 0, 0.4f),
                Utils.getColorFrom255(0, 0, 1, 0.4f),
                Utils.getColorFrom255(0, 1, 0, 0.4f));
                backgroundGradient.end();

  }


    private void renderLayerActors() {
        for (int a = 1; a < actorHolder.getActors().size; ++a)
            renderActor(actorHolder.getActors().get(a));

    }

    private void renderLayerProjectiles() {
        for (int p = 1; p < actorHolder.getProjectiles().size; ++p)
            renderActor(actorHolder.getProjectiles().get(p));

    }

    private void renderLayerHero() {
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

    private void updateLayerProjectileBoltParticles() {
        if(actorHolder.enemyDeathOnHit) {
            actorHolder.enemyDeathOnHit = false;
            particleExplosionOnDeathPooled = particleProjectileBoltPool.obtain();
            for (ParticleEmitter emitter : particleExplosionOnDeathPooled.getEmitters()){
                emitter.setPosition(
                        actorHolder.enemyDeathOnHitLocation.x,
                        actorHolder.enemyDeathOnHitLocation.y);
            }

            particleExplosionOnDeathArray.add(particleExplosionOnDeathPooled);
        }
    }

    private void renderLayerProjectileBoltParticles(float deltaTime) {
        for (int i = 0; i < particleProjectileBoltArray.size; i++) {
            particleProjectileBoltPooled  = particleProjectileBoltArray.get(i);
            particleProjectileBoltPooled.draw(spriteBatch, Gdx.graphics.getDeltaTime());

            if (particleProjectileBoltPooled.isComplete()) {
                particleProjectileBoltArray.removeIndex(i);
                particleProjectileBoltPooled.free();
            }
        }
    }


    private void renderLayerVignettes() {
        Color col = spriteBatch.getColor();
        spriteBatch.setColor(new Color(0f, 0f, 0f, 0.5f));
        spriteBatch.draw(vignetteLeftBottom, 0f, 0f, Const.CAMERA_WIDTH, vignetteLeftBottom.getHeight());
        spriteBatch.draw(vignetteLeftTop, 0f, Const.CAMERA_HEIGHT - vignetteLeftTop.getHeight(), vignetteLeftTop.getWidth(), vignetteLeftTop.getHeight());
        spriteBatch.draw(vignetteRightTop, Const.CAMERA_WIDTH - vignetteRightTop.getWidth(), Const.CAMERA_HEIGHT - vignetteLeftTop.getHeight(), vignetteLeftTop.getWidth(), vignetteLeftTop.getHeight());
        spriteBatch.draw(vignetteRightBottom, Const.CAMERA_WIDTH - vignetteRightTop.getWidth(), 0f, vignetteLeftTop.getWidth(), vignetteLeftTop.getHeight());
        spriteBatch.setColor(col);
    }

    private void createParticlePoolExplosionOnDeath() {
        ParticleEffect particleExplosionOnDeath = new ParticleEffect();
        particleExplosionOnDeath.load(
                Gdx.files.internal(AssetRouting.PARTICLE_EXPLOSION_ONDEATH),
                Gdx.files.internal(""));
        particleExplosionOnDeath.scaleEffect(3f);
        particleExplosionOnDeathPool = new ParticleEffectPool(particleExplosionOnDeath, 2, 10);
        particleExplosionOnDeathArray = new Array<ParticleEffectPool.PooledEffect>();
    }

    private void createParticlePoolProjectileBolt() {
        ParticleEffect particleProjectileBolt = new ParticleEffect();
        particleProjectileBolt.load(
                Gdx.files.internal(AssetRouting.PARTICLE_PROJECTILE_BOLT),
                Gdx.files.internal(""));
        particleProjectileBoltPool = new ParticleEffectPool(particleProjectileBolt, 15, 30);
        particleProjectileBoltArray = new Array<ParticleEffectPool.PooledEffect>();
    }

    private void createParticleSlidingStars() {
        slidingStarsBackground1 = new ParticleEffect();
        slidingStarsBackground1.load(
                Gdx.files.internal(AssetRouting.PARTICLE_SLIDINGSTARS),
                Gdx.files.internal(""));
        slidingStarsBackground1.setPosition(0f, 0f);
        slidingStarsBackground2 = new ParticleEffect(slidingStarsBackground1);

        for (ParticleEmitter emitter : slidingStarsBackground1.getEmitters())
            emitter.setContinuous(true);

        for (ParticleEmitter emitter : slidingStarsBackground2.getEmitters())
            emitter.setContinuous(true);


        slidingStarsBackground1.start();
        slidingStarsBackground2.start();
    }

    private void renderParticleSlidingStars() {
        slidingStarsBackground1.draw(spriteBatch, Gdx.graphics.getDeltaTime() * MathUtils.clamp(actualLevel * 0.20f, 1f, 100f));
        slidingStarsBackground2.draw(spriteBatch, Gdx.graphics.getDeltaTime() * MathUtils.clamp(actualLevel * 0.34f, 1f, 100f));
        if(slidingStarsBackground1.isComplete()) slidingStarsBackground1.reset();
        if(slidingStarsBackground2.isComplete()) slidingStarsBackground2.reset();

    }

    private void renderActor(SpaceInvadersActor actor) {
        spriteBatch.draw(
                actor.getActorSprite().getTexture(),
                actor.getActorSprite().getX(),
                actor.getActorSprite().getY());
    }

    public void updateLevel(int actualLevel) {
        this.actualLevel = actualLevel;
    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }


    @Override
    public void dispose() {
        super.dispose();
        sceneUI.dispose();
    }
}
