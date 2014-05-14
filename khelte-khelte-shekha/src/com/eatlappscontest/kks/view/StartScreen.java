package com.eatlappscontest.kks.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.eatlappscontest.kks.model.Flag;
import com.eatlappscontest.kks.model.Patriot;

public class StartScreen extends BaseScreen {

	public OrthographicCamera camera;
	public Flag nationalFlag;
	public Patriot patriot;
	public Music nationalAnthem;
	
	public StartScreen(OnScreenDisposeListener disposeListener) {
		super(disposeListener);
		camera = new OrthographicCamera(KKS.VIEWPORT_WIDTH, KKS.VIEWPORT_HEIGHT);
	}
	
	@Override
	public void render(float delta) {
		Gdx.graphics.getGL20().glClearColor( 1, 1, 1, 1 );
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		super.render(delta);
	}

	@Override
	public void draw(SpriteBatch batch, float delta) {
		batch.begin();
		nationalFlag.draw(batch, ppuX, ppuY, delta);
		patriot.draw(batch, ppuX, ppuY, delta);
		batch.end();
	}
	
	@Override
	public void show() {
		camera.position.set(3.5f, 5, 0);
		camera.update();
		super.show();
	}

}
