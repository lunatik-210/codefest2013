package com.codefest2013.game.scenes.objects;

import org.andengine.entity.Entity;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.input.touch.TouchEvent;

import com.codefest2013.game.managers.ResourceManager;

public class Player extends Entity implements IOnSceneTouchListener {
	private ResourceManager mResourceManager = ResourceManager.getInstance();
	
    public final static int LEFT_DIRECTION = 0;
    public final static int RIGHT_DIRECTION = 1;
    
    private final float SPEED = mResourceManager.CAMERA_WIDTH*0.2f;
    private final long ANIMATION_SPEED = 120;
    private final float RATIO = 1.7f;
    
    private AnimatedSprite leftSprite;
    private AnimatedSprite rightSprite;
    
    private float speed = 0;
    private int currentDirection = LEFT_DIRECTION;
    private int fingersNumber = 0;
    
    public Player(float x, float y)
    {   
    	super(x, y);
    	
        float height = mResourceManager.CAMERA_HEIGHT/6;
        
        leftSprite = new AnimatedSprite(0, 0, RATIO*height, height, 
        		mResourceManager.goblinTiledLeftWalk,
        		mResourceManager.engine.getVertexBufferObjectManager());
        
        rightSprite = new AnimatedSprite(0, 0, RATIO*height, height,
        		mResourceManager.goblinTiledRightWalk,
        		mResourceManager.engine.getVertexBufferObjectManager());

		leftSprite.setVisible(true);
		rightSprite.setVisible(false);
		attachChild(leftSprite);
		attachChild(rightSprite);
    }
    
    @Override
    protected void onManagedUpdate(float pSecondsElapsed) {
    	move(pSecondsElapsed);
    	super.onManagedUpdate(pSecondsElapsed);
    }

	@Override
	public boolean onSceneTouchEvent(Scene arg0, TouchEvent arg1) {
        switch( arg1.getAction() )
        {
            case TouchEvent.ACTION_DOWN:
                ++fingersNumber;
                if( arg1.getX() < mResourceManager.engine.getCamera().getCenterX() )
                {
                	setDirection(LEFT_DIRECTION);
                }
                else
                {
                	setDirection(RIGHT_DIRECTION);
                }
                return true;
            case TouchEvent.ACTION_UP:
                if( --fingersNumber == 0 )
                {
                	stop();
                }
                return true;
        }
        return false;
	}
    
    private void setDirection( int direction )
    {
        currentDirection = direction;
        speed = SPEED;
        
        switch( currentDirection )
        {
            case LEFT_DIRECTION:
            	rightSprite.setVisible(false);
        		leftSprite.setVisible(true);
        		leftSprite.animate(ANIMATION_SPEED);
                break;
            case RIGHT_DIRECTION:
        		leftSprite.setVisible(false);
        		rightSprite.setVisible(true);
        		rightSprite.animate(ANIMATION_SPEED);
                break;
        }
    }
    
    private void move(float pSecondsElapsed)
    {
    	final float leftCorner = 0.0f;
    	final float rightCorner = mResourceManager.WORLD_WIDTH - leftSprite.getWidth();
        if( getX() < leftCorner || getX() > rightCorner)
        {
            switch(currentDirection)
            {
		        case LEFT_DIRECTION:
		            setPosition(leftCorner+1, getY());
		            break;
		        case RIGHT_DIRECTION:
		            setPosition(rightCorner-1, getY());
		            break;
            }
            stop();
        }
        switch(currentDirection)
        {
            case LEFT_DIRECTION:
                setPosition(getX()-speed*pSecondsElapsed, getY());
                break;
            case RIGHT_DIRECTION:
                setPosition(getX()+speed*pSecondsElapsed, getY());
                break;
        }
    }
    
    private void stop()
    {
        speed = 0;
        switch(currentDirection)
        {
            case LEFT_DIRECTION:
            	leftSprite.stopAnimation(0);
                break;
            case RIGHT_DIRECTION:
                rightSprite.stopAnimation(0);
                break;
        }
    }
    
    public AnimatedSprite getSprite() {
    	return leftSprite;
    }
}
