package com.codefest2013.game.scenes;

import com.codefest2013.game.MainActivity;
import com.codefest2013.game.ResourcesManager;
import com.codefest2013.game.scenes.objects.Background;
import com.codefest2013.game.scenes.objects.Player;

import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.TouchEvent;

public class GameScene extends Scene implements IOnSceneTouchListener {

	private Player mPlayer = null;
    
    public GameScene()
    {
        // sprite as background
        //attachChild(new Sprite(0, 0, ResourcesManager.WORLD_WIDTH, ResourcesManager.WORLD_HEIGHT,
        //		ResourcesManager.getInstance().backgroundTextureRegion,
        //		MainActivity.getInstance().getVertexBufferObjectManager() ));
    	
    	attachChild(new Background());
        
		mPlayer = new Player(ResourcesManager.CAMERA_WIDTH/2, ResourcesManager.CAMERA_HEIGHT-ResourcesManager.CAMERA_HEIGHT/5);
		attachChild(mPlayer.sprite);
		registerUpdateHandler(mPlayer);
		
		setOnSceneTouchListener(this);
        MainActivity.getInstance().mCamera.setChaseEntity(mPlayer.sprite);
    }

	@Override
	public boolean onSceneTouchEvent(Scene arg0, TouchEvent arg1) {
		return mPlayer.onSceneTouchEvent(arg0, arg1);
	}
    
}
