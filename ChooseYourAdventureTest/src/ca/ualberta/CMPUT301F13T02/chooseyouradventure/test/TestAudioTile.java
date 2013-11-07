package ca.ualberta.CMPUT301F13T02.chooseyouradventure.test;

import ca.ualberta.CMPUT301F13T02.chooseyouradventure.AudioTile;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.PhotoTile;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.test.InstrumentationTestCase;

public class TestAudioTile extends InstrumentationTestCase {
	
	public void testSetContent() {
		AudioTile tile = new AudioTile();
		Object audio = new Object();
		tile.setContent(audio);
		
		Object tileAudio = tile.getAudio();
		assertEquals(audio, tileAudio);
		
	}
	
	public void testGetType() {
		AudioTile tile = new AudioTile();
		assertEquals("audio", tile.getType());
	}
	

}