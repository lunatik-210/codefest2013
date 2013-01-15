package com.codefest2013.game.world;

public class WayPoint {
	public int x;
	public int y;
	public int angle;
	public boolean isThrowable;
	public WayPoint(int x, int y, int angle, boolean isThrowable)
	{
		this.x = x;
		this.y = y;
		this.angle = angle;
		this.isThrowable = isThrowable;
	}
}
