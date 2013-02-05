package com.codefest2013.game.scenes.objects;

import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.RotationAtModifier;
import org.andengine.entity.sprite.Sprite;

import com.codefest2013.game.MegaAnimatedSprite;
import com.codefest2013.game.managers.ResourceManager;

public class Background extends Entity {
	
	private ResourceManager mResourceManager = ResourceManager.getInstance();
	
	private final float FIREPLACE_WIDTH = 580.0f * mResourceManager.WORLD_SCALE_CONSTANT;
	private final float FIREPLACE_HEIGHT = 510.0f * mResourceManager.WORLD_SCALE_CONSTANT;
	private final float FIREPLACE_X = 1425.6f * mResourceManager.WORLD_SCALE_CONSTANT;
	private final float FIREPLACE_Y = 406.0f * mResourceManager.WORLD_SCALE_CONSTANT;
	
	private final float TREE_WIDTH = 490.0f * mResourceManager.WORLD_SCALE_CONSTANT;
	private final float TREE_HEIGHT = 774.0f * mResourceManager.WORLD_SCALE_CONSTANT;
	private final float TREE_X = 280.0f * mResourceManager.WORLD_SCALE_CONSTANT;
	private final float TREE_Y = 97.0f * mResourceManager.WORLD_SCALE_CONSTANT;
	
	private final float CLOCK_WIDTH = 150.0f * mResourceManager.WORLD_SCALE_CONSTANT;
	private final float CLOCK_HEIGHT = 608.0f * mResourceManager.WORLD_SCALE_CONSTANT;
	private final float CLOCK_X = 32.0f * mResourceManager.WORLD_SCALE_CONSTANT;
	private final float CLOCK_Y = 232.0f * mResourceManager.WORLD_SCALE_CONSTANT;
	
	private final float LAMP_WIDTH = 250.0f * mResourceManager.WORLD_SCALE_CONSTANT;
	private final float LAMP_HEIGHT = 170.0f * mResourceManager.WORLD_SCALE_CONSTANT;
	private final float LAMP_X = 870.0f * mResourceManager.WORLD_SCALE_CONSTANT;
	private final float LAMP_Y = 0.0f * mResourceManager.WORLD_SCALE_CONSTANT;
	
	private final float STOCKING_WIDTH = 90.0f * mResourceManager.WORLD_SCALE_CONSTANT;
	private final float STOCKING_HEIGHT = 140.0f * mResourceManager.WORLD_SCALE_CONSTANT;
	private final float STOCKING_X = 1534.5f * mResourceManager.WORLD_SCALE_CONSTANT;
	private final float STOCKING_Y = 386.1f * mResourceManager.WORLD_SCALE_CONSTANT;
	
	private final float STEP_X = 39.6f * mResourceManager.WORLD_SCALE_CONSTANT;
	private final float STEP_Y = 9.9f * mResourceManager.WORLD_SCALE_CONSTANT;
	
	private MegaAnimatedSprite clockSprite;
	private MegaAnimatedSprite firePlaceSprite;
	
	public Background()
	{
		Sprite sprite = null;
		
		sprite = new Sprite(0, 0, mResourceManager.WORLD_WIDTH/2, mResourceManager.WORLD_HEIGHT/2,
				mResourceManager.bgLT,
        		mResourceManager.engine.getVertexBufferObjectManager());
		sprite.setIgnoreUpdate(true);
		attachChild(sprite);
		
		sprite = new Sprite(mResourceManager.WORLD_WIDTH/2, 0, mResourceManager.WORLD_WIDTH/2, 
				mResourceManager.WORLD_HEIGHT/2, mResourceManager.bgRT,
        		mResourceManager.engine.getVertexBufferObjectManager());
		sprite.setIgnoreUpdate(true);
		attachChild(sprite);
		
		sprite = new Sprite(0, mResourceManager.WORLD_HEIGHT/2, mResourceManager.WORLD_WIDTH/2, 
				mResourceManager.WORLD_HEIGHT/2, mResourceManager.bgLB,
        		mResourceManager.engine.getVertexBufferObjectManager()); 
		sprite.setIgnoreUpdate(true);
		attachChild(sprite);
		
		sprite = new Sprite(mResourceManager.WORLD_WIDTH/2, mResourceManager.WORLD_HEIGHT/2, 
				mResourceManager.WORLD_WIDTH/2, mResourceManager.WORLD_HEIGHT/2, mResourceManager.bgRB,
				mResourceManager.engine.getVertexBufferObjectManager());
		sprite.setIgnoreUpdate(true);
		attachChild(sprite);
		
		sprite = new Sprite(TREE_X, TREE_Y, TREE_WIDTH, TREE_HEIGHT, 
				mResourceManager.tree, mResourceManager.engine.getVertexBufferObjectManager());
		sprite.setIgnoreUpdate(true);
		attachChild(sprite);
		
		sprite = new Sprite(LAMP_X, LAMP_Y, LAMP_WIDTH, LAMP_HEIGHT, 
				mResourceManager.lamp, mResourceManager.engine.getVertexBufferObjectManager());
		RotationAtModifier rotationAtModifier = new RotationAtModifier(1.5f, -20.0f, 20.0f, LAMP_WIDTH/2, 0) {
			@Override
			protected void onModifierFinished(IEntity pItem) {
				reset(getDuration(), getToValue(), getFromValue());
				super.onModifierFinished(pItem);
			}
		};
		sprite.registerEntityModifier(rotationAtModifier);
		attachChild(sprite);
		
		clockSprite = new MegaAnimatedSprite(10);
		for( int i=0; i<10; ++i )
		{
			clockSprite.attachTexture( new Sprite(CLOCK_X, CLOCK_Y, CLOCK_WIDTH, CLOCK_HEIGHT,
					mResourceManager.clock[i], mResourceManager.engine.getVertexBufferObjectManager() ) );
		}
		clockSprite.animate(1.0f / 6f);
		attachChild(clockSprite);
		
		firePlaceSprite = new MegaAnimatedSprite(2);
		firePlaceSprite.attachTexture( new Sprite(FIREPLACE_X, FIREPLACE_Y, FIREPLACE_WIDTH, FIREPLACE_HEIGHT,
				mResourceManager.fireplace1, mResourceManager.engine.getVertexBufferObjectManager() ) );
		firePlaceSprite.attachTexture( new Sprite(FIREPLACE_X, FIREPLACE_Y, FIREPLACE_WIDTH, FIREPLACE_HEIGHT,
				mResourceManager.fireplace2, mResourceManager.engine.getVertexBufferObjectManager() ) );
		firePlaceSprite.animate(1.0f / 6f);
		attachChild(firePlaceSprite);
		
		sprite = new Sprite(STOCKING_X, STOCKING_Y, STOCKING_WIDTH, STOCKING_HEIGHT, 
				mResourceManager.stocking, mResourceManager.engine.getVertexBufferObjectManager());
		sprite.setIgnoreUpdate(true);
		attachChild(sprite);

		sprite = new Sprite(STOCKING_X + STOCKING_WIDTH + STEP_X, STOCKING_Y+STEP_Y, STOCKING_WIDTH, STOCKING_HEIGHT, 
				mResourceManager.stocking, mResourceManager.engine.getVertexBufferObjectManager());
		sprite.setIgnoreUpdate(true);
		attachChild(sprite);
		
		sprite = new Sprite(STOCKING_X + STOCKING_WIDTH*2 + STEP_X*2, STOCKING_Y+STEP_Y*2, STOCKING_WIDTH, STOCKING_HEIGHT, 
				mResourceManager.stocking, mResourceManager.engine.getVertexBufferObjectManager());
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
