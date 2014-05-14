package com.eatlappscontest.kks.controller;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.eatlappscontest.kks.data.AssetsEntry;
import com.eatlappscontest.kks.data.LetterPixelSequence;
import com.eatlappscontest.kks.model.Animal;
import com.eatlappscontest.kks.model.Animal.MotionPath;
import com.eatlappscontest.kks.model.Balloon;
import com.eatlappscontest.kks.model.Bullet;
import com.eatlappscontest.kks.model.Cage;
import com.eatlappscontest.kks.model.Coin;
import com.eatlappscontest.kks.model.DrawingBoard;
import com.eatlappscontest.kks.model.Evil;
import com.eatlappscontest.kks.model.Explosion;
import com.eatlappscontest.kks.model.IMotionObject;
import com.eatlappscontest.kks.model.IStaticObject;
import com.eatlappscontest.kks.model.Letter;
import com.eatlappscontest.kks.model.Rocket;
import com.eatlappscontest.kks.model.Universe;
import com.eatlappscontest.kks.spec.AnimalType;
import com.eatlappscontest.kks.spec.StaticObject;
import com.eatlappscontest.kks.util.GameAssetsManager;
import com.eatlappscontest.kks.view.GameScreen;
import com.eatlappscontest.kks.view.KKS;

public class GameScreenController extends BaseScreenController<GameScreen> {

	int scoreCount, letterDrawingError, nextLetter, nextUniverseBg, nextBackgroundMusic,
			letterDrawCount;
	long lastEvilSpawnTime, lastBulletShootTime;
	float accelerometerY, lastAccelerometerY;
	
	Music backgroundMusic;

	static final int MAX_LETTER_DRAW = 2;

	public GameScreenController(KKS game, GameScreen screen) {
		super(game, screen);
	}

	@Override
	public void init(GameAssetsManager assetsManager) {
		screen.shootSound = assetsManager.getSound(AssetsEntry.sound.SHOOT);
		screen.explosionSound = assetsManager
				.getSound(AssetsEntry.sound.Explosion);
		
		screen.backgroundMusics = new Array<Music>();
		loadBackgroundMusics();
		setBackgroundMusic();

		screen.universeBackgrounds = new Array<Texture>();
		screen.letters = new Array<Letter>();
		screen.animals = new ObjectMap<AnimalType, Animal>();

		screen.drawingBoard = new DrawingBoard(
				assetsManager.getTexture(AssetsEntry.texture.TRANSPARENT_BG));

		screen.rocket = new Rocket(new Vector2(KKS.VIEWPORT_WIDTH / 2
				- Rocket.WIDTH / 2, 0),
				assetsManager.getTexture(AssetsEntry.texture.ROCKET));

		screen.evils = new Array<Evil>();
		screen.bullets = new Array<Bullet>();
		screen.staticObjects = new Array<IStaticObject>();

		loadUniverseBg();
		setUniverseBg();
		loadLetters();
		loadAnimals();
		screen.letter = screen.letters.get(nextLetter);
		screen.animal = screen.animals.get(screen.letter.getAnimalType());
		screen.cage = new Cage(new Vector2(), assetsManager.getTextureCage(
				screen.animal.getAnimalType(), true));
		setCagePosition(screen.animal.getMotionPath());
		spawnEvil();
	}

	private void loadBackgroundMusics() {
		screen.backgroundMusics.add(game.assetsManager.getMusic(AssetsEntry.music.BACKGROUND_1));
		screen.backgroundMusics.add(game.assetsManager.getMusic(AssetsEntry.music.BACKGROUND_2));
		screen.backgroundMusics.add(game.assetsManager.getMusic(AssetsEntry.music.BACKGROUND_3));
	}

	private void setUniverseBg() {
		screen.staticUniverse = screen.universeBackgrounds.get(nextUniverseBg);
		screen.universeLayer1 = new Universe(new Vector2(0f, 0f),
				screen.universeBackgrounds.get(nextUniverseBg));
		screen.universeLayer2 = new Universe(new Vector2(0f,
				Universe.FRAME_HEIGHT),
				screen.universeBackgrounds.get(nextUniverseBg));
	}
	
	private void setBackgroundMusic() {
		if(backgroundMusic != null && backgroundMusic.isPlaying())
			backgroundMusic.stop();
		backgroundMusic = screen.backgroundMusics.get(nextBackgroundMusic);
		backgroundMusic.setLooping(true);
		backgroundMusic.play();
	}

	@Override
	public void update() {
		updateUniverse();
		if (!game.paused)
			updateBullets();
		if (!game.paused)
			updateEvils();
		if (!game.paused)
			screen.rocket.updatePosition();
		updateStaticObjects();

		if (TimeUtils.millis() - lastEvilSpawnTime > Evil.SPAWN_INTERVAL
				&& !game.paused)
			spawnEvil();

		accelerometerY = Gdx.input.getAccelerometerY();

		if (Math.abs(accelerometerY - lastAccelerometerY) >= 5.0f
				&& TimeUtils.millis() - lastBulletShootTime > Bullet.SHOOT_INTERVAL
				&& !game.paused) {
			shoot(screen.rocket.getPosition().x, screen.rocket.getBounds());
			lastAccelerometerY = accelerometerY;
		}

		if (scoreCount == 3) {
			if (!game.paused) {
				onScreenPause();
				setInputController(this);
			}

			if (screen.drawingBoard.isDrawing())
				screen.drawingBoard.zoomOut();
			else
				screen.drawingBoard.setDrawing(true);

			if (screen.drawingBoard.isAnimationDone())
				if (!screen.letter.isDrawing() && !screen.animal.isDrawing()
						&& !screen.animal.isDrawingName())
					screen.letter.setDrawing(true);

			if (screen.animal.isAnimalNameDrawn()) {
				if (!screen.animal.isDrawing()) {
					screen.animal.setDrawing(true);
					screen.cage.setDrawing(true);
				}
				if (screen.animal.isInCage(screen.cage)) {
					screen.cage.setTexture(game.assetsManager.getTextureCage(
							screen.animal.getAnimalType(), false));
					screen.animal.stopSounds();
					screen.animal.pauseAnimation();
					if (delay(1000)) {
						screen.cage.setDrawing(false);
						screen.animal.setDrawing(false);
						screen.drawingBoard.setDrawing(false);
						setInputController(null);
						onScreenResume();
					}
				} else {
					screen.animal.playAnimalSound(true);
					screen.animal.updatePosition();
				}
			}
		}
	}

	private void updateUniverse() {
		screen.universeLayer1.updatePosition();
		screen.universeLayer2.updatePosition();
	}

	private void updateStaticObjects() {
		Iterator<IStaticObject> objectIterator = screen.staticObjects
				.iterator();
		while (objectIterator.hasNext()) {

			IStaticObject staticObject = objectIterator.next();

			if (TimeUtils.millis() - staticObject.getSpawnTime() > staticObject
					.getLifeTime()) {
				objectIterator.remove();
				continue;
			}
		}
	}

	private void updateEvils() {
		Iterator<Evil> evilIterator = screen.evils.iterator();
		IMotionObject evil;
		Vector2 position;

		while (evilIterator.hasNext()) {

			evil = evilIterator.next();
			position = evil.getPosition();

			if (position.y <= screen.rocket.getBounds().height / 2f) {
				evilIterator.remove();
				continue;
			}

			evil.updatePosition();
		}
	}

	private void shoot(float rocketPosX, Rectangle rocketBounds) {
		screen.bullets.add(new Bullet(new Vector2(rocketPosX
				+ rocketBounds.width / 2 - Bullet.WIDTH / 2,
				rocketBounds.height), game.assetsManager
				.getTexture(AssetsEntry.texture.BULLET)));
		lastBulletShootTime = TimeUtils.millis();
		screen.shootSound.play();
	}

	private void spawnStaticObject(float x, float y, StaticObject type) {
		switch (type) {
		case Balloon:
			int randomBallon = MathUtils.random(0,
					AssetsEntry.texture.BALLOONS.length - 1);
			screen.staticObjects
					.add(new Balloon(
							new Vector2(x, y),
							game.assetsManager
									.getTexture(AssetsEntry.texture.BALLOONS[randomBallon])));
			break;
		case Coin:
			screen.staticObjects.add(new Coin(new Vector2(x, y),
					game.assetsManager.getTexture(AssetsEntry.texture.COIN)));
			break;
		case Explosion:
			screen.staticObjects.add(new Explosion(new Vector2(x, y),
					game.assetsManager
							.getTexture(AssetsEntry.texture.EXPLOSION)));
			break;

		default:
			break;
		}
	}

	private void spawnEvil() {
		int randomEvil = MathUtils.random(0,
				AssetsEntry.texture.EVILS.length - 1);
		screen.evils.add(new Evil(new Vector2(MathUtils.random(0,
				KKS.VIEWPORT_WIDTH - Evil.WIDTH), KKS.VIEWPORT_HEIGHT
				+ Evil.HEIGHT), game.assetsManager
				.getTexture(AssetsEntry.texture.EVILS[randomEvil])));

		lastEvilSpawnTime = TimeUtils.millis();
	}

	private void updateBullets() {
		Iterator<Bullet> bulletIterator = screen.bullets.iterator();
		Bullet bullet;
		Vector2 position;

		while (bulletIterator.hasNext()) {
			bullet = (Bullet) bulletIterator.next();
			position = bullet.getPosition();

			if (position.y >= KKS.VIEWPORT_HEIGHT) {
				bulletIterator.remove();
				continue;
			}

			Evil evil = (Evil) bullet.collides(screen.evils);

			if (evil != null) {
				scoreCount++;
				spawnStaticObject(evil.getPosition().x, evil.getPosition().y,
						bullet.getStaticObjectOnCollision());
				screen.explosionSound.play();
				screen.evils.removeValue(evil, false);
				bulletIterator.remove();
				continue;
			}

			bullet.updatePosition();
		}
	}

	private void loadAnimals() {
		Animal crow = new Animal(AnimalType.Crow, new Vector2(Animal.POS_X,
				Animal.POS_Y), AssetsEntry.texture_atlas.CROW, 1f, 0.8f,
				MotionPath.Diagonal, game.assetsManager);
		Animal tiger = new Animal(AnimalType.Tiger, new Vector2(Animal.POS_X,
				Animal.POS_Y), AssetsEntry.texture_atlas.TIGER, 1.5f, 0.8f,
				MotionPath.Linear, game.assetsManager);
		Animal elephant = new Animal(AnimalType.Elephant, new Vector2(
				Animal.POS_X, Animal.POS_Y),
				AssetsEntry.texture_atlas.ELEPHANT, 1.2f, 1f,
				MotionPath.Linear, game.assetsManager);
		Animal lion = new Animal(AnimalType.Lion, new Vector2(Animal.POS_X,
				Animal.POS_Y), AssetsEntry.texture_atlas.LION, 1.5f, 1f,
				MotionPath.Linear, game.assetsManager);
		Animal parrot = new Animal(AnimalType.Parrot, new Vector2(Animal.POS_X,
				Animal.POS_Y), AssetsEntry.texture_atlas.PARROT, 1.2f, 1f,
				MotionPath.Diagonal, game.assetsManager);
		Animal ostrich = new Animal(AnimalType.Ostrich, new Vector2(
				Animal.POS_X, Animal.POS_Y), AssetsEntry.texture_atlas.OSTRICH,
				1.2f, 1f, MotionPath.Linear, game.assetsManager);
		Animal bear = new Animal(AnimalType.Bear, new Vector2(Animal.POS_X,
				Animal.POS_Y), AssetsEntry.texture_atlas.BEAR, 1.5f, 1f,
				MotionPath.Linear, game.assetsManager);

		screen.animals.put(AnimalType.Crow, crow);
		screen.animals.put(AnimalType.Tiger, tiger);
		screen.animals.put(AnimalType.Elephant, elephant);
		screen.animals.put(AnimalType.Lion, lion);
		screen.animals.put(AnimalType.Parrot, parrot);
		screen.animals.put(AnimalType.Ostrich, ostrich);
		screen.animals.put(AnimalType.Bear, bear);
	}

	private void loadLetters() {
		screen.letters.add(new Letter(LetterPixelSequence.KO, 1,
				AnimalType.Crow, game.assetsManager).init());
		screen.letters.add(new Letter(LetterPixelSequence.BO, 1,
				AnimalType.Tiger, game.assetsManager).init());
		screen.letters.add(new Letter(LetterPixelSequence.HO, 1,
				AnimalType.Elephant, game.assetsManager).init());
		screen.letters.add(new Letter(LetterPixelSequence.SHO, 1,
				AnimalType.Lion, game.assetsManager).init());
		screen.letters.add(new Letter(LetterPixelSequence.TO, 1,
				AnimalType.Parrot, game.assetsManager).init());
		screen.letters.add(new Letter(LetterPixelSequence.UU, 1,
				AnimalType.Ostrich, game.assetsManager).init());
		screen.letters.add(new Letter(LetterPixelSequence.VO, 1,
				AnimalType.Bear, game.assetsManager).init());
	}

	private void loadUniverseBg() {
		screen.universeBackgrounds.add(game.assetsManager
				.getTexture(AssetsEntry.texture.UNIVERSE1));
		screen.universeBackgrounds.add(game.assetsManager
				.getTexture(AssetsEntry.texture.UNIVERSE2));
		screen.universeBackgrounds.add(game.assetsManager
				.getTexture(AssetsEntry.texture.UNIVERSE3));
	}

	private void setCagePosition(MotionPath motionPath) {
		switch (motionPath) {
		case Diagonal:
			screen.cage.getPosition().set(Cage.POS_X_DIAGONAL,
					Cage.POS_Y_DIAGONAL);
			break;
		case Linear:
			screen.cage.getPosition().set(Cage.POS_X_LINEAR, Cage.POS_Y_LINEAR);

		default:
			break;
		}
	}

	private void resetScoreCounter() {
		this.scoreCount = 0;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (screen.letter.isDrawingSeqMaintained()) {
			if (screen.letter.getCountFadedPixels() == screen.letter
					.getCountPixels()) {
				letterDrawCount++;
				screen.letter.setDrawing(false);
				letterDrawingError = 0;
				screen.animal.setDrawingName(true);
				screen.animal.playAnimalNameSound();
			}
		} else {
			letterDrawingError++;
			if (letterDrawingError == Letter.MAX_DRAWING_ERROR) {
				screen.letter.setDrawing(false);
				letterDrawingError = 0;
				onScreenResume();
			} else {
				screen.letter.reset();
			}
		}
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		screen.letter.fadePixel(screenX / screen.ppuX,
				((screen.height - screenY) / screen.ppuY));
		return true;
	}

	@Override
	public void onScreenPause() {
		game.paused = true;
		if (backgroundMusic.isPlaying())
			backgroundMusic.pause();
	}

	@Override
	public void onScreenResume() {
		game.paused = false;
		backgroundMusic.play();

		resetScoreCounter();
		if (letterDrawCount == MAX_LETTER_DRAW) {
			letterDrawCount = 0;
			nextUniverseBg++;
			if (nextUniverseBg == screen.universeBackgrounds.size)
				nextUniverseBg = 0;
			setUniverseBg();
			nextBackgroundMusic++;
			if (nextBackgroundMusic == screen.backgroundMusics.size)
				nextBackgroundMusic = 0;
			setBackgroundMusic();
			game.onDispose(screen);
		}
		
		screen.drawingBoard.reset();
		nextLetter++;
		if (nextLetter == screen.letters.size)
			nextLetter = 0;
		screen.letter = screen.letters.get(nextLetter);
		screen.letter.reset();
		screen.animal = screen.animals.get(screen.letter.getAnimalType());
		screen.animal.reset();
		setCagePosition(screen.animal.getMotionPath());
		screen.cage.setTexture(game.assetsManager.getTextureCage(
				screen.animal.getAnimalType(), true));
	}
}
