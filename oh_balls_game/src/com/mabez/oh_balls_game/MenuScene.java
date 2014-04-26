package com.mabez.oh_balls_game;

import android.app.Activity;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;

public class MenuScene
  extends Scene
{
  private MenuButton[] ButtonArray;
  private Camera Cam;
  public AllChoice CurrentChoice;
  public int Difficulty = 0;
  private Activity act;
  private Engine eng;
  public SceneManager mSceneManager;
  
  public MenuScene(Camera paramCamera, Engine paramEngine, Activity paramActivity, SceneManager paramSceneManager)
  {
    this.Cam = paramCamera;
    this.eng = paramEngine;
    this.act = paramActivity;
    this.mSceneManager = paramSceneManager;
    this.ButtonArray = new MenuButton[6];
    try
    {
      loadBg();
      createButtonSprites();
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  
  
  
  public ITextureRegion LoadButtonTextures(String paramString1, String paramString2, int paramInt1, int paramInt2)
  {
    BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(paramString1);
    BitmapTextureAtlas localBitmapTextureAtlas = new BitmapTextureAtlas(this.eng.getTextureManager(), paramInt1, paramInt2);
    localBitmapTextureAtlas.load();
    return BitmapTextureAtlasTextureRegionFactory.createFromAsset(localBitmapTextureAtlas, this.act, paramString2, 0, 0);
  }
  
  public void addLogo()
  {
    Sprite localSprite = new Sprite(this.Cam.getWidth() / 2.0F, 0.0F, 600.0F, 150.0F, LoadButtonTextures("gfx/", "logo.png", 600, 150), this.eng.getVertexBufferObjectManager());
    localSprite.setPosition(localSprite.getX() - localSprite.getWidth() / 2.0F, localSprite.getHeight() / 2.0F - 20.0F);
    attachChild(localSprite);
  }
  
  public void createButtonSprites()
  {
    SwipeListener localSwipeListener = new SwipeListener(this.eng, this.mSceneManager, this.act);
    for (int i = 1;; i++)
    {
      if (i >= 5)
      {
        int j = (int)((this.Cam.getWidth() - this.ButtonArray[1].getTextureRegion().getWidth()) / 2.0F);
        int k = (int)((this.Cam.getHeight() - this.ButtonArray[1].getTextureRegion().getHeight()) / 2.0F);
        localSwipeListener.createMenu((int)this.Cam.getWidth(), j, k, 640);
        attachChild(localSwipeListener);
        localSwipeListener.onShow(this);
        return;
      }
      this.ButtonArray[i] = new MenuButton(-400.0F, this.Cam.getHeight() / 2.0F, 400.0F, 100.0F, LoadButtonTextures("gfx/buttons/", Integer.toString(i) + ".png", 401, 101), this.eng.getVertexBufferObjectManager(), i, this.eng, this.act, this.Cam);
      localSwipeListener.addItem(this.ButtonArray[i].getTextureRegion());
    }
  }
  
  public AllChoice getChoice()
  {
    return this.CurrentChoice;
  }
  
  public void loadBg()
  {
    attachChild(new Sprite(0.0F, 0.0F, 1280.0F, 720.0F, LoadButtonTextures("gfx/", "menubg.png", 1280, 720), this.eng.getVertexBufferObjectManager()));
  }
  
  public void setChoice(AllChoice paramAllChoice)
  {
    this.CurrentChoice = paramAllChoice;
    switch (this.CurrentChoice)
    {
    default: 
      return;
    case EASY: 
      this.Difficulty = 1;
      return;
    case MEDIUM: 
        this.Difficulty = 2;
        return;
    case HARD: 
      this.Difficulty = 3;
      return;
    }
 
  }
  
  public static enum AllChoice
  {
    HARD,  MEDIUM,  EASY;
  }
}

