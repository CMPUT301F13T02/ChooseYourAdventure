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
