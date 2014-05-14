package com.eatlappscontest.kks.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.eatlappscontest.kks.spec.MotionObject;
import com.eatlappscontest.kks.spec.StaticObject;

public class Evil extends BaseMotionObject {

	public static final float HEIGHT = 0.8f;
	public static final float WIDTH = 0.8f;
	public static final float ACCELERATION = 0.5f;
	public static final long SPAWN_INTERVAL = 3000;
	
	public Evil(Vector2 position, Texture texture) {
		super(position, texture);
	}
	
	@Override
	public void updatePosition() {
		
		position.y = position.y - ACCELERATION * Gdx.graphics.getDeltaTime();
		bounds.setX(position.x);
		bounds.setY(position.y);
	}

	@Override
	public void setBounds() {
		
		bounds.width = WIDTH;
		bounds.height = HEIGHT;
	}

	@Override
	public MotionObject getType() {
		
		return MotionObject.Evil;
	}

	@Override
	public StaticObject getStaticObjectOnCollision() {
		
		return StaticObject.Coin;
	}

}
