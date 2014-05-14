package com.eatlappscontest.kks.view;

public interface ScreenEvent {

	void onScreenRender(float delta);
	void onScreenResize(int width, int height);
	void onScreenShow();
	void onScreenHide();
	void onScreenPause();
	void onScreenResume();
	void onScreenDispose();
}
