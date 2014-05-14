package com.eatlappscontest.kks.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.eatlappscontest.kks.spec.StaticObject;
import com.eatlappscontest.kks.view.KKS;

public class Cage extends BaseStaticObject {

	public static final float HEIGHT = 1.5f;
	public static final float WIDTH = 2f;
	public static final float POS_X_DIAGONAL = KKS.VIEWPORT_WIDTH - 2f;
	public static final float POS_Y_DIAGONAL = KKS.VIEWPORT_HEIGHT - 2f;
	public static final float POS_X_LINEAR = KKS.VIEWPORT_WIDTH - 2f;
	public static final float POS_Y_LINEAR = 0.1f;
	
	private boolean drawing = false;

	public Cage(Vector2 position, Texture texture) {
		super(position, texture);
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
		super.draw(batch, ppuX, ppuY, delta);
	}

	@Override
	public StaticObject getType() {
		return StaticObject.Cage;
	}

	@Override
	public long getLifeTime() {
		return 0;
	}

	@Override
	public void setBounds() {
		bounds.width = WIDTH;
		bounds.height = HEIGHT;
	}

}