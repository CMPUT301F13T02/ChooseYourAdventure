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

import ca.ualberta.CMPUT301F13T02.chooseyouradventure.elasticsearch.ESHandler;
import android.app.Application;
import android.content.Intent;
import android.provider.Settings.Secure;

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
	 * This gets the current Story
	 * @return The current Story
	 */
	public Story getStory() {
		return currentStory;
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
	
	/**
	 * This adds a comment to the current page
	 * @param A comment to add
	 */
	public void addComment(Comment comment) {
		currentPage.addComment(comment);
	}
	
	public <T> void jump(Class<T> classItem, Story story, Page page) {
		setStory(story);
		setPage(page);
		Intent intent = new Intent(this, classItem);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}
	
	protected void initializeNewStory(String storyTitle) throws HandlerException{
	    	
	    	final Story newStory = new Story(); 	
	    	newStory.setTitle(storyTitle);	    	
	    	Page newPage = initializeNewPage("First Page");
	    	newStory.addPage(newPage);
	    	newStory.setFirstpage(newPage.getId());
	    	newStory.setAuthor(Secure.getString(
					getBaseContext().getContentResolver(), Secure.ANDROID_ID));
		    try
			{			    	
		    	ESHandler eshandler = new ESHandler();
				eshandler.addStory(newStory);
				

			} catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		    jump(EditStoryActivity.class,newStory, newPage);
	    }
	
	protected Page initializeNewPage(String pageTitle){
		final Page newPage = new Page();
		newPage.setTitle(pageTitle);
		return newPage;
	}
	
	protected <T> ArrayList<String> updateView(ArrayList<T> itemList, ArrayList<String> infoText){
		
		
		
		infoText.clear();
		if(itemList.size() != 0)
		{
			for (int i = 0; i < itemList.size(); i++) {
				String outList = "";
			
				if(itemList.get(i).getClass().equals(Page.class))
				{
					
					
					if(itemList.get(i).equals(currentStory.getFirstpage())){
						outList = "{Start} ";
					}
					
					if(((Page) itemList.get(i)).getDecisions().size() == 0){				
						outList = outList + "{Endpoint} ";
					}	
					outList = outList + "(" + ((Page) itemList.get(i)).getRefNum() + ") " + ((Page) itemList.get(i)).getTitle();
				}
				else if(itemList.get(i).getClass().equals(Story.class)){
				
					outList = ((Story) itemList.get(i)).getTitle();
				}
				infoText.add(outList);
			}
		
			
		}
		
		return infoText;
	}
	
	protected void updateTitle(String pageTitle, Page currentPage){
		currentPage.setTitle(pageTitle);		
		currentStory.updateStory();
	}
	
	protected void updateTitle(String pageTitle){
		Page newPage = initializeNewPage(pageTitle);
		currentStory.addPage(newPage);
		currentStory.updateStory();
	}
	
	protected void updateFP(Page currentPage){
		UUID newID = currentPage.getId();
		currentStory.setFirstpage(newID);
		
	}
	
	protected void removePage(Page currentPage){
		currentStory.deletePage(currentPage);
		currentStory.updateStory();
	}
	
	
	
	
	
}
