package com.codefest2013.game.scenes.objects;

import org.andengine.entity.Entity;
import org.andengine.entity.sprite.Sprite;

import com.codefest2013.game.managers.ResourceManager;
import com.codefest2013.game.scenes.GameScene;

public class Squirrel extends Entity {
	private ResourceManager mResourceManager = ResourceManager.getInstance();
	private GameScene gameScene = null;	

    public final static int LEFT_DIRECTION = 0;
    public final static int RIGHT_DIRECTION = 1;
    private int currentDirection = LEFT_DIRECTION;
	
	private final float RATIO = 0.9208f;
	
    private Sprite leftSprite;
    private Sprite rightSprite;
	
	public Squirrel(GameScene gameScene){
		this.gameScene = gameScene;
		
		float height = mResourceManager.CAMERA_HEIGHT/9;
		
        leftSprite = new Sprite(-RATIO*height/2, -height, RATIO*height, height, 
        		mResourceManager.squirrelLeft,
        		mResourceManager.engine.getVertexBufferObjectManager());
        
        rightSprite = new Sprite(-RATIO*height/2, -height, RATIO*height, height,
        		mResourceManager.squirrelRight,
        		mResourceManager.engine.getVertexBufferObjectManager());

		leftSprite.setVisible(true);
		rightSprite.setVisible(false);
		attachChild(leftSprite);
		attachChild(rightSprite);
	}
	
	public void start(){
		gameScene.updateSquirrelPath();
	}
	
	public void stop(){
		leftSprite.clearEntityModifiers();
		rightSprite.clearEntityModifiers();
	}
	
	public void rotate(float angle){
		setRotation(angle);
	}
	
	public void switchDirection(){
		setDirection(1-currentDirection);
	}
	
    public void setDirection(int direction)
    {
        currentDirection = direction;
        switch( currentDirection )
        {
            case LEFT_DIRECTION:
            	rightSprite.setVisible(false);
        		leftSprite.setVisible(true);
                break;
            case RIGHT_DIRECTION:
        		leftSprite.setVisible(false);
        		rightSprite.setVisible(true);
                break;
        }
    }
    
    public float height() {
    	return leftSprite.getHeight();
    }
    
    public float width() {
    	return rightSprite.getWidth();
    }
	
}
