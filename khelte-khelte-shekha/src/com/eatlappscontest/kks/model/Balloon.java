package com.eatlappscontest.kks.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.eatlappscontest.kks.spec.StaticObject;

public class Balloon extends BaseStaticObject {

	public static final long LIFE_TIME = 2000;
	public static final float HEIGHT = 1f;
	public static final float WIDTH = 1f;
	public static final float ACCELERATION = 0.5f;
	
	public Balloon(Vector2 position, Texture texture) {
		super(position, texture);
	}

	@Override
	public void setBounds() {
		
		bounds.width = WIDTH;
		bounds.height = HEIGHT;
	}

	@Override
	public StaticObject getType() {
		
		return StaticObject.Balloon;
	}

	@Override
	public long getLifeTime() {
		
		return LIFE_TIME;
	}

}
