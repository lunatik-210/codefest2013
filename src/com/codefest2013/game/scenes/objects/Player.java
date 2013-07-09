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
    private final float RATIO1 = 1.7f;
    private final float RATIO2 = 1.1f;
    
    private AnimatedSprite leftSprite;
    private AnimatedSprite rightSprite;
    private AnimatedSprite leftJumpSprite;
    private AnimatedSprite rightJumpSprite;
    
    
    private float speed = 0;
    private int currentDirection = LEFT_DIRECTION;
    private int fingersNumber = 0;
    
    public Player(float x, float y)
    {   
    	super(x, y);
    	
        float height1 = mResourceManager.CAMERA_HEIGHT/6;
        float height2 = height1+height1*1.0f/3.0f;
        
        leftSprite = new AnimatedSprite(0, 0, RATIO1*height1, height1, 
        		mResourceManager.goblinTiledLeftWalk,
        		mResourceManager.engine.getVertexBufferObjectManager());
        
        rightSprite = new AnimatedSprite(0, 0, RATIO1*height1, height1,
        		mResourceManager.goblinTiledRightWalk,
        		mResourceManager.engine.getVertexBufferObjectManager());
        
        leftJumpSprite = new AnimatedSprite(0, 0, RATIO2*height2, height2, 
        		mResourceManager.goblinTiledLeftJump,
        		mResourceManager.engine.getVertexBufferObjectManager());
        
        rightJumpSprite = new AnimatedSprite(0, 0, RATIO2*height2, height2,
        		mResourceManager.goblinTiledRightJump,
        		mResourceManager.engine.getVertexBufferObjectManager());

		leftSprite.setVisible(true);
		rightSprite.setVisible(false);
		leftJumpSprite.setVisible(false);
		rightJumpSprite.setVisible(false);
		attachChild(leftSprite);
		attachChild(rightSprite);
		attachChild(leftJumpSprite);
		attachChild(rightJumpSprite);
    }
    
    @Override
    protected void onManagedUpdate(float pSecondsElapsed) {
    	move(pSecondsElapsed);
    	super.onManagedUpdate(pSecondsElapsed);
    }
    
    public void jump()
    {
    	stop();
    	leftSprite.setVisible(false);
    	rightSprite.setVisible(false);
        switch(currentDirection)
        {
            case LEFT_DIRECTION:
            	leftJumpSprite.setVisible(true);
            	leftJumpSprite.animate(ANIMATION_SPEED, false);
                break;
            case RIGHT_DIRECTION:
            	rightJumpSprite.setVisible(true);
            	rightJumpSprite.animate(ANIMATION_SPEED, false);
                break;
        }
    }

	@Override
	public boolean onSceneTouchEvent(Scene arg0, TouchEvent arg1) {
        switch( arg1.getAction() )
        {
            case TouchEvent.ACTION_DOWN:
                ++fingersNumber;
        		if(rightJumpSprite.isAnimationRunning() || leftJumpSprite.isAnimationRunning())
        		{
        			return false;
        		}
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
            	--fingersNumber;
        		if(rightJumpSprite.isAnimationRunning() || leftJumpSprite.isAnimationRunning())
        		{
        			return false;
        		}
                if( fingersNumber == 0 )
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
        leftJumpSprite.setVisible(false);
        rightJumpSprite.setVisible(false);
        leftJumpSprite.stopAnimation();
        rightJumpSprite.stopAnimation();
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
