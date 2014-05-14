package com.eatlappscontest.kks.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.eatlappscontest.kks.spec.StaticObject;

public abstract class BaseStaticObject implements IStaticObject {

	protected Rectangle bounds;
	protected Vector2 position;
	protected long spawnTime;
	protected Texture textureStaticObject;
	protected StaticObject type;
	private boolean isBlur = false;

	public BaseStaticObject(Vector2 position, Texture texture) {

		this.position = position;
		this.textureStaticObject = texture;

		bounds = new Rectangle();
		spawnTime = TimeUtils.millis();
		setBounds();
	}
	
	abstract void setBounds();

	public void setTexture(Texture texture) {
		textureStaticObject = texture;
	}

	public void setBlur(boolean blur) {
		this.isBlur = blur;
	}

	@Override
	public Vector2 getPosition() {
		return position;
	}
	
	@Override
	public void draw(SpriteBatch batch, float ppuX, float ppuY, float delta) {
		batch.draw(textureStaticObject, position.x * ppuX, position.y * ppuY,
				bounds.getWidth() * ppuX, bounds.getHeight() * ppuY);
	}

	@Override
	public Rectangle getBounds() {
		return bounds;
	}

	@Override
	public long getSpawnTime() {
		return spawnTime;
	}

	@Override
	public Texture getTexture() {
		return textureStaticObject;
	}

	@Override
	public boolean isBlur() {
		return isBlur;
	}
}
