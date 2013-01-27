package com.codefest2013.game.scenes;

import com.codefest2013.game.MainActivity;
import com.codefest2013.game.ResourcesManager;
import com.codefest2013.game.scenes.objects.Background;
import com.codefest2013.game.scenes.objects.Squirrel;
import com.codefest2013.game.scenes.objects.Player;
import com.codefest2013.game.scenes.objects.WayPoint;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.TouchEvent;

public class GameScene extends Scene implements IOnSceneTouchListener {

	private Player mPlayer = null;
	private Squirrel mFox = null;
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
		
		WayPoint wp[] = {
				new WayPoint(100, 220, 0, 0, false),
				new WayPoint(172, 260, 0, 0, false),
				new WayPoint(218, 184, 0, 0, false),
				new WayPoint(274, 76, 0, 0, false),
				new WayPoint(408, 112, 0, 0, false),
				new WayPoint(476, 260, 0, 0, false),
				new WayPoint(526, 98, 0, 0, false),
				new WayPoint(664, 374, 0, 0, false),
				new WayPoint(664, 374, 0, 0, false),
				new WayPoint(718, 264, 0, 0, false),
				new WayPoint(950, 262, 0, 0, false),
				new WayPoint(1002, 404, 0, 0, false),
				new WayPoint(1082, 494, 0, 0, false),
				new WayPoint(1282, 522, 0, 0, false),
				new WayPoint(1364, 480, 0, 0, false),
				new WayPoint(1654, 210, 0, 0, false)
		};
		
		for(int i=0; i<wp.length; i++)
		{
			attachChild(new Rectangle(wp[i].x*ResourcesManager.WORLD_SCALE_CONSTANT, wp[i].y*ResourcesManager.WORLD_SCALE_CONSTANT, 8, 8, MainActivity.getInstance().getVertexBufferObjectManager()));
		}
		
		registerUpdateHandler(mPlayer);
		
		setOnSceneTouchListener(this);
        MainActivity.getInstance().mCamera.setChaseEntity(mPlayer);

		ResourcesManager.getInstance().fireplaceMusic.play();
		ResourcesManager.getInstance().tickTookMusic.play();
		ResourcesManager.getInstance().fireplaceMusic.setVolume(0.0f);
		ResourcesManager.getInstance().tickTookMusic.setVolume(0.0f);
		ResourcesManager.getInstance().fireplaceMusic.setLooping(true);
		ResourcesManager.getInstance().tickTookMusic.setLooping(true);
    }
    
	@Override
	public boolean onSceneTouchEvent(Scene arg0, TouchEvent arg1) {
		return mPlayer.onSceneTouchEvent(arg0, arg1);
	}
    
	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		float val = Math.abs(mBackground.getFirePlaceSprite().getSceneCenterCoordinates()[0]-mPlayer.getX());
		float threshold = 1000.0f*ResourcesManager.WORLD_SCALE_CONSTANT;
		if(val > threshold) {
			ResourcesManager.getInstance().fireplaceMusic.setVolume(0.0f);
		}
		else {
			ResourcesManager.getInstance().fireplaceMusic.setVolume(1-(val/threshold));
		}
	
		val = Math.abs(mBackground.getClockSprite().getSceneCenterCoordinates()[0]-mPlayer.getX());
		threshold = 600.0f*ResourcesManager.WORLD_SCALE_CONSTANT;
		if(val > threshold) {
			ResourcesManager.getInstance().tickTookMusic.setVolume(0.0f);
		}
		else {
			ResourcesManager.getInstance().tickTookMusic.setVolume(1-(val/threshold));
		}
		super.onManagedUpdate(pSecondsElapsed);
	}
}
