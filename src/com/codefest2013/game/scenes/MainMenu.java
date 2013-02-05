package com.codefest2013.game.scenes;

import org.andengine.entity.Entity;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;

import com.codefest2013.game.managers.ResourceManager;
import com.codefest2013.game.managers.SceneManager;

public class MainMenu extends ManagedScene {
	
	private ResourceManager mResourceManager = ResourceManager.getInstance();
	
	private ManagedScene thisManagedMainMenu = this;
	
	private final float BUTTON_SCALE_FACTOR = 1.921052632f;
	private final float BUTTON_HEIGHT = mResourceManager.CAMERA_HEIGHT / 4;
	private final float BUTTON_WIDTH = BUTTON_HEIGHT * BUTTON_SCALE_FACTOR;
	
	private final float BUTTON_PANEL_X = mResourceManager.CAMERA_WIDTH / 2 - BUTTON_WIDTH / 2;
	private final float BUTTON_PANEL_Y = (mResourceManager.CAMERA_HEIGHT - BUTTON_HEIGHT*3) / 3;
	
	private final float BUTTON_Y_BIAS = BUTTON_PANEL_Y / 3;
	
	private ButtonSprite startButton = null;
	private ButtonSprite aboutButton = null;
	private ButtonSprite exitButton = null;
	
	public MainMenu() {
		this(0.0f);
	}

	public MainMenu(float pLoadingScreenMinimumSecondsShown) {
		super(pLoadingScreenMinimumSecondsShown);
		this.setTouchAreaBindingOnActionDownEnabled(true);
		this.setTouchAreaBindingOnActionMoveEnabled(true);
	}

	@Override
	public Scene onLoadingScreenLoadAndShown() {
		return null;
	}

	@Override
	public void onLoadingScreenUnloadAndHidden() {
	}

	@Override
	public void onLoadScene() {		
		Entity buttonPanel = new Entity(BUTTON_PANEL_X, BUTTON_PANEL_Y);
		startButton = createButton(0, 0, new OnClickListener() {
			@Override
			public void onClick(ButtonSprite arg0, float arg1, float arg2) {
				SceneManager.getInstance().showScene(new GameScene());
			}
		});
		aboutButton = createButton(0, BUTTON_Y_BIAS+BUTTON_HEIGHT, new OnClickListener() {
			@Override
			public void onClick(ButtonSprite arg0, float arg1, float arg2) {
				// TODO: smth
			}
		});
		exitButton  = createButton(0, BUTTON_Y_BIAS*2+BUTTON_HEIGHT*2, new OnClickListener() {
			@Override
			public void onClick(ButtonSprite arg0, float arg1, float arg2) {
				ResourceManager.unloadGameResources();
				ResourceManager.unloadSharedResources();
				System.exit(0);
			}
		});
		this.registerTouchArea(startButton);
		this.registerTouchArea(aboutButton);
		this.registerTouchArea(exitButton);
		buttonPanel.attachChild(startButton);
		buttonPanel.attachChild(aboutButton);
		buttonPanel.attachChild(exitButton);
		this.attachChild(buttonPanel);
	}

	@Override
	public void onShowScene() {
		if( !mResourceManager.splashMusic.isPlaying() )
		{
			mResourceManager.splashMusic.play();
		}
	}

	@Override
	public void onHideScene() {
		this.unregisterTouchArea(startButton);
		this.unregisterTouchArea(aboutButton);
		this.unregisterTouchArea(exitButton);
		mResourceManager.splashMusic.pause();
	}

	@Override
	public void onUnloadScene() {
		mResourceManager.engine.runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
				thisManagedMainMenu.detachChildren();
				for(int i = 0; i < thisManagedMainMenu.getChildCount(); i++)
					thisManagedMainMenu.getChildByIndex(i).dispose();
				thisManagedMainMenu.clearEntityModifiers();
				thisManagedMainMenu.clearTouchAreas();
				thisManagedMainMenu.clearUpdateHandlers();
			}});
	}
	
	private ButtonSprite createButton(float x, float y, OnClickListener clickListener)
	{
		ButtonSprite sprite = new ButtonSprite(x, y, mResourceManager.button,
				mResourceManager.engine.getVertexBufferObjectManager(), clickListener);
		sprite.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		return sprite;
	}

}
