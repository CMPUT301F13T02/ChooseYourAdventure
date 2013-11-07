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

import java.util.ArrayList;

import ca.ualberta.CMPUT301F13T02.chooseyouradventure.Comment;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.Handler;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.HandlerException;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.Page;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.Story;


/**
 * A MockHandler for testing where the calls should never fail
 */

public class MockHandler implements Handler {

	ArrayList<Story> stories = new ArrayList<Story>();

	/**
	 * Updates passed story
	 * 
	 * @param story The story to update
	 */
	@Override
	public void updateStory(Story story) {
		for (int i = 0; i < stories.size(); i++) {
			if (stories.get(i).getId().equals(story.getId())) {
				stories.set(i, story);
			}
		}
	}

	/**
	 * Deletes the passed story in the DB
	 * 
	 * @param story The story to delete
	 */
	@Override
	public void deleteStory(Story story) {
		for (int i = 0; i < stories.size(); i++) {
			if (stories.get(i).getId().equals(story.getId())) {
				stories.remove(i);
			}
		}
	}

	/**
	 * Adds the passed story, sets its ID
	 * @param story The story to add
	 */
	@Override
	public void addStory(Story story) {
		stories.add(story);
	}

	/**
	 * Updates the passed page of passed story by adding passed comment
	 * @param story The story the page is in
	 * @param page The page the comment is in
	 * @param comment The comment to add
	 */
	@Override
	public void addComment(Story story, Page page, Comment comment) {
		for (int i = 0; i < stories.size(); i++) {
			if (stories.get(i).getId().equals(story.getId())) {
				stories.set(i, story);
			}
		}
	}

	/**
	 * Retrieves the story with passed ID
	 * @param id The ID of the story to retrieve
	 * @return The story with the passed ID 
	 */
	@Override
	public Story getStory(String id) {
		for (Story s : stories)
			if (s.getId().equals(id))
				return s;
		
		return null;
	}

    /**
     * Gets all stories in the handler
     * 
     * @return An ArrayList containing all stories in the DB
     */
	@Override
	public ArrayList<Story> getAllStories() {
		return stories;
	}
	
	/**
	 * Deletes all stories in the handler
	 */
	public void deleteAllStories() {
		stories.clear();
	}
}
