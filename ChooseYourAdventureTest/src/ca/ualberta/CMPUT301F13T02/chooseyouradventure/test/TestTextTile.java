package ca.ualberta.CMPUT301F13T02.chooseyouradventure.test;

import ca.ualberta.CMPUT301F13T02.chooseyouradventure.PhotoTile;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.TextTile;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.VideoTile;
import android.test.InstrumentationTestCase;

public class TestTextTile extends InstrumentationTestCase {
	
	public void testEquals(){
		TextTile tile1 = new TextTile("New1");
		TextTile tile2 = new TextTile("New2");
		
		assertFalse(tile1.equals(tile2));
		
		tile2 = new TextTile("New1");
		
		assertTrue(tile1.equals(tile2));
		//These next tests make sure text inequals to picture or video
		assertFalse(tile1.equals(new PhotoTile()));
		
		assertFalse(tile1.equals(new VideoTile()));
	}
	
	public void testSetContent() {
		TextTile tile = new TextTile();
		String content = "A text tile";
		tile.setContent(content);
		String tileContent = tile.getText();
		assertEquals(content, tileContent);
		
		TextTile tile2 = new TextTile("Another text tile");
		content = "Another text tile";
		tileContent = tile2.getText();
		assertEquals(content, tileContent);
	}

}

