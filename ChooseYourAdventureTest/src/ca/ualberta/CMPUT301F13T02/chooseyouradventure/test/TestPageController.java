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

import ca.ualberta.CMPUT301F13T02.chooseyouradventure.ApplicationController;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.Decision;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.Page;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.PageController;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.Story;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.StoryController;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.TextTile;
import android.test.InstrumentationTestCase;

public class TestPageController extends InstrumentationTestCase {
	
	private static final ApplicationController app = ApplicationController.getInstance();
	
	private PageController pageController;
	private StoryController storyController;
	private Page page;
	private Story story;
	

	
	private MockHandler handler = new MockHandler();


	protected void setUp() {
		pageController = app.getPageController();
		storyController = app.getStoryController();
		page = new Page();
		story = new Story();
		
		pageController.setPage(page);
		storyController.setStory(story);
		story.setHandler(handler);
		
	}
	
	public void testSetPage() {
		pageController.setPage(page);
		assertEquals(page, pageController.getPage());
	}
	
	public void testGetPage() {
		pageController.setPage(page);
		Page p = pageController.getPage();
		assertEquals(p, page);
	}
	
	public void testAddComment() {
		page.getComments().clear();
		pageController.addComment("Test Comment", null, story);
		int l = page.getComments().size();
		assertEquals(l, 1);
		
	}
	
	public void testAddTile() {
		page.getTiles().clear();
		pageController.addTile(new TextTile());
		int l = page.getTiles().size();
		assertEquals(l, 1);
		
	}
	
	public void testDeleteTile() {
		page.getTiles().clear();
		page.addTile(new TextTile());
		pageController.deleteTile(0);
		int l = page.getTiles().size();
		assertEquals(l, 0);
	}
	
	public void testSetEnding() {
		String ending = new String("The End");
		pageController.setEnding(ending);
		String pageEnding = page.getPageEnding();
		assertTrue(pageEnding.equals(ending));
	}
	
	public void testDeleteDecision() {
		page.getDecisions().clear();
		page.addDecision(new Decision("A decision", page));
		pageController.deleteDecision(0);
		int l = page.getDecisions().size();
		assertEquals(l, 0);
	}
	
	public void testAddDecision() {
		page.getDecisions().clear();
		page.addDecision(new Decision("A decision", page));
		int l = page.getDecisions().size();
		assertEquals(l, 1);
	}

}
