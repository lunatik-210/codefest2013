package com.codefest2013.game.scenes;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;

import com.codefest2013.game.managers.ResourceManager;
import com.codefest2013.game.managers.SceneManager;

/**
 * Splash screen scene
 */
public class SplashScene extends ManagedScene {
	public SplashScene() {
		this(2.0f);
	}

	public SplashScene(float pLoadingScreenMinimumSecondsShown)
	{
		super(pLoadingScreenMinimumSecondsShown);
	}
	
	@Override
	public Scene onLoadingScreenLoadAndShown() {
		ResourceManager.loadSharedResources();
		ResourceManager mResourceManager = ResourceManager.getInstance();
		
        Sprite mSplash = new Sprite(0, 0, mResourceManager.splashTextureRegion, mResourceManager.engine.getVertexBufferObjectManager());
        mSplash.setPosition((mResourceManager.CAMERA_WIDTH - mSplash.getWidth()) * 0.5f,
        		(mResourceManager.CAMERA_HEIGHT - mSplash.getHeight()) * 0.5f);
        
        Scene scene = new Scene();
		scene.attachChild(mSplash);
		
		mResourceManager.splashMusic.play();
		mResourceManager.splashMusic.setLooping(true);
		return scene;
	}

	@Override
	public void onLoadingScreenUnloadAndHidden() {
	}

	@Override
	public void onLoadScene() {
		ResourceManager.loadGameResources();
	}

	@Override
	public void onShowScene() {
		SceneManager.getInstance().showScene(new MainMenu());
	}

	@Override
	public void onHideScene() {
	}

	@Override
	public void onUnloadScene() {
	}
}
