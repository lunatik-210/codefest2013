package com.codefest2013.game.scenes.objects;

import org.andengine.entity.Entity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.util.color.Color;

import com.codefest2013.game.managers.ResourceManager;
import com.codefest2013.game.scenes.GameScene;

public class Squirrel extends Entity {

	private ResourceManager mResourceManager = ResourceManager.getInstance();
	private GameScene gameScene = null;	

	private int speed; // pixels per second.
	private Rectangle rect;
	
	public Squirrel(GameScene gameScene, int startIndex,  int speed){
		this.gameScene = gameScene;
		this.setSpeed(speed);
		setRect(new Rectangle(-10, -20, 20, 20, mResourceManager.engine.getVertexBufferObjectManager()));
		setPosition(this.gameScene.getWps().get(startIndex).x, this.gameScene.getWps().get(startIndex).y);
		getRect().setColor(Color.YELLOW);
		attachChild(getRect());
	}
	
	public Squirrel(GameScene gameScene, int startIndex){
		this(gameScene, 0, 100);
	}
	
	public Squirrel(GameScene gameScene){
		this(gameScene, 0);
	}
		
	public void start(){
		gameScene.updateSquirrelPath();
	} 
	
	public void stop(){
		getRect().clearEntityModifiers(); // not tested
	}
	
	public void rotate(float angle){
		setRotation(angle);
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public Rectangle getRect() {
		return rect;
	}

	public void setRect(Rectangle rect) {
		this.rect = rect;
	}
	
}
