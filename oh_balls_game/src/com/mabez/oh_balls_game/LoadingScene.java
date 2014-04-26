package com.mabez.oh_balls_game;
import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;

public class LoadingScene
  extends Scene
{
  private TimerHandler MainTimer;
  private SceneManager.AllScenes SceneToSwitch;
  private Activity act;
  private Engine eng;
  private SceneManager mSceneManager;
  private float time = 0.0F;//
  
  public LoadingScene(SceneManager paramSceneManager, SceneManager.AllScenes paramAllScenes, final Engine paramEngine, Activity paramActivity, float paramFloat)
  {
	  
	 /*
	  * Initialise class variables
	  */
    this.mSceneManager = paramSceneManager;
    this.SceneToSwitch = paramAllScenes;
    this.eng = paramEngine;
    this.act = paramActivity;
    this.time = paramFloat;
    
    Log.i("Current Scene: ", this.mSceneManager.getCurrentScene().toString());
    this.loadgfx();
    this.MainTimer = new TimerHandler(this.time, new ITimerCallback()
    {
      public void onTimePassed(TimerHandler paramAnonymousTimerHandler)
      {
        if (LoadingScene.this.SceneToSwitch.equals(SceneManager.AllScenes.GAME))
        {
          LoadingScene.this.mSceneManager.createGameScene(LoadingScene.this.mSceneManager.Difficulty);
          LoadingScene.this.mSceneManager.gameScene.myHUD.setChildrenVisible(true);
          LoadingScene.this.mSceneManager.gameScene.registerHandlers();
        }
        LoadingScene.this.mSceneManager.setCurrentScene(LoadingScene.this.SceneToSwitch);
        paramEngine.unregisterUpdateHandler(LoadingScene.this.MainTimer);
      }
    });
    this.eng.registerUpdateHandler(this.MainTimer);
  }
  
  private Font loadFonts()
  {
    FontFactory.setAssetBasePath("fonts/");
    Font localFont = FontFactory.createFromAsset(this.eng.getFontManager(), this.eng.getTextureManager(), 150, 150, TextureOptions.BILINEAR, this.act.getAssets(), "gillsansultra.ttf", 48.0F, true, Color.rgb(48, 100, 255));
    localFont.load();
    return localFont;
  }
  
  private void loadgfx()
  {
    Text localText = new Text(this.eng.getCamera().getWidth() / 2.0F, this.eng.getCamera().getWidth() / 2.0F, loadFonts(), "LOADING ...", this.eng.getVertexBufferObjectManager());
    localText.setPosition(localText.getX() - localText.getWidth() / 2.0F, localText.getY() - localText.getHeight() / 2.0F);
    attachChild(localText);
  }
}

