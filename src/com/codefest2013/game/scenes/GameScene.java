package com.codefest2013.game.scenes;

import com.codefest2013.game.MainActivity;
import com.codefest2013.game.ResourcesManager;
import com.codefest2013.game.world.Player;
import com.codefest2013.game.world.World;

import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;

public class GameScene extends Scene implements IOnSceneTouchListener  {

    private World mWorld;
    private int fingersNumber = 0;
    
    public GameScene()
    {
        // sprite as background
        attachChild(new Sprite(0, 0, ResourcesManager.WORLD_WIDTH, ResourcesManager.WORLD_HEIGHT,
        		ResourcesManager.getInstance().backgroundTextureRegion,
        		MainActivity.getInstance().getVertexBufferObjectManager() ));
        
        mWorld = new World(this);
        
        MainActivity.getInstance().mCamera.setChaseEntity(mWorld.getPlayer().sprite);
        
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
                	mWorld.getPlayer().setDirection(Player.LEFT_DIRECTION);
                }
                else
                {
                	mWorld.getPlayer().setDirection(Player.RIGHT_DIRECTION);
                }
                return true;
            case TouchEvent.ACTION_UP:
                if( --fingersNumber == 0 )
                {
                	mWorld.getPlayer().stop();
                }
                return true;
        }
        return false;
    }
    
}
