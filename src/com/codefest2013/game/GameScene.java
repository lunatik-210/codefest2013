package com.codefest2013.game;

import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.debug.Debug;

public class GameScene extends Scene implements IOnSceneTouchListener {

	private Player player;
	private int fingersNumber = 0;
	
	public GameScene()
	{
		setBackground(new Background(0, 0, 0.8784f));
		player = new Player(MainActivity.CAMERA_WIDTH/2, MainActivity.CAMERA_HEIGHT-MainActivity.CAMERA_HEIGHT/5);
		attachChild(player.sprite);
		registerUpdateHandler(player);
		setOnSceneTouchListener(this);
	}
	
	@Override
	public boolean onSceneTouchEvent(Scene arg0, TouchEvent arg1) {
		switch( arg1.getAction() )
		{
			case TouchEvent.ACTION_DOWN:
				++fingersNumber;
				if( arg1.getX() < MainActivity.CAMERA_WIDTH/2 )
				{
					player.setDirection(Player.LEFT_DIRECTION);
				}
				else
				{
					player.setDirection(Player.RIGHT_DIRECTION);
				}
				return true;
			case TouchEvent.ACTION_UP:
				if( --fingersNumber == 0 )
				{
					player.stop();
				}
				return true;
		}
		return false;
	}
	
}
