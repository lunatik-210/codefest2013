package com.codefest2013.game;

import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;

public class GameScene extends Scene implements IOnSceneTouchListener  {

    private Player player;
    private int fingersNumber = 0;
    
    public GameScene()
    {
        super();
        setBackground(new Background(0, 0, 0.8784f));
        
        // sprite as background
        attachChild(new Sprite(0, 0, MainActivity.WORLD_WIDTH, MainActivity.WORLD_HEIGHT,
        		MainActivity.getInstance().mBackgroundTextureRegion,
        		MainActivity.getInstance().getVertexBufferObjectManager() ));
        
        player = new Player(MainActivity.CAMERA_WIDTH/2, MainActivity.CAMERA_HEIGHT-MainActivity.CAMERA_HEIGHT/5);
        attachChild(player.sprite);
        registerUpdateHandler(player);
        MainActivity.getInstance().mCamera.setChaseEntity(player.sprite);
        setOnSceneTouchListener(this);   
    }
    
    @Override
    public boolean onSceneTouchEvent(Scene arg0, TouchEvent arg1) {
        switch( arg1.getAction() )
        {
            case TouchEvent.ACTION_DOWN:
                ++fingersNumber;
                if( arg1.getX() < MainActivity.getInstance().mCamera.getCenterX() )
                {
                    player.setDirection(Player.LEFT_DIRECTION);
                }
                else
                {
                    player.setDirection(Player.RIGHT_DIRECTION);
                }
                return true;
            case TouchEvent.ACTION_UP:
                if( --fingersNumber == 0 )
                {
                    player.stop();
                }
                return true;
        }
        return false;
    }
    
}
