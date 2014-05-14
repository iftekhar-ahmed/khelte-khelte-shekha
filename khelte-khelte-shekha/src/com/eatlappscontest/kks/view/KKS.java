package com.eatlappscontest.kks.view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.eatlappscontest.kks.controller.GameScreenController;
import com.eatlappscontest.kks.controller.StartScreenController;
import com.eatlappscontest.kks.controller.ZooScreenController;
import com.eatlappscontest.kks.util.GameAssetsManager;

public class KKS extends Game implements OnScreenDisposeListener {

	private GameScreen nextScreen;
	private GameScreenController gameScreenController;
	
	public SpriteBatch batch;
	public BitmapFont font;
	public GameAssetsManager assetsManager;
	public boolean paused;
	
	public static final float VIEWPORT_WIDTH = 7f;
	public static final float VIEWPORT_HEIGHT = 10f;
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont();
		assetsManager = new GameAssetsManager();
		assetsManager.enque();
		
		setScreen(new SplashScreen(batch, assetsManager, this));
	}
	
	@Override
	public void pause() {
		paused = true;
		super.pause();
	}
	
	@Override
	public void resume() {
		paused = false;
		super.resume();
	}
	
	@Override
	public void dispose() {
		super.dispose();
		
		assetsManager.dispose();
		batch.dispose();
		font.dispose();
	}

	@Override
	public void onDispose(Screen screen) {
		if(screen instanceof SplashScreen) {
			StartScreen nextScreen = new StartScreen(this);
			new StartScreenController(this, nextScreen);
			setScreen(nextScreen);
		}
		if(screen instanceof StartScreen) {
			if(nextScreen == null)
				nextScreen = new GameScreen(this);
			if(gameScreenController == null)
				gameScreenController = new GameScreenController(this, nextScreen);
			setScreen(nextScreen);
		}
		if(screen instanceof GameScreen) {
			ZooScreen zooScreen = new ZooScreen(this);
			new ZooScreenController(this, zooScreen);
			setScreen(zooScreen);
		}
		if(screen instanceof ZooScreen) {
			setScreen(nextScreen);
		}
	}
}
