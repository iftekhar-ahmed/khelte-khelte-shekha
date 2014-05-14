package com.eatlappscontest.kks.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.eatlappscontest.kks.spec.MotionObject;
import com.eatlappscontest.kks.spec.StaticObject;
import com.eatlappscontest.kks.view.KKS;

public class Universe extends BaseMotionObject {

	public static final float FRAME_DURATION = 0.5f;
	public static final float FRAME_WIDTH = KKS.VIEWPORT_WIDTH;
	public static final float FRAME_HEIGHT = KKS.VIEWPORT_HEIGHT;
	public static final float FRAME_ACCELERATION = 1.0f;

	public Universe(Vector2 position, Texture texture) {
		super(position, texture);
	}

	@Override
	public void updatePosition() {
		position.y = position.y - FRAME_ACCELERATION
				* Gdx.graphics.getDeltaTime();
		if (position.y <= -FRAME_HEIGHT)
			position.y = FRAME_HEIGHT;
	}

	@Override
	public void setBounds() {
		bounds.width = FRAME_WIDTH;
		bounds.height = FRAME_HEIGHT;
	}

	@Override
	public MotionObject getType() {
		return MotionObject.Universe;
	}

	@Override
	public StaticObject getStaticObjectOnCollision() {
		return null;
	}
}
