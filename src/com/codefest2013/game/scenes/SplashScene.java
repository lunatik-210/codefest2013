package com.codefest2013.game.scenes;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;

import com.codefest2013.game.managers.ResourceManager;

/**
 * Splash screen scene
 */
public class SplashScene extends Scene {
	public SplashScene() {
		ResourceManager mResourceManager = ResourceManager.getInstance();
		
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        BitmapTextureAtlas mSplashTextureAtlas = new BitmapTextureAtlas(mResourceManager.engine.getTextureManager(),
        		256, 256, TextureOptions.DEFAULT);
        ITextureRegion mSplashTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mSplashTextureAtlas,
        		mResourceManager.context, "splash.png", 0, 0);
        mSplashTextureAtlas.load();
		
        Sprite mSplash = new Sprite(0, 0, mSplashTextureRegion, mResourceManager.engine.getVertexBufferObjectManager());
        mSplash.setPosition((mResourceManager.CAMERA_WIDTH - mSplash.getWidth()) * 0.5f,
        		(mResourceManager.CAMERA_HEIGHT - mSplash.getHeight()) * 0.5f);
        
		this.attachChild(mSplash);
	}
}
