package com.codefest2013.game.scenes;

import java.util.List;

import android.hardware.SensorManager;

import com.badlogic.gdx.math.Vector2;
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
		createWorld();
		
		List<WayPoint> wps = null;
		wps = LevelLoader.getInstance().load("levels/level.xml");
	
    	mPlayer = new Player(PLAYER_START_X, PLAYER_START_Y);
		mSquirrel = new Squirrel(wps, world, mPlayer);
		mBackground = new Background(mPlayer);
		
    	attachChild(mBackground);
		attachChild(mPlayer);
		
		for(int i=0; i<wps.size(); i++)
		{
			WayPoint wp = wps.get(i);
			wp.x *= mResourceManager.WORLD_SCALE_CONSTANT;
			wp.y *= mResourceManager.WORLD_SCALE_CONSTANT;
			Rectangle r = new Rectangle(wp.x, wp.y, 8, 8, mResourceManager.engine.getVertexBufferObjectManager());
			r.setIgnoreUpdate(true);
			if(wp.isThrowable) {
				r.setColor(Color.BLUE);
			} else {
				r.setColor(Color.WHITE);
			}
			attachChild(r);
		}
		attachChild(mSquirrel);
		
		registerUpdateHandler(mPlayer);
		mResourceManager.engine.getCamera().setChaseEntity(mPlayer);
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
				((GameScene) thisManagedGameScene).destroyWorld();
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
	}
	
	private void destroyWorld()
	{
		world.clearForces();
		world.clearPhysicsConnectors();
		world.reset();
		world.dispose();
	}
}
