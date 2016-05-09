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
import com.gamedesire.pszemek.recruitment.utilities.Constants;

/**
 * Created by Ciemek on 08/05/16.
 *
 * Implementation of SceneRenderer, serves as VIEW component for main game screen.
 * Encapsulates all rendering data and operations inside itself. Only one 'heavy' dependency is needed
 * (ActorHolder) to render scene on a screen.
 *
 * Encapsulates sprites, particle emitters, Textures, UIs and methods for rendering entities (Actors) on screen.
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

    private ParticleEffect      slidingStarsBackground1;
    private ParticleEffect      slidingStarsBackground2;
    private ParticleEffect      bigStarsParticle1;
    private ParticleEffect      bigStarsParticle2;



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
        viewport = new ScalingViewport(Scaling.stretch, Constants.CAMERA_WIDTH, Constants.CAMERA_HEIGHT, camera);
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
//        createParticlePoolExplosion002();
//        createParticlePoolExplosionBolt();
//        createParticlePoolProjectileBolt();
        createParticleSlidingStars();
    }


    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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

//        updateLayerExplosionParticles002();
//        renderLayerExplosionParticles002(deltaTime);
//        updateLayerProjectileBoltParticles();
//        renderLayerProjectileBoltParticles(deltaTime);

        renderLayerVignettes();

        spriteBatch.end();
    }



    private void renderLayerBackgroundSprite() {
        spriteBatch.draw(backgroundSprite.getTexture(), 0f, 0f, Constants.CAMERA_WIDTH, Constants.CAMERA_HEIGHT);
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

//    private void updateLayerExplosionParticles002() {
//        if(actorHolder.enemy002DeathOnHit) {
//            actorHolder.enemy002DeathOnHit = false;
//            particleExplosion002Pooled = particleExplosion002Pool.obtain();
//            for (ParticleEmitter emitter : particleExplosionOnDeathPooled.getEmitters()){
//                emitter.setPosition(
//                        actorHolder.enemy002DeathOnHitLocation.x,
//                        actorHolder.enemy002DeathOnHitLocation.y);
//            }
//            particleExplosion002Array.add(particleExplosion002Pooled);
//        }
//
//    }
//
//    private void renderLayerExplosionParticles002(float deltaTime) {
//        for (int i = 0; i < particleExplosion002Array.size; i++) {
//            particleExplosion002Pooled = particleExplosion002Array.get(i);
//            particleExplosion002Pooled.draw(spriteBatch, Gdx.graphics.getDeltaTime());
//
//            if (particleExplosion002Pooled.isComplete()) {
//                particleExplosion002Array.removeIndex(i);
//                particleExplosion002Pooled.free();
//            }
//        }
//    }

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

//    private void updateLayerProjectileBoltParticles() {
//        if(actorHolder.enemyDeathOnHit) {
//            actorHolder.enemyDeathOnHit = false;
//            particleExplosionOnDeathPooled = particleProjectileBoltPool.obtain();
//            for (ParticleEmitter emitter : particleExplosionOnDeathPooled.getEmitters()){
//                emitter.setPosition(
//                        actorHolder.enemyDeathOnHitLocation.x,
//                        actorHolder.enemyDeathOnHitLocation.y);
//            }
//
//            particleExplosionOnDeathArray.add(particleExplosionOnDeathPooled);
//        }
//    }
//
//    private void renderLayerProjectileBoltParticles(float deltaTime) {
//        for (int i = 0; i < particleProjectileBoltArray.size; i++) {
//            particleProjectileBoltPooled  = particleProjectileBoltArray.get(i);
//            particleProjectileBoltPooled.draw(spriteBatch, Gdx.graphics.getDeltaTime());
//
//            if (particleProjectileBoltPooled.isComplete()) {
//                particleProjectileBoltArray.removeIndex(i);
//                particleProjectileBoltPooled.free();
//            }
//        }
//    }


    private void renderLayerVignettes() {
        Color col = spriteBatch.getColor();
        spriteBatch.setColor(new Color(0f, 0f, 0f, 0.5f));
        spriteBatch.draw(vignetteLeftBottom, 0f, 0f, Constants.CAMERA_WIDTH, vignetteLeftBottom.getHeight());
        spriteBatch.draw(vignetteLeftTop, 0f, Constants.CAMERA_HEIGHT - vignetteLeftTop.getHeight(), vignetteLeftTop.getWidth(), vignetteLeftTop.getHeight());
        spriteBatch.draw(vignetteRightTop, Constants.CAMERA_WIDTH - vignetteRightTop.getWidth(), Constants.CAMERA_HEIGHT - vignetteLeftTop.getHeight(), vignetteLeftTop.getWidth(), vignetteLeftTop.getHeight());
        spriteBatch.draw(vignetteRightBottom, Constants.CAMERA_WIDTH - vignetteRightTop.getWidth(), 0f, vignetteLeftTop.getWidth(), vignetteLeftTop.getHeight());
        spriteBatch.setColor(col);
    }

//    private void createParticlePoolExplosion002() {
//        ParticleEffect explo002 = new ParticleEffect();
//        explo002.load(
//                Gdx.files.internal("particle_explosion_ondeath_enemy002.p"),
//                Gdx.files.internal(""));
//        explo002.scaleEffect(3f);
//        particleExplosion002Pool = new ParticleEffectPool(explo002, 5, 15);
//        particleExplosion002Array = new Array<ParticleEffectPool.PooledEffect>();
//    }

    private void createParticlePoolExplosionOnDeath() {
        ParticleEffect particleExplosionOnDeath = new ParticleEffect();
        particleExplosionOnDeath.load(
                Gdx.files.internal(AssetRouting.PARTICLE_EXPLOSION_ONDEATH),
                Gdx.files.internal(""));
        particleExplosionOnDeath.scaleEffect(3.5f);
        particleExplosionOnDeathPool = new ParticleEffectPool(particleExplosionOnDeath, 2, 10);
        particleExplosionOnDeathArray = new Array<ParticleEffectPool.PooledEffect>();
    }

//    private void createParticlePoolProjectileBolt() {
//        ParticleEffect particleProjectileBolt = new ParticleEffect();
//        particleProjectileBolt.load(
//                Gdx.files.internal(AssetRouting.PARTICLE_PROJECTILE_BOLT),
//                Gdx.files.internal(""));
//        particleProjectileBoltPool = new ParticleEffectPool(particleProjectileBolt, 15, 30);
//        particleProjectileBoltArray = new Array<ParticleEffectPool.PooledEffect>();
//    }

    private void createParticleSlidingStars() {
        bigStarsParticle1 = new ParticleEffect();
        bigStarsParticle1.load(
                Gdx.files.internal("particles_bigstars_background.p"),
                Gdx.files.internal(""));
        bigStarsParticle2 = new ParticleEffect(bigStarsParticle1);

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

        for (ParticleEmitter emitter : bigStarsParticle1.getEmitters())
            emitter.setContinuous(true);

        for (ParticleEmitter emitter : bigStarsParticle2.getEmitters())
            emitter.setContinuous(true);


        slidingStarsBackground1.start();
        slidingStarsBackground2.start();
        bigStarsParticle1.start();
        bigStarsParticle2.start();
    }

    private void renderParticleSlidingStars() {
        if(slidingStarsBackground1.isComplete()) slidingStarsBackground1.reset();
        if(slidingStarsBackground2.isComplete()) slidingStarsBackground2.reset();
        if(bigStarsParticle1.isComplete()) bigStarsParticle1.reset();
        if(bigStarsParticle2.isComplete()) bigStarsParticle2.reset();

        slidingStarsBackground1.draw(spriteBatch, Gdx.graphics.getDeltaTime() * MathUtils.clamp(actualLevel * 0.20f, 1f, 100f));
        slidingStarsBackground2.draw(spriteBatch, Gdx.graphics.getDeltaTime() * MathUtils.clamp(actualLevel * 0.34f, 1f, 100f));
        bigStarsParticle1.draw(spriteBatch, Gdx.graphics.getDeltaTime() * MathUtils.clamp(actualLevel * 0.20f, 1f, 100f));
        bigStarsParticle2.draw(spriteBatch, Gdx.graphics.getDeltaTime() * MathUtils.clamp(actualLevel * 0.34f, 1f, 100f));

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
