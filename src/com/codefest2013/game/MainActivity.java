package com.codefest2013.game;

import org.andengine.engine.Engine;
import org.andengine.engine.LimitedFPSEngine;
import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;

import org.andengine.ui.activity.SimpleBaseGameActivity;

import com.codefest2013.game.managers.ResourceManager;
import com.codefest2013.game.managers.SceneManager;
import com.codefest2013.game.scenes.ManagedGameScene;

public class MainActivity extends SimpleBaseGameActivity
{
	private final int FPS = 60;
    
    private ResourceManager mResourceManager = ResourceManager.getInstance();

    public EngineOptions onCreateEngineOptions() {
    	mResourceManager.setupContext(this);
    	
    	BoundCamera camera = new BoundCamera(0, 0, mResourceManager.CAMERA_WIDTH, mResourceManager.CAMERA_HEIGHT,
        		0, mResourceManager.WORLD_WIDTH, 0, mResourceManager.WORLD_HEIGHT);
    	camera.setBoundsEnabled(true);
        
        final EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR, 
                new RatioResolutionPolicy( mResourceManager.CAMERA_WIDTH / mResourceManager.CAMERA_HEIGHT), camera);
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
    	final Engine engine = new LimitedFPSEngine(pEngineOptions, FPS);
    	mResourceManager.setupEngine(engine);
    	return engine;
    }
    
	@Override
	protected void onCreateResources() {
	}

	@Override
	protected Scene onCreateScene() {
		SceneManager.getInstance().showScene(new ManagedGameScene());
		return mEngine.getScene();
	}
}
