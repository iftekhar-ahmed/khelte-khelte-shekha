package com.eatlappscontest.kks.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.eatlappscontest.kks.spec.StaticObject;

public interface IStaticObject {

	StaticObject getType();
	
	Rectangle getBounds();
	
	Vector2 getPosition();
	
	long getSpawnTime();
	
	Texture getTexture();
	
	long getLifeTime();
	
	boolean isBlur();
	
	void draw(SpriteBatch batch, float ppuX, float ppuY, float delta);
}
