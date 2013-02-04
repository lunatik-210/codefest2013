package com.codefest2013.game.scenes.objects;

import java.util.ArrayList;
import java.util.Random;

import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.entity.modifier.PathModifier.IPathModifierListener;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.util.color.Color;
import org.andengine.util.debug.Debug;

import com.codefest2013.game.managers.ResourceManager;

public class Squirrel extends Entity {
	
	private enum Direction {
		RIGHT,
		LEFT
	}
	private WayPoint wps[];
	private ArrayList<Integer> throwablePoints;
	private ArrayList<Integer> currentWay;
	private int currentIndex;
	private Random r;
	private int speed; // pixels per second.
	private Direction currDirection = Direction.RIGHT;
	private ResourceManager mResourceManager = ResourceManager.getInstance();
	
	private Rectangle rect;
	private IPathModifierListener modifierListener;
	
	public Squirrel(WayPoint[] wayPointsArray, int startIndex, int speed){
		wps = wayPointsArray;
		currentIndex = startIndex;
		this.speed = speed;
		
		r = new Random();
		throwablePoints = new ArrayList<Integer>();
		currentWay = new ArrayList<Integer>();
		for(int i=0; i<wps.length; i++)
		{
			if (wps[i].isThrowable)
			{
				throwablePoints.add(i);
			}
		}
		
		modifierListener = new IPathModifierListener() {
			
			@Override
			public void onPathWaypointStarted(PathModifier pPathModifier, IEntity pEntity, int index) {		
			}
			
			@Override
			public void onPathWaypointFinished(PathModifier pPathModifier, IEntity pEntity, int index) {
				int indexInWps = currentWay.get(index);
				WayPoint wp = wps[indexInWps];
				switch (currDirection) {
				case RIGHT:
					rotate(wp.rangle);
					Debug.d("dbg", "wp: x:" + wp.x + " y: " + wp.y + " direction: right");
					break;
				case LEFT:
					rotate(wp.langle);
					Debug.d("dbg", "wp: x:" + wp.x + " y: " + wp.y + " direction: left");
					break;
				default:
					Debug.e("Unknown direction from OnPathWaypointFinished");
				}
				
			}
			
			@Override
			public void onPathStarted(PathModifier pPathModifier, IEntity pEntity) {	
			}
			
			@Override
			public void onPathFinished(PathModifier pPathModifier, IEntity pEntity) {
				currentWay.clear();
				Debug.d("dbg", "restart " + currentIndex);
				setNextGoal();
				Debug.d("dbg_past", "restart " + currentIndex);
			}
		};
		
		rect = new Rectangle(wps[0].x, wps[0].y, 20, 20, mResourceManager.engine.getVertexBufferObjectManager());
		rect.setColor(Color.YELLOW);
		attachChild(rect);
	}
	
	public Squirrel(WayPoint[] wayPointsArray, int startIndex){
		this(wayPointsArray, 0, 100);
	}
	
	public Squirrel(WayPoint[] wayPointsArray){
		this(wayPointsArray, 0);
	}
		
	public void start(){
		setNextGoal();
	} 
	
	public void stop(){
		rect.clearEntityModifiers(); // not tested
	}
	
	public float getDimensionOfCurrentPath(int speed) {
		float pathLength = 0;
		if (currentWay.size() < 2){
			return 0f;
		}
		for (int i = 1; i < currentWay.size(); i++) {
			WayPoint previous = wps[currentWay.get(i - 1)];
			WayPoint next = wps[currentWay.get(i)];
			pathLength += Math.sqrt(Math.pow(next.x-previous.x, 2)  +  Math.pow(next.y-previous.y, 2));
		}
		return pathLength/speed;
	}
	
	public float getDimensionOfCurrentPath() {
		return getDimensionOfCurrentPath(this.speed);
	}
	
	private void setNextGoal(){
		int randIndex = throwablePoints.get(r.nextInt(throwablePoints.size()));
		int indexDiff = randIndex - currentIndex;
		if (indexDiff == 0) {
			//Debug.d("dbg", "Recursively call setNextGoal");
			setNextGoal();
			return;
		}
		int indexModifier;
		
		if (currentIndex > randIndex){
			currDirection = Direction.LEFT;
			indexModifier = -1;
		} else {
			currDirection = Direction.RIGHT;
			indexModifier = 1;
		}
		do {
			currentWay.add(currentIndex);
			currentIndex += indexModifier;
			Debug.d("dbg", "do: " + currentIndex + " != " + randIndex);
		} while (currentIndex != randIndex);
		
		Path path = formPath();
		PathModifier pathModifier = new PathModifier(getDimensionOfCurrentPath(), path, modifierListener);
		pathModifier.setAutoUnregisterWhenFinished(true);
		rect.registerEntityModifier(pathModifier);
	}
	
	private Path formPath(){
		Path path = new Path(currentWay.size());
		for (int i = 0; i < currentWay.size(); i++) {
			int indexInWps = currentWay.get(i);
			float realX = wps[indexInWps].x - rect.getWidth()/2;
			float realY = wps[indexInWps].y - rect.getHeight();
			path.to(realX, realY);
		}
		return path;
	}
	
	private void rotate(float angle){
		rect.setRotation(angle);
	}
	
}
