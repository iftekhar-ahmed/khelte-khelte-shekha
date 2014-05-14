package com.eatlappscontest.kks.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.eatlappscontest.kks.spec.MotionObject;
import com.eatlappscontest.kks.spec.StaticObject;
import com.eatlappscontest.kks.view.KKS;

public class Rocket extends BaseMotionObject {

	public static final float HEIGHT = 1.5f;
	public static final float WIDTH = 1f;
	public static final float ACCELERATION = 0.5f;

	public Rocket(Vector2 position, Texture texture) {
		super(position, texture);
	}

	@Override
	public void updatePosition() {
		float newX = position.x - Gdx.input.getAccelerometerX() * WIDTH
				* Gdx.graphics.getDeltaTime();

		if (newX >= 0f && newX <= KKS.VIEWPORT_WIDTH - bounds.getWidth())
			position.x = newX;
	}

	@Override
	public void draw(SpriteBatch batch, float ppuX, float ppuY, float delta) {

		super.draw(batch, ppuX, ppuY, delta);
	}

	@Override
	public void setBounds() {
		bounds.width = WIDTH;
		bounds.height = HEIGHT;
	}

	@Override
	public MotionObject getType() {
		return MotionObject.Rocket;
	}

	@Override
	public StaticObject getStaticObjectOnCollision() {
		return null;
	}
}
