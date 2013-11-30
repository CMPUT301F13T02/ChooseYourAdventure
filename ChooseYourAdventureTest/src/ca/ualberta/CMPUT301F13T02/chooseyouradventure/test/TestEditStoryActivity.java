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

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.ApplicationController;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.EditStoryActivity;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.Page;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.Story;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.StoryController;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.elasticsearch.ESHandler;

public class TestEditStoryActivity extends ActivityInstrumentationTestCase2<EditStoryActivity> {
	
	private EditStoryActivity activity;
	
	private Button addPageButton;
	private Button deleteStoryButton;
	
	private Story story;
	
	private static final ApplicationController app = ApplicationController.getInstance();
	private StoryController storyController;
	
	private static int numPages = 1;
	
	private ESHandler handler;

	public TestEditStoryActivity() {
		super(EditStoryActivity.class);
		
	}
	
	protected void setUp() throws Exception {
		story = new Story();
		story.setId("255");
		story.setTitle("Unit Test Generated");
		
		storyController = app.getStoryController();
		storyController.setStory(story);
		
		activity = getActivity();
		
		addPageButton = (Button) activity.findViewById(ca.ualberta.CMPUT301F13T02.chooseyouradventure.R.id.createButton2);
		deleteStoryButton = (Button) activity.findViewById(ca.ualberta.CMPUT301F13T02.chooseyouradventure.R.id.deleteButton);
		handler = new ESHandler();
		story.setHandler(handler);
		
	}
	
	public void testStateDestroy() {
		//story = app.getStory();
		story.getPages().clear();
		story.addPage(new Page());
		
		activity.finish();
		activity = getActivity();
		
		int np = story.getPages().size();
		assertEquals(numPages, np);
		//assertFalse(numPages == np);
	}
	
	public void testLayout() {
		assertNotNull(addPageButton);
		assertNotNull(deleteStoryButton);
	}

}

