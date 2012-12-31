package com.codefest2013.game;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.extension.svg.opengl.texture.atlas.bitmap.SVGBitmapTextureAtlasTextureRegionFactory;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.debug.Debug;

import android.view.KeyEvent;

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

	private static final int CAMERA_WIDTH = 720;
	private static final int CAMERA_HEIGHT = 480;

	// ===========================================================
	// Fields
	// ===========================================================

	private Camera mCamera;
	public SceneType currentScene = SceneType.SPLASH;
	
	private BuildableBitmapTextureAtlas mBuildableBitmapTextureAtlas;
	private TiledTextureRegion textureRegion;
	private AnimatedSprite player;
	
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
	    mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
	    return new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR, 
	    		new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), mCamera);
	}

	@Override
	protected void onCreateResources() {
		// TODO Auto-generated method stub
		this.mBuildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas(this.getTextureManager(), 1024, 1024, TextureOptions.NEAREST);
	
		SVGBitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		this.textureRegion = (TiledTextureRegion)SVGBitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBuildableBitmapTextureAtlas, this, "goblinRight.svg", 1024, 1024, 12, 1);

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

		final Scene scene = new Scene();
		
		scene.setBackground(new Background(0, 0, 0.8784f));
		
		player = new AnimatedSprite(100, 100, 128, 128, textureRegion, this.getVertexBufferObjectManager());
		player.animate(50);
		scene.attachChild(player);
		
		scene.setOnSceneTouchListener( new IOnSceneTouchListener() {
				@Override
				public boolean onSceneTouchEvent(Scene arg0, TouchEvent arg1) {
					Debug.d("Touch screen1: " + mCamera.getHeight() + " " + mCamera.getWidth() );
					Debug.d("Touch screen2: " + arg1.getX() + " " + arg1.getY() );
					if( arg1.getX() < mCamera.getWidth()/2 )
					{
						player.setPosition(player.getX()-5, player.getY());
					}
					else
					{
						player.setPosition(player.getX()+5, player.getY());
					}
					return true;
				}
			});
		
		return scene;
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

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
