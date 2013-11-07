package ca.ualberta.CMPUT301F13T02.chooseyouradventure.test;

import ca.ualberta.CMPUT301F13T02.chooseyouradventure.VideoTile;
import android.test.InstrumentationTestCase;

public class TestVideoTile extends InstrumentationTestCase {

	public void testSetContent() {
		VideoTile tile = new VideoTile();
		Object video = new Object();
		
		tile.setContent(video);
		Object tileContent = tile.getVideo();
		assertTrue(video == tileContent);
		
		
		
	}
	
	public void testGetType() {
		VideoTile tile = new VideoTile();
		assertEquals("video", tile.getType());
	}

	
}