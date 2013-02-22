package com.codefest2013.game.scenes;

import java.util.ArrayList;
import java.util.Arrays;

import android.hardware.SensorManager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.codefest2013.game.managers.ResourceManager;
import com.codefest2013.game.scenes.objects.Background;
import com.codefest2013.game.scenes.objects.Squirrel;
import com.codefest2013.game.scenes.objects.Player;
import com.codefest2013.game.scenes.objects.WayPoint;

import org.andengine.audio.music.Music;
import org.andengine.entity.Entity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.color.Color;

public class GameScene extends ManagedScene implements IOnSceneTouchListener {
	private ResourceManager mResourceManager = ResourceManager.getInstance();
	
	private Player mPlayer = null;
	private Squirrel mSquirrel = null;
    private Background mBackground = null;
	
    private ManagedScene thisManagedGameScene = this;
    
    private final float PLAYER_START_X = mResourceManager.CAMERA_WIDTH/2;
    private final float PLAYER_START_Y = mResourceManager.CAMERA_HEIGHT-mResourceManager.CAMERA_HEIGHT/5;
			
    private PhysicsWorld world = null;
    
    public GameScene()
    {
    	this(0.0f);
    }
    
    public GameScene(float pLoadingScreenMinimumSecondsShown)
    {
    	super(pLoadingScreenMinimumSecondsShown);
    }
    
	@Override
	public boolean onSceneTouchEvent(Scene arg0, TouchEvent arg1) {
		return mPlayer.onSceneTouchEvent(arg0, arg1);
	}

	@Override
	public Scene onLoadingScreenLoadAndShown() {
		return null;
	}

	@Override
	public void onLoadingScreenUnloadAndHidden() {
	}

	@Override
	public void onLoadScene() {
		ArrayList<WayPoint> wps = new ArrayList<WayPoint>();
		wps.add(new WayPoint(Arrays.asList(1), 112, 230, 0, 0, false));
		wps.add(new WayPoint(Arrays.asList(0,2), 170, 260, 0, 0, false));
		wps.add(new WayPoint(Arrays.asList(1,3), 238, 120, 0, 0, false));
		wps.add(new WayPoint(Arrays.asList(2,4), 264, 77, 0, 0, true));
		wps.add(new WayPoint(Arrays.asList(3,5), 400, 110, 0, 0, false));
		wps.add(new WayPoint(Arrays.asList(4,6), 424, 154, 45, 0, false));
		wps.add(new WayPoint(Arrays.asList(5,7), 444, 218, 60, 0, false));
		wps.add(new WayPoint(Arrays.asList(6,8), 474, 256, 0, 0, true));
		wps.add(new WayPoint(Arrays.asList(7,9), 512, 182, 0, 0, false));
		wps.add(new WayPoint(Arrays.asList(8,10), 526, 126, 0, 0, false));
		wps.add(new WayPoint(Arrays.asList(9,11), 548, 210, 0, 45, false));
		wps.add(new WayPoint(Arrays.asList(10,12), 595, 286, 0, 0, true));
		wps.add(new WayPoint(Arrays.asList(11,13), 632, 366, 60, 0, false));
		wps.add(new WayPoint(Arrays.asList(12,14), 668, 372, 0, 0, false));
		wps.add(new WayPoint(Arrays.asList(13,15), 726, 264, 0, 0, false));
		wps.add(new WayPoint(Arrays.asList(14,16), 948, 264, 0, 0, true));
		wps.add(new WayPoint(Arrays.asList(15,17), 980, 370, 60, 45, false));
		wps.add(new WayPoint(Arrays.asList(16,18), 1031, 450, 0, 0, false));
		wps.add(new WayPoint(Arrays.asList(17,19), 1078, 494, 0, 0, false));
		wps.add(new WayPoint(Arrays.asList(18,20), 1288, 528, 60, 0, true));
		wps.add(new WayPoint(Arrays.asList(19,21), 1376, 482, 0, 0, false));
		wps.add(new WayPoint(Arrays.asList(20,22), 1430, 406, 0, 0, false));
		wps.add(new WayPoint(Arrays.asList(21,23), 1582, 264, 0, 0, false));
		wps.add(new WayPoint(Arrays.asList(22), 1726, 190, 0, 0, true));
		
    	mBackground = new Background();
    	mPlayer = new Player(PLAYER_START_X, PLAYER_START_Y);
		mSquirrel = new Squirrel(wps);
		
    	attachChild(mBackground);
		attachChild(mPlayer);
		for(int i=0; i<wps.size(); i++)
		{
			wps.get(i).x = wps.get(i).x * mResourceManager.WORLD_SCALE_CONSTANT;
			wps.get(i).y = wps.get(i).y * mResourceManager.WORLD_SCALE_CONSTANT;
			attachChild(new Rectangle(wps.get(i).x, wps.get(i).y, 8, 8, ResourceManager.getInstance().engine.getVertexBufferObjectManager()));
		}
		attachChild(mSquirrel);
		
		registerUpdateHandler(mPlayer);
		mResourceManager.engine.getCamera().setChaseEntity(mPlayer);
		
		createWorld();
	}

	@Override
	public void onShowScene() {
		setOnSceneTouchListener(this);
		
		mSquirrel.start();
		
		mResourceManager.fireplaceMusic.play();
		mResourceManager.tickTookMusic.play();
		mResourceManager.fireplaceMusic.setVolume(0.0f);
		mResourceManager.tickTookMusic.setVolume(0.0f);
		mResourceManager.fireplaceMusic.setLooping(true);
		mResourceManager.tickTookMusic.setLooping(true);
	}

	@Override
	public void onHideScene() {
		mPlayer.setPosition(PLAYER_START_X, PLAYER_START_Y);
		mSquirrel.stop();
		mResourceManager.fireplaceMusic.pause();
		mResourceManager.tickTookMusic.pause();
	}

	@Override
	public void onUnloadScene() {
		mResourceManager.engine.runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
				thisManagedGameScene.detachChildren();
				for(int i = 0; i < thisManagedGameScene.getChildCount(); i++)
					thisManagedGameScene.getChildByIndex(i).dispose();
				thisManagedGameScene.clearEntityModifiers();
				thisManagedGameScene.clearTouchAreas();
				thisManagedGameScene.clearUpdateHandlers();
			}});
	}
	
	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		manageMusicValue(mBackground.getFirePlaceSprite(), mResourceManager.fireplaceMusic, 1000.0f);
		manageMusicValue(mBackground.getClockSprite(), mResourceManager.tickTookMusic, 600.0f);
		super.onManagedUpdate(pSecondsElapsed);
	}
	
	private void manageMusicValue( final Entity object, Music music, final float distance )
	{
		final float val = Math.abs(object.getSceneCenterCoordinates()[0]-mPlayer.getX());
		final float threshold = distance*mResourceManager.WORLD_SCALE_CONSTANT;
		if(val > threshold) {
			music.setVolume(0.0f);
		}
		else {
			music.setVolume(1-(val/threshold));
		}
	}
	
	private void createWorld()
	{
		world = new FixedStepPhysicsWorld(60, new Vector2(0f, SensorManager.GRAVITY_EARTH*2), false, 8, 3);
		registerUpdateHandler(world);
		
		final FixtureDef WALL_FIXTURE_DEF = PhysicsFactory.createFixtureDef(0.0f, 0.0f, 1.0f);
		final Rectangle ground = new Rectangle(0f, mResourceManager.WORLD_HEIGHT-1f, mResourceManager.WORLD_WIDTH, 1f,
				mResourceManager.engine.getVertexBufferObjectManager());
		final Rectangle roof = new Rectangle(0f, 0f, mResourceManager.WORLD_WIDTH, 1f, 
				mResourceManager.engine.getVertexBufferObjectManager());
		final Rectangle left = new Rectangle(0f, 0f, 1f, mResourceManager.WORLD_HEIGHT,
				mResourceManager.engine.getVertexBufferObjectManager());
		final Rectangle right = new Rectangle(mResourceManager.WORLD_WIDTH-1f, 0f, 1f, mResourceManager.WORLD_HEIGHT,
				mResourceManager.engine.getVertexBufferObjectManager());
		
		PhysicsFactory.createBoxBody(world, ground, BodyType.StaticBody, WALL_FIXTURE_DEF);
		PhysicsFactory.createBoxBody(world, roof, BodyType.StaticBody, WALL_FIXTURE_DEF);
		PhysicsFactory.createBoxBody(world, left, BodyType.StaticBody, WALL_FIXTURE_DEF);
		PhysicsFactory.createBoxBody(world, right, BodyType.StaticBody, WALL_FIXTURE_DEF);
		
		attachChild(ground);
		attachChild(roof);
		attachChild(left);
		attachChild(right);
		
		FixtureDef BALL_FIXTURE_DEF = PhysicsFactory.createFixtureDef(1, 0.0f, 1.0f);
		
		final Rectangle ball = new Rectangle(100, 100, 10, 10, mResourceManager.engine.getVertexBufferObjectManager());
		ball.setColor(Color.WHITE);
		final Body body =  PhysicsFactory.createBoxBody(world, ball, BodyType.DynamicBody, BALL_FIXTURE_DEF);
		
		attachChild(ball);
		body.setLinearVelocity(10, 0);
		
		world.registerPhysicsConnector(new PhysicsConnector(ball, body, true, true));
	}
}
