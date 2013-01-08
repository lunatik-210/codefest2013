package com.codefest2013.game;

import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;

public class GameScene extends Scene implements IOnSceneTouchListener  {

    private Player player;
    private int fingersNumber = 0;
    
    public GameScene()
    {
        // sprite as background
        attachChild(new Sprite(0, 0, ResourcesManager.WORLD_WIDTH, ResourcesManager.WORLD_HEIGHT,
        		ResourcesManager.getInstance().backgroundTextureRegion,
        		MainActivity.getInstance().getVertexBufferObjectManager() ));
        
        player = new Player(ResourcesManager.CAMERA_WIDTH/2, ResourcesManager.CAMERA_HEIGHT-ResourcesManager.CAMERA_HEIGHT/5);
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
