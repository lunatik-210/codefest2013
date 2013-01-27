package com.codefest2013.game.scenes.objects;

import org.andengine.entity.Entity;
import org.andengine.entity.sprite.Sprite;

import com.codefest2013.game.MainActivity;
import com.codefest2013.game.MegaAnimatedSprite;
import com.codefest2013.game.ResourcesManager;

public class Background extends Entity {
	
	private final float FIREPLACE_WIDTH = 580.0f * ResourcesManager.WORLD_SCALE_CONSTANT;
	private final float FIREPLACE_HEIGHT = 510.0f * ResourcesManager.WORLD_SCALE_CONSTANT;
	private final float FIREPLACE_X = 1425.6f * ResourcesManager.WORLD_SCALE_CONSTANT;
	private final float FIREPLACE_Y = 406.0f * ResourcesManager.WORLD_SCALE_CONSTANT;
	
	private final float TREE_WIDTH = 490.0f * ResourcesManager.WORLD_SCALE_CONSTANT;
	private final float TREE_HEIGHT = 774.0f * ResourcesManager.WORLD_SCALE_CONSTANT;
	private final float TREE_X = 280.0f * ResourcesManager.WORLD_SCALE_CONSTANT;
	private final float TREE_Y = 97.0f * ResourcesManager.WORLD_SCALE_CONSTANT;
	
	private final float CLOCK_WIDTH = 150.0f * ResourcesManager.WORLD_SCALE_CONSTANT;
	private final float CLOCK_HEIGHT = 608.0f * ResourcesManager.WORLD_SCALE_CONSTANT;
	private final float CLOCK_X = 32.0f * ResourcesManager.WORLD_SCALE_CONSTANT;
	private final float CLOCK_Y = 232.0f * ResourcesManager.WORLD_SCALE_CONSTANT;
	
	private final float LAMP_WIDTH = 250.0f * ResourcesManager.WORLD_SCALE_CONSTANT;
	private final float LAMP_HEIGHT = 170.0f * ResourcesManager.WORLD_SCALE_CONSTANT;
	private final float LAMP_X = 870.0f * ResourcesManager.WORLD_SCALE_CONSTANT;
	private final float LAMP_Y = 0.0f * ResourcesManager.WORLD_SCALE_CONSTANT;
	
	private final float STOCKING_WIDTH = 90.0f * ResourcesManager.WORLD_SCALE_CONSTANT;
	private final float STOCKING_HEIGHT = 140.0f * ResourcesManager.WORLD_SCALE_CONSTANT;
	private final float STOCKING_X = 1534.5f * ResourcesManager.WORLD_SCALE_CONSTANT;
	private final float STOCKING_Y = 386.1f * ResourcesManager.WORLD_SCALE_CONSTANT;
	
	private final float STEP_X = 39.6f * ResourcesManager.WORLD_SCALE_CONSTANT;
	private final float STEP_Y = 9.9f * ResourcesManager.WORLD_SCALE_CONSTANT;
	
	private MegaAnimatedSprite clockSprite;
	private MegaAnimatedSprite firePlaceSprite;
	
	public Background()
	{
		Sprite sprite = null;
		
		sprite = new Sprite(0, 0, ResourcesManager.WORLD_WIDTH/2, ResourcesManager.WORLD_HEIGHT/2,
        		ResourcesManager.getInstance().bgLT,
        		MainActivity.getInstance().getVertexBufferObjectManager());
		sprite.setIgnoreUpdate(true);
		attachChild(sprite);
		
		sprite = new Sprite(ResourcesManager.WORLD_WIDTH/2, 0, ResourcesManager.WORLD_WIDTH/2, 
				ResourcesManager.WORLD_HEIGHT/2,
        		ResourcesManager.getInstance().bgRT,
        		MainActivity.getInstance().getVertexBufferObjectManager());
		sprite.setIgnoreUpdate(true);
		attachChild(sprite);
		
		sprite = new Sprite(0, ResourcesManager.WORLD_HEIGHT/2, ResourcesManager.WORLD_WIDTH/2, 
				ResourcesManager.WORLD_HEIGHT/2,
        		ResourcesManager.getInstance().bgLB,
        		MainActivity.getInstance().getVertexBufferObjectManager()); 
		sprite.setIgnoreUpdate(true);
		attachChild(sprite);
		
		sprite = new Sprite(ResourcesManager.WORLD_WIDTH/2, ResourcesManager.WORLD_HEIGHT/2, 
					ResourcesManager.WORLD_WIDTH/2, ResourcesManager.WORLD_HEIGHT/2,
	        		ResourcesManager.getInstance().bgRB,
	        		MainActivity.getInstance().getVertexBufferObjectManager());
		sprite.setIgnoreUpdate(true);
		attachChild(sprite);
		
		sprite = new Sprite(TREE_X, TREE_Y, TREE_WIDTH, TREE_HEIGHT, 
				ResourcesManager.getInstance().tree, MainActivity.getInstance().getVertexBufferObjectManager());
		sprite.setIgnoreUpdate(true);
		attachChild(sprite);
		
		sprite = new Sprite(LAMP_X, LAMP_Y, LAMP_WIDTH, LAMP_HEIGHT, 
				ResourcesManager.getInstance().lamp, MainActivity.getInstance().getVertexBufferObjectManager());
		sprite.setIgnoreUpdate(true);
		attachChild(sprite);
		
		clockSprite = new MegaAnimatedSprite(10);
		for( int i=0; i<10; ++i )
		{
			clockSprite.attachTexture( new Sprite(CLOCK_X, CLOCK_Y, CLOCK_WIDTH, CLOCK_HEIGHT,
					ResourcesManager.getInstance().clock[i], MainActivity.getInstance().getVertexBufferObjectManager() ) );
		}
		clockSprite.animate(1.0f / 6f);
		attachChild(clockSprite);
		
		firePlaceSprite = new MegaAnimatedSprite(2);
		firePlaceSprite.attachTexture( new Sprite(FIREPLACE_X, FIREPLACE_Y, FIREPLACE_WIDTH, FIREPLACE_HEIGHT,
				ResourcesManager.getInstance().fireplace1, MainActivity.getInstance().getVertexBufferObjectManager() ) );
		firePlaceSprite.attachTexture( new Sprite(FIREPLACE_X, FIREPLACE_Y, FIREPLACE_WIDTH, FIREPLACE_HEIGHT,
				ResourcesManager.getInstance().fireplace2, MainActivity.getInstance().getVertexBufferObjectManager() ) );
		firePlaceSprite.animate(1.0f / 6f);
		attachChild(firePlaceSprite);
		
		sprite = new Sprite(STOCKING_X, STOCKING_Y, STOCKING_WIDTH, STOCKING_HEIGHT, 
				ResourcesManager.getInstance().stocking, MainActivity.getInstance().getVertexBufferObjectManager());
		sprite.setIgnoreUpdate(true);
		attachChild(sprite);

		sprite = new Sprite(STOCKING_X + STOCKING_WIDTH + STEP_X, STOCKING_Y+STEP_Y, STOCKING_WIDTH, STOCKING_HEIGHT, 
				ResourcesManager.getInstance().stocking, MainActivity.getInstance().getVertexBufferObjectManager());
		sprite.setIgnoreUpdate(true);
		attachChild(sprite);
		
		sprite = new Sprite(STOCKING_X + STOCKING_WIDTH*2 + STEP_X*2, STOCKING_Y+STEP_Y*2, STOCKING_WIDTH, STOCKING_HEIGHT, 
				ResourcesManager.getInstance().stocking, MainActivity.getInstance().getVertexBufferObjectManager());
		sprite.setIgnoreUpdate(true);
		attachChild(sprite);
	}
	
	public Sprite getClockSprite() {
		return clockSprite.getCurrentSprite();
	}

	public Sprite getFirePlaceSprite() {
		return firePlaceSprite.getCurrentSprite();
	}
	
	
}
