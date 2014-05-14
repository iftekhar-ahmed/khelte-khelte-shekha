package com.eatlappscontest.kks.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.eatlappscontest.kks.spec.StaticObject;
import com.eatlappscontest.kks.view.KKS;

public class DrawingBoard extends BaseStaticObject {

	boolean drawing, animationComplete;

	private static final float ANIMATION_X = 0.14f;
	private static final float ANIMATION_Y = 0.20f;
	private static final float PADDING = 0.3f;
	public static final float POS_X = KKS.VIEWPORT_WIDTH - PADDING;
	public static final float POS_Y = KKS.VIEWPORT_HEIGHT - PADDING;

	public DrawingBoard(Texture texture) {
		super(new Vector2(POS_X, POS_Y), texture);
	}

	@Override
	public void draw(SpriteBatch batch, float ppuX, float ppuY, float delta) {
		if (!drawing)
			return;
		super.draw(batch, ppuX, ppuY, delta);
	}

	public void zoomOut() {

		if (position.x <= PADDING && position.y <= PADDING) {
			animationComplete = true;
		} else {
			if (position.x > PADDING) {
				position.x -= ANIMATION_X;
				bounds.width += ANIMATION_X;
			}
			if (position.y > PADDING) {
				position.y -= ANIMATION_Y;
				bounds.height += ANIMATION_Y;
			}
		}
	}

	public boolean isDrawing() {
		return drawing;
	}

	public void setDrawing(boolean drawing) {
		this.drawing = drawing;
	}

	public boolean isAnimationDone() {
		return animationComplete;
	}

	public void reset() {
		position.x = POS_X;
		position.y = POS_Y;
		setBounds();
		drawing = false;
		animationComplete = false;
	}

	@Override
	public StaticObject getType() {
		return StaticObject.TransparentBG;
	}

	@Override
	public long getLifeTime() {
		return 0;
	}

	@Override
	public void setBounds() {
		bounds.width = 0f;
		bounds.height = 0f;
	}

}
