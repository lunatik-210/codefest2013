package com.codefest2013.game.scenes.objects;

import org.andengine.audio.music.Music;
import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.RotationAtModifier;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;

import android.opengl.GLES20;

import com.codefest2013.game.MegaAnimatedSprite;
import com.codefest2013.game.managers.ResourceManager;

public class Background extends Entity {
	
	private ResourceManager mResourceManager = ResourceManager.getInstance();
	
	private final float FIREPLACE_WIDTH = 580.0f * mResourceManager.WORLD_SCALE_CONSTANT;
	private final float FIREPLACE_HEIGHT = 510.0f * mResourceManager.WORLD_SCALE_CONSTANT;
	private final float FIREPLACE_X = 1425.6f * mResourceManager.WORLD_SCALE_CONSTANT;
	private final float FIREPLACE_Y = 406.0f * mResourceManager.WORLD_SCALE_CONSTANT;
	
	private final float FIREPLACE_LIGHT_MASK_WIDTH = 631.0f * mResourceManager.WORLD_SCALE_CONSTANT;
	private final float FIREPLACE_LIGHT_MASK_HEIGHT = 168.0f * mResourceManager.WORLD_SCALE_CONSTANT;
	private final float FIREPLACE_LIGHT_MASK_X = 1328.0f * mResourceManager.WORLD_SCALE_CONSTANT;
	private final float FIREPLACE_LIGHT_MASK_Y = 820.0f * mResourceManager.WORLD_SCALE_CONSTANT;	
	
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
	
	private final float LAMP_ROTATION_SPEED = 2.0f;
	private final float LAMP_ROTATION_ANGLE = 4.0f;
	private final float LAMP_ROTATION_CENTER_X = LAMP_WIDTH/2;
	private final float LAMP_ROTATION_CENTER_Y = -20f * mResourceManager.WORLD_SCALE_CONSTANT;
	
	private final float LAMP_LIGHT_MASK_WIDTH = 629.0f * mResourceManager.WORLD_SCALE_CONSTANT;
	private final float LAMP_LIGHT_MASK_HEIGHT = 839.0f * mResourceManager.WORLD_SCALE_CONSTANT;
	private final float LAMP_LIGHT_MASK_X = -191.0f * mResourceManager.WORLD_SCALE_CONSTANT;
	private final float LAMP_LIGHT_MASK_Y = 113.0f * mResourceManager.WORLD_SCALE_CONSTANT;	
	
	private final float STOCKING_WIDTH = 90.0f * mResourceManager.WORLD_SCALE_CONSTANT;
	private final float STOCKING_HEIGHT = 140.0f * mResourceManager.WORLD_SCALE_CONSTANT;
	private final float STOCKING_X = 1534.5f * mResourceManager.WORLD_SCALE_CONSTANT;
	private final float STOCKING_Y = 386.1f * mResourceManager.WORLD_SCALE_CONSTANT;
	
	private final float STEP_X = 39.6f * mResourceManager.WORLD_SCALE_CONSTANT;
	private final float STEP_Y = 9.9f * mResourceManager.WORLD_SCALE_CONSTANT;
	
	private MegaAnimatedSprite clockSprite;
	private MegaAnimatedSprite firePlaceSprite;
	
	private Player mPlayer = null;
	
	public Background( Player player )
	{
		setPlayer(player);
		
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
		
		sprite = new Sprite(LAMP_X, LAMP_Y, LAMP_WIDTH, LAMP_HEIGHT, 
				mResourceManager.lamp, mResourceManager.engine.getVertexBufferObjectManager());
		RotationAtModifier rotationAtModifier = new RotationAtModifier(LAMP_ROTATION_SPEED, -LAMP_ROTATION_ANGLE, 
				LAMP_ROTATION_ANGLE, LAMP_ROTATION_CENTER_X, LAMP_ROTATION_CENTER_Y) {
			@Override
			protected void onModifierFinished(IEntity pItem) {
				reset(getDuration(), getToValue(), getFromValue());
				super.onModifierFinished(pItem);
			}
		};
		
		sprite.registerEntityModifier(rotationAtModifier);
		Sprite lightLampSprite = new Sprite(LAMP_LIGHT_MASK_X, LAMP_LIGHT_MASK_Y, LAMP_LIGHT_MASK_WIDTH, LAMP_LIGHT_MASK_HEIGHT,
				mResourceManager.lampLightMask, mResourceManager.engine.getVertexBufferObjectManager());
		lightLampSprite.setBlendFunction(GLES20.GL_ONE_MINUS_SRC_ALPHA, GLES20.GL_ONE);
		lightLampSprite.setBlendingEnabled(true);
		sprite.attachChild(lightLampSprite);
		attachChild(sprite);
		
		AnimatedSprite fireplaceLightSprite = new AnimatedSprite(FIREPLACE_LIGHT_MASK_X, FIREPLACE_LIGHT_MASK_Y,
				FIREPLACE_LIGHT_MASK_WIDTH, FIREPLACE_LIGHT_MASK_HEIGHT,
				mResourceManager.fireplaceLightMask, mResourceManager.engine.getVertexBufferObjectManager());
		fireplaceLightSprite.setBlendFunction(GLES20.GL_DST_COLOR, GLES20.GL_ONE);
		fireplaceLightSprite.setBlendingEnabled(true);
		fireplaceLightSprite.animate(75);
		attachChild(fireplaceLightSprite);
		
	}
	
	public Sprite getClockSprite() {
		return clockSprite.getCurrentSprite();
	}

	public Sprite getFirePlaceSprite() {
		return firePlaceSprite.getCurrentSprite();
	}
	
	public void start() {
		mResourceManager.fireplaceMusic.play();
		mResourceManager.tickTookMusic.play();
		mResourceManager.fireplaceMusic.setVolume(0.0f);
		mResourceManager.tickTookMusic.setVolume(0.0f);
		mResourceManager.fireplaceMusic.setLooping(true);
		mResourceManager.tickTookMusic.setLooping(true);
	}
	
	public void stop() {
		mResourceManager.fireplaceMusic.pause();
		mResourceManager.tickTookMusic.pause();
	}
	
	public void update() {
		manageMusicValue(this.getFirePlaceSprite(), mResourceManager.fireplaceMusic, 1000.0f);
		manageMusicValue(this.getClockSprite(), mResourceManager.tickTookMusic, 600.0f);
	}
	
	private void manageMusicValue( final Entity object, Music music, final float distance )
	{
		final float val = Math.abs(object.getSceneCenterCoordinates()[0]-getPlayer().getX());
		final float threshold = distance*mResourceManager.WORLD_SCALE_CONSTANT;
		if(val > threshold) {
			music.setVolume(0.0f);
		}
		else {
			music.setVolume(1-(val/threshold));
		}
	}
	
	private Player getPlayer() {
		return mPlayer;
	}

	private void setPlayer(Player player) {
		this.mPlayer = player;
	}
}
