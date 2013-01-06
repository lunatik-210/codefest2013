package com.codefest2013.game;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.util.GLState;

/**
 * Splash screen scene
 */
public class SplashScene extends Scene {
	private Sprite mSplash;

	public SplashScene(Engine engine, ITextureRegion splashTextureRegion, int cameraWidth, int cameraHeight) {
		mSplash = new Sprite(0, 0, splashTextureRegion, engine.getVertexBufferObjectManager())
        {
            @Override
            protected void preDraw(GLState pGLState, Camera pCamera) 
            {
                super.preDraw(pGLState, pCamera);
                pGLState.enableDither();
            }
        };

        mSplash.setScale(1.5f);
        mSplash.setPosition((cameraWidth - mSplash.getWidth()) * 0.5f, (cameraHeight - mSplash.getHeight()) * 0.5f);
        this.attachChild(mSplash);
	}

	public Sprite getSplash() {
		return mSplash;
	}

	public void detachSplash() {
		mSplash.detachSelf();
	}
}
