package com.eatlappscontest.kks.util;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.eatlappscontest.kks.data.AssetsEntry;
import com.eatlappscontest.kks.data.AssetsEntry.music;
import com.eatlappscontest.kks.data.AssetsEntry.sound;
import com.eatlappscontest.kks.data.AssetsEntry.texture;
import com.eatlappscontest.kks.data.AssetsEntry.texture_atlas;
import com.eatlappscontest.kks.data.TextureRegionData;
import com.eatlappscontest.kks.spec.AnimalType;

public class GameAssetsManager extends AssetManager {

	/** Puts assets in preload queue. Call before invoking {@link #update()}. */
	public void enque() {
		for (int i = 0; i < texture.EVILS.length; i++) {
			load(texture.EVILS[i], Texture.class);
		}
		for (int i = 0; i < texture.LETTERS.length; i++) {
			load(texture.LETTERS[i], Texture.class);
		}
		for (int i = 0; i < texture.BALLOONS.length; i++) {
			load(texture.BALLOONS[i], Texture.class);
		}
		load(texture.ROCKET, Texture.class);
		load(texture.BULLET, Texture.class);
		load(texture.EXPLOSION, Texture.class);
		load(texture.COIN, Texture.class);
		load(texture.COIN_LITE, Texture.class);
		load(texture.UNIVERSE1, Texture.class);
		load(texture.UNIVERSE2, Texture.class);
		load(texture.UNIVERSE3, Texture.class);
		load(texture.TRANSPARENT_BG, Texture.class);
		load(texture.CAGE_SMALL_BLANK, Texture.class);
		load(texture.CAGE_NORMAL_OPEN, Texture.class);
		load(texture.CAGE_NORMAL_CLOSED, Texture.class);
		load(texture.CAGE_CROW, Texture.class);
		load(texture.KO, Texture.class);
		load(texture.BO, Texture.class);
		load(texture.SHO, Texture.class);
		load(texture.KHO, Texture.class);
		load(texture.VO, Texture.class);
		load(texture.HO, Texture.class);
		load(texture.GO, Texture.class);
		load(texture.TO, Texture.class);
		load(texture.U, Texture.class);

		// load(texture_atlas.EVIL, TextureAtlas.class);
		load(texture_atlas.CROW, TextureAtlas.class);
		load(texture_atlas.TIGER, TextureAtlas.class);
		load(texture_atlas.ELEPHANT, TextureAtlas.class);
		load(texture_atlas.BEAR, TextureAtlas.class);
		load(texture_atlas.LION, TextureAtlas.class);
		load(texture_atlas.PARROT, TextureAtlas.class);
		load(texture_atlas.RABBIT, TextureAtlas.class);
		load(texture_atlas.OSTRICH, TextureAtlas.class);
		load(texture_atlas.GORILLA, TextureAtlas.class);
		load(texture_atlas.GRASS, TextureAtlas.class);
		load(texture_atlas.FLAG, TextureAtlas.class);
		load(texture_atlas.PATRIOT, TextureAtlas.class);

		load(music.BACKGROUND_1, Music.class);
		load(music.BACKGROUND_2, Music.class);
		load(music.BACKGROUND_3, Music.class);
		load(music.NATIONAL_ANTHEM, Music.class);

		load(sound.SHOOT, Sound.class);
		load(sound.Explosion, Sound.class);
		load(sound.CROW, Sound.class);
		load(sound.TIGER, Sound.class);
		load(sound.ELEPHANT, Sound.class);
		load(sound.BEAR, Sound.class);
		load(sound.LION, Sound.class);
		load(sound.PARROT, Sound.class);
		load(sound.GORILLA, Sound.class);
		load(sound.OSTRICH, Sound.class);
		load(sound.RABBIT, Sound.class);
		load(sound.NAME_CAT, Sound.class);
		load(sound.NAME_CROW, Sound.class);
		load(sound.NAME_LION, Sound.class);
		load(sound.NAME_TIGER, Sound.class);
		load(sound.NAME_PARROT, Sound.class);
		load(sound.NAME_BEAR, Sound.class);
		load(sound.NAME_GORILLA, Sound.class);
		load(sound.NAME_RABBIT, Sound.class);
		load(sound.NAME_OSTRICH, Sound.class);
		load(sound.NAME_ELEPHANT, Sound.class);
		load(sound.QUESTION, Sound.class);
		load(sound.MORAL1, Sound.class);
		load(sound.MORAL2, Sound.class);
		load(sound.MORAL3, Sound.class);
	}

	public Texture getTexture(String assetFileName) {
		return get(assetFileName, Texture.class);
	}

	public Music getMusic(String assetFileName) {
		return get(assetFileName, Music.class);
	}

	public Sound getSound(String assetFileName) {
		return get(assetFileName, Sound.class);
	}

	public TextureAtlas getTextureAtlas(String packFileName) {
		return get(packFileName, TextureAtlas.class);
	}

	public TextureRegionData getTextureRegionData(String packFileName) {
		return new TextureRegionData(
				AssetsEntry.texture_atlas.resolveFlip(packFileName),
				AssetsEntry.texture_atlas.getRegionPrefix(packFileName));
	}

	public Sound getAnimalSound(AnimalType animalType) {
		switch (animalType) {
		case Crow:
			return get(sound.CROW, Sound.class);
		case Tiger:
			return get(sound.TIGER, Sound.class);
		case Elephant:
			return get(sound.ELEPHANT, Sound.class);
		case Bear:
			return get(sound.BEAR, Sound.class);
		case Lion:
			return get(sound.LION, Sound.class);
		case Ostrich:
			return get(sound.OSTRICH, Sound.class);
		case Gorilla:
			return get(sound.GORILLA, Sound.class);
		case Parrot:
			return get(sound.PARROT, Sound.class);
		case Rabbit:
			return get(sound.RABBIT, Sound.class);
		default:
			assert false;
			return null;
		}
	}

	public Sound getAnimalNameSound(AnimalType animalType) {
		switch (animalType) {
		case Crow:
			return get(sound.NAME_CROW, Sound.class);
		case Tiger:
			return get(sound.NAME_TIGER, Sound.class);
		case Elephant:
			return get(sound.NAME_ELEPHANT, Sound.class);
		case Bear:
			return get(sound.NAME_BEAR, Sound.class);
		case Gorilla:
			return get(sound.NAME_GORILLA, Sound.class);
		case Lion:
			return get(sound.NAME_LION, Sound.class);
		case Ostrich:
			return get(sound.NAME_OSTRICH, Sound.class);
		case Parrot:
			return get(sound.NAME_PARROT, Sound.class);
		case Rabbit:
			return get(sound.NAME_RABBIT, Sound.class);
		default:
			assert false;
			return null;
		}
	}

	public Texture getTextureAnimalName(AnimalType animalType) {
		switch (animalType) {
		case Crow:
			return get(texture.KO, Texture.class);
		case Tiger:
			return get(texture.BO, Texture.class);
		case Elephant:
			return get(texture.HO, Texture.class);
		case Bear:
			return get(texture.VO, Texture.class);
		case Gorilla:
			return get(texture.GO, Texture.class);
		case Lion:
			return get(texture.SHO, Texture.class);
		case Ostrich:
			return get(texture.U, Texture.class);
		case Parrot:
			return get(texture.TO, Texture.class);
		case Rabbit:
			return get(texture.KHO, Texture.class);
		default:
			assert false;
			return null;
		}
	}

	public Texture getTextureCage(AnimalType animalType, boolean doorOpen) {
		switch (animalType) {
		case Tiger:
		case Elephant:
		case Bear:
		case Lion:
		case Ostrich:
		case Gorilla:
		case Rabbit:
			if(doorOpen)
				return get(texture.CAGE_NORMAL_OPEN, Texture.class);
			else
				return get(texture.CAGE_NORMAL_CLOSED, Texture.class);
		case Crow:
		case Parrot:
			return get(texture.CAGE_SMALL_BLANK, Texture.class);
		default:
			assert false;
			return null;
		}
	}
	
	public Texture getRandomLetter() {
		return get(texture.LETTERS[MathUtils.random(texture.LETTERS.length - 1)], Texture.class);
	}
}
