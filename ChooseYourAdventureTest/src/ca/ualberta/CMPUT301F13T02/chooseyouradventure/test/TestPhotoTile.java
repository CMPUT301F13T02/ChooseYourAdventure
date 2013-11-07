package ca.ualberta.CMPUT301F13T02.chooseyouradventure.test;

import ca.ualberta.CMPUT301F13T02.chooseyouradventure.PhotoTile;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.test.InstrumentationTestCase;

public class TestPhotoTile extends InstrumentationTestCase {
	
	public void testSetContent() {
		PhotoTile tile = new PhotoTile();
		Bitmap bm = BitmapFactory.decodeFile("/ChooseYourAdventure/res/drawable-hdpi/ic_launcher.png");
		tile.setImage(bm);
		Bitmap tileBm = tile.getImage();
		assertEquals(bm, tileBm);
		
	}
	
	public void testGetType() {
		PhotoTile tile = new PhotoTile();
		assertEquals("photo", tile.getType());
	}

}
