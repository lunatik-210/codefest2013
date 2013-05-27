package com.codefest2013.game.scenes;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import android.hardware.SensorManager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.codefest2013.game.logic.SquirrelLogic;
import com.codefest2013.game.logic.WayPoint;
import com.codefest2013.game.logic.tools.LevelLoader;
import com.codefest2013.game.managers.ResourceManager;
import com.codefest2013.game.scenes.objects.Background;
import com.codefest2013.game.scenes.objects.Gift;
import com.codefest2013.game.scenes.objects.Squirrel;
import com.codefest2013.game.scenes.objects.Player;
import com.codefest2013.game.scenes.objects.SquirrelPathModifierListener;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.text.Text;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.touch.TouchEvent;

public class GameScene extends ManagedScene implements IOnSceneTouchListener {
	private ResourceManager mResourceManager = ResourceManager.getInstance();
	
	private SquirrelLogic squirrelLogic = null;
	
	private Player player = null;
	private Squirrel squirrel = null;
    private Background background = null;
    private List<WayPoint> wps = null;
    private int score = 0;
    private HUD hud;
    private Text scoreText;
    private LinkedList<Gift> gifts;
    private Rectangle ground;
    		
    private ManagedScene thisManagedGameScene = this;
    
    private final float PLAYER_START_X = mResourceManager.CAMERA_WIDTH/2;
    private final float PLAYER_START_Y = mResourceManager.CAMERA_HEIGHT-mResourceManager.CAMERA_HEIGHT/5;
			
    private PhysicsWorld world = null;
    
    public GameScene() {
    	this(0.0f);
    }
    
    public GameScene(float pLoadingScreenMinimumSecondsShown) {
    	super(pLoadingScreenMinimumSecondsShown);
    }
    
	@Override
	public boolean onSceneTouchEvent(Scene arg0, TouchEvent arg1) {
		return getPlayer().onSceneTouchEvent(arg0, arg1);
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
		
		gifts = new LinkedList<Gift>();
	    ground = new Rectangle(0f, mResourceManager.WORLD_HEIGHT-10f, mResourceManager.WORLD_WIDTH, 1f,
				mResourceManager.engine.getVertexBufferObjectManager());
		
		setWps(LevelLoader.getInstance().load("levels/level.xml"));
	
		setSquirrelLogic(new SquirrelLogic(getWps(), 0, 250));
		
    	setPlayer(new Player(PLAYER_START_X, PLAYER_START_Y));
    	
		setSquirrel(new Squirrel(this));
		
		scoreText = new Text(20, 20, mResourceManager.gameFont, "Score: 00000", mResourceManager.engine.getVertexBufferObjectManager());
		
		background = new Background(getPlayer());
		
    	attachChild(background);
		attachChild(getPlayer());
		
		for(int i=0; i<getWps().size(); i++)
		{
			WayPoint wp = getWps().get(i);
			wp.x *= mResourceManager.WORLD_SCALE_CONSTANT;
			wp.y *= mResourceManager.WORLD_SCALE_CONSTANT;
			/*
			Rectangle r = new Rectangle(wp.x, wp.y, 8, 8, mResourceManager.engine.getVertexBufferObjectManager());
			r.setIgnoreUpdate(true);
			if(wp.isThrowable) {
				r.setColor(Color.BLUE);
			} else {
				r.setColor(Color.WHITE);
			}
			attachChild(r);
			*/
		}
		attachChild(getSquirrel());
		
		registerUpdateHandler(getPlayer());
		mResourceManager.engine.getCamera().setChaseEntity(getPlayer());
		
		hud = new HUD();
		hud.attachChild(scoreText);
		
		mResourceManager.engine.getCamera().setHUD(hud);
	}

	@Override
	public void onShowScene() {
		score = 0;
		setOnSceneTouchListener(this);
		getSquirrel().start();
		background.start();
	}

	@Override
	public void onHideScene() {
		getPlayer().setPosition(PLAYER_START_X, PLAYER_START_Y);
		getSquirrel().stop();
		background.stop();
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
		background.update();
		String str = "Score: " + Integer.toString(score);
		scoreText.setText(str);
		Iterator<Gift> it = gifts.iterator();
		while(it.hasNext()) {
			Gift g = it.next();
			if( g.sprite.collidesWith(player.getSprite()) )
			{
				score += 5;
				g.sprite.detachSelf();
				getWorld().destroyBody(g.body);
				it.remove();
			}
			else if(g.sprite.collidesWith(ground))
			{
				g.sprite.detachSelf();
				getWorld().destroyBody(g.body);
				it.remove();				
			}
		}
		super.onManagedUpdate(pSecondsElapsed);
	}
	
	private void createWorld() {
		setWorld(new FixedStepPhysicsWorld(60, new Vector2(0f, SensorManager.GRAVITY_EARTH*2), false, 8, 3));
		registerUpdateHandler(getWorld());
		
		final FixtureDef WALL_FIXTURE_DEF = PhysicsFactory.createFixtureDef(0.0f, 0.0f, 1.0f);
		final Rectangle ground = new Rectangle(0f, mResourceManager.WORLD_HEIGHT-1f, mResourceManager.WORLD_WIDTH, 1f,
				mResourceManager.engine.getVertexBufferObjectManager());
		final Rectangle roof = new Rectangle(0f, 0f, mResourceManager.WORLD_WIDTH, 1f, 
				mResourceManager.engine.getVertexBufferObjectManager());
		final Rectangle left = new Rectangle(0f, 0f, 1f, mResourceManager.WORLD_HEIGHT,
				mResourceManager.engine.getVertexBufferObjectManager());
		final Rectangle right = new Rectangle(mResourceManager.WORLD_WIDTH-1f, 0f, 1f, mResourceManager.WORLD_HEIGHT,
				mResourceManager.engine.getVertexBufferObjectManager());
		
		PhysicsFactory.createBoxBody(getWorld(), ground, BodyType.StaticBody, WALL_FIXTURE_DEF);
		PhysicsFactory.createBoxBody(getWorld(), roof, BodyType.StaticBody, WALL_FIXTURE_DEF);
		PhysicsFactory.createBoxBody(getWorld(), left, BodyType.StaticBody, WALL_FIXTURE_DEF);
		PhysicsFactory.createBoxBody(getWorld(), right, BodyType.StaticBody, WALL_FIXTURE_DEF);
	}
	
	private void destroyWorld() {
		getWorld().clearForces();
		getWorld().clearPhysicsConnectors();
		getWorld().reset();
		getWorld().dispose();
	}

	public void updateSquirrelPath()
	{
		getSquirrelLogic().pickNextGoal();
		
		List<Integer> currentPath = squirrelLogic.getPath();
		Path path = new Path(currentPath.size());
		for( Integer i : currentPath )
		{
			path.to(getWps().get(i).x, getWps().get(i).y);
		}
		
		float pathLength = 0;
		float relativeSpeed = 0.0f;
		if (currentPath.size() >= 2){
			for (int i = 1; i < currentPath.size(); i++) {
				WayPoint previous = getWps().get(currentPath.get(i - 1));
				WayPoint next = getWps().get(currentPath.get(i));
				pathLength += Math.sqrt(Math.pow(next.x-previous.x, 2)  +  Math.pow(next.y-previous.y, 2));
			}
			relativeSpeed = pathLength/getSquirrelLogic().getSpeed();
		}
		
		PathModifier pathModifier = new PathModifier(relativeSpeed, path, new SquirrelPathModifierListener(this, currentPath));
		pathModifier.setAutoUnregisterWhenFinished(true);
		
		getSquirrel().registerEntityModifier(pathModifier);
	}
	
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Squirrel getSquirrel() {
		return squirrel;
	}

	public void setSquirrel(Squirrel squirrel) {
		this.squirrel = squirrel;
	}

	public SquirrelLogic getSquirrelLogic() {
		return squirrelLogic;
	}

	public void setSquirrelLogic(SquirrelLogic squirrelLogic) {
		this.squirrelLogic = squirrelLogic;
	}

	public List<WayPoint> getWps() {
		return wps;
	}

	public void setWps(List<WayPoint> wps) {
		this.wps = wps;
	}

	public PhysicsWorld getWorld() {
		return world;
	}

	public void setWorld(PhysicsWorld world) {
		this.world = world;
	}
	
	public LinkedList<Gift> getGifts() {
		return gifts;
	}
}
