package com.eatlappscontest.kks.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.eatlappscontest.kks.spec.MotionObject;

public abstract class BaseMotionObject implements IMotionObject {

	protected Rectangle bounds;
	protected Vector2 position;
	protected Texture textureMotionObject;
	protected MotionObject type;

	public BaseMotionObject(Vector2 position, Texture texture) {

		this.position = position;
		this.textureMotionObject = texture;

		bounds = new Rectangle();
		setBounds();
	}
	
	abstract void setBounds();

	public void setTexture(Texture texture) {
		this.textureMotionObject = texture;
	}

	@Override
	public void draw(SpriteBatch batch, float ppuX, float ppuY, float delta) {
		batch.draw(textureMotionObject, position.x * ppuX, position.y * ppuY,
				bounds.getWidth() * ppuX, bounds.getHeight() * ppuY);
	}

	/**
	 * Detects simple collision with {@link Rectangle#overlaps(Rectangle)}
	 * method on rectangular bounds of motionObjects. Returns the first object
	 * that collides with the invoking object 
	 */
	@Override
	public IMotionObject collides(Array<? extends IMotionObject> motionObjects) {
		for (IMotionObject motionObject : motionObjects) {
			if (getBounds().overlaps(motionObject.getBounds())) {
				return motionObject;
			}
		}
		return null;
	}

	@Override
	public Rectangle getBounds() {
		return bounds;
	}

	@Override
	public Vector2 getPosition() {
		return position;
	}

	@Override
	public Texture getTexture() {
		return textureMotionObject;
	}
}
