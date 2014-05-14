package com.eatlappscontest.kks.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.eatlappscontest.kks.util.GameAssetsManager;

public class SplashScreen implements Screen {

	private SpriteBatch batch;
	private GameAssetsManager assetsManager;
	private OnScreenDisposeListener disposeListener;
	private OrthographicCamera camera;
	private Texture textureHome;
	
	float ppuX, ppuY;
	
	public SplashScreen(SpriteBatch batch, GameAssetsManager assetsManager, OnScreenDisposeListener disposeListener) {
		this.batch = batch;
		this.assetsManager = assetsManager;
		this.disposeListener = disposeListener;
		camera = new OrthographicCamera(KKS.VIEWPORT_WIDTH, KKS.VIEWPORT_HEIGHT);
		camera.position.set(3.5f, 5, 0);
		camera.update();
		
		textureHome = new Texture(Gdx.files.internal("splash.png"));
	}

	@Override
	public void render(float delta) {
		Gdx.graphics.getGL20().glClearColor( 1, 1, 1, 1 );
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		batch.begin();
		batch.draw(textureHome, 0f, 0f, KKS.VIEWPORT_WIDTH * ppuX, KKS.VIEWPORT_HEIGHT * ppuY);
		batch.end();

		if(assetsManager.update())
		{
			dispose();
		}
	}

	@Override
	public void resize(int width, int height) {	
		ppuX = (float) width / KKS.VIEWPORT_WIDTH;
		ppuY = (float) height / KKS.VIEWPORT_HEIGHT;
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		textureHome.dispose();
		disposeListener.onDispose(this);
	}

}
