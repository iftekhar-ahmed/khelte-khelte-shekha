package com.eatlappscontest.kks.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.eatlappscontest.kks.spec.StaticObject;

public class Coin extends BaseStaticObject{

	public static final float DIAMETER = 0.5f;
	public static final long LIFE_TIME = 500;

	public Coin(Vector2 position, Texture texture) {
		super(position, texture);
	}

	@Override
	public void setBounds() {
		
		bounds.width = DIAMETER;
		bounds.height = DIAMETER;
	}

	@Override
	public StaticObject getType() {
		
		return StaticObject.Coin;
	}

	@Override
	public long getLifeTime() {
		
		return LIFE_TIME;
	}
}
