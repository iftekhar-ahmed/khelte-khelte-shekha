package com.eatlappscontest.kks.model;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.eatlappscontest.kks.data.AssetsEntry;
import com.eatlappscontest.kks.spec.StaticObject;
import com.eatlappscontest.kks.util.Animator;
import com.eatlappscontest.kks.util.GameAssetsManager;

public class Flag extends BaseStaticObject {
	
	public static final float WIDTH = 10f;
	public static final float HEIGHT = 10f;
	public static final float FRAME_DURATION = 0.1f;
	
	boolean drawing = false, pauseAnimation = false;
	float stateTimeAnimFrame = 0f;

	Animation animation;
	
	public Flag(Vector2 position, GameAssetsManager assetsManager) {
		super(position, null);
		animation = new Animator(assetsManager.getTextureAtlas(AssetsEntry.texture_atlas.FLAG),
				assetsManager.getTextureRegionData(AssetsEntry.texture_atlas.FLAG),
				FRAME_DURATION).getAnimation();
	}
	
	private void updateStateTimeAnimFrame(float delta) {
		this.stateTimeAnimFrame += delta;
	}
	
	public void pauseAnimation() {
		pauseAnimation = true;
	}
	
	public void unpauseAnimation() {
		pauseAnimation = false;
	}
	
	public boolean isPaused() {
		return pauseAnimation;
	}
	
	public boolean isDrawing() {
		return drawing;
	}

	public void setDrawing(boolean drawing) {
		this.drawing = drawing;
	}

	@Override
	public void draw(SpriteBatch batch, float ppuX, float ppuY, float delta) {
		if(!drawing)
			return;
		
		if(!pauseAnimation) {
			updateStateTimeAnimFrame(delta);
		}
		TextureRegion currentBirdFrame = animation.getKeyFrame(
				stateTimeAnimFrame, true);
		batch.draw(currentBirdFrame, position.x * ppuX, position.y * ppuY,
				bounds.getWidth() * ppuX, bounds.getHeight() * ppuY);
	}

	@Override
	public StaticObject getType() {
		return StaticObject.Flag;
	}

	@Override
	public long getLifeTime() {
		return 0;
	}

	@Override
	void setBounds() {
		bounds.width = WIDTH;
		bounds.height = HEIGHT; 
	}

}
