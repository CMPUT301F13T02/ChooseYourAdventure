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

/**
 * This is the structure of a story 
 */
public class Story {
	/** 
	 * @uml.property name="pages"
	 * @uml.associationEnd aggregation="composite" inverse="story:ca.ualberta.CMPUT301F13T02.chooseyouradventure.Page"
	 */
    private ArrayList<Page> pages = new ArrayList<Page>();
    private String id;
    private UUID firstpage;
    private int currRefNum = 1;
	/**
	 * @return the firstpage
	 */
	public Page getFirstpage()
	{
		Page fp = new Page();
		Page[] pagesList = pages.toArray(new Page[pages.size()]);
		for(int i = 0; i < pages.size(); i++){
			if (firstpage.equals(pagesList[i].getId()))
			{
				fp = pagesList[i];
			}
		}
		return fp;

	}
	/**
	 * @param firstpage the firstpage to set
	 */
	public void setFirstpage(UUID firstpage)
	{
		this.firstpage = firstpage;
	}
	private String title;
    /**
     * This gets the title of the story
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * This sets the title of the story
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * This is the main constructor for Story
	 */
	public Story() {
    	
    }
    /**
     * This gets the pages of a story
     * @return The pages
     */
    public ArrayList<Page> getPages() {
    	return pages;
    }
    /**
     * This adds a page to a story
     * @param newPage A new page
     */
    public void addPage(Page newPage) {
    	pages.add(newPage);
    	currRefNum++;
    }
    /**
     * This deletes a page from a story
     * @param aPage What to delete
     */
    public void deletePage(Page aPage) {
    	
    }
    /**
     * This sets the ID of the story
     * @param id
     */
    public void setId(String id) {
    	this.id = id;
    }
    /**
     * This gets the ID of the story
     * @return
     */
    public String getId() {
    	return id;
    }
	/**
	 * Compares this story for deep equality with another story
	 * @param The story to test equality with
	 * @return The equality Truth value
	 */
	public boolean equals(Story story) {

		if (pages.size() != story.getPages().size())
			return false;
		//Check that all comments are the same
		for (int i = 0; i < pages.size(); i++) {
			if (!pages.get(i).equals(story.getPages().get(i))) 
				return false;
		}
		return true;
	}
	public int getCurrRefNum() {
		return currRefNum;
	}
	
	public void save() {
		ESHandler eshandler = new ESHandler();
		try
		{
			eshandler.updateStory(this);
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
