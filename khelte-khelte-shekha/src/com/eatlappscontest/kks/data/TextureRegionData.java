package com.eatlappscontest.kks.data;

public class TextureRegionData {

	public boolean flipX;
	public boolean flipY;
	public String regionPrefix;
	
	public TextureRegionData(boolean[] flipXY, String regionPrefix) {
		this.flipX = flipXY[0];
		this.flipY = flipXY[1];
		this.regionPrefix = regionPrefix;
	}
}
