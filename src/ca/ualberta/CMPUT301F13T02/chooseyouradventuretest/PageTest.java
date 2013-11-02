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
package ca.ualberta.CMPUT301F13T02.chooseyouradventuretest;

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.Test;

import ca.ualberta.CMPUT301F13T02.chooseyouradventure.Comment;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.Decision;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.Page;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.PhotoTile;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.TextTile;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.VideoTile;

public class PageTest {

	/**
	 * Tests the equals method of Page 
	 */
	@Test
	public void equalsTest() {
		Page page1 = new Page();
		Page page2 = new Page();
		
		//testing UUID equality
		assertFalse(page1.equals(page2));
		
		UUID id = UUID.randomUUID();
		page1.setId(id);
		page2.setId(id);
		
		assertTrue(page1.equals(page2));
		
		//testing comment equality
		page1.addComment(new Comment("hello", "ben"));
		
		assertFalse(page1.equals(page2));
		
		page2.addComment(new Comment("hello", "ben"));
		
		assertTrue(page1.equals(page2));
		
		//testing tile equality
		page1.addTile(new TextTile("text"));
		
		assertFalse(page1.equals(page2));
		
		page2.addTile(new TextTile("text"));
		
		assertTrue(page1.equals(page2));
		
		//testing tile type equality
		page1.addTile(new PhotoTile());
		page1.addTile(new VideoTile());
		
		assertFalse(page1.equals(page2));
		
		page2.addTile(new PhotoTile());
		page2.addTile(new VideoTile());
		
		assertTrue(page1.equals(page2));
		
		//testing decision equality
		page1.addDecision(new Decision("text", page1));
		
		assertFalse(page1.equals(page2));
		
		page2.addDecision(new Decision("text", page1));
		
		assertTrue(page1.equals(page2));
		
		//testing title equality
		page1.setTitle("title");
		page2.setTitle("monkey");
		
		assertFalse(page1.equals(page2));
		
		page2.setTitle("title");
		
		assertTrue(page1.equals(page2));
	}
}
