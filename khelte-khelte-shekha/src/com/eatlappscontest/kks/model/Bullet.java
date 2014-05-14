package com.eatlappscontest.kks.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.eatlappscontest.kks.spec.MotionObject;
import com.eatlappscontest.kks.spec.StaticObject;

public class Bullet extends BaseMotionObject{

	public static final long SHOOT_INTERVAL = 500;
	public static final float WIDTH = 0.10f;
	public static final float HEIGHT = 0.25f;
	public static final float ACCELERATION = 5f;
	
	public Bullet(Vector2 position, Texture texture) {
		
		super(position, texture);
	}
	
	@Override
	public void updatePosition()
	{
		position.y = position.y + ACCELERATION * Gdx.graphics.getDeltaTime();
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
		
		return MotionObject.Bullet;
	}

	@Override
	public StaticObject getStaticObjectOnCollision() {
		
		return StaticObject.Explosion;
	}
}
