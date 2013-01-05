package com.codefest2013.game;

import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.svg.opengl.texture.atlas.bitmap.SVGBitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.util.GLState;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.debug.Debug;

import android.util.DisplayMetrics;
import android.view.WindowManager;

public class MainActivity extends BaseGameActivity
{
    private Scene mSplashScene;
    private Scene mMainScene;
    
    private BitmapTextureAtlas mSplashTextureAtlas;
    private ITextureRegion mSplashTextureRegion;
    private Sprite mSplash;
    
    private enum SceneType
    {
        SPLASH,
        MAIN
    }
    private SceneType mCurrentScene = SceneType.SPLASH;
    
    public static int CAMERA_WIDTH = 720;
    public static int CAMERA_HEIGHT = 480;
    public static int WORLD_WIDTH = 720;
    public static int WORLD_HEIGHT = 480;
    
    public BoundCamera camera;
    
    private BuildableBitmapTextureAtlas mBuildableBitmapTextureAtlas;
    public TiledTextureRegion goblinTextureRegion;
    
    public static MainActivity instance;
    
    public EngineOptions onCreateEngineOptions() {
        instance = this;
        
        final DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager)getSystemService(WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        wm.getDefaultDisplay().getRotation();
        CAMERA_WIDTH = displayMetrics.widthPixels;
        CAMERA_HEIGHT = displayMetrics.heightPixels;
        WORLD_WIDTH = CAMERA_WIDTH * 2;
        WORLD_HEIGHT = CAMERA_HEIGHT;       
        
        //camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
        camera = new BoundCamera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT, 0, WORLD_WIDTH, 0, WORLD_HEIGHT);
        camera.setBoundsEnabled(true);
        
        final EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR, 
                new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
        engineOptions.getTouchOptions().setNeedsMultiTouch(true);
        
        return engineOptions;
    }

    @Override
    public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws Exception
    {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        mSplashTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 256, 256, TextureOptions.DEFAULT);
        mSplashTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mSplashTextureAtlas, this, "splash.png", 0, 0);
        mSplashTextureAtlas.load();

        pOnCreateResourcesCallback.onCreateResourcesFinished();
    }

    @Override
    public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws Exception
    {
        initSplashScene();
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
                loadResources();
                loadScenes();         
                mSplash.detachSelf();
                mEngine.setScene(mMainScene);
                mCurrentScene = SceneType.MAIN;
            }
        }));

        pOnPopulateSceneCallback.onPopulateSceneFinished();
    }
    
    /**
     * Load resources while splash screen is on
     */
    public void loadResources() 
    {
        mBuildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas(this.getTextureManager(), 1024, 1024, TextureOptions.NEAREST);
        
        SVGBitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        this.goblinTextureRegion = (TiledTextureRegion)SVGBitmapTextureAtlasTextureRegionFactory
                .createTiledFromAsset(this.mBuildableBitmapTextureAtlas, this, "goblinWalks.svg", 1024, 1024, 12, 2);

        try {
            mBuildableBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
            mBuildableBitmapTextureAtlas.load();
        } catch (final TextureAtlasBuilderException e) {
            Debug.e(e);
        }
    }
    
    /**
     * Load scenes here
     */
    private void loadScenes()
    {
        mMainScene = new GameScene();
    }

    /**
     * Create splash screen
     */
    private void initSplashScene()
    {
        mSplashScene = new Scene();
        mSplash = new Sprite(0, 0, mSplashTextureRegion, mEngine.getVertexBufferObjectManager())
        {
            @Override
            protected void preDraw(GLState pGLState, Camera pCamera) 
            {
                super.preDraw(pGLState, pCamera);
                pGLState.enableDither();
            }
        };
        
        mSplash.setScale(1.5f);
        mSplash.setPosition((CAMERA_WIDTH - mSplash.getWidth()) * 0.5f, (CAMERA_HEIGHT - mSplash.getHeight()) * 0.5f);
        mSplashScene.attachChild(mSplash);
    }

    public static MainActivity getSharedInstance() {
        return instance;
    }
}
