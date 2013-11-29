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

import ca.ualberta.CMPUT301F13T02.chooseyouradventure.Counters;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.Page;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.Story;
import android.test.InstrumentationTestCase;

public class TestStory extends InstrumentationTestCase {
	
	private Story story;
	private MockHandler handler = new MockHandler();
	
	protected void setUp() {
		story = new Story();
		story.setHandler(handler);
	}
	
	public void testSetTitle() {
		story.setTitle("A Story");
		assertTrue(story.getTitle().equals("A Story"));
	}
	
	public void testGetTitle() {
		String title = "Adventure Story";
		story.setTitle(title);
		String storyTitle = story.getTitle();
		assertTrue(title.equals(storyTitle));
	}
	
	public void testSetId() {
		story.setId("ID");
		assertTrue(story.getId().equals("ID"));
	}
	
	public void testGetId() {
		String id = "AAAAA";
		story.setId(id);
		String storyId = story.getId();
		assertTrue(id.equals(storyId));
	}
	
	public void testSetAuthor() {
		story.setAuthor("Author");
		assertTrue(story.getAuthor().equals("Author"));
	}
	
	public void testGetAuthor() {
		String author = "Stephen King";
		story.setAuthor(author);
		String storyAuthor = story.getAuthor();
		assertTrue(author.equals(storyAuthor));
	}
	
	public void testAddPage() {
		story.getPages().clear();
		story.addPage(new Page());
		int l = story.getPages().size();
		assertEquals(l, 1);
	}
	
	public void testDeletePage() {
		story.getPages().clear();
		story.addPage(new Page());
		story.deletePage(0);
		int l = story.getPages().size();
		assertEquals(l, 0);
	}
	
	public void testSetHandler() {
		MockHandler mockHandler = new MockHandler();
		story.setHandler(mockHandler);
		assertEquals(mockHandler, story.getHandler());
	}
	
	public void testGetHandler() {
		MockHandler mockHandler = new MockHandler();
		story.setHandler(mockHandler);
		MockHandler storyHandler = (MockHandler) story.getHandler();
		assertEquals(mockHandler, storyHandler);
	}
	
	public void testIsUsesCombat() {
		boolean usesCombat = true;
		story.setUsesCombat(usesCombat);
		boolean storyUsesCombat = story.isUsesCombat();
		assertEquals(usesCombat, storyUsesCombat);
	}
	
	public void testSetUsesCombat() {
		story.setUsesCombat(true);
		assertTrue(story.isUsesCombat());
	}
	
	public void testSetPlayStats() {
		Counters stats = new Counters();
		story.setPlayerStats(stats);
		assertEquals(stats, story.getPlayerStats());
	}
	
	public void testGetPlayerStats() {
		Counters stats = new Counters();
		story.setPlayerStats(stats);
		Counters storyStats = story.getPlayerStats();
		assertEquals(stats, storyStats);
	}

}