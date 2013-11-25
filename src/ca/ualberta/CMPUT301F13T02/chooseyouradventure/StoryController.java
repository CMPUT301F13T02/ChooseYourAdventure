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
	
	public ArrayList<Page> getPages(){
		return getStory().getPages();
	}
	
	/**
	 * Updates a Pages title and pushes to the handler
	 * @param pageTitle
	 * @param currentPage
	 */
	protected void updateTitle(String pageTitle, Page currentPage){
		currentPage.setTitle(pageTitle);		
		currentStory.updateStory();
	}

	/**
	 * Similar to the above method, but it creates a new page object with the 
	 * title.
	 * @param pageTitle
	 */
	protected void updateFightTitle(String pageTitle, boolean fight, String health, String name, Page page){
		page.setTitle(pageTitle);
		page.setFightingFrag(fight);
		page.setEnemyName(name);
		try{
			page.setEnemyHealth(Integer.parseInt(health));
		} catch(Exception e){}
		
		currentStory.updateStory();
	}
	
	protected void newTitle(String pageTitle, boolean fight, String health, String name){
		Page newPage = initializeNewPage(pageTitle);
		newPage.setFightingFrag(fight);
		newPage.setEnemyName(name);
		try{
			newPage.setEnemyHealth(Integer.parseInt(health));
		} catch(Exception e){}
		currentStory.addPage(newPage);
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
	 * Deletes a page.
	 * @param currentPage
	 */
	protected void removePage(Page currentPage){
		currentStory.deletePage(currentPage);
		currentStory.updateStory();
	}
	
	/**
	 * Similar to the above function, this method creates a new page object
	 * @param pageTitle
	 * @return
	 */
	public Page initializeNewPage(String pageTitle){
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
	
	
	
	public Page grabFirstPage(){
		return currentStory.getFirstpage();
	}
	
	public ArrayList<Page> grabPages(){
		return currentStory.getPages();
	}
}
