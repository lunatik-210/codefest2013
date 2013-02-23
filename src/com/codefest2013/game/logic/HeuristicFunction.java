package com.codefest2013.game.logic;
import java.util.ArrayList;
import java.util.Comparator;


public class HeuristicFunction implements Comparator<Integer>
{
	ArrayList<WayPoint> wps = null;
	Integer goal = null;
	
	HeuristicFunction(ArrayList<WayPoint> wps, Integer goal)
	{
		this.wps = wps;
		this.goal = goal;
	}
	
	private double getDistanceTo(Integer indx)
	{
		return Math.sqrt(Math.pow(wps.get(goal).x-wps.get(indx).x, 2)+Math.pow(wps.get(goal).y-wps.get(indx).y, 2));
	}

	@Override
	public int compare(Integer indx1, Integer indx2) {
		return (int)(getDistanceTo(indx1) - getDistanceTo(indx2));
	}
}
