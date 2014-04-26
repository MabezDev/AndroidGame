package com.mabez.oh_balls_game;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.BaseGameActivity;

public class SceneManager
{
  
public int Difficulty = 0;
  public GGScene GGScene;
  public LoadingScene LoadingScene;
  private BaseGameActivity activity;
  private Camera camera;
  public AllScenes currentScene;
  private Engine engine;
  public GameScene gameScene;
  public MenuScene menuScene;
  public Scene splashScene;
  private BitmapTextureAtlas splashTA;
  private ITextureRegion splashTR;
  
  
  
  public SceneManager(BaseGameActivity paramBaseGameActivity, Engine paramEngine, Camera paramCamera)
  {
    this.activity = paramBaseGameActivity;
    this.engine = paramEngine;
    this.camera = paramCamera;
  }
  
  public enum AllScenes{
	  SPLASH,LOAD,GAME,GG,MENU
  }
  
  public Scene createGGScene(String paramString)
  {
    Engine localEngine = this.engine;
    BaseGameActivity localBaseGameActivity = this.activity;
    Camera localCamera = this.camera;
    SceneManager localSceneManager = this;
    String str = paramString;
    GGScene localGGScene = new GGScene(localEngine, localBaseGameActivity, localCamera, localSceneManager, str);
    this.GGScene = localGGScene;
    return this.GGScene;
  }
  
  public void createGameScene(int paramInt)
  {
    this.Difficulty = paramInt;
    Engine localEngine = this.engine;
    BaseGameActivity localBaseGameActivity = this.activity;
    Camera localCamera = this.camera;
    int i = this.Difficulty;
    SceneManager localSceneManager = this;
    GameScene localGameScene = new GameScene(localEngine, localBaseGameActivity, localCamera, localSceneManager, i);
    this.gameScene = localGameScene;
  }
  
  public Scene createMenuScene()
  {
    Camera localCamera = this.camera;
    Engine localEngine = this.engine;
    BaseGameActivity localBaseGameActivity = this.activity;
    MenuScene localMenuScene1 = new MenuScene(localCamera, localEngine, localBaseGameActivity, this);
    this.menuScene = localMenuScene1;
    MenuScene localMenuScene2 = this.menuScene;
    Background localBackground = new Background(1.0F, 1.0F, 1.0F);
    localMenuScene2.setBackground(localBackground);
    this.menuScene.Difficulty = 0;
    this.menuScene.addLogo();
    return this.menuScene;
  }
  
  public Scene createSplashScene()
  {
    Scene localScene = new Scene();
    this.splashScene = localScene;
    ITextureRegion localITextureRegion = this.splashTR;
    VertexBufferObjectManager localVertexBufferObjectManager = this.engine.getVertexBufferObjectManager();
    float f = 0.0F;
    Sprite localSprite = new Sprite(0.0F, f, 1280.0F, 720.0F, localITextureRegion, localVertexBufferObjectManager);
    this.splashScene.attachChild(localSprite);
    return this.splashScene;
  }
  
  public AllScenes getCurrentScene()
  {
    return this.currentScene;
  }
  
  public void loadSplashResources()
  {
    BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
    TextureManager localTextureManager = this.engine.getTextureManager();
    BitmapTextureAtlas localBitmapTextureAtlas1 = new BitmapTextureAtlas(localTextureManager, 1280, 720);
    this.splashTA = localBitmapTextureAtlas1;
    BitmapTextureAtlas localBitmapTextureAtlas2 = this.splashTA;
    BaseGameActivity localBaseGameActivity = this.activity;
    TextureRegion localTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(localBitmapTextureAtlas2, localBaseGameActivity, "splash.png", 0, 0);
    this.splashTR = localTextureRegion;
    this.splashTA.load();
  }
  
  public void setCurrentScene(AllScenes paramAllScenes)
  {
    this.currentScene = paramAllScenes;
    switch (this.currentScene)
    {
    default: 
      break;
    case SPLASH: 
      this.engine.setScene(splashScene);
      break;
    case MENU: 
    	this.engine.setScene(menuScene);
      break;
    case GAME: 
    	this.engine.setScene(gameScene);
    	break;
    case GG: 
    	this.engine.setScene(GGScene);
    	break;
    case LOAD: 
    	this.engine.setScene(LoadingScene);
    	break;
    }
   
  }
  
}

