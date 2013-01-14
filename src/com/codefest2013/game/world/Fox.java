package com.codefest2013.game.world;

import java.util.ArrayList;
import java.util.Random;


public class Fox {
	private ArrayList<WayPoint> wps;
	private ArrayList<Integer> throwablePoints;
	private int current;
	private Random r;
	public Fox(ArrayList<WayPoint> wayPointsArray, int startIndex){
		/*
		 * i don't know but 
		 * 
		if (wayPointsArray.size() < startIndex)
		{
			throw new Exception("Start index can't be more than count of points");
		}
		*/
		r = new Random();
		wps = wayPointsArray;
		current = startIndex;
		throwablePoints = new ArrayList<Integer>();
		for(int i=0; i<wps.size(); i++)
		{
			if (wps.get(i).isThrowable)
			{
				throwablePoints.add(i);
			}
		}
	}
	public Fox(ArrayList<WayPoint> wayPointsArray){
		this(wayPointsArray, 0);
	}
	
	public int selectNext()
	{
		/*
		 * it wont work if we call not in throwable point	
		int randomIndex = r.nextInt(throwablePoints.size());
		int randomElement = throwablePoints.get(randomIndex);
		throwablePoints.remove(randomIndex);
		throwablePoints.add(current);
		return randomElement;
		//*/
		
		int randomIndex = r.nextInt(throwablePoints.size());
		while (randomIndex == current)
		{
			randomIndex = r.nextInt(throwablePoints.size());
		}
		current = randomIndex; // no need in real life
		return throwablePoints.get(randomIndex);
		
	}
}
