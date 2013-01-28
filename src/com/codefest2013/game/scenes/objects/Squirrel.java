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

import com.codefest2013.game.MainActivity;

public class Squirrel extends Entity {
	private WayPoint wps[];
	private ArrayList<Integer> throwablePoints;
	private ArrayList<Integer> currentWay;
	private int currentIndex;
	private Random r;
	private int speed; // pixels per second 
	Rectangle rect;
	IPathModifierListener modifierListener;
	
	public Squirrel(WayPoint[] wayPointsArray, int startIndex){
		r = new Random();
		wps = wayPointsArray;
		currentIndex = startIndex;
		speed = 90;
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
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPathWaypointFinished(PathModifier pPathModifier, IEntity pEntity, int index) {
				pEntity.setColor(Color.GREEN);
				
			}
			
			@Override
			public void onPathStarted(PathModifier pPathModifier, IEntity pEntity) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPathFinished(PathModifier pPathModifier, IEntity pEntity) {
				currentWay.clear();
				Debug.d("dbg", "restart " + currentIndex);
				setNextGoal();
			}
		};
		
		rect = new Rectangle(wps[0].x, wps[0].y, 20, 20, MainActivity.getInstance().getVertexBufferObjectManager());
		rect.setColor(Color.YELLOW);
		attachChild(rect);
	}
	public Squirrel(WayPoint[] wayPointsArray){
		this(wayPointsArray, 0);
	}
		
	public void start(){
		setNextGoal();
	} 
	
	public void stop(){
		// TODO: implement
		
	}
	
	private void setNextGoal(){
		int randIndex = throwablePoints.get(r.nextInt(throwablePoints.size()));
		int indexDiff = randIndex - currentIndex;
		if (indexDiff == 0) {
			//Debug.d("dbg", "Recursively call setNextGoal");
			setNextGoal();
		}
		int indexModifier;
		
		if (currentIndex > randIndex){
			indexModifier = -1;
		} else {
			indexModifier = 1;
		}
		do {
			currentWay.add(currentIndex);
			currentIndex += indexModifier;
			Debug.d("dbg", "do: " + currentIndex + " != " + randIndex);
		} while (currentIndex != randIndex);
		
		Path path = new Path(currentWay.size());
		for (int i = 0; i < currentWay.size(); i++) {
			int indexInWps = currentWay.get(i);
			path.to(wps[indexInWps].x, wps[indexInWps].y);
		}
		PathModifier pathModifier = new PathModifier(getDimensionOfCurrentPath(), path, modifierListener);
		pathModifier.setAutoUnregisterWhenFinished(true);
		rect.registerEntityModifier(pathModifier);
	}
	
	private float getDimensionOfCurrentPath(int speed) {
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
	private float getDimensionOfCurrentPath() {
		return getDimensionOfCurrentPath(this.speed);
	}
	
}
