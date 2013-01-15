package com.codefest2013.game;

import org.andengine.extension.svg.opengl.texture.atlas.bitmap.SVGBitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.bitmap.BitmapTextureFormat;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.debug.Debug;

import android.util.DisplayMetrics;
import android.view.WindowManager;

public class ResourcesManager {
	
    public static float CAMERA_WIDTH;
    public static float CAMERA_HEIGHT;
    public static float WORLD_WIDTH;
    public static float WORLD_HEIGHT;
    
    /**
     * it was calculated by size of room(background) image
     * WORLD_SCALE_CONSTANT = width / height
     * XXX I think that scaling system is not so well implemented right now 
     */
    private static final float WORLD_SCALE_CONSTANT = 2.0645f;

    public TiledTextureRegion goblinTextureRegion;
    public ITextureRegion backgroundTextureRegion;
    
	private static ResourcesManager mInstance = new ResourcesManager();
	
	private ResourcesManager()
	{ 
	}
	
	public static void init()
	{
        final DisplayMetrics displayMetrics = MainActivity.getInstance().getResources().getDisplayMetrics();

        CAMERA_WIDTH = displayMetrics.widthPixels;
        CAMERA_HEIGHT = displayMetrics.heightPixels;

        WORLD_WIDTH = CAMERA_HEIGHT * WORLD_SCALE_CONSTANT;
        WORLD_HEIGHT = CAMERA_HEIGHT;
	}
	
	public void load()
	{
		/*
		final DisplayMetrics displayMetrics = MainActivity.getInstance().getResources().getDisplayMetrics();
        
		float mScaleFactor = 1;
	    int deviceDpi = displayMetrics.densityDpi;
	    switch(deviceDpi){
		    case DisplayMetrics.DENSITY_LOW:
			    // Scale factor already set to 1
			    break;
		    case DisplayMetrics.DENSITY_MEDIUM:
			    // Increase scale to a suitable value for mid-size displays
			    mScaleFactor = 1.5f;
			    break;
		    case DisplayMetrics.DENSITY_HIGH:
			    // Increase scale to a suitable value for larger displays
			    mScaleFactor = 2;
			    break;
		    case DisplayMetrics.DENSITY_XHIGH:
			    // Increase scale to suitable value for largest displays
			    mScaleFactor = 2.5f;
			    break;
		    default:
			    // Scale factor already set to 1
			    break;
	    }
	    */
	    
		BaseGameActivity instance = MainActivity.getInstance();
		
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
    	BitmapTextureAtlas mBackgroundTextureAtlas = new BitmapTextureAtlas(instance.getTextureManager(), 1024, 1024,
        		BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR);
        backgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBackgroundTextureAtlas, instance, "background.png", 0, 0);
        mBackgroundTextureAtlas.load();
    	
        SVGBitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        BuildableBitmapTextureAtlas mBuildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas(instance.getTextureManager(), 1024, 1024, 
        		BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR);
        goblinTextureRegion = (TiledTextureRegion)SVGBitmapTextureAtlasTextureRegionFactory
                .createTiledFromAsset(mBuildableBitmapTextureAtlas, instance,"goblinWalks.svg", 1024, 1024, 12, 2);

        try {
            mBuildableBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
            mBuildableBitmapTextureAtlas.load();
        } catch (final TextureAtlasBuilderException e) {
            Debug.e(e);
        }
	}

	public static ResourcesManager getInstance()
	{
		return mInstance;
	}
	
}
