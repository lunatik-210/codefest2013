package com.codefest2013.game.scenes.objects;

import java.util.ArrayList;
import java.util.Random;

import org.andengine.entity.Entity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.util.color.Color;

import com.codefest2013.game.MainActivity;

public class Fox extends Entity {
	private WayPoint wps[];
	private ArrayList<Integer> throwablePoints;
	private int current;
	private int goal;
	private Random r;
	Rectangle rect;
	
	public Fox(WayPoint[] wayPointsArray, int startIndex){
		r = new Random();
		wps = wayPointsArray;
		current = startIndex;
		throwablePoints = new ArrayList<Integer>();
		for(int i=0; i<wps.length; i++)
		{
			if (wps[i].isThrowable)
			{
				throwablePoints.add(i);
			}
		}
		rect = new Rectangle(wps[0].x, wps[0].y, 20, 20, MainActivity.getInstance().getVertexBufferObjectManager());
		rect.setColor(Color.YELLOW);
		attachChild(rect);
	}
	public Fox(WayPoint[] wayPointsArray){
		this(wayPointsArray, 0);
	}
		
	public void start(){
		//TODO: implement
	} 
	
	public void stop(){
		// TODO: implement
	}
	
	private WayPoint getNextThrowablePoint(){
		
	}
	
	
}
