package com.eatlappscontest.kks.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.eatlappscontest.kks.model.DrawingBoard;
import com.eatlappscontest.kks.model.Zoo;

public class ZooScreen extends BaseScreen {
	
	public static final float LETTER_DIAMETER = 3f;
	public static final float LETTER_POS_X = KKS.VIEWPORT_WIDTH / 4f;
	public static final float LETTER_POS_Y_CORRECT = 2f;
	public static final float LETTER_POS_Y_RANDOM = 5.5f;
	
	public boolean drawLetters = false;
	
	public OrthographicCamera camera;
	public Zoo zoo;
	public DrawingBoard drawingBoard;
	public Texture correctLetter, randomLetter;
	public Sound question;
	public Array<Sound> morals;

	public ZooScreen(OnScreenDisposeListener disposeListener) {
		super(disposeListener);
		camera = new OrthographicCamera();
		camera.setToOrtho(false);
	}

	@Override
	public void render(float delta) {
		Gdx.graphics.getGL20().glClearColor(0, 1, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		super.render(delta);
	}

	@Override
	public void draw(SpriteBatch batch, float delta) {
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		zoo.draw(batch, ppuX, ppuY, delta);
		drawingBoard.draw(batch, ppuX, ppuY, delta);
		if (drawLetters && correctLetter != null && randomLetter != null) {
			batch.draw(correctLetter, LETTER_POS_X * ppuX,
					LETTER_POS_Y_CORRECT * ppuY, LETTER_DIAMETER * ppuX, LETTER_DIAMETER * ppuX);
			batch.draw(randomLetter, LETTER_POS_X * ppuX,
					LETTER_POS_Y_RANDOM * ppuY, LETTER_DIAMETER * ppuX, LETTER_DIAMETER * ppuX);
		}
		batch.end();
	}
}
