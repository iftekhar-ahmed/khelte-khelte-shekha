package com.eatlappscontest.kks.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.eatlappscontest.kks.spec.AnimalType;
import com.eatlappscontest.kks.spec.MotionObject;
import com.eatlappscontest.kks.spec.StaticObject;
import com.eatlappscontest.kks.util.Animator;
import com.eatlappscontest.kks.util.GameAssetsManager;
import com.eatlappscontest.kks.view.KKS;

public class Animal extends BaseMotionObject {

	GameAssetsManager assetsManager;
	Animation animation;
	MotionPath motionPath;
	Sound animalSound, animalNameSound;
	Texture textureAnimalName;
	Vector2 animalNamePos;
	Rectangle animalNameBounds;
	AnimalType animalType;

	public enum MotionPath {
		Linear, Diagonal
	}

	float width, height;
	float stateTimePreAnimation = 0f, stateTimeAnimFrame = 0f,
			stateTimePreAnimalNameDraw = 0f;
	boolean drawing, drawingName, animate, pauseAnimation, animalNameDrawn, playingAnimalNameSound, playingAnimalSound;

	static final float ANIMATION_X = 0.21f;
	static final float ANIMATION_Y = 0.30f;
	static final float ANIMATION_PADDING = 0.5f;
	static final float ANIMATION_POS_X = 1f;
	static final float ANIMATION_POS_Y = KKS.VIEWPORT_HEIGHT
			- ANIMATION_PADDING;

	public static final float ACCELERATION = 1.2f;
	public static final float FRAME_DURATION = 0.2f;
	public static final float POS_X = 0f;
	public static final float POS_Y = 0.3f;
	public static final float PRE_ANIM_DELAY = 1f;
	public static final float POST_ANIM_DELAY = 1f;

	public Animal(AnimalType type, Vector2 position, Texture texture, float w,
			float h, MotionPath path, GameAssetsManager manager) {
		super(position, texture);

		width = w;
		height = h;
		setBounds();

		animalType = type;
		motionPath = path;
		assetsManager = manager;
		animate = false;

		animalSound = assetsManager.getAnimalSound(animalType);
		animalNameSound = assetsManager.getAnimalNameSound(animalType);

		textureAnimalName = assetsManager.getTextureAnimalName(animalType);
		animalNamePos = new Vector2(ANIMATION_POS_X, ANIMATION_POS_Y);
		animalNameBounds = new Rectangle(animalNamePos.x, animalNamePos.y, 0f,
				0f);
	}

	public Animal(AnimalType type, Vector2 position, String animationFile,
			float w, float h, MotionPath path, GameAssetsManager manager) {
		super(position, null);

		width = w;
		height = h;
		setBounds();

		animalType = type;
		motionPath = path;
		assetsManager = manager;

		animation = new Animator(assetsManager.getTextureAtlas(animationFile),
				assetsManager.getTextureRegionData(animationFile),
				FRAME_DURATION).getAnimation();
		animate = true;

		animalSound = assetsManager.getAnimalSound(animalType);
		animalNameSound = assetsManager.getAnimalNameSound(animalType);

		textureAnimalName = assetsManager.getTextureAnimalName(animalType);
		animalNamePos = new Vector2(ANIMATION_POS_X, ANIMATION_POS_Y);
		animalNameBounds = new Rectangle(animalNamePos.x, animalNamePos.y, 0f,
				0f);
	}

	private void updateStateTimeAnimFrame(float delta) {
		this.stateTimeAnimFrame += delta;
	}

	private void updateStateTimePreAnimalNameDraw(float delta) {
		this.stateTimePreAnimalNameDraw += delta;
	}

	private void updateStateTimePreAnim(float delta) {
		this.stateTimePreAnimation += delta;
	}

	@Override
	public void setBounds() {
		bounds.width = width;
		bounds.height = height;
	}

	public void playAnimalNameSound() {
		if (!playingAnimalNameSound) {
			animalNameSound.play(1f, 1f, 0f);
			playingAnimalNameSound = true;
		}
	}

	public void playAnimalSound(boolean loop) {
		if(animate && stateTimePreAnimation <= PRE_ANIM_DELAY)
			return;
		if(loop) {
			if(!playingAnimalSound) {
				animalSound.loop(1f, 1f, 0f);
				playingAnimalSound = true;
			}
		}
		else
			animalSound.play(1f, 1f, 0f);
	}

	public void stopSounds() {
		animalNameSound.stop();
		animalSound.stop();
	}

	/**
	 * Draws name of animal with its starting letter using zoom-in
	 * interpolation. Check {@link #isAnimalNameDrawn()} to see status.
	 */
	public void drawName(SpriteBatch batch, float ppuX, float ppuY, float delta) {
		if (!drawingName)
			return;

		if (stateTimePreAnimalNameDraw <= PRE_ANIM_DELAY) {
			updateStateTimePreAnimalNameDraw(delta);
			return;
		}

		if (animalNamePos.y <= ANIMATION_PADDING) {
			animalNameDrawn = true;
		} else {
			animalNameBounds.width += ANIMATION_X;
			animalNamePos.y -= ANIMATION_Y;
			animalNameBounds.height += ANIMATION_Y;
		}

		batch.draw(textureAnimalName, animalNamePos.x * ppuX, animalNamePos.y
				* ppuY, animalNameBounds.getWidth() * ppuX,
				animalNameBounds.getHeight() * ppuY);
	}

	@Override
	public void draw(SpriteBatch batch, float ppuX, float ppuY, float delta) {
		if (!drawing)
			return;

		if (animate) {
			if (stateTimePreAnimation <= PRE_ANIM_DELAY) {
				updateStateTimePreAnim(delta);
				return;
			}
			if (!pauseAnimation)
				updateStateTimeAnimFrame(delta);
			TextureRegion currentBirdFrame = animation.getKeyFrame(
					stateTimeAnimFrame, true);
			batch.draw(currentBirdFrame, position.x * ppuX, position.y * ppuY,
					bounds.getWidth() * ppuX, bounds.getHeight() * ppuY);
		} else if (textureMotionObject != null)
			batch.draw(textureMotionObject, position.x * ppuX, position.y
					* ppuY, bounds.getWidth() * ppuX, bounds.getHeight() * ppuY);
	}

	@Override
	public void updatePosition() {
		if (animate && stateTimePreAnimation <= PRE_ANIM_DELAY) {
			return;
		}
		float velocity = ACCELERATION * Gdx.graphics.getDeltaTime();
		switch (motionPath) {
		case Diagonal:
			position.x += (KKS.VIEWPORT_WIDTH / KKS.VIEWPORT_HEIGHT) * velocity;
			position.y = position.y + velocity;
			break;
		case Linear:
			position.x += (KKS.VIEWPORT_WIDTH / KKS.VIEWPORT_HEIGHT) * velocity;
			break;
		default:
			break;
		}
	}

	public void reset() {
		position.x = POS_X;
		position.y = POS_Y;
		playingAnimalNameSound = false;
		playingAnimalSound = false;
		drawing = false;
		drawingName = false;
		animalNameDrawn = false;
		pauseAnimation = false;
		stateTimePreAnimation = 0f;
		stateTimeAnimFrame = 0f;
		stateTimePreAnimalNameDraw = 0f;
		setAnimalNamePosition();
		setAnimalNameBounds();
	}

	public boolean isInCage(Cage cage) {
		switch (motionPath) {
		case Diagonal:
			return position.x >= cage.getPosition().x
					&& position.x - cage.getPosition().x >= Cage.WIDTH / 6f
					&& position.y >= cage.getPosition().y
					&& position.y - cage.getPosition().y <= Cage.WIDTH;

		case Linear:
			return position.x >= cage.getPosition().x
					&& position.x - cage.getPosition().x >= Cage.WIDTH / 6f;

		default:
			return false;
		}
	}

	public void pauseAnimation() {
		pauseAnimation = true;
	}

	public void unpauseAnimation() {
		pauseAnimation = false;
	}

	public void setDrawingName(boolean drawingName) {
		this.drawingName = drawingName;
	}

	public AnimalType getAnimalType() {
		return animalType;
	}

	public void setAnimalNameBounds() {
		animalNameBounds.width = 0f;
		animalNameBounds.height = 0f;
	}

	public void setAnimalNamePosition() {
		animalNamePos.x = ANIMATION_POS_X;
		animalNamePos.y = ANIMATION_POS_Y;
	}

	public MotionPath getMotionPath() {
		return motionPath;
	}

	public boolean isDrawing() {
		return drawing;
	}

	public void setDrawing(boolean drawing) {
		this.drawing = drawing;
	}

	public boolean isDrawingName() {
		return drawingName;
	}

	public boolean isAnimalNameDrawn() {
		return animalNameDrawn;
	}

	@Override
	public MotionObject getType() {
		return MotionObject.Animal;
	}

	@Override
	public StaticObject getStaticObjectOnCollision() {
		return null;
	}
}
