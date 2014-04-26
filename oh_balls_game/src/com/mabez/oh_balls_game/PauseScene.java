package com.mabez.oh_balls_game;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;

import android.app.Activity;
import android.util.Log;

public class PauseScene extends Scene {
	
	private Engine eng;
	private Activity act;
	private Camera cam;
	private SceneManager mSceneManager;

	public PauseScene(Engine eng, Activity act, Camera cam,SceneManager m) {
		this.eng=eng;
		this.cam=cam;
		this.act=act;
		this.mSceneManager=m;
		Log.i("called","pasuescene");
		loadGFX();
		this.setBackgroundEnabled(false);
	}

	private void loadGFX() {
		Sprite pbg = new Sprite(0,0,500,300,LoadButtonTextures("gfx/pause/","pbg.png",500,300),this.eng.getVertexBufferObjectManager());
		pbg.setPosition(this.cam.getWidth()/2-pbg.getWidth()/2, this.cam.getHeight()/2-pbg.getHeight()/2);
		
		Sprite pquit = new Sprite(0,0,400,100,LoadButtonTextures("gfx/pause/","quit.png",400,100),this.eng.getVertexBufferObjectManager()){
			public boolean onAreaTouched(TouchEvent paramAnonymousTouchEvent, float paramAnonymousFloat1, float paramAnonymousFloat2)
		      {
				mSceneManager.createGGScene(Integer.toString(mSceneManager.gameScene.Score));
				mSceneManager.setCurrentScene(SceneManager.AllScenes.GG);
				return true;
		      }
		};
		pquit.setPosition(this.cam.getWidth()/2-pquit.getWidth()/2, this.cam.getHeight()/2-pquit.getHeight()/2+80);
		
		
		
		Sprite presume = new Sprite(0,0,400,100,LoadButtonTextures("gfx/pause/","resume.png",400,100),this.eng.getVertexBufferObjectManager()){
			public boolean onAreaTouched(TouchEvent paramAnonymousTouchEvent, float paramAnonymousFloat1, float paramAnonymousFloat2)
		      {
				mSceneManager.gameScene.resume();
				back();
				return true;
		      }
		};
		
		presume.setPosition(this.cam.getWidth()/2-presume.getWidth()/2, this.cam.getHeight()/2-presume.getHeight()/2-80);
		
		this.attachChild(pbg);
		this.attachChild(pquit);
		this.attachChild(presume);
		
		
		this.registerTouchArea(pquit);
		this.registerTouchArea(presume);
				 
			
		
		
		
	}
	
	
	
	public ITextureRegion LoadButtonTextures(String paramString1, String paramString2, int paramInt1, int paramInt2)
	  {
	    BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(paramString1);
	    BitmapTextureAtlas localBitmapTextureAtlas = new BitmapTextureAtlas(this.eng.getTextureManager(), paramInt1, paramInt2);
	    localBitmapTextureAtlas.load();
	    return BitmapTextureAtlasTextureRegionFactory.createFromAsset(localBitmapTextureAtlas, this.act, paramString2, 0, 0);
	  }
}
