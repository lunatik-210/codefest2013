package com.codefest2013.game.scenes;

import java.util.List;

import android.hardware.SensorManager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.codefest2013.game.logic.WayPoint;
import com.codefest2013.game.logic.tools.LevelLoader;
import com.codefest2013.game.managers.ResourceManager;
import com.codefest2013.game.scenes.objects.Background;
import com.codefest2013.game.scenes.objects.Squirrel;
import com.codefest2013.game.scenes.objects.Player;

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
		List<WayPoint> wps = null;
		wps = LevelLoader.getInstance().load("levels/level.xml");
	
    	mPlayer = new Player(PLAYER_START_X, PLAYER_START_Y);
		mSquirrel = new Squirrel(wps);
		mBackground = new Background(mPlayer);
		
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
		mBackground.start();
	}

	@Override
	public void onHideScene() {
		mPlayer.setPosition(PLAYER_START_X, PLAYER_START_Y);
		mSquirrel.stop();
		mBackground.stop();
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
		mBackground.update();
		super.onManagedUpdate(pSecondsElapsed);
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
