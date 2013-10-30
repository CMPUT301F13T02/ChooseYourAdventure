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

package ca.ualberta.CMPUT301F13T02.chooseyouradventure;

import java.util.ArrayList;
import android.app.Application;

/**
 * This is the Controller for MVC
 */
public class ControllerApp extends Application{

	private Story currentStory;
	private Page currentPage;
	private ArrayList<Story> stories;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
	}
	/**
	 * This sets the current story
	 * @param A Story
	 */
	public void setStory(Story story) {
		this.currentStory = story;
	}

	/**
	 * This sets the current Page
	 * @param A Page
	 */
	public void setPage(Page page) {
		this.currentPage = page;
	}
	/**
	 * This gets the current page
	 * @return The current Page
	 */
	public Page getPage() {
		return currentPage;
	}
	
	public void setStories(ArrayList<Story> stories) {
		this.stories = stories;
	}
	
	public ArrayList<Story> getStories() {
		return this.stories;
	}
	
	public Story createFakeStory() {
		Page page2 = createFakePage();
		Page page1 = createFakePageWithDecision(page2);
		
		Story story = new Story();
		
		story.addPage(page1);
		story.addPage(page2);
		
		return story;
	}
	
	/**
	 * This creates a fake page
	 * @return A Fake page
	 * @deprecated This will not be used in the final project
	 */
	public Page createFakePage() {
		Page newPage = new Page();
		TextTile newTile = new TextTile("This is a text tile for next page");
		newPage.addTile(newTile);
		Comment newComment = new Comment("This is a comment on next page");
		newPage.addComment(newComment);
		currentPage = newPage;
		return newPage;
	}
	
	/**
	 * This creates a fake page with a decision
	 * @param toPage
	 * @return A Fake page with a decision
	 * @deprecated This will not be used in the final project
	 */
	public Page createFakePageWithDecision(Page toPage) {
		Page newPage = new Page();
		TextTile newTile = new TextTile("This is my experiment TextTile");
		newPage.addTile(newTile);
		Decision decision = new Decision("Go to second page", toPage);
		newPage.addDecision(decision);
		Comment newComment = new Comment("This is my experiment Comment");
		newPage.addComment(newComment);
		currentPage = newPage;
		return newPage;
	}
	/**
	 * This adds a comment to the current page
	 * @param A comment to add
	 */
	public void addComment(Comment comment) {
		currentPage.addComment(comment);
	}

	/**
	 * This gets the current Story
	 * @return The current Story
	 */
	public Story getStory() {
		return currentStory;
	}
	
}
