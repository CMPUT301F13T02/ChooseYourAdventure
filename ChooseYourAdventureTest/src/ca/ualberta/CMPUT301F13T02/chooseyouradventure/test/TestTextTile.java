/*
* Copyright (c) 2013, TeamCMPUT301F13T02
* All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification,
* are permitted provided that the following conditions are met:
*
* Redistributions of source code must retain the above copyright notice, this
* list of conditions and the following disclaimer.
*
* Redistributions in binary form must reproduce the above copyright notice, this
* list of conditions and the following disclaimer in the documentation and/or
* other materials provided with the distribution.
*
* Neither the name of the {organization} nor the names of its
* contributors may be used to endorse or promote products derived from
* this software without specific prior written permission.
*
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
* ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
* WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
* DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
* ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
* (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
* LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
* (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
* SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package ca.ualberta.CMPUT301F13T02.chooseyouradventure.test;

import ca.ualberta.CMPUT301F13T02.chooseyouradventure.PhotoTile;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.TextTile;
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
		
		//assertFalse(tile1.equals(new VideoTile()));
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
	
	public void testGetType() {
		TextTile tile = new TextTile();
		assertEquals("text", tile.getType());
	}
	

}

