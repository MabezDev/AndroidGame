package com.mabez.oh_balls_game;

import android.app.Activity;
import android.util.Log;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import java.io.IOException;
import java.util.Random;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class Ball
  extends Sprite
{
  Sound Boop;
  TimerHandler MainHandlerLoop;
  Activity act;
  Body body;
  Camera camera;
  Engine engine;
  boolean hasPlayed = false;
  PhysicsWorld mWorld;
  
  public Ball(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, ITextureRegion paramITextureRegion, VertexBufferObjectManager paramVertexBufferObjectManager, PhysicsWorld paramPhysicsWorld, Camera paramCamera, Engine paramEngine, Activity paramActivity)
  {
    super(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramITextureRegion, paramVertexBufferObjectManager);
    this.camera = paramCamera;
    this.engine = paramEngine;
    this.mWorld = paramPhysicsWorld;
    this.act = paramActivity;
    createBodyAndAttach();
    loadSound();
  }
  
  private void createBodyAndAttach()
  {
    FixtureDef localFixtureDef = PhysicsFactory.createFixtureDef(1.0F, 0.8F, 0.0F);
    this.body = PhysicsFactory.createCircleBody(this.mWorld, this, BodyType.DynamicBody, localFixtureDef);
    this.mWorld.registerPhysicsConnector(new PhysicsConnector(this, this.body, true, false));
  }
  
  private void loadSound()
  {
    try
    {
      this.Boop = SoundFactory.createSoundFromAsset(this.engine.getSoundManager(), this.act, "sound/bounce.ogg");
      this.Boop.setVolume(0.5F);
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
  
  private int randInt(int paramInt1, int paramInt2)
  {
    return paramInt1 + new Random().nextInt(1 + (paramInt2 - paramInt1));
  }
  
  public void move(float paramFloat1, float paramFloat2)
  {
    Vector2 localVector2 = new Vector2(paramFloat1, paramFloat2);
    this.body.setLinearVelocity(localVector2);
    Log.i("Called", "MoveBalllNigger");
  }
  
  public boolean onAreaTouched(TouchEvent paramTouchEvent, float paramFloat1, float paramFloat2)
  {
    if (paramTouchEvent.isActionUp()) {
      this.Boop.play();
    }
    move(randInt(-11, 11), -10.0F);
    return true;
  }
  
  public void setCentre()
  {
    setPosition(this.camera.getWidth() - getWidth(), this.camera.getHeight() - getHeight());
  }
}

