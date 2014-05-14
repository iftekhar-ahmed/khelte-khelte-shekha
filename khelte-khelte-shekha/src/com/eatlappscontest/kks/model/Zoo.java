package com.eatlappscontest.kks.model;

import java.util.Iterator;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectMap.Entry;
import com.eatlappscontest.kks.data.AssetsEntry;
import com.eatlappscontest.kks.data.TextureRegionData;
import com.eatlappscontest.kks.spec.StaticObject;
import com.eatlappscontest.kks.util.Animator;
import com.eatlappscontest.kks.util.GameAssetsManager;
import com.eatlappscontest.kks.view.KKS;

public class Zoo extends BaseStaticObject {

	public static final float WIDTH = KKS.VIEWPORT_WIDTH * 2f;
	public static final float HEIGHT = KKS.VIEWPORT_HEIGHT * 2f;
	public static final float FRAME_DURATION_GRASS_LEFT = 0.2f;
	public static final float FRAME_DURATION_GRASS_RIGHT = 0.25f;
	public static final float GRASS_BUNCH_HEIGHT = 1f;
	public static final float GRASS_OVERLAP_PADDING = 1.5f;
	public static final int NUM_GRASS_BUNCH = (int) Math.abs((HEIGHT
			/ GRASS_BUNCH_HEIGHT) * GRASS_OVERLAP_PADDING);

	ObjectMap<Animal, Cage> animals;
	GameAssetsManager assetsManager;
	Animation grassLeftAnimation, grassRightAnimation;

	float stateTimeAnimFrame = 0f;

	public Zoo(Vector2 position, GameAssetsManager assetsManager) {
		super(position, null);
		this.assetsManager = assetsManager;
		grassLeftAnimation = new Animator(
				this.assetsManager
						.getTextureAtlas(AssetsEntry.texture_atlas.GRASS),
				this.assetsManager
						.getTextureRegionData(AssetsEntry.texture_atlas.GRASS),
				FRAME_DURATION_GRASS_LEFT).getAnimation();
		grassRightAnimation = new Animator(
				this.assetsManager
						.getTextureAtlas(AssetsEntry.texture_atlas.GRASS),
				new TextureRegionData(
						new boolean[] { true, false },
						AssetsEntry.texture_atlas
								.getRegionPrefix(AssetsEntry.texture_atlas.GRASS)),
				FRAME_DURATION_GRASS_RIGHT).getAnimation();
		animals = new ObjectMap<Animal, Cage>();
	}

	private void updateStateTimeAnimFrame(float delta) {
		this.stateTimeAnimFrame += delta;
	}
	
	public void addAnimal(Animal animal, Cage cage) {
		this.animals.put(animal, cage);
	}

	public ObjectMap<Animal, Cage> getAnimals() {
		return animals;
	}

	@Override
	public void draw(SpriteBatch batch, float ppuX, float ppuY, float delta) {
		updateStateTimeAnimFrame(delta);
		TextureRegion keyFrameLeft = grassLeftAnimation.getKeyFrame(
				stateTimeAnimFrame, true);
		TextureRegion keyFrameRight = grassRightAnimation.getKeyFrame(
				stateTimeAnimFrame, true);
		for (int i = 0; i <= NUM_GRASS_BUNCH; i++) {
			if (i % 2 == 0)
				batch.draw(keyFrameLeft, position.x * ppuX,
						(Zoo.HEIGHT - (i * GRASS_BUNCH_HEIGHT / GRASS_OVERLAP_PADDING)) * ppuY,
						bounds.getWidth() * ppuX, GRASS_BUNCH_HEIGHT * ppuY);
			else
				batch.draw(keyFrameRight, position.x * ppuX,
						(Zoo.HEIGHT - (i * GRASS_BUNCH_HEIGHT / GRASS_OVERLAP_PADDING)) * ppuY,
						bounds.getWidth() * ppuX, GRASS_BUNCH_HEIGHT * ppuY);
		}
		for(Iterator<Entry<Animal, Cage>> itr = animals.entries().iterator(); itr.hasNext();) {
			Entry<Animal, Cage> pair = itr.next();
			pair.key.draw(batch, ppuX, ppuY, delta);
			pair.value.draw(batch, ppuX, ppuY, delta);
		}
	}

	@Override
	public StaticObject getType() {
		return StaticObject.Zoo;
	}

	@Override
	public long getLifeTime() {
		return 0;
	}

	@Override
	void setBounds() {
		bounds.width = WIDTH;
		bounds.height = HEIGHT;
	}
}
