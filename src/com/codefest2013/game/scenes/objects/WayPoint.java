package com.codefest2013.game.scenes.objects;
import java.util.List;

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
	List<Integer> neighbors;
	
	public WayPoint(List<Integer> list, float x, float y, float rangle, float langle, boolean isThrowable)
	{
		this.x = x;
		this.y = y;
		this.rangle = rangle;
		this.langle = langle;
		this.isThrowable = isThrowable;
		this.neighbors = list;
	}
}
