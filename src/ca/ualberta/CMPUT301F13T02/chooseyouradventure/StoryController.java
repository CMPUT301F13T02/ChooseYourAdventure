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
import java.util.UUID;

import android.widget.Spinner;

/**
 * This class handles all of the interactions with the user that cause a change
 * in the current story in the model.
 *
 * This is a controller in the MVC style.
 *
 * @author James Cadek
 */

public class StoryController {
	private Story currentStory;
	
	/**
	 * This sets the current story
	 * @param A Story
	 */
	public void setStory(Story story) {
		this.currentStory = story;
	}

	/**
	 * This gets the current Story
	 * @return The current Story
	 */
	public Story getStory() {
		return currentStory;
	}
	
	/**
	 * Gets all the pages in the current story
	 * @return the pages of the story
	 */
	public ArrayList<Page> getPages(){
		return currentStory.getPages();
	}
	
	/**
	 * Updates a Page's title and the players stats and pushes them 
	 * to the handler
	 * @param pageTitle
	 * @param fight
	 * @param health
	 * @param name
	 * @param page
	 */
	protected void updatePageData(String pageTitle, boolean fight, String health, String name, Page page){
		page.setTitle(pageTitle);
		page.setFightingFrag(fight);
		if(fight == true){
			page.setEnemyName(name);
			try{
				page.setEnemyHealth(Integer.parseInt(health));
			} catch(Exception e){}
		}
		currentStory.updateStory();
	}
	
	/**
	 * Sets up a new page for addition, based on the input parameters.
	 * @param pageTitle
	 * @param fight
	 * @param health
	 * @param name
	 */
	public void newPage(String pageTitle, boolean fight, String health, String name){
		Page page = initializeNewPage(pageTitle);
		page.setFightingFrag(fight);
		page.setEnemyName(name);
		try{
			page.setEnemyHealth(Integer.parseInt(health));
		} catch(Exception e){}
		currentStory.addPage(page);
		currentStory.updateStory();
	}
	
	/**
	 * Updates the first page of a story and pushes to handler
	 * @param currentPage
	 */
	protected void updateFP(Page currentPage){
		UUID newID = currentPage.getId();
		currentStory.setFirstpage(newID);
		currentStory.updateStory();
	}

	/**
	 * Deletes a page at position item.
	 * @param item
	 */
	protected void removePage(int item){
		currentStory.deletePage(item);
		currentStory.updateStory();
	}
	
	/**
	 * This method creates a new page object, but a simpler one without 
	 * player statistics (for non-fighting pages)
	 * @param pageTitle
	 * @return the new page
	 */
	protected Page initializeNewPage(String pageTitle){
		final Page newPage = new Page();
		newPage.setTitle(pageTitle);
		return newPage;
	}
	
	/**
	 * Tells the current story to save itself.
	 */
	public void saveStory() {
		currentStory.updateStory();
	}
	
	/**
	 * Gets the first page of the current story. 
	 * @return The firstpage
	 */
	public Page grabFirstPage(){
		return currentStory.getFirstpage();
	}
	
	/**
	 * Returns the page selected in a spinner.
	 * @param pageSpinner
	 * @return Page
	 */
	protected Page getPageFromSpinner(Spinner pageSpinner){
		int whichPage = pageSpinner.getSelectedItemPosition();     		
		ArrayList<Page> pages = getPages();
		
		Page page = new Page("RANDOM");
			
		if(whichPage != pages.size()){
			page = pages.get(whichPage);
		}
		return page;
	}
	
}
