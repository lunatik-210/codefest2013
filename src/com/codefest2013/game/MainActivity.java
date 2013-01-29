package com.codefest2013.game;

import java.io.IOException;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.engine.Engine;
import org.andengine.engine.LimitedFPSEngine;
import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.BaseGameActivity;

import com.codefest2013.game.scenes.GameScene;
import com.codefest2013.game.scenes.SplashScene;

public class MainActivity extends BaseGameActivity
{
	private final int FPS = 60;
	
    private SplashScene mSplashScene;
    private Scene mMainScene;
    
    private ITextureRegion mSplashTextureRegion;
    private Music mSplashMusic;

    private enum SceneType
    {
        SPLASH,
        MAIN
    }
    private SceneType mCurrentScene = SceneType.SPLASH;
    
    public BoundCamera mCamera;
    
    private static MainActivity mInstance;
    
    public EngineOptions onCreateEngineOptions() {
    	mInstance = this;
    	ResourcesManager.init();

        mCamera = new BoundCamera(0, 0, ResourcesManager.CAMERA_WIDTH, ResourcesManager.CAMERA_HEIGHT,
        		0, ResourcesManager.WORLD_WIDTH, 0, ResourcesManager.WORLD_HEIGHT);
        mCamera.setBoundsEnabled(true);
        
        final EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR, 
                new RatioResolutionPolicy( ResourcesManager.CAMERA_WIDTH / ResourcesManager.CAMERA_HEIGHT), mCamera);
        engineOptions.getTouchOptions().setNeedsMultiTouch(true);
        engineOptions.getAudioOptions().setNeedsMusic(true);
        engineOptions.getAudioOptions().setNeedsSound(true);
        engineOptions.getRenderOptions().setDithering(true);
        engineOptions.getRenderOptions().setMultiSampling(true);
        engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
        return engineOptions;
    }

    @Override
    public Engine onCreateEngine(EngineOptions pEngineOptions) {
    	return new LimitedFPSEngine(pEngineOptions, FPS);
    }
    
    /**
     * Load resources for splash screen only
     * Another resources are loading in ResourcesManager class
     */
    @Override
    public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws Exception
    {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        BitmapTextureAtlas mSplashTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 256, 256, TextureOptions.DEFAULT);
        mSplashTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mSplashTextureAtlas, this, "splash.png", 0, 0);
        mSplashTextureAtlas.load();

        MusicFactory.setAssetBasePath("afx/");
        try {
        	mSplashMusic = MusicFactory.createMusicFromAsset(getMusicManager(), this, "splashMusic.mp3");
        } catch (IOException e) {
        e.printStackTrace();
        }
        
        pOnCreateResourcesCallback.onCreateResourcesFinished();
    }

    /**
     * Call splash screen scene at first
     */
    @Override
    public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws Exception
    {
        mSplashScene = new SplashScene(mEngine, mSplashTextureRegion, 
        		(int)ResourcesManager.CAMERA_WIDTH, (int)ResourcesManager.CAMERA_HEIGHT);
        pOnCreateSceneCallback.onCreateSceneFinished(mSplashScene);
    }

    @Override
    public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception
    {
        mEngine.registerUpdateHandler(new TimerHandler(1f, new ITimerCallback() 
        {
            public void onTimePassed(final TimerHandler pTimerHandler) 
            {	
                mEngine.unregisterUpdateHandler(pTimerHandler);
                ResourcesManager.getInstance().load();
                loadScenes();
                mSplashScene.detachSplash();
                mEngine.setScene(mMainScene);
                mCurrentScene = SceneType.MAIN;
            }
        }));
        pOnPopulateSceneCallback.onPopulateSceneFinished();
    }
    
    /**
     * Load scenes here
     */
    private void loadScenes()
    {
        mMainScene = new GameScene();
    }

    public static MainActivity getInstance() {
        return mInstance;
    }
}
