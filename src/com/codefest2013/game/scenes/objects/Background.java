package com.codefest2013.game.scenes.objects;

import org.andengine.entity.Entity;
import org.andengine.entity.sprite.Sprite;

import com.codefest2013.game.MainActivity;
import com.codefest2013.game.MegaAnimatedSprite;
import com.codefest2013.game.ResourcesManager;

public class Background extends Entity {
	
	private final float FIREPLACE_WIDTH = 0.5858f * ResourcesManager.WORLD_SCALE_CONSTANT;
	private final float FIREPLACE_HEIGHT = 0.5151f * ResourcesManager.WORLD_SCALE_CONSTANT;
	private final float FIREPLACE_X = 1.44f * ResourcesManager.WORLD_SCALE_CONSTANT;
	private final float FIREPLACE_Y = 0.4101f * ResourcesManager.WORLD_SCALE_CONSTANT;
	
	private final float TREE_WIDTH = 0.4949f * ResourcesManager.WORLD_SCALE_CONSTANT;
	private final float TREE_HEIGHT = 0.7818f * ResourcesManager.WORLD_SCALE_CONSTANT;
	private final float TREE_X = 0.2808f * ResourcesManager.WORLD_SCALE_CONSTANT;
	private final float TREE_Y = 0.0979f * ResourcesManager.WORLD_SCALE_CONSTANT;
	
	private final float CLOCK_WIDTH = 0.1515f * ResourcesManager.WORLD_SCALE_CONSTANT;
	private final float CLOCK_HEIGHT = 0.6141f * ResourcesManager.WORLD_SCALE_CONSTANT;
	private final float CLOCK_X = 0.0323f * ResourcesManager.WORLD_SCALE_CONSTANT;
	private final float CLOCK_Y = 0.2343f * ResourcesManager.WORLD_SCALE_CONSTANT;
	
	private final float LAMP_WIDTH = 0.2525f * ResourcesManager.WORLD_SCALE_CONSTANT;
	private final float LAMP_HEIGHT = 0.1717f * ResourcesManager.WORLD_SCALE_CONSTANT;
	private final float LAMP_X = 0.8787f * ResourcesManager.WORLD_SCALE_CONSTANT;
	private final float LAMP_Y = 0.0000f * ResourcesManager.WORLD_SCALE_CONSTANT;
	
	public Background()
	{
		attachChild( new Sprite(0, 0, ResourcesManager.WORLD_WIDTH/2, ResourcesManager.WORLD_HEIGHT/2,
        		ResourcesManager.getInstance().bgLT,
        		MainActivity.getInstance().getVertexBufferObjectManager() ) );
		
		attachChild( new Sprite(ResourcesManager.WORLD_WIDTH/2, 0, ResourcesManager.WORLD_WIDTH/2, 
				ResourcesManager.WORLD_HEIGHT/2,
        		ResourcesManager.getInstance().bgRT,
        		MainActivity.getInstance().getVertexBufferObjectManager() ) );
		
		attachChild( new Sprite(0, ResourcesManager.WORLD_HEIGHT/2, ResourcesManager.WORLD_WIDTH/2, 
				ResourcesManager.WORLD_HEIGHT/2,
        		ResourcesManager.getInstance().bgLB,
        		MainActivity.getInstance().getVertexBufferObjectManager() ) );

		attachChild( new Sprite(ResourcesManager.WORLD_WIDTH/2, ResourcesManager.WORLD_HEIGHT/2, 
				ResourcesManager.WORLD_WIDTH/2, ResourcesManager.WORLD_HEIGHT/2,
        		ResourcesManager.getInstance().bgRB,
        		MainActivity.getInstance().getVertexBufferObjectManager() ) );
		
		attachChild( new Sprite(TREE_X, TREE_Y, TREE_WIDTH, TREE_HEIGHT, 
				ResourcesManager.getInstance().tree, MainActivity.getInstance().getVertexBufferObjectManager() ) );
		
		attachChild( new Sprite(LAMP_X, LAMP_Y, LAMP_WIDTH, LAMP_HEIGHT, 
				ResourcesManager.getInstance().lamp, MainActivity.getInstance().getVertexBufferObjectManager() ) );
		
		MegaAnimatedSprite clockSprite = new MegaAnimatedSprite(10);
		for( int i=0; i<10; ++i )
		{
			clockSprite.attachTexture( new Sprite(CLOCK_X, CLOCK_Y, CLOCK_WIDTH, CLOCK_HEIGHT,
					ResourcesManager.getInstance().clock[i], MainActivity.getInstance().getVertexBufferObjectManager() ) );
		}
		clockSprite.animate(1.0f / 6f);
		attachChild(clockSprite);
		
		MegaAnimatedSprite firePlaceSprite = new MegaAnimatedSprite(2);
		firePlaceSprite.attachTexture( new Sprite(FIREPLACE_X, FIREPLACE_Y, FIREPLACE_WIDTH, FIREPLACE_HEIGHT,
				ResourcesManager.getInstance().fireplace1, MainActivity.getInstance().getVertexBufferObjectManager() ) );
		firePlaceSprite.attachTexture( new Sprite(FIREPLACE_X, FIREPLACE_Y, FIREPLACE_WIDTH, FIREPLACE_HEIGHT,
				ResourcesManager.getInstance().fireplace2, MainActivity.getInstance().getVertexBufferObjectManager() ) );
		firePlaceSprite.animate(1.0f / 6f);
		attachChild(firePlaceSprite);
	}
}
