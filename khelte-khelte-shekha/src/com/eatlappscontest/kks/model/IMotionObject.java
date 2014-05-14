package com.eatlappscontest.kks.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.eatlappscontest.kks.spec.MotionObject;
import com.eatlappscontest.kks.spec.StaticObject;

public interface IMotionObject {
	
	MotionObject getType();

	Rectangle getBounds();

	Vector2 getPosition();

	void updatePosition();

	IMotionObject collides(Array<? extends IMotionObject> motionObjects);

	Texture getTexture();
	
	void draw(SpriteBatch batch, float ppuX, float ppuY, float delta);

	StaticObject getStaticObjectOnCollision();
}
