package com.codefest2013.game.world;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.scene.Scene;

import com.codefest2013.game.ResourcesManager;

public class World implements IUpdateHandler {

	private Player mPlayer = null;
	//private Fox fox = null;
	
	public World(Scene scene)
	{
		mPlayer = new Player(ResourcesManager.CAMERA_WIDTH/2, ResourcesManager.CAMERA_HEIGHT-ResourcesManager.CAMERA_HEIGHT/5);
		scene.attachChild(mPlayer.sprite);
		
		scene.registerUpdateHandler(this);
	}

	@Override
	public void onUpdate(float arg0) {
		mPlayer.move();
	}

	@Override
	public void reset() {
	}
	
	public Player getPlayer() {
		return mPlayer;
	}

}
