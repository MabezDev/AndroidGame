package com.mabez.oh_balls_game;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;

import java.io.IOException;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.font.StrokeFont;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class GGScene
  extends Scene
{
  private String StringScore;
  private Sound Tap;
  private Activity act;
  private Camera cam;
  private Engine eng;
  private boolean hasPlayed = false;
  private SceneManager mSceneManager;
  HUD myHUD;
  
  public GGScene(Engine paramEngine, Activity paramActivity, Camera paramCamera, SceneManager paramSceneManager, String paramString)
  {
	  /*
	   * Initialise class variables
	   */ 
	  
		this.cam = paramCamera;
		this.eng = paramEngine;
		this.act = paramActivity;
		this.mSceneManager = paramSceneManager;
		this.StringScore = paramString;
		loadBg();
		//loadSound();
		setBackground(new Background(0.0F, 0.0F, 0.0F));

		try {
			createGGButtons();
			return;
		} catch (Exception localException) {
			localException.printStackTrace();
		}
  }
  
  
  
  private void createGGButtons()
  {
    MenuButton local1 = new MenuButton(640.0F, this.cam.getHeight() - 360.0F, 400.0F, 100.0F, LoadButtonTextures("gfx/ggScene/", "yes.png", 400, 100), this.eng.getVertexBufferObjectManager(), 0, this.eng, this.act, this.cam)
    {
      public boolean onAreaTouched(TouchEvent paramAnonymousTouchEvent, float paramAnonymousFloat1, float paramAnonymousFloat2)
      {
        if (paramAnonymousTouchEvent.isActionUp())
        {
          
          GGScene.this.myHUD.setChildrenVisible(false);
          GGScene.this.myHUD.detachSelf();
          GGScene.this.reloadGame();
        }
        return true;
      }
    };
    local1.setPosition(local1.getX() - local1.getWidth() / 2.0F - local1.getWidth(), 406.0F + local1.getY() / 2.0F);
    MenuButton local2 = new MenuButton(640.0F, this.cam.getHeight() - 360.0F, 400.0F, 100.0F, LoadButtonTextures("gfx/ggScene/", "no.png", 400, 100), this.eng.getVertexBufferObjectManager(), 0, this.eng, this.act, this.cam)
    {
      public boolean onAreaTouched(TouchEvent paramAnonymousTouchEvent, float paramAnonymousFloat1, float paramAnonymousFloat2)
      {
        if (paramAnonymousTouchEvent.isActionUp())
        {
          
          GGScene.this.mSceneManager.Difficulty = 0;
          GGScene.this.mSceneManager.createMenuScene();
          GGScene.this.mSceneManager.Difficulty = 0;
          GGScene.this.eng.registerUpdateHandler(new TimerHandler(0.5F, new ITimerCallback()
          {
            public void onTimePassed(TimerHandler paramAnonymous2TimerHandler)
            {
              GGScene.this.eng.unregisterUpdateHandler(paramAnonymous2TimerHandler);
              GGScene.this.myHUD.setChildrenVisible(false);
              GGScene.this.myHUD.detachSelf();
              GGScene.this.mSceneManager.setCurrentScene(SceneManager.AllScenes.MENU);
            }
          }));
        }
        return true;
      }
    };
    local2.setPosition(local2.getX() + local2.getWidth() / 2.0F, 406.0F + local2.getY() / 2.0F);
    Sprite localSprite1 = new Sprite(0.0F, 0.0F, 600.0F, 150.0F, LoadButtonTextures("gfx/ggScene/", "gameover.png", 600, 150), this.eng.getVertexBufferObjectManager());
    localSprite1.setPosition(this.cam.getWidth() / 2.0F - localSprite1.getWidth() / 2.0F, localSprite1.getHeight() / 2.0F);
    Sprite localSprite2 = new Sprite(0.0F, 0.0F, 400.0F, 100.0F, LoadButtonTextures("gfx/ggScene/", "score.png", 400, 100), this.eng.getVertexBufferObjectManager());
    localSprite2.setPosition(200.0F, 300.0F);
    Sprite localSprite3 = new Sprite(0.0F, 0.0F, 400.0F, 100.0F, LoadButtonTextures("gfx/ggScene/", "placeholder.png", 400, 100), this.eng.getVertexBufferObjectManager());
    localSprite3.setPosition(this.cam.getWidth() - localSprite3.getWidth() - 200.0F, 300.0F);
    setScoreHud(new Text(this.cam.getWidth() - localSprite3.getWidth() - 170.0F, 325.0F, loadFonts(), this.StringScore, this.eng.getVertexBufferObjectManager()));
    
    /*
     *Attack the buttons and placeholder 
     */
    attachChild(localSprite3);
    attachChild(localSprite2);
    attachChild(localSprite1);
    attachChild(local2);
    attachChild(local1);
    
    /*
     * register touch areas to scene
     */
    registerTouchArea(local2);
    
    registerTouchArea(local1);
  }
  
  private StrokeFont loadFonts()
  {
    FontFactory.setAssetBasePath("fonts/");
    BitmapTextureAtlas localBitmapTextureAtlas = new BitmapTextureAtlas(this.eng.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
    StrokeFont localStrokeFont = new StrokeFont(this.eng.getFontManager(), localBitmapTextureAtlas, Typeface.create(Typeface.DEFAULT, 1), 48.0F, true, Color.rgb(255, 85, 0), 2.0F, -16777216);
    localStrokeFont.load();
    return localStrokeFont;
  }
  
  private void loadSound()
  {
    try
    {
      this.Tap = SoundFactory.createSoundFromAsset(this.eng.getSoundManager(), this.act, "sound/tap.ogg");
      this.Tap.setVolume(1.0F);
      return;
    }
    catch (IllegalStateException localIllegalStateException)
    {
      localIllegalStateException.printStackTrace();
      return;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
  }
  
  private void reloadGame()
  {
    this.mSceneManager.LoadingScene = null;
    this.mSceneManager.LoadingScene = new LoadingScene(this.mSceneManager, SceneManager.AllScenes.GAME, this.eng, this.act, 1.0F);
    this.mSceneManager.setCurrentScene(SceneManager.AllScenes.LOAD);
  }
  
  private void setScoreHud(Text paramText)
  {
    this.myHUD = new HUD();
    this.myHUD.attachChild(paramText);
    this.cam.setHUD(this.myHUD);
  }
  
  public ITextureRegion LoadButtonTextures(String paramString1, String paramString2, int paramInt1, int paramInt2)
  {
    BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(paramString1);
    BitmapTextureAtlas localBitmapTextureAtlas = new BitmapTextureAtlas(this.eng.getTextureManager(), paramInt1, paramInt2);
    localBitmapTextureAtlas.load();
    return BitmapTextureAtlasTextureRegionFactory.createFromAsset(localBitmapTextureAtlas, this.act, paramString2, 0, 0);
  }
  
  public void loadBg()
  {
    attachChild(new Sprite(0.0F, 0.0F, 1280.0F, 720.0F, LoadButtonTextures("gfx/", "menubg.png", 1280, 720), this.eng.getVertexBufferObjectManager()));
  }
}

