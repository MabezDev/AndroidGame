package com.mabez.oh_balls_game;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.AudioOptions;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.IGameInterface;
import org.andengine.ui.IGameInterface.OnCreateResourcesCallback;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;
import org.andengine.ui.IGameInterface.OnPopulateSceneCallback;
import org.andengine.ui.activity.BaseGameActivity;

import android.util.Log;

public class Main extends BaseGameActivity
{
  private Camera mCamera;
  private float mCameraHeight = 720.0F;
  private float mCameraWidth = 1280.0F;
  SceneManager mSceneManager;
  
  
  /*
   * Overriding onBackPressed so it doesn't randomly quit 
   */
  
  
  @Override
  public void onBackPressed()
  {
    if(this.mSceneManager.getCurrentScene().equals(SceneManager.AllScenes.GAME)){
    	
    	this.mSceneManager.gameScene.Pause();
    }
  }
  
  
  
  public EngineOptions onCreateEngineOptions()
  {
    this.mCamera = new Camera(0.0F, 0.0F, this.mCameraWidth, this.mCameraHeight);
    EngineOptions localEngineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR, new RatioResolutionPolicy(this.mCameraWidth, this.mCameraHeight), this.mCamera);
    localEngineOptions.getAudioOptions().setNeedsSound(true);
    return localEngineOptions;
  }
  
  public void onCreateResources(IGameInterface.OnCreateResourcesCallback paramOnCreateResourcesCallback)
    throws Exception
  {
	/*
	 * Initialise TheScenemanager class to change Scenes
	 */
	  
    this.mSceneManager = new SceneManager(this, getEngine(), this.mCamera);
    paramOnCreateResourcesCallback.onCreateResourcesFinished();
  }
  
  public void onCreateScene(IGameInterface.OnCreateSceneCallback paramOnCreateSceneCallback)
    throws Exception
  {
    this.mSceneManager.loadSplashResources();
    paramOnCreateSceneCallback.onCreateSceneFinished(this.mSceneManager.createSplashScene());
  }
  
  public void onPopulateScene(Scene paramScene, IGameInterface.OnPopulateSceneCallback paramOnPopulateSceneCallback)
    throws Exception
  {
	  /*
	   * Give Time for Menu Resources To load
	   */
	  
	  
    this.mEngine.registerUpdateHandler(new TimerHandler(3.0F, new ITimerCallback()
    {
      public void onTimePassed(TimerHandler paramAnonymousTimerHandler)
      {
        Main.this.mEngine.unregisterUpdateHandler(paramAnonymousTimerHandler);
        Main.this.mSceneManager.createMenuScene();
        Main.this.mSceneManager.setCurrentScene(SceneManager.AllScenes.MENU);
      }
    }));
    paramOnPopulateSceneCallback.onPopulateSceneFinished();
  }
}

