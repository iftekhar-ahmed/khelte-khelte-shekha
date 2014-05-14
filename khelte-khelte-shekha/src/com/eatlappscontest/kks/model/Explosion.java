package com.eatlappscontest.kks.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.eatlappscontest.kks.spec.StaticObject;

public class Explosion extends BaseStaticObject{

	public static final float HEIGHT = 1f;
	public static final float WIDTH = 1f;
	public static final long LIFE_TIME = 500;
	
	public Explosion(Vector2 position, Texture texture) {
		super(position, texture);
	}

	@Override
	public void setBounds() {
		
		bounds.width = WIDTH;
		bounds.height = HEIGHT;
	}

	@Override
	public StaticObject getType() {
		
		return StaticObject.Explosion;
	}

	@Override
	public long getLifeTime() {
		
		return LIFE_TIME;
	}

}
