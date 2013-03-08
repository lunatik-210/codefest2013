package com.codefest2013.game.logic;
import java.util.List;
import java.util.ArrayList;

/*
 * rangle - angle for left->right moving
 * langle - angle for right->left moving
 */
public class WayPoint {
	public float x;
	public float y;
	public float rangle;
	public float langle;
	public boolean isThrowable;
	public List<Integer> neighbors;
	public List<String> objects;
	
	public WayPoint(List<Integer> neighbors, List<String> objects, float x, float y, float rangle, float langle, boolean isThrowable)
	{
		this.x = x;
		this.y = y;
		this.rangle = rangle;
		this.langle = langle;
		this.isThrowable = isThrowable;
		this.neighbors = neighbors;
		this.objects = objects;
	}
	
	public WayPoint()
	{
		this(new ArrayList<Integer>(), new ArrayList<String>(), 0.0f, 0.0f, 0.0f, 0.0f, false);
	}
	
}
