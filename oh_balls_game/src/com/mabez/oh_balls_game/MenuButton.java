package com.mabez.oh_balls_game;

import android.app.Activity;
import android.util.Log;
import java.io.IOException;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class MenuButton extends Sprite
{
  public int ID = 0;
  public Sound Tap;
  private Activity act;
  private Camera cam;
  private Engine eng;
  private boolean hasPlayed = false;
  public boolean wasTouched = false;
  
  public MenuButton(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, ITextureRegion paramITextureRegion, VertexBufferObjectManager paramVertexBufferObjectManager, int paramInt, Engine paramEngine, Activity paramActivity, Camera paramCamera)
  {
    super(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramITextureRegion, paramVertexBufferObjectManager);
    /*
     * Initialise class variables
     */
    this.ID = paramInt;
    this.cam = paramCamera;
    this.eng = paramEngine;
    this.act = paramActivity;
    
  }
  
  
  
  
  
  public int giveID()
  {
    return this.ID;
  }
  
  public boolean onAreaTouched(TouchEvent paramTouchEvent, float paramFloat1, float paramFloat2)
  {
    
    
    return true;
  }
  
  public void setCentred()
  {
    setPosition(getX() - getWidth() / 2.0F, getY() - getHeight() / 2.0F);
  }
}

