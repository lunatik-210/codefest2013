package com.codefest2013.game.world;

import org.andengine.engine.handler.IUpdateHandler;

import com.codefest2013.game.ResourcesManager;

public class World implements IUpdateHandler {

	private Player mPlayer = null;
	//private Fox fox = null;
	
	public World()
	{
		mPlayer = new Player(ResourcesManager.CAMERA_WIDTH/2, ResourcesManager.CAMERA_HEIGHT-ResourcesManager.CAMERA_HEIGHT/5);
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
