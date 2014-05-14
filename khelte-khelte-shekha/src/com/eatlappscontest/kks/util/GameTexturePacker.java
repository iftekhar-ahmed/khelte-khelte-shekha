package com.eatlappscontest.kks.util;

import java.io.IOException;

import com.badlogic.gdx.tools.imagepacker.TexturePacker2;

public class GameTexturePacker {

	public static void main(String[] args) throws IOException {
		TexturePacker2.process("./assets/animal", "./assets/textures",
				"salute.pack");
	}

}