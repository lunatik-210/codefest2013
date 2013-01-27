package com.codefest2013.game.scenes.objects;


import org.andengine.entity.Entity;
import com.codefest2013.game.MegaAnimatedSprite;
import com.codefest2013.game.ResourcesManager;


public class Fox extends Entity {
    public final static int LEFT_DIRECTION = 0;
    public final static int RIGHT_DIRECTION = 1;
    
    private final float SPEED = ResourcesManager.CAMERA_WIDTH*0.35f;
    private final float ANIMATION_SPEED = 1.0f/8f;
    private final float RATIO = 1.7f;
    
    private MegaAnimatedSprite lieftSprite;
    private MegaAnimatedSprite rightSprite;
    
    private float speed = 0;
    private int currentDirection = LEFT_DIRECTION;
    private int fingersNumber = 0;
    
    public Fox(float x, float y)
    {   
    	super(x, y);
    	
    }
    
//    @Override
//    protected void onManagedUpdate(float pSecondsElapsed) {
//    	super.onManagedUpdate(pSecondsElapsed);
//    	move(pSecondsElapsed);
//    }
//
//    private void setDirection( int direction )
//    {
//        currentDirection = direction;
//        speed = SPEED;
//        
//        switch( currentDirection )
//        {
//            case LEFT_DIRECTION:
//        		lieftSprite.setVisible(true);
//        		rightSprite.setVisible(false);
//        		lieftSprite.animate(ANIMATION_SPEED);
//                break;
//            case RIGHT_DIRECTION:
//        		lieftSprite.setVisible(false);
//        		rightSprite.setVisible(true);
//        		rightSprite.animate(ANIMATION_SPEED);
//                break;
//        }
//    }
//    
//    private void move(float pSecondsElapsed)
//    {
//        if( getX() < 0.0f )
//        {
//            setPosition(getX()+speed*pSecondsElapsed, getY());
//            return;
//        }
//        if( getX() > ResourcesManager.WORLD_WIDTH - lieftSprite.getWidth() )
//        {
//            setPosition(getX()-speed*pSecondsElapsed, getY());
//            return;
//        }
//        switch(currentDirection)
//        {
//            case LEFT_DIRECTION:
//                setPosition(getX()-speed*pSecondsElapsed, getY());
//                break;
//            case RIGHT_DIRECTION:
//                setPosition(getX()+speed*pSecondsElapsed, getY());
//                break;
//        }
//    }
}
