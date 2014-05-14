package com.eatlappscontest.kks.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.eatlappscontest.kks.spec.MotionObject;
import com.eatlappscontest.kks.spec.StaticObject;

public class FallingLetter extends BaseMotionObject{

	public static final float HEIGHT = 0.6f;
	public static final float WIDTH = 0.6f;
	public static final float RECT_HEIGHT_PADDING = 0.2f;
	public static final float RECT_WIDTH_PADDING = 0.4f;
	public static final float ACCELERATION = 0.5f;
	
	public FallingLetter(Vector2 position, Texture texture) {
		super(position, texture);
	}
	
	@Override
	public void updatePosition() {
		
		position.y = position.y - ACCELERATION * Gdx.graphics.getDeltaTime();
	}

	@Override
	public void setBounds() {
		
		bounds.width = WIDTH;
		bounds.height = HEIGHT;
	}

	@Override
	public MotionObject getType() {
		
		return MotionObject.FallingLetter;
	}

	@Override
	public StaticObject getStaticObjectOnCollision() {
		
		return StaticObject.Balloon;
	}

}
