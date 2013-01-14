package com.codefest2013.game;

import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.BaseGameActivity;

import android.util.DisplayMetrics;
import android.view.WindowManager;

public class MainActivity extends BaseGameActivity
{
    private SplashScene mSplashScene;
    private Scene mMainScene;
    
    private BitmapTextureAtlas mSplashTextureAtlas;
    private ITextureRegion mSplashTextureRegion;

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
        
        final DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager)MainActivity.getInstance().getSystemService(MainActivity.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        wm.getDefaultDisplay().getRotation();
        
        final EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR, 
                new RatioResolutionPolicy( (float)displayMetrics.widthPixels / (float)displayMetrics.heightPixels), mCamera);
        engineOptions.getTouchOptions().setNeedsMultiTouch(true);
        
        return engineOptions;
    }

    /**
     * Load resources for splash screen only
     * Another resources are loading in ResourcesManager class
     */
    @Override
    public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws Exception
    {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        mSplashTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 256, 256, TextureOptions.DEFAULT);
        mSplashTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mSplashTextureAtlas, this, "splash.png", 0, 0);
        mSplashTextureAtlas.load();

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
        mEngine.registerUpdateHandler(new TimerHandler(3f, new ITimerCallback() 
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
