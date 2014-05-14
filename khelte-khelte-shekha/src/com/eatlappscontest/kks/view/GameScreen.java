package com.eatlappscontest.kks.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.eatlappscontest.kks.model.Animal;
import com.eatlappscontest.kks.model.Bullet;
import com.eatlappscontest.kks.model.Cage;
import com.eatlappscontest.kks.model.DrawingBoard;
import com.eatlappscontest.kks.model.Evil;
import com.eatlappscontest.kks.model.IStaticObject;
import com.eatlappscontest.kks.model.Letter;
import com.eatlappscontest.kks.model.Rocket;
import com.eatlappscontest.kks.model.Universe;
import com.eatlappscontest.kks.spec.AnimalType;

public class GameScreen extends BaseScreen {

	public OrthographicCamera camera;
	public Sound shootSound, explosionSound;
	public Letter letter;
	public DrawingBoard drawingBoard;
	public Animal animal;
	public Cage cage;
	public Universe universeLayer1, universeLayer2;
	public Rocket rocket;
	public Texture staticUniverse;
	public ObjectMap<AnimalType, Animal> animals;
	public Array<Music> backgroundMusics;
	public Array<Texture> universeBackgrounds;
	public Array<Letter> letters;
	public Array<Bullet> bullets;
	public Array<Evil> evils;
	public Array<IStaticObject> staticObjects;

	public GameScreen(OnScreenDisposeListener disposeListener) {
		super(disposeListener);
		camera = new OrthographicCamera(KKS.VIEWPORT_WIDTH, KKS.VIEWPORT_HEIGHT);		
	}

	@Override
	public void render(float delta) {
		Gdx.graphics.getGL20().glClearColor( 0, 0, 1, 1 );
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		super.render(delta);
	}

	@Override
	public void draw(SpriteBatch batch, float delta) {
		batch.begin();

		batch.draw(staticUniverse, 0f, 0f, KKS.VIEWPORT_WIDTH * ppuX, KKS.VIEWPORT_HEIGHT * ppuY);
		universeLayer1.draw(batch, ppuX, ppuY, delta);
		universeLayer2.draw(batch, ppuX, ppuY, delta);

		for (Bullet bullet : bullets)
			bullet.draw(batch, ppuX, ppuY, delta);
		for (Evil evil : evils)
			evil.draw(batch, ppuX, ppuY, delta);
		for (IStaticObject staticObject : staticObjects)
			staticObject.draw(batch, ppuX, ppuY, delta);

		rocket.draw(batch, ppuX, ppuY, delta);

		drawingBoard.draw(batch, ppuX, ppuY, delta);

		letter.draw(batch, ppuX, ppuY, delta);

		if(animal.isInCage(cage)) {
			animal.drawName(batch, ppuX, ppuY, delta);
			animal.draw(batch, ppuX, ppuY, delta);
			cage.draw(batch, ppuX, ppuY, delta);
		}
		else {
			animal.drawName(batch, ppuX, ppuY, delta);
			cage.draw(batch, ppuX, ppuY, delta);
			animal.draw(batch, ppuX, ppuY, delta);
		}

		batch.end();
	}

	@Override
	public void show() {
		camera.position.set(3.5f, 5, 0);
		camera.update();
		super.show();
	}
}
