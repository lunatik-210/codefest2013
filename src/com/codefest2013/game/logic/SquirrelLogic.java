package com.codefest2013.game.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;

import org.andengine.util.debug.Debug;

import com.codefest2013.game.managers.ResourceManager;

public class SquirrelLogic {

	private int DISTANCE_FACTOR = (int) (ResourceManager.getInstance().WORLD_WIDTH/5);
	
	private List<WayPoint> wpnList = null;
	private List<Integer> goals = null;

	private Integer currentPos;
	private Integer nextGoal = null;
	private Anchor anchor = null;
	
	private Random r = new Random();
	
	public SquirrelLogic(List<WayPoint> wpn, Integer _currentPos) {
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
		anchor = new Anchor(2, 7);
	}
	
	public Integer pickNextGoal()
	{
		int weight = 0;
		switch(anchor.getBalance()) 
		{
			case LEFT_SHIFT:
				do
				{
					nextGoal = goals.get(r.nextInt(goals.size()));
					weight = getPointWeight(nextGoal);
				} while( this.currentPos == nextGoal || weight < 0 );
				break;
			case RIGHT_SHIFT:
				do
				{
					nextGoal = goals.get(r.nextInt(goals.size()));
					weight = getPointWeight(nextGoal);
				} while( this.currentPos == nextGoal || weight > 0 );
			case STABLE:
				do
				{
					nextGoal = goals.get(r.nextInt(goals.size()));
					weight = getPointWeight(nextGoal);
				} while( this.currentPos == nextGoal );
				break;
			default:
				weight = 0;
				break;
		}
		anchor.addWeight(weight);
		Debug.d("debug", "Anchor: " + anchor.getValue() + " Weight: " + weight);
		return nextGoal;
	}
	
	private int getPointWeight(int index)
	{
		WayPoint next = wpnList.get(index);
		WayPoint cur = wpnList.get(currentPos);
		switch((int)(Math.sqrt(Math.pow(next.x-cur.x,2)+Math.pow(next.y-cur.y,2))/DISTANCE_FACTOR))
		{
			case 0:
				return -1;
			case 1:
				return -2;
			case 2:
				return 2;
			case 3:
				return 3;
			case 4:
				return 4;
			default:
				return 5;
		}
	}
	
	public List<Integer> getPath()
	{
		//A* path search implementation
		
		// store points which we have already visited
		ArrayList<Integer> visited = new ArrayList<Integer>();

		// sort itself by using HeuristicFunction rule
		PriorityQueue<Integer> toVisit = new PriorityQueue<Integer>(wpnList.size(), new AStarHeuristic(wpnList, nextGoal));
		
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
