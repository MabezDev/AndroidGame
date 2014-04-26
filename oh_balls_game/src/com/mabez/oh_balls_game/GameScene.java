package com.mabez.oh_balls_game;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Joint;

import java.util.Iterator;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.debug.Debug;

public class GameScene
  extends Scene
{
  private Ball[] BallArray;
  private boolean Done = false;
  private String FinalScore = "";
  private TimerHandler MainHandlerLoop;
  private TimerHandler MainHandlerTimer;
  private int Minuets = 0;
  public int Score = 0;
  private int Seconds = 0;
  private Activity act;
  private int ballCount = 0;
  private Camera cam;
  private Multiplyer currentMultiplyer;
  private Engine eng;
  private SceneManager mSceneManager;
  private Text mText;
  private PhysicsWorld mWorld;
  public HUD myHUD;
  private Text sText;
  public int scoreMultiplayer;
  int timeInSeconds = 0;
  private boolean isPaused =false;
  
  
  /*
   * TO DO READD PAUSE CHILD SCENE-IHAVEGFX FOR THSES-DONE
   * REDO TIMER SYSTEM-DONE
   * OVERIDE ON PAUSE AND ONRESUME METHODS IN MAIN-DONE
   * ONBACKPRESS OVERRIDE-DONE
   * SCREEN ORRIENTATION CONFIG CHNAGES TO STOP RESTART ON LOCK-DONE
   * 
   */
  
  public GameScene(Engine paramEngine, Activity paramActivity, Camera paramCamera, SceneManager paramSceneManager, int paramInt)
  {
    this.act = paramActivity;
    this.cam = paramCamera;
    this.eng = paramEngine;
    this.mSceneManager = paramSceneManager;
    this.ballCount = paramInt;
    Log.i("Current Scene: ", this.mSceneManager.getCurrentScene().toString());
    /*
     * Create Physics World under Earths gravity
     */
    
    this.mWorld = new PhysicsWorld(new Vector2(0.0F, 9.80665F), false);
    registerUpdateHandler(this.mWorld);
    /*
     * Load Balls and textures
     */
    calculateMultiplyer();
    loadgameResources();
    registerHandlers();
    createMainScene();
    calculateAndAddBalls();
  }
  public void Pause(){
	  isPaused=true;
	  PauseScene p = new PauseScene(this.eng,this.act,this.cam,this.mSceneManager);
	  this.setChildScene(p,false, true, true);
  }
  
  public void resume(){
	  isPaused=false;
  }
  
  
  
  
  private void CreateWalls(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    FixtureDef localFixtureDef = PhysicsFactory.createFixtureDef(0.0F, 0.0F, 0.0F);
    Sprite localSprite = new Sprite(paramFloat1, paramFloat2, paramFloat3, paramFloat4, LoadButtonTextures("gfx/", "walls.png", 15, 720), this.eng.getVertexBufferObjectManager());
    PhysicsFactory.createBoxBody(this.mWorld, localSprite, BodyType.StaticBody, localFixtureDef).setUserData("floor");
    attachChild(localSprite);
  }
  
  private void calculateAndAddBalls()
  {
    for (int i = 1;; i++)
    {
      if (i >= 1 + this.ballCount) {
        return;
      }
      addBall(i, 0 + i * 256);
    }
  }
  
  private void calculateMultiplyer()
  {
	  Log.i("ball",Integer.toString(ballCount));
    if (this.ballCount == 1) {
      setCurrentMultiplyer(Multiplyer.BASE);
    }
    if (this.ballCount == 2) {
      setCurrentMultiplyer(Multiplyer.DOUBLE);
    }
    if (this.ballCount == 3) {
      setCurrentMultiplyer(Multiplyer.TRIPLE);
    }
    if (this.ballCount == 4) {
      setCurrentMultiplyer(Multiplyer.TENTIMES);
    }
  }
  
  private void checkSpriteCoordinate()
  {
    try
    {
      for (int i=1; i<ballCount+1;i++)
      {
        if (this.BallArray[i].getY() > this.cam.getHeight())
        {
          //this.unregisterUpdateHandler(this.MainHandlerLoop);
          //this.unregisterUpdateHandler(this.MainHandlerTimer);
          gameOver();
        }
      }
    }
    catch (NullPointerException localNullPointerException)
    {
      localNullPointerException.printStackTrace();
    }
  }
  
  private void createMainScene()
  {
    this.BallArray = new Ball[5];
    this.Score = 0;
    this.Minuets = 0;
    this.Seconds = 0;
    this.timeInSeconds = 0;
    this.sText = new Text(50.0F, 20.0F, loadFonts(), "00:00", this.eng.getVertexBufferObjectManager());
    this.sText.setColor(48.0F, 100.0F, 255.0F);
    this.mText = new Text(this.cam.getWidth() - 340.0F, 20.0F, loadFonts(), "0000000000", this.eng.getVertexBufferObjectManager());
    this.mText.setColor(48.0F, 100.0F, 255.0F);
    /*
     * Create Walls
     */
    
    CreateWalls(0.0F, 0.0F, 15.0F, this.cam.getHeight());
    CreateWalls(0.0F, -this.cam.getHeight(), 15.0F, this.cam.getHeight());
    CreateWalls(this.cam.getWidth() - 15.0F, 0.0F, 15.0F, this.cam.getHeight());
    CreateWalls(this.cam.getWidth() - 15.0F, -this.cam.getHeight(), 15.0F, this.cam.getHeight());
    createHUD();
    this.registerUpdateHandler(this.MainHandlerTimer);
    this.registerUpdateHandler(this.MainHandlerLoop);
  }
  
  private void gameOver()
  {
    this.myHUD.setChildrenVisible(false);
    this.MainHandlerLoop = null;
    this.MainHandlerLoop = null;
    this.eng.runOnUpdateThread(new Runnable()
    {
      public void run()
      {
        Log.i("Called", "inside Gameover");
        GameScene.this.BallArray = null;
        try
        {
          GameScene.this.myHUD.detachChildren();
          GameScene.this.myHUD.detachSelf();
          GameScene.this.myGarbageCollection();
          return;
        }
        catch (Exception localException)
        {
          for (;;)
          {
            localException.printStackTrace();
          }
        }
      }
    });
    this.mSceneManager.createGGScene(Integer.toString(this.Score));
    this.mSceneManager.setCurrentScene(SceneManager.AllScenes.GG);
  }
  
  private Font loadFonts()
  {
    FontFactory.setAssetBasePath("fonts/");
    Font localFont = FontFactory.createFromAsset(this.eng.getFontManager(), this.eng.getTextureManager(), 150, 150, TextureOptions.BILINEAR, this.act.getAssets(), "gillsansultra.ttf", 36.0F, true, Color.rgb(48, 100, 255));
    localFont.load();
    return localFont;
  }
  
  private void loadgameResources()
  {
    attachChild(new Sprite(0.0F, 0.0F, this.cam.getWidth(), this.cam.getHeight(), LoadButtonTextures("gfx/", "backgroundgame.png", 1280, 720), this.eng.getVertexBufferObjectManager()));
  }
  
  private void myGarbageCollection()
  {
    Iterator<Joint> localIterator = this.mWorld.getJoints();
    for (;;)
    {
      if (!localIterator.hasNext())
      {
        detachChildren();
        return;
      }
      try
      {
        final Joint localJoint = (Joint)localIterator.next();
        this.eng.runOnUpdateThread(new Runnable()
        {
          public void run()
          {
            GameScene.this.mWorld.destroyJoint(localJoint);
          }
        });
      }
      catch (Exception localException)
      {
        Debug.d("SPK - THE JOINT DOES NOT WANT TO DIE: " + localException);
      }
    }
  }
  
  public ITextureRegion LoadButtonTextures(String paramString1, String paramString2, int paramInt1, int paramInt2)
  {
    BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(paramString1);
    BitmapTextureAtlas localBitmapTextureAtlas = new BitmapTextureAtlas(this.eng.getTextureManager(), paramInt1, paramInt2);
    localBitmapTextureAtlas.load();
    return BitmapTextureAtlasTextureRegionFactory.createFromAsset(localBitmapTextureAtlas, this.act, paramString2, 0, 0);
  }
  
  public void addBall(int paramInt1, int paramInt2)
  {
    this.BallArray[paramInt1] = new Ball(paramInt2, 0.0F, 128.0F, 128.0F, LoadButtonTextures("gfx/", "ball.png", 128, 128), this.eng.getVertexBufferObjectManager(), this.mWorld, this.cam, this.eng, this.act);
    this.BallArray[paramInt1].setCentre();
    registerTouchArea(this.BallArray[paramInt1]);
    attachChild(this.BallArray[paramInt1]);
  }
  
  public void createHUD()
  {
    this.myHUD = new HUD();
    this.cam.setHUD(this.myHUD);
    this.myHUD.attachChild(this.sText);
    this.myHUD.attachChild(this.mText);
    this.myHUD.setChildrenVisible(false);
  }
  
  public Multiplyer getCurrentMultiplyer()
  {
    return this.currentMultiplyer;
  }
  
  public void registerHandlers()
  {
    this.MainHandlerTimer = new TimerHandler(1.0F, new ITimerCallback()
    {
      public void onTimePassed(TimerHandler paramAnonymousTimerHandler)
      {
    	  if(isPaused==false){
    		  timeInSeconds+=1;
    	  }
        
        	paramAnonymousTimerHandler.reset();
      }
    });
    this.MainHandlerLoop = new TimerHandler(0.1F, new ITimerCallback()
    {
      public void onTimePassed(TimerHandler paramAnonymousTimerHandler)
      {
        checkSpriteCoordinate();
        Score = (GameScene.this.timeInSeconds * GameScene.this.scoreMultiplayer);
        updateScore(GameScene.this.Score);
        paramAnonymousTimerHandler.reset();
      }
    });
  }
  
  public void setCurrentMultiplyer(Multiplyer paramMultiplyer)
  {
    this.currentMultiplyer = paramMultiplyer;
    switch (paramMultiplyer)
    {
    default: 
      return;
    case BASE: 
      this.scoreMultiplayer = 100;
      break;
    case DOUBLE: 
      this.scoreMultiplayer = 200;
      break;
    case TRIPLE: 
      this.scoreMultiplayer = 300;
      break;
    }
    //this.scoreMultiplayer = 1000;
  }
  
  protected void updateScore(int paramInt)
  {
    String str1 = "";
    String str2 = Integer.toString(paramInt);
    int i = 10 - str2.length();
    for (int j = 0;; j++)
    {
      if (j >= i)
      {
        String str3 = str1 + str2;
        this.mText.setText(str3);
        return;
      }
      str1 = str1 + "0";
    }
  }
  
  
  
  public static enum Multiplyer
  {
    TENTIMES,  TRIPLE,  DOUBLE,  BASE;
  }



}
