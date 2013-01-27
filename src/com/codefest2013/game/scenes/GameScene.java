package com.codefest2013.game.scenes;

import com.codefest2013.game.MainActivity;
import com.codefest2013.game.ResourcesManager;
import com.codefest2013.game.scenes.objects.Background;
import com.codefest2013.game.scenes.objects.Fox;
import com.codefest2013.game.scenes.objects.Player;

import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.debug.Debug;

public class GameScene extends Scene implements IOnSceneTouchListener {

	private Player mPlayer = null;
	private Fox mFox = null;
    private Background mBackground = null;
    
    public GameScene()
    {
    	mBackground = new Background();
    	mPlayer = new Player(ResourcesManager.CAMERA_WIDTH/2, 
    			ResourcesManager.CAMERA_HEIGHT-ResourcesManager.CAMERA_HEIGHT/5);
    	//mFox = new Fox(300,300);
    	
    	attachChild(mBackground);
		attachChild(mPlayer);
		//attachChild(mFox);
		
		registerUpdateHandler(mPlayer);
		
		setOnSceneTouchListener(this);
        MainActivity.getInstance().mCamera.setChaseEntity(mPlayer);
        
		ResourcesManager.getInstance().fireplaceMusic.play();
		ResourcesManager.getInstance().fireplaceMusic.setVolume(1.0f);
    }

	@Override
	public boolean onSceneTouchEvent(Scene arg0, TouchEvent arg1) {
		return mPlayer.onSceneTouchEvent(arg0, arg1);
	}
    
	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);
		//Debug.d("Fireplace: " + mBackground.getFirePlaceSprite().get() + " " + 
		//		mBackground.getFirePlaceSprite().getScaleCenterY() );
		
	}
}
