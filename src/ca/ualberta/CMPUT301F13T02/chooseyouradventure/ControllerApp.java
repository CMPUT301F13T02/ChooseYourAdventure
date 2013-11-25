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
import java.util.Random;
import java.util.UUID;

import android.app.Application;
import android.content.Intent;
import android.provider.Settings.Secure;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.elasticsearch.ESHandler;

/**
 * This class represents the controller for our application. This class stores the state
 * of the application -- notably the story and page currently being viewed or edited.
 * 
 * This class is a controller and in typical MVC style is responsible for co-ordinating 
 * saving of the model and maintaining coherence between the model and the view.
 */

public class ControllerApp extends Application {
	
	private ArrayList<Story> stories;
	private static ControllerApp instance = new ControllerApp();
	private StoryController storyController = new StoryController();
	private PageController pageController = new PageController();
	
	@Override
	public void onCreate() {
			super.onCreate();
	}
	
	public ControllerApp() {
		instance = this;
	}
	
	/**
	 * Allows for accessing the singleton ControllerApp class from outside
	 * of an activity.
	 * @return the singleton instance of ControllerApp
	 */
	public static ControllerApp getInstance() {
		if (instance == null) {
			instance = new ControllerApp();
		}
		return instance;
	}
	

	
	/**
	 * Sets the list of stories.
	 * @param stories
	 */
	public void setStories(ArrayList<Story> stories) {
		this.stories = stories;
	}
	
	/**
	 * Returns the list of all stories.
	 * @return the list of all stories.
	 */
	public ArrayList<Story> getStories() {
		return this.stories;
	}
	

	/**
	 * Returns a list of strings for each page to be displayed in the Spinner
	 * for editing a decision.
	 * @param pages
	 * @return A list of Strings, one representing each page in the story
	 */
	public ArrayList<String> getPageStrings(ArrayList<Page> pages) {
		ArrayList<String> pageNames = new ArrayList<String>();
		for (int i = 0; i < pages.size(); i++) {
			pageNames.add("(" + pages.get(i).getRefNum() + ") " + pages.get(i).getTitle());
		}		
		return pageNames;
	}
	


	/**
	 * This method is used for gathering new data from the model, it then 
	 * returns it to update the listviews. It is generalized to work on both
	 * listviews for Pages and Stories.
	 * @param itemList
	 * @param infoText
	 * @return
	 */
	protected <T> ArrayList<String> updateView(ArrayList<T> itemList,
	                                            ArrayList<String> infoText) {
		infoText.clear();
		if(itemList.size() != 0)
		{
			for (int i = 0; i < itemList.size(); i++) {
				String outList = "";
				if(itemList.get(i).getClass().equals(Page.class))
				{
					
					if(itemList.get(i).equals(storyController.grabFirstPage())){
						outList = getString(R.string.startDesignator);
					}

					
					if(((Page) itemList.get(i)).isFightingFrag() == true){
						outList = outList + getString(R.string.fightDesignator);
					}
					

					if(((Page) itemList.get(i)).getDecisions().size() == 0){				
						outList = outList + getString(R.string.endDesignator);
					}
					outList = outList + "(" + 
					          ((Page) itemList.get(i)).getRefNum() + ") " + 
							  ((Page) itemList.get(i)).getTitle();
				} else if(itemList.get(i).getClass().equals(Story.class)) {
					
					//If the story has been saved locally, note it
					if(((Story) itemList.get(i)).getHandler() instanceof DBHandler){
						outList = getString(R.string.cachedDesignator);
					}
					outList = outList + ((Story) itemList.get(i)).getTitle();
				}
				infoText.add(outList);
			}
		}
		
		return infoText;
	}





	/**
	 * First sets the currentStory and currentPage to story and page 
	 * respectively, and then moves to the activity in classItem.
	 * @param classItem
	 * @param story
	 * @param page
	 */
	public <T> void jump(Class<T> classItem, Story story, Page page) {	
		storyController.setStory(story);
		pageController.setPage(page);
		pageController.setOnEntry(true);
		Intent intent = new Intent(this, classItem);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}


	/**
	 * Updates the decision at position whichDecision to have the given text
	 * and to point to the page at position whichPage in the story's list of
	 * pages.
	 * @param text
	 * @param whichPage
	 * @param whichDecision
	 */
	public void updateDecision(String text, int whichPage, int whichDecision) {
		ArrayList<Page> pages = storyController.getPages();
		if(whichPage == pages.size()){
			pageController.getPage().updateDecision(text, new Page(null), whichDecision);
		}
		else{
			pageController.getPage().updateDecision(text, pages.get(whichPage), whichDecision);
		}
		pageController.setDecisionsChanged();
	}
	
	public void updateDecisionFight(String text, int whichPage, int whichDecision, Counters counter) {
		ArrayList<Page> pages = storyController.getPages();
		if(whichPage == pages.size()){
			pageController.getPage().updateDecisionFight(text, new Page(null), whichDecision, counter);
		}
		else{
			pageController.getPage().updateDecisionFight(text, pages.get(whichPage), whichDecision, counter);
		}
		pageController.setDecisionsChanged();
	}
	

	

	/**
	 * This adds a comment to the current page
	 * @param A comment to add
	 */
	public void addComment(String text, PhotoTile photo) {
		String poster = Secure.getString( 
				getBaseContext().getContentResolver(), Secure.ANDROID_ID);

		Comment comment = new Comment(text, poster, photo);
		
		
		pageController.getPage().addComment(comment);
		pageController.setCommentsChanged();
		try
		{
			storyController.getStory().getHandler().addComment(storyController.getStory(), pageController.getPage(), comment);
		} catch (HandlerException e)
		{
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Sets the currentPage to the page pointed to by the decision selected
	 * and then the page is refreshed.
	 * @param whichDecision
	 */
	public void followDecision(int whichDecision) {
	    Decision decision = pageController.findDecisionByIndex(whichDecision);
		
		UUID toPageId = decision.getPageID();
		ArrayList<Page> pages = storyController.getPages();
		Page currentPage = pageController.getPage();
		Page toPage = currentPage;
		while(toPageId == null){
			Random rn = new Random();
			decision = currentPage.getDecisions().get(rn.nextInt(currentPage.getDecisions().size()));
			toPageId = decision.getPageID();
		}
		for (int i = 0; i < pages.size(); i++) {
			if (toPageId.equals(pages.get(i).getId())) {
				toPage = pages.get(i);
				break;
			}
		}
		pageController.calculateEntry(toPage, toPageId);
		
		
	}
	
	/**
	 * This function is a generalized class for creating a new story and placing it on the server
	 * @param storyTitle
	 * @throws HandlerException
	 */
	
	public void initializeNewStory(String storyTitle, Counters playerStats, boolean state) throws HandlerException{
    	
    	final Story newStory = new Story(); 
    	newStory.setUsesCombat(state);
    	newStory.setPlayerStats(playerStats);
    	newStory.setTitle(storyTitle);	    	
    	Page page = storyController.initializeNewPage("First Page");
    	newStory.addPage(page);
    	newStory.setFirstpage(page.getId());
    	newStory.setAuthor(Secure.getString(getBaseContext().getContentResolver(), Secure.ANDROID_ID));
    	newStory.setHandler(new ESHandler());
	    try
		{			    	
	    	newStory.getHandler().addStory(newStory);
			

		} catch (Exception e)
		{
			e.printStackTrace();
		}	
	    jump(EditStoryActivity.class,newStory, page);
    }

	/**
	 * @return the storyController
	 */
	public StoryController getStoryController() {
		return storyController;
	}


	/**
	 * @return the pageController
	 */
	public PageController getPageController() {
		return pageController;
	}

	
	
	
	
}
