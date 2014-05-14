package com.eatlappscontest.kks.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.eatlappscontest.kks.data.AssetsEntry;
import com.eatlappscontest.kks.spec.AnimalType;
import com.eatlappscontest.kks.util.GameAssetsManager;
import com.eatlappscontest.kks.view.KKS;

public class Letter {

	AnimalType animalType;
	Array<Coin> coins;
	GameAssetsManager assetsManager;

	boolean drawing = false, drawingSequenceMaintained = true;
	int numFadedPixels, numPixelsToDraw;
	int[][] pixelPositions;
	float drawingDelta = 0f;
	float drawingStartTime = 0f;
	float paddingX;

	public static final int MAX_DRAWING_ERROR = 3;
	public static final float PADDING_BOTTOM_X = KKS.VIEWPORT_WIDTH - 5.0f;
	public static final float PADDING_BOTTOM_Y = KKS.VIEWPORT_HEIGHT - 6.0f;
	public static final float DRAWING_INTERVAL_SECONDS = 0.2f;
	public static final float DRAWING_PAUSE = 1f;

	public Letter(int[][] pixelPositions, float paddingX,
			AnimalType animalType, GameAssetsManager manager) {

		this.pixelPositions = pixelPositions;
		this.paddingX = paddingX;
		this.animalType = animalType;
		this.assetsManager = manager;
		coins = new Array<Coin>();
	}

	public void draw(SpriteBatch batch, float ppuX, float ppuY,
			float renderingDelta) {

		if (!drawing)
			return;

		if ((drawingStartTime += renderingDelta) <= DRAWING_PAUSE)
			return;

		drawingDelta += renderingDelta;

		if (drawingDelta >= DRAWING_INTERVAL_SECONDS
				&& numPixelsToDraw < coins.size) {
			numPixelsToDraw++;
			drawingDelta = 0f;
		}

		for (int i = 0; i < numPixelsToDraw; i++)
			coins.get(i).draw(batch, ppuX, ppuY, renderingDelta);
	}

	/** Initializes an already created letter with {@link Coin} objects as its pixel. */
	public Letter init() {
		coins.clear();
		for (int i = 0; i < pixelPositions.length; i++) {
			coins.add(new Coin(new Vector2((pixelPositions[i][0] + paddingX)
					* Coin.DIAMETER, (pixelPositions[i][1] + PADDING_BOTTOM_Y)
					* Coin.DIAMETER), assetsManager.getTexture(AssetsEntry.texture.COIN)));
		}
		return this;
	}

	public void reset() {
		int i;
		for(Coin coin : coins) {
			i = coins.indexOf(coin, false);
			coin.getPosition().set((pixelPositions[i][0] + paddingX)
					* Coin.DIAMETER, (pixelPositions[i][1] + PADDING_BOTTOM_Y)
					* Coin.DIAMETER);
			coin.setBlur(false);
			coin.setTexture(assetsManager.getTexture(AssetsEntry.texture.COIN));
		}
		numFadedPixels = 0;
		numPixelsToDraw = 0;
		drawingStartTime = 0f;
		drawing = false;
		drawingSequenceMaintained = true;
	}

	public boolean isDrawing() {
		return drawing;
	}

	public void setDrawing(boolean drawing) {
		this.drawing = drawing;
	}

	public AnimalType getAnimalType() {
		return animalType;
	}

	public boolean isDrawingSeqMaintained() {
		return drawingSequenceMaintained;
	}

	public boolean hasPixels() {
		if (coins.size > 0)
			return true;
		return false;
	}

	public int getCountPixels() {
		return coins.size;
	}

	public int getCountFadedPixels() {
		return numFadedPixels;
	}

	public int numPixelsToDraw() {
		return pixelPositions.length;
	}

	private void countFadedPixels() {

		numFadedPixels = 0;

		for (IStaticObject coin : coins) {
			{
				if (coin.isBlur())
					numFadedPixels++;
			}
		}
	}

	public void fadePixel(float x, float y) {

		Vector2 position;

		for (Coin coin : coins) {

			position = coin.getPosition();

			if (x - position.x >= 0 && x - position.x <= Coin.DIAMETER
					&& y - position.y >= 0 && y - position.y <= Coin.DIAMETER) {

				int currentIndex = coins.indexOf(coin, false);

				if (currentIndex > 0) {
					if (!coins.get(currentIndex - 1).isBlur())
						drawingSequenceMaintained = false;
				}

				coin.setTexture(assetsManager.getTexture(AssetsEntry.texture.COIN_LITE));
				coin.setBlur(true);
				countFadedPixels();
				break;
			}
		}
	}

}
