package com.eatlappscontest.kks.data;

import com.badlogic.gdx.utils.ObjectMap;

public final class AssetsEntry {

	public static final class texture {

		public static final String ROCKET = 	"rocket.png";
		public static final String BULLET = 	"bullet.png";
		public static final String EXPLOSION = 	"explosion.png";
		public static final String[] EVILS =	{ 
												"evil_1.png", 
												"evil_2.png",
												"evil_3.png", 
												"evil_4.png" 
												};
		public static final String[] LETTERS = 	{
												"letter_bo.png",
												"letter_ho.png", 
												"letter_ko.png", 
												"letter_sho.png", 
												"letter_to.png",
												"letter_u.png", 
												"letter_vo.png" 
												};
		public static final String[] BALLOONS = {
												"balloon_1.png",
												"balloon_2.png",
												"balloon_4.png"
												};
		public static final String UNIVERSE1 = 	"bg_01.png";
		public static final String UNIVERSE2 = 	"bg_02.png";
		public static final String UNIVERSE3 = 	"bg_03.png";
		public static final String TRANSPARENT_BG = 
												"transparent_bg.png";
		public static final String CAGE_SMALL_BLANK = 		
												"cage_small_blank.png";
		public static final String CAGE_NORMAL_OPEN =
												"cage_normal_open.png";
		public static final String CAGE_NORMAL_CLOSED =
												"cage_normal_closed.png";
		public static final String CAGE_CROW = 	"cage_crow.png";
		public static final String COIN = 		"coin.png";
		public static final String COIN_LITE = 	"coin_lite.png";
		public static final String KO = 		"ko.png";
		public static final String HO = 		"ho.png";
		public static final String BO = 		"bo.png";
		public static final String GO = 		"go.png";
		public static final String SHO = 		"sho.png";
		public static final String KHO = 		"kho.png";
		public static final String TO = 		"to.png";
		public static final String VO = 		"vo.png";
		public static final String U = 			"u.png";
	}

	public static final class music {
		public static final String BACKGROUND_1 = 
												"background_music_01.ogg";
		public static final String BACKGROUND_2 = 
												"background_music_02.ogg";
		public static final String BACKGROUND_3 = 
												"background_music_03.mp3";
		public static final String NATIONAL_ANTHEM = 
												"national_anthem.mp3";
	}

	public static final class sound {
		public static final String SHOOT = 
												"sound_shoot.mp3";
		public static final String Explosion = 	"sound_blast.ogg";
		public static final String CROW = 		"sound_crow.mp3";
		public static final String TIGER = 		"sound_tiger.mp3";
		public static final String ELEPHANT = 	"sound_elephant.mp3";
		public static final String BEAR =	 	"sound_bear.mp3";
		public static final String LION =	 	"sound_lion.mp3";
		public static final String GORILLA = 	"sound_gorilla.mp3";
		public static final String OSTRICH = 	"sound_ostrich.mp3";
		public static final String PARROT = 	"sound_parrot.mp3";
		public static final String RABBIT = 	"sound_rabbit.mp3";
		public static final String NAME_CROW = 	"sound_name_crow.mp3";
		public static final String NAME_CAT = 	"sound_name_cat.mp3";
		public static final String NAME_TIGER = "sound_name_tiger.mp3";
		public static final String NAME_PARROT =
												"sound_name_parrot.mp3";
		public static final String NAME_OSTRICH = 	
												"sound_name_ostrich.mp3";
		public static final String NAME_BEAR = 	"sound_name_bear.mp3";
		public static final String NAME_RABBIT = 	
												"sound_name_rabbit.mp3";
		public static final String NAME_GORILLA = 	
												"sound_name_gorilla.mp3";
		public static final String NAME_ELEPHANT = 	
												"sound_name_elephant.mp3";
		public static final String NAME_LION = 	"sound_name_lion.mp3";
		public static final String QUESTION = 	"sound_question.mp3";
		public static final String MORAL1 =		"moral_01.mp3";
		public static final String MORAL2 =		"moral_02.mp3";
		public static final String MORAL3 =		"moral_03.mp3";
	}
	
	public static final class texture_atlas {
		//public static final String EVIL = 		"textures/evil.pack";
		public static final String CROW = 		"textures/crow.pack";
		public static final String ELEPHANT = 	"textures/elephant.pack";
		public static final String TIGER = 		"textures/tiger.pack";
		public static final String BEAR = 		"textures/bear.pack";
		public static final String LION = 		"textures/lion.pack";
		public static final String GORILLA = 	"textures/gorilla.pack";
		public static final String PARROT = 	"textures/parrot.pack";
		public static final String OSTRICH = 	"textures/ostrich.pack";
		public static final String RABBIT = 	"textures/rabbit.pack";
		public static final String GRASS = 		"textures/grass.pack";
		public static final String FLAG = 		"textures/flag.pack";
		public static final String PATRIOT = 	"textures/salute.pack";
		
		static final ObjectMap<String, boolean[]> packFileToFlipMap = new ObjectMap<String, boolean[]>();
		
		static {
			//packFileToFlipMap.put(EVIL, new boolean[] {false, false});
			packFileToFlipMap.put(CROW, new boolean[] {true, false});
			packFileToFlipMap.put(ELEPHANT, new boolean[] {false, false});
			packFileToFlipMap.put(TIGER, new boolean[] {false, false});
			packFileToFlipMap.put(BEAR, new boolean[] {false, false});
			packFileToFlipMap.put(LION, new boolean[] {false, false});
			packFileToFlipMap.put(GORILLA, new boolean[] {false, false});
			packFileToFlipMap.put(PARROT, new boolean[] {true, false});
			packFileToFlipMap.put(OSTRICH, new boolean[] {false, false});
			packFileToFlipMap.put(RABBIT, new boolean[] {true, false});
			packFileToFlipMap.put(GRASS, new boolean[] {false, false});
			packFileToFlipMap.put(FLAG, new boolean[] {false, false});
			packFileToFlipMap.put(PATRIOT, new boolean[] {false, false});
		}
		
		public static final String getRegionPrefix(String packFile) {
			return packFile.split("/")[1].split("\\.")[0];
		}
		
		public static boolean[] resolveFlip(String packFile) {
			return packFileToFlipMap.get(packFile);
		}
	}
}
