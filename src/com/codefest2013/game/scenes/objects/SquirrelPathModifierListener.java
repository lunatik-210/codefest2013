package com.codefest2013.game.scenes.objects;

import java.util.List;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.PathModifier.IPathModifierListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.codefest2013.game.logic.WayPoint;
import com.codefest2013.game.managers.ResourceManager;
import com.codefest2013.game.scenes.GameScene;

public class SquirrelPathModifierListener implements IPathModifierListener {
	
	private ResourceManager mResourceManager = ResourceManager.getInstance();
	private GameScene gameScene = null;
	private List<Integer> currentPath = null;
	
	public SquirrelPathModifierListener( GameScene gameScene, List<Integer> currentPath ) {
		this.gameScene = gameScene;
		this.currentPath = currentPath;
	}

	@Override
	public void onPathFinished(PathModifier pPathModifier, IEntity pEntity) {
		WayPoint wp = gameScene.getWps().get(currentPath.get(currentPath.size()-1));
		if( wp.isThrowable )
		{
			FixtureDef BALL_FIXTURE_DEF = PhysicsFactory.createFixtureDef(1, 0.0f, 1.0f);
			
			final Sprite gift = new Sprite(wp.x, wp.y-gameScene.getSquirrel().height()*0.75f, 40, 40, mResourceManager.gift, mResourceManager.engine.getVertexBufferObjectManager());
			final Body body =  PhysicsFactory.createBoxBody(gameScene.getWorld(), gift, BodyType.DynamicBody, BALL_FIXTURE_DEF);
			
			gameScene.attachChild(gift);

			if( wp.x - gameScene.getPlayer().getX() < 0.0f ) {
				gameScene.getSquirrel().setDirection(Squirrel.RIGHT_DIRECTION);
				body.setLinearVelocity(10, -8);
			} else {
				gameScene.getSquirrel().setDirection(Squirrel.LEFT_DIRECTION);
				body.setLinearVelocity(-10, -8);
			}
			
			gameScene.getWorld().registerPhysicsConnector(new PhysicsConnector(gift, body, true, true));
			gameScene.getGifts().add(new Gift(gift, body));
		}
		gameScene.updateSquirrelPath();
	}

	@Override
	public void onPathStarted(PathModifier arg0, IEntity arg1) {
	}

	@Override
	public void onPathWaypointFinished(PathModifier arg0, IEntity arg1, int arg2) {
	}

	@Override
	public void onPathWaypointStarted(PathModifier pPathModifier, IEntity pEntity, int index) {
		WayPoint currentWP = gameScene.getWps().get(currentPath.get(index));
		WayPoint nextWP = gameScene.getWps().get(currentPath.get(index+1));
		
		float x = nextWP.x-currentWP.x;
		float y = nextWP.y-currentWP.y;
		
		if(x>0)
		{
			gameScene.getSquirrel().setDirection(Squirrel.RIGHT_DIRECTION);
		}
		else
		{
			gameScene.getSquirrel().setDirection(Squirrel.LEFT_DIRECTION);
		}
		
		if( index+1 < currentPath.size()) 
		{
			float xpow2 = x*x;
			
			float degree = (float)Math.toDegrees(Math.acos((xpow2/(Math.sqrt(xpow2+y*y)*Math.sqrt(xpow2)))));
			if( !(x < 0.0f && y < 0.0f || x > 0.0f && y > 0.0f )) {
				degree = -degree;
			}
			
			gameScene.getSquirrel().rotate(degree);
		}
	}

}
