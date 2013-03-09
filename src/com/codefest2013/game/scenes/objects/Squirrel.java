package com.codefest2013.game.scenes.objects;

import java.util.List;

import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.entity.modifier.PathModifier.IPathModifierListener;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.util.color.Color;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.codefest2013.game.logic.SquirrelLogic;
import com.codefest2013.game.logic.WayPoint;
import com.codefest2013.game.managers.ResourceManager;

public class Squirrel extends Entity {

	private ResourceManager mResourceManager = ResourceManager.getInstance();
		
	private List<WayPoint> wps;
	private List<Integer> currentPath;
	private int speed; // pixels per second.

	private Rectangle rect;
	private IPathModifierListener modifierListener;
	
	private SquirrelLogic logic;
	
	private PhysicsWorld mWorld = null;
	private Player mPlayer = null;
	
	public Squirrel(List<WayPoint> wayPointsArray, PhysicsWorld world, Player player, int startIndex, int speed){
		this.mWorld = world;
		this.mPlayer = player;
		
		logic = new SquirrelLogic(wayPointsArray, startIndex);
		
		wps = wayPointsArray;
		this.speed = speed;
		
		modifierListener = new IPathModifierListener() {
			
			@Override
			public void onPathWaypointStarted(PathModifier pPathModifier, IEntity pEntity, int index) {
				if( index+1 < currentPath.size()) 
				{
					WayPoint currentWP = wps.get(currentPath.get(index));
					WayPoint nextWP = wps.get(currentPath.get(index+1));
					
					float x = nextWP.x-currentWP.x;
					float y = nextWP.y-currentWP.y;
					float xpow2 = x*x;
					
					float degree = (float)Math.toDegrees(Math.acos((xpow2/(Math.sqrt(xpow2+y*y)*Math.sqrt(xpow2)))));
					
					//Debug.d("debug", "degree: " + degree);
					if( x < 0.0f && y < 0.0f || x > 0.0f && y > 0.0f ) {
						rotate(degree);
					} else {
						rotate(-degree);
					}
				}
			}
			
			@Override
			public void onPathWaypointFinished(PathModifier pPathModifier, IEntity pEntity, int index) {
			}
			
			@Override
			public void onPathStarted(PathModifier pPathModifier, IEntity pEntity) {	
			}
			
			@Override
			public void onPathFinished(PathModifier pPathModifier, IEntity pEntity) {
				WayPoint wp = wps.get(currentPath.get(currentPath.size()-1));
				if( wp.isThrowable )
				{
					FixtureDef BALL_FIXTURE_DEF = PhysicsFactory.createFixtureDef(1, 0.0f, 1.0f);
					
					final Rectangle ball = new Rectangle(wp.x, wp.y, 10, 10, mResourceManager.engine.getVertexBufferObjectManager());
					ball.setColor(Color.WHITE);
					final Body body =  PhysicsFactory.createBoxBody(mWorld, ball, BodyType.DynamicBody, BALL_FIXTURE_DEF);
					
					attachChild(ball);
					
					if( wp.x - mPlayer.getX() < 0.0f ) {
						body.setLinearVelocity(6, -8);
					} else {
						body.setLinearVelocity(-6, -8);
					}
					
					mWorld.registerPhysicsConnector(new PhysicsConnector(ball, body, true, true));
				}
				setNextGoal();
			}
		};
		
		rect = new Rectangle(wps.get(0).x, wps.get(0).y, 20, 20, mResourceManager.engine.getVertexBufferObjectManager());
		rect.setRotationCenter(rect.getWidth()/2, rect.getHeight());
		rect.setColor(Color.YELLOW);
		attachChild(rect);
	}
	
	public Squirrel(List<WayPoint> wayPointsArray, PhysicsWorld world, Player player, int startIndex){
		this(wayPointsArray, world, player, 0, 100);
	}
	
	public Squirrel(List<WayPoint> wayPointsArray, PhysicsWorld world, Player player ){
		this(wayPointsArray, world, player, 0);
	}
		
	public void start(){
		setNextGoal();
	} 
	
	public void stop(){
		rect.clearEntityModifiers(); // not tested
	}
	
	public float getDimensionOfCurrentPath(int speed) {
		float pathLength = 0;
		if (currentPath.size() < 2){
			return 0f;
		}
		for (int i = 1; i < currentPath.size(); i++) {
			WayPoint previous = wps.get(currentPath.get(i - 1));
			WayPoint next = wps.get(currentPath.get(i));
			pathLength += Math.sqrt(Math.pow(next.x-previous.x, 2)  +  Math.pow(next.y-previous.y, 2));
		}
		return pathLength/speed;
	}
	
	public float getDimensionOfCurrentPath() {
		return getDimensionOfCurrentPath(this.speed);
	}
	
	private void setNextGoal(){
		logic.pickNextGoal();
		Path path = formPath();
		PathModifier pathModifier = new PathModifier(getDimensionOfCurrentPath(), path, modifierListener);
		pathModifier.setAutoUnregisterWhenFinished(true);
		rect.registerEntityModifier(pathModifier);
	}
	
	private Path formPath(){
		currentPath = logic.getPath();
		Path path = new Path(currentPath.size());
		for( Integer i : currentPath )
		{
			float realX = wps.get(i).x - rect.getWidth()/2;
			float realY = wps.get(i).y - rect.getHeight();
			path.to(realX, realY);
		}
		return path;
	}
	
	private void rotate(float angle){
		rect.setRotation(angle);
	}
	
}
