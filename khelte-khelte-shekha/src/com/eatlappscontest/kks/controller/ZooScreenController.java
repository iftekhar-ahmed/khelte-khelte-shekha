package com.eatlappscontest.kks.controller;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectMap.Entry;
import com.eatlappscontest.kks.data.AssetsEntry;
import com.eatlappscontest.kks.model.Animal;
import com.eatlappscontest.kks.model.Animal.MotionPath;
import com.eatlappscontest.kks.model.Cage;
import com.eatlappscontest.kks.model.DrawingBoard;
import com.eatlappscontest.kks.model.Zoo;
import com.eatlappscontest.kks.spec.AnimalType;
import com.eatlappscontest.kks.util.GameAssetsManager;
import com.eatlappscontest.kks.view.KKS;
import com.eatlappscontest.kks.view.ZooScreen;

public class ZooScreenController extends BaseScreenController<ZooScreen>
		implements GestureListener {

	static final float POST_SCREEN_DELAY = 8f;
	
	boolean isAnimalTouch, isLetterTouch, isSoundPlaying;
	float zooWidthInPixel, zooHeightInPixel, stateTimePostScreen;

	Array<Animal> allAnimals;
	Animal touchedAnimal;
	Animal freeAnimal;
	Texture touchedLetter;
	ObjectMap<Animal, Texture> animalLetterMap;

	public ZooScreenController(KKS game, ZooScreen screen) {
		super(game, screen);
		setInputController(new GestureDetector(this));
	}

	private void addAnimals(GameAssetsManager assetsManager) {
		Animal crow = new Animal(AnimalType.Crow, new Vector2(1f, 6f),
				AssetsEntry.texture_atlas.CROW, 2f, 1.5f, MotionPath.Diagonal,
				assetsManager);
		crow.setDrawing(true);
		crow.pauseAnimation();
		Cage cageCrow = new Cage(new Vector2(0.5f, 5.5f),
				assetsManager.getTextureCage(AnimalType.Crow, false));
		cageCrow.getBounds().width = 3f;
		cageCrow.getBounds().height = 3f;
		cageCrow.setDrawing(true);
		Animal tiger = new Animal(AnimalType.Tiger, new Vector2(0.5f, 3f),
				AssetsEntry.texture_atlas.TIGER, 3f, 1.5f, MotionPath.Linear,
				assetsManager);
		tiger.setDrawing(true);
		tiger.pauseAnimation();
		Cage cageTiger = new Cage(new Vector2(0.5f, 3f),
				assetsManager.getTextureCage(AnimalType.Tiger, false));
		cageTiger.getBounds().width = 3.5f;
		cageTiger.getBounds().height = 2f;
		cageTiger.setDrawing(true);
		Animal elephant = new Animal(AnimalType.Elephant,
				new Vector2(3.5f, 6f), AssetsEntry.texture_atlas.ELEPHANT, 3f,
				3f, MotionPath.Linear, assetsManager);
		elephant.setDrawing(true);
		elephant.pauseAnimation();
		Cage cageElephant = new Cage(new Vector2(3.5f, 6f),
				assetsManager.getTextureCage(AnimalType.Elephant, false));
		cageElephant.getBounds().width = 3.5f;
		cageElephant.getBounds().height = 3.5f;
		cageElephant.setDrawing(true);
		Animal lion = new Animal(AnimalType.Lion, new Vector2(0.25f, 0.5f),
				AssetsEntry.texture_atlas.LION, 3f, 1.5f, MotionPath.Linear,
				assetsManager);
		lion.setDrawing(true);
		lion.pauseAnimation();
		Cage cageLion = new Cage(new Vector2(0.25f, 0.5f),
				assetsManager.getTextureCage(AnimalType.Lion, false));
		cageLion.getBounds().width = 3.5f;
		cageLion.getBounds().height = 2f;
		cageLion.setDrawing(true);
		Animal parrot = new Animal(AnimalType.Parrot, new Vector2(4f, 3.5f),
				AssetsEntry.texture_atlas.PARROT, 3f, 1.5f,
				MotionPath.Diagonal, assetsManager);
		parrot.setDrawing(true);
		parrot.pauseAnimation();
		Cage cageParrot = new Cage(new Vector2(4f, 3f),
				assetsManager.getTextureCage(AnimalType.Parrot, false));
		cageParrot.getBounds().width = 3f;
		cageParrot.getBounds().height = 3f;
		cageParrot.setDrawing(true);
		Animal bear = new Animal(AnimalType.Bear, new Vector2(4f, 0.5f),
				AssetsEntry.texture_atlas.BEAR, 3f, 1.5f, MotionPath.Linear,
				assetsManager);
		bear.setDrawing(true);
		bear.pauseAnimation();
		Cage cageBear = new Cage(new Vector2(4f, 0.5f),
				assetsManager.getTextureCage(AnimalType.Bear, false));
		cageBear.getBounds().width = 3f;
		cageBear.getBounds().height = 2f;
		cageBear.setDrawing(true);
		screen.zoo.addAnimal(crow, cageCrow);
		screen.zoo.addAnimal(tiger, cageTiger);
		screen.zoo.addAnimal(elephant, cageElephant);
		screen.zoo.addAnimal(lion, cageLion);
		screen.zoo.addAnimal(parrot, cageParrot);
		screen.zoo.addAnimal(bear, cageBear);

		animalLetterMap = new ObjectMap<Animal, Texture>();
		animalLetterMap.put(crow,
				assetsManager.getTexture(AssetsEntry.texture.LETTERS[2]));
		animalLetterMap.put(tiger,
				assetsManager.getTexture(AssetsEntry.texture.LETTERS[0]));
		animalLetterMap.put(elephant,
				assetsManager.getTexture(AssetsEntry.texture.LETTERS[1]));
		animalLetterMap.put(lion,
				assetsManager.getTexture(AssetsEntry.texture.LETTERS[3]));
		animalLetterMap.put(parrot,
				assetsManager.getTexture(AssetsEntry.texture.LETTERS[4]));
		animalLetterMap.put(bear,
				assetsManager.getTexture(AssetsEntry.texture.LETTERS[6]));
	}
	
	private void updateStateTimePostScreen(float delta) {
		stateTimePostScreen += delta;
	}

	@Override
	void init(GameAssetsManager assetsManager) {
		screen.zoo = new Zoo(new Vector2(0f, 0f), assetsManager);
		addAnimals(assetsManager);
		allAnimals = screen.zoo.getAnimals().keys().toArray();
		screen.drawingBoard = new DrawingBoard(
				assetsManager.getTexture(AssetsEntry.texture.TRANSPARENT_BG));
		isAnimalTouch = true;
		screen.question = assetsManager.getSound(AssetsEntry.sound.QUESTION);
		screen.morals = new Array<Sound>();
		screen.morals.add(assetsManager.getSound(AssetsEntry.sound.MORAL1));
		screen.morals.add(assetsManager.getSound(AssetsEntry.sound.MORAL2));
		screen.morals.add(assetsManager.getSound(AssetsEntry.sound.MORAL3));
	}

	@Override
	void update() {
		if (touchedAnimal != null) {
			if (isAnimalTouch)
				isAnimalTouch = false;
			if (!isLetterTouch)
				isLetterTouch = true;

			if (!screen.drawingBoard.isDrawing()) {
				screen.drawingBoard.setDrawing(true);
				screen.question.play();
			} else
				screen.drawingBoard.zoomOut();

			if (screen.drawingBoard.isAnimationDone()
					&& (screen.correctLetter == null || screen.randomLetter == null)) {
				screen.drawLetters = true;
				screen.correctLetter = animalLetterMap.get(touchedAnimal);
				screen.randomLetter = game.assetsManager.getRandomLetter();
				while (screen.randomLetter == screen.correctLetter)
					screen.randomLetter = game.assetsManager.getRandomLetter();
			}
		}
		 
		if (touchedLetter != null) {
			if (touchedLetter.equals(screen.correctLetter)) {
				if (screen.drawingBoard.isDrawing()) {
					screen.drawingBoard.setDrawing(false);
					screen.drawingBoard.reset();
				}
				
				if(screen.drawLetters)
					screen.drawLetters = false;
				
				if (isLetterTouch)
					isLetterTouch = false;
				if (!isAnimalTouch)
					isAnimalTouch = true;

				if(touchedAnimal != null) {
					freeAnimal = touchedAnimal;
					touchedAnimal = null;
				}
				
				for (Animal animal : allAnimals) {
					if (animal.equals(freeAnimal)) {
						screen.zoo
								.getAnimals()
								.get(animal)
								.setTexture(game.assetsManager.getTextureCage(animal.getAnimalType(), true));
						animal.unpauseAnimation();
					} else {
						if (animal.isDrawing()) {
							animal.setDrawing(false);
							screen.zoo.getAnimals().get(animal).setDrawing(false);
						}
					}
				}

				if(!isSoundPlaying) {
					screen.morals.get(MathUtils.random(screen.morals.size - 1)).play();
					isSoundPlaying = true;
				}
				
				updateStateTimePostScreen(Gdx.graphics.getDeltaTime() * 1000);
				if(stateTimePostScreen >= POST_SCREEN_DELAY) {
					if(freeAnimal.getPosition().x >= KKS.VIEWPORT_WIDTH)
						game.onDispose(screen);
					else
						freeAnimal.updatePosition();
				}
					
			} else {
				game.onDispose(screen);
			}
		}
		
		if(touchedAnimal == null && touchedLetter == null) {
			if (delay(2000)) {
				Animal randomAnimal = allAnimals.random();
				randomAnimal.playAnimalSound(false);
			}
		}
	}

	@Override
	public void onScreenPause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onScreenResume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onScreenResize(int width, int height) {
		zooWidthInPixel = Zoo.WIDTH * screen.ppuX;
		zooHeightInPixel = Zoo.HEIGHT * screen.ppuY;
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		if (isAnimalTouch)
			checkTouchForAnimal(x, y);
		else if (isLetterTouch)
			checkTouchForLetter(x, y);
		return true;
	}

	private void checkTouchForLetter(float x, float y) {
		float touchX = x / screen.ppuX;
		float touchY = (Gdx.graphics.getHeight() - y) / screen.ppuY;
		if (touchX - ZooScreen.LETTER_POS_X >= 0
				&& touchX - ZooScreen.LETTER_POS_X <= ZooScreen.LETTER_DIAMETER
				&& touchY - ZooScreen.LETTER_POS_Y_CORRECT >= 0
				&& touchY - ZooScreen.LETTER_POS_Y_CORRECT <= ZooScreen.LETTER_DIAMETER) {
			touchedLetter = screen.correctLetter;
		} else if (touchX - ZooScreen.LETTER_POS_X >= 0
				&& touchX - ZooScreen.LETTER_POS_X <= ZooScreen.LETTER_DIAMETER
				&& touchY - ZooScreen.LETTER_POS_Y_RANDOM >= 0
				&& touchY - ZooScreen.LETTER_POS_Y_RANDOM <= ZooScreen.LETTER_DIAMETER) {
			touchedLetter = screen.randomLetter;
		} else
			touchedLetter = null;
	}

	private void checkTouchForAnimal(float x, float y) {
		Iterator<Entry<Animal, Cage>> itr = screen.zoo.getAnimals().entries()
				.iterator();
		float touchX = x / screen.ppuX;
		float touchY = (Gdx.graphics.getHeight() - y) / screen.ppuY;
		touchedAnimal = null;
		while (itr.hasNext()) {
			Entry<Animal, Cage> entry = itr.next();
			Animal animal = entry.key;
			Cage cage = entry.value;
			if (touchX - cage.getPosition().x >= 0
					&& touchX - cage.getPosition().x <= cage.getBounds().width
					&& touchY - cage.getPosition().y >= 0
					&& touchY - cage.getPosition().y <= cage.getBounds().height) {
				touchedAnimal = animal;
				break;
			}
		}
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		float devX = screen.camera.position.x - deltaX;
		float devY = screen.camera.position.y + deltaY;
		if (devX >= screen.camera.viewportWidth / 2
				&& devX <= zooWidthInPixel / 1.35f
				&& devY >= screen.camera.viewportHeight / 2
				&& devY <= zooHeightInPixel / 1.25f) {
			screen.camera.translate(-deltaX, deltaY);
			screen.camera.update();
		}
		return true;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {
		return false;
	}
}
