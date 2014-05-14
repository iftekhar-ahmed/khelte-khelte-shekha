package com.eatlappscontest.kks.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.eatlappscontest.kks.util.GameAssetsManager;
import com.eatlappscontest.kks.view.BaseScreen;
import com.eatlappscontest.kks.view.KKS;
import com.eatlappscontest.kks.view.ScreenEvent;

public abstract class BaseScreenController<T extends BaseScreen> implements InputProcessor, ScreenEvent {
	
	KKS game;
	T screen;
	int delay;
	boolean isScreenShowing;
	
	public BaseScreenController(KKS game, T screen) {
		this.game = game;
		this.screen = screen;
		this.screen.addScreenEventListener(this);
		setInputController(this);
		init(game.assetsManager);
	}
	
	/** Initialize screen objects here. */
	abstract void init(GameAssetsManager assetsManager);

	/** Update drawing, position etc. of screen objects. Called before rendering objects.*/
	abstract void update();
	
	/** Ticks a timer until <b>millis</b> is up. Works synchronously.
	 * @return <b>true</b> when delay is over. */
	synchronized boolean delay(long millis) {
		if(delay > millis) {
			delay = 0;
			return true;
		}
		delay += Gdx.graphics.getDeltaTime() * 1000;
		return false;
	}
	
	void setInputController(InputProcessor processor) {
		Gdx.input.setInputProcessor(processor);
	}
	
	@Override
	public void onScreenRender(float delta) {
		if(isScreenShowing) {
			update();
			screen.draw(game.batch, delta);
		}
	}

	@Override
	public void onScreenResize(int width, int height) {}

	@Override
	public void onScreenShow() {
		isScreenShowing = true;
		onScreenResume();
	}

	@Override
	public void onScreenHide() {
		isScreenShowing = false;
		onScreenPause();
	}

	@Override
	public void onScreenDispose() {}
	
	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}
	
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}
}
