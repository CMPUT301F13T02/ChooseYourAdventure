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


import com.jayway.android.robotium.solo.Solo;

import ca.ualberta.CMPUT301F13T02.chooseyouradventure.ApplicationController;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.Comment;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.Decision;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.Page;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.PageController;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.Story;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.StoryController;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.TextTile;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.ViewPageActivity;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.elasticsearch.ESHandler;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.TextView;

public class TestViewPageActivity extends ActivityInstrumentationTestCase2<ViewPageActivity> {
	
	private ViewPageActivity activity;
	
	private Button addTileButton;
	private Button addDecisionButton;
	
	private TextView pageEnding;
	private TextView addComment;
	private TextView commentsTitle;
	
	private static final ApplicationController app = ApplicationController.getInstance();
	private StoryController storyController;
	private PageController pageController;
	
	private Page page;
	private Story story;
	
	private static int numComments = 1;
	private static int numTiles = 1;
	private static int numDecisions = 1;
	
	private ESHandler handler;
	

	public TestViewPageActivity() {
		super(ViewPageActivity.class);
		
	}
	
	
	protected void setUp() throws Exception{
		super.setUp();
		page = new Page();
		story = new Story();
		handler = new ESHandler();
		story.setHandler(handler);
		story.setId("25");
		story.setTitle("Unit Test Generated");
		
		storyController = app.getStoryController();
		pageController = app.getPageController();
		
		storyController.setStory(story);
		pageController.setPage(page);
		
		activity = getActivity();
		
		addTileButton = (Button) activity.findViewById(ca.ualberta.CMPUT301F13T02.chooseyouradventure.R.id.addTile);
		addDecisionButton = (Button) activity.findViewById(ca.ualberta.CMPUT301F13T02.chooseyouradventure.R.id.addDecision);
		
		pageEnding = (TextView) activity.findViewById(ca.ualberta.CMPUT301F13T02.chooseyouradventure.R.id.pageEnding);
		addComment = (TextView) activity.findViewById(ca.ualberta.CMPUT301F13T02.chooseyouradventure.R.id.addComment);
		commentsTitle = (TextView) activity.findViewById(ca.ualberta.CMPUT301F13T02.chooseyouradventure.R.id.commentTitle);
		
	}
	
	
	public void testLayout() {
		assertNotNull(addTileButton);
		assertNotNull(addDecisionButton);
		assertNotNull(pageEnding);
		assertNotNull(addComment);
		assertNotNull(commentsTitle);
	}
	
	
	
	
	public void testStateDestroy() {
		page = pageController.getPage();
		page.addComment(new Comment("A comment", null));
		page.addTile(new TextTile());
		page.addDecision(new Decision("A decision", new Page()));
		
		activity.finish();
		activity = getActivity();
		
		int c = page.getComments().size();
		int t = page.getTiles().size();
		int d = page.getDecisions().size();
		
		assertEquals(numComments, c);
		assertEquals(numTiles, t);
		assertEquals(numDecisions, d);
		
	}
	
	
	
	
	public void testAddTextTile() {
		page.getTiles().clear();
		page.addTile(new TextTile());
		int l = page.getTiles().size();
		assertEquals(l, 1);
		
		/*
	    Solo solo = new Solo(getInstrumentation(), getActivity());
	    getInstrumentation().waitForIdleSync();
	    
		activity.runOnUiThread(
				new Runnable() {
					public void run() {
						addTileButton.requestFocus();
						addTileButton.performClick();
					}
				});
		solo.clickOnText("Text Tile");
		//solo.clickOnText("Cancel");
		page = pageController.getPage();
		int l = page.getTiles().size();
		assertEquals(l, 1);
		*/
		
	}
	
	

	
	
	/*
	public void testAddPhotoTile() {
		page.getTiles().clear();
		
		activity.runOnUiThread(
				new Runnable() {
					public void run() {
						app.addTile(new PhotoTile());
						page = app.getPage();
						int l = page.getTiles().size();
						assertEquals(l, 1);
					}
				});

		
	}
	*/
	
	
	
	
	public void testAddComment() {
		page.getComments().clear();
		page.addComment(new Comment("Test Comment", null));
		int l = page.getComments().size();
		assertEquals(l, 1);
		
		/*
	    //Solo solo = new Solo(getInstrumentation(), getActivity());
	    //getInstrumentation().waitForIdleSync();
	    
		activity.runOnUiThread(
				new Runnable() {
					public void run() {
						pageController.addComment("Test Comment", null, story);
						page = pageController.getPage();
						int l = page.getComments().size();
						assertEquals(l, 1);
						//addComment.requestFocus();
						//addComment.performClick();
					}
				});
		//solo.clickOnText("No Image");
	    //solo.enterText(0, "Test Comment");
	    //solo.clickOnButton("Save");
	    //page = pageController.getPage();
	    //int l = page.getComments().size();
	    //assertEquals(l, 1);
	     */
				
	}
	
	
	
	
	
	
	public void testAddDecision() {
		page.getDecisions().clear();
		page.addDecision(new Decision("Test Decision", page));
		int l = page.getDecisions().size();
		assertEquals(l, 1);
		
		/*
		page.getDecisions().clear();
		
	    
		activity.runOnUiThread(
				new Runnable() {
					public void run() {
						addDecisionButton.requestFocus();
						addDecisionButton.performClick();
						//page = pageController.getPage();
						int l = page.getDecisions().size();
						assertEquals(l, 1);
					}
				});
				*/
	}
	
	
	

}

