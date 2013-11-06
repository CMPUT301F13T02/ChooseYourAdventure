package ca.ualberta.CMPUT301F13T02.chooseyouradventure.test;

import ca.ualberta.CMPUT301F13T02.chooseyouradventure.PhotoTile;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.test.InstrumentationTestCase;

public class TestAudioTile extends InstrumentationTestCase {
	
	public void testSetContent() {
		PhotoTile tile = new PhotoTile();
		Bitmap bm = BitmapFactory.decodeFile("/ChooseYourAdventure/res/drawable-hdpi/ic_launcher.png");
		tile.setImage(bm);
		Bitmap tileBm = tile.getImage();
		assertEquals(bm, tileBm);
		
	}
	
	public void testEquals() {
		PhotoTile tile1 = new PhotoTile();
		PhotoTile tile2 = new PhotoTile();
		assertFalse(tile1.equals(tile2));
		
		Bitmap bm = BitmapFactory.decodeFile("/ChooseYourAdventure/res/drawable-hdpi/ic_launcher.png");
		tile1.setImage(bm);
		tile2.setImage(bm);
		assertFalse(tile1.equals(tile2));
		
	}

}
