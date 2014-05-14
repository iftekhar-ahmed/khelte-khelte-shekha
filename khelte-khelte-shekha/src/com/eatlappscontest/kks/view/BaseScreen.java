package com.eatlappscontest.kks.view;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class BaseScreen implements Screen {

	ScreenEvent screenEvent;
	OnScreenDisposeListener disposeListener;
	public int width, height;
	public float ppuX, ppuY;

	public BaseScreen(OnScreenDisposeListener disposeListener) {
		this.disposeListener = disposeListener;
	}

	/**
	 * Draw screen objects here. Must call batch.
	 * {@link com.badlogic.gdx.graphics.g2d.SpriteBatch#begin() begin()} before
	 * drawing and batch.{@link com.badlogic.gdx.graphics.g2d.SpriteBatch#end()
	 * end()} after drawing
	 */
	public abstract void draw(SpriteBatch batch, float delta);

	public void addScreenEventListener(ScreenEvent screenEvent) {
		this.screenEvent = screenEvent;
	}

	@Override
	public void render(float delta) {
		if (screenEvent != null)
			screenEvent.onScreenRender(delta);
	}

	@Override
	public void resize(int width, int height) {
		this.width = width;
		this.height = height;
		ppuX = (float) width / KKS.VIEWPORT_WIDTH;
		ppuY = (float) height / KKS.VIEWPORT_HEIGHT;
		if (screenEvent != null)
			screenEvent.onScreenResize(width, height);
	}

	@Override
	public void show() {
		if (screenEvent != null)
			screenEvent.onScreenShow();
	}

	@Override
	public void hide() {
		if (screenEvent != null)
			screenEvent.onScreenHide();
	}

	@Override
	public void pause() {
		if (screenEvent != null)
			screenEvent.onScreenPause();
	}

	@Override
	public void resume() {
		if (screenEvent != null)
			screenEvent.onScreenResume();
	}

	@Override
	public void dispose() {
		if (screenEvent != null)
			screenEvent.onScreenDispose();
		if (disposeListener != null)
			disposeListener.onDispose(this);
	}

}
