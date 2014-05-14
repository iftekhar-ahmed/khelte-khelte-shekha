package com.eatlappscontest.kks.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.eatlappscontest.kks.data.AssetsEntry;
import com.eatlappscontest.kks.model.Flag;
import com.eatlappscontest.kks.model.Patriot;
import com.eatlappscontest.kks.util.GameAssetsManager;
import com.eatlappscontest.kks.view.KKS;
import com.eatlappscontest.kks.view.StartScreen;

public class StartScreenController extends BaseScreenController<StartScreen> {

	static final float POST_SCREEN_DELAY = 2f;
	
	float stateTimePostScreen = 0f;
	
	public StartScreenController(KKS game, StartScreen screen) {
		super(game, screen);
	}
	
	private void updateStateTimePostScreen(float delta) {
		stateTimePostScreen += delta; 
	}

	@Override
	public void onScreenPause() {
		screen.nationalFlag.pauseAnimation();
		screen.patriot.pauseAnimation();
		screen.nationalAnthem.stop();
	}

	@Override
	public void onScreenResume() {
		update();
	}

	@Override
	void init(GameAssetsManager assetsManager) {
		screen.nationalFlag = new Flag(new Vector2(-2f, -1f), assetsManager);
		screen.patriot = new Patriot(new Vector2(1f, 1f), assetsManager);
		screen.nationalAnthem = assetsManager.getMusic(AssetsEntry.music.NATIONAL_ANTHEM);
		screen.nationalFlag.setDrawing(true);
		screen.nationalFlag.pauseAnimation();
		screen.patriot.setDrawing(true);
		screen.patriot.pauseAnimation();
	}

	@Override
	void update() {
		if(delay(2000)) {
			if(screen.patriot.isPaused()) 
				screen.patriot.unpauseAnimation();
			if(screen.nationalFlag.isPaused()) {
				screen.nationalFlag.unpauseAnimation();
				screen.nationalAnthem.play();
			}
			if(!screen.nationalAnthem.isPlaying()) {
				updateStateTimePostScreen(Gdx.graphics.getDeltaTime() * 1000);
				if(stateTimePostScreen >= POST_SCREEN_DELAY)
					game.onDispose(screen);
			}
		}
	}

}
