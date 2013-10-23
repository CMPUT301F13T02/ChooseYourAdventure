package ca.ualberta.CMPUT301F13T02.chooseyouradventure;

import java.util.ArrayList;

public interface Handler {

	public void updateStory(Story story) throws HandlerException;
	public void deleteStory(Story story);
	public void addStory(Story story) throws HandlerException;
	public void updatePage(Page page);
	public void addComment(Story story, Page page, Comment comment) throws HandlerException;
	public void addPage(Page page) throws HandlerException;
	public Page getPage(int id) throws HandlerException;
	public Story getStory(String id) throws HandlerException;
	public ArrayList<Story> getAllStories() throws HandlerException;
}
