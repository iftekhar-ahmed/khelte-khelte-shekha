package com.eatlappscontest.kks.util;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.eatlappscontest.kks.data.TextureRegionData;

public class Animator {

	TextureAtlas atlas;
	TextureRegion[] keyFrames;
	Animation animation;
	String regionPrefix;
	int regionCount;
	float frameDuration;
	boolean flipX, flipY;
	
	public Animator(TextureAtlas atlas, TextureRegionData data, float frameDuration) {
		
		this.atlas = atlas;
		this.flipX = data.flipX;
		this.flipY = data.flipY;
		this.regionPrefix = data.regionPrefix;
		this.frameDuration = frameDuration;
		regionCount = atlas.getRegions().size;
		keyFrames = new TextureRegion[regionCount];
	}

	public Animation getAnimation() {
		if(animation != null)
			return animation;
		for(int i = 0; i < regionCount; i++)
		{
			keyFrames[i] = atlas.findRegion(regionPrefix + "-0" + (i + 1));
			keyFrames[i].flip(flipX, flipY);
		}
		animation = new Animation(frameDuration, keyFrames);
		return animation;
	}
}
