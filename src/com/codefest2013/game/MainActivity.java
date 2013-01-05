package com.codefest2013.game;

import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.util.FPSLogger;
import org.andengine.extension.svg.opengl.texture.atlas.bitmap.SVGBitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.debug.Debug;

import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.WindowManager;

public class MainActivity extends SimpleBaseGameActivity {
	
	public enum SceneType
	{
		SPLASH,
		MENU,
		OPTIONS,
		GAME
	};
	
	// ===========================================================
	// Constants
	// ===========================================================
	
	public static int CAMERA_WIDTH = 720;
	public static int CAMERA_HEIGHT = 480;
	
	public static int WORLD_WIDTH = 720;
	public static int WORLD_HEIGHT = 480;

	// ===========================================================
	// Fields
	// ===========================================================

	public BoundCamera mCamera;
	public SceneType currentScene = SceneType.SPLASH;
	
	private BuildableBitmapTextureAtlas mBuildableBitmapTextureAtlas;
	public TiledTextureRegion goblinTextureRegion;
	
	public static MainActivity instance;
	
	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
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
		
	    //mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
	    mCamera = new BoundCamera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT, 0, WORLD_WIDTH, 0, WORLD_HEIGHT);
	    mCamera.setBoundsEnabled(true);
	    
	    final EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR, 
	    		new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), mCamera);
	    engineOptions.getTouchOptions().setNeedsMultiTouch(true);
	    
	    return engineOptions;
	}

	@Override
	protected void onCreateResources() {
		// TODO Auto-generated method stub
		this.mBuildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas(this.getTextureManager(), 1024, 1024, TextureOptions.NEAREST);
	
		SVGBitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		this.goblinTextureRegion = (TiledTextureRegion)SVGBitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.mBuildableBitmapTextureAtlas, this, "goblinWalks.svg", 1024, 1024, 12, 2);

		try {
			this.mBuildableBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
			this.mBuildableBitmapTextureAtlas.load();
		} catch (final TextureAtlasBuilderException e) {
			Debug.e(e);
		}

	}

	@Override
	protected Scene onCreateScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());
		return new GameScene();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN )
		{
			switch(currentScene)
			{
				case SPLASH:
					System.exit(0);
					break;
				case MENU:
					System.exit(0);
				case OPTIONS:
					break;
				case GAME:
					break;
			}
		}
		return false;
	}

	public static MainActivity getSharedInstance() {
	    return instance;
	}
	
	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
