package com.codefest2013.game.scenes.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;

public class SquirrelLogic {

	private ArrayList<WayPoint> wpnList;
	private ArrayList<Integer> goals;

	private Integer currentPos;
	private Integer nextGoal = null;
	
	private Random r = new Random();
	
	public SquirrelLogic(ArrayList<WayPoint> wpn, Integer _currentPos) {
		wpnList = wpn;
		currentPos = _currentPos;
		goals = new ArrayList<Integer>();
		for( WayPoint wayPoint : wpn )
		{
			if(wayPoint.isThrowable)
			{
				goals.add(wpnList.indexOf(wayPoint));
			}
		}
	}
	
	public Integer pickNextGoal()
	{
		do
		{
			nextGoal = goals.get(r.nextInt(goals.size()));
		} while( this.currentPos == nextGoal );
		return nextGoal;
	}
	
	public LinkedList<Integer> getPath()
	{
		//A* path search implementation
		
		// store points which we have already visited
		ArrayList<Integer> visited = new ArrayList<Integer>();

		// sort itself by using HeuristicFunction rule
		PriorityQueue<Integer> toVisit = new PriorityQueue<Integer>(wpnList.size(), new HeuristicFunction(wpnList, nextGoal));
		
		Map<Integer, Integer> comeFrom = new HashMap<Integer, Integer>();
		
		// add initial position
		toVisit.offer(currentPos);
		
		while(!toVisit.isEmpty())
		{
			// take position with the shortest distance to the goal
			Integer currentPos = toVisit.remove();
			visited.add(currentPos);
			
			if( currentPos == nextGoal )
			{
				break;
			}
			
			// when we come to the a new position, add neighbors to visit queue
			// and sort them so that position with the shortest distance would be picked
			// Note: PriorityQueue - is sort itself
			for( Integer i : wpnList.get(currentPos).neighbors )
			{
				if(!visited.contains(i))
				{
					toVisit.offer(i);
					// for each point, store value where we come from to reproduce the path
					comeFrom.put(i, currentPos);
				}
			}
		}
		
		LinkedList<Integer> path = new LinkedList<Integer>();
		int pos = nextGoal;
		while(pos!=currentPos)
		{
			path.addFirst(pos);
			pos = comeFrom.get(pos);
		}
		path.addFirst(currentPos);
		
		currentPos = nextGoal;
		return path;
	}
	
}
