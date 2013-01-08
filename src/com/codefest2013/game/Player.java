package com.codefest2013.game;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.sprite.AnimatedSprite;

public class Player implements IUpdateHandler {
    public final static int LEFT_DIRECTION = 0;
    public final static int RIGHT_DIRECTION = 1;
    
    private final float SPEED = ResourcesManager.CAMERA_WIDTH*0.01f;
    private final float RATIO = 1.7f;
    
    private final long d = 60;
    private final long frameDuration[] = new long[] {d, d, d, d, d, d, d, d, d, d, d, d};
    private final int leftAnimationSeq[] = new int[] {23, 22, 21, 20, 19, 18, 17, 16, 15, 14, 13, 12};
    
    public AnimatedSprite sprite;
    
    private float speed = 0;
    private int currentDirection = LEFT_DIRECTION;
    
    public Player(float x, float y)
    {   
        float height = ResourcesManager.CAMERA_HEIGHT/6;
        
        sprite = new AnimatedSprite(x, y, RATIO*height, height, ResourcesManager.getInstance().goblinTextureRegion, 
                MainActivity.getInstance().getVertexBufferObjectManager());
    }
    
    @Override
    public void onUpdate(float arg0) {
        this.move();
    }

    @Override
    public void reset() {
    }
    
    public void setDirection( int direction )
    {
        currentDirection = direction;
        speed = SPEED;
        
        switch( currentDirection )
        {
            case LEFT_DIRECTION:
                sprite.animate(frameDuration, leftAnimationSeq, -1);
                break;
            case RIGHT_DIRECTION:
                sprite.animate(frameDuration, 0, 11, -1);
                break;
        }
    }
    
    public void move()
    {
        if( sprite.getX() < 0.0f )
        {
            sprite.setPosition(sprite.getX()+speed, sprite.getY());
            return;
        }
        if( sprite.getX() > ResourcesManager.WORLD_WIDTH - sprite.getWidth() )
        {
            sprite.setPosition(sprite.getX()-speed, sprite.getY());
            return;
        }
        switch(currentDirection)
        {
            case LEFT_DIRECTION:
                sprite.setPosition(sprite.getX()-speed, sprite.getY());
                break;
            case RIGHT_DIRECTION:
                sprite.setPosition(sprite.getX()+speed, sprite.getY());
                break;
        }
    }
    
    public void stop()
    {
        speed = 0;
        switch(currentDirection)
        {
            case LEFT_DIRECTION:
                sprite.stopAnimation(12);
                break;
            case RIGHT_DIRECTION:
                sprite.stopAnimation(0);
                break;
        }
    }
    
}
