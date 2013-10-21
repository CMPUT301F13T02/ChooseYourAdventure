package ca.ualberta.CMPUT301F13T02.chooseyouradventuretest;

/*
 * Tests various functions of the ESHandler class
 */ 

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import ca.ualberta.CMPUT301F13T02.chooseyouradventure.Comment;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.Decision;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.HandlerException;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.Page;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.Story;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.TextTile;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.elasticsearch.ESHandler;

public class ESHandlerTest {

	/**
	 * Tests the addPage function
	 */
	@Test
	public void addPageTest() {

		Page page1 = new Page();
		page1.addTile(new TextTile("test1"));
		page1.addTile(new TextTile("test2"));
		
		try {
			//Add a page
			ESHandler esHandler = new ESHandler();
			esHandler.addPage(page1);
			
			//Retrieve same page
			Page page2 = esHandler.getPage(1);
			
			//Check they are the same
			assertTrue(page1.equals(page2));
		}
		catch (HandlerException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	/**
	 * Tests the updateStory function
	 */
	@Test
	public void updateStoryTest() {

		try {
			//Create  2 pages
			Page page1 = new Page();
			page1.addTile(new TextTile("test1"));
			page1.addTile(new TextTile("test2"));
			page1.addComment(new Comment("LOLz I CN HAZ CHEEZBUERGR?"));
			
			Page page2 = new Page();
			page2.addTile(new TextTile("test3"));
			page2.addTile(new TextTile("test4"));
			page2.addDecision(new Decision("Go second", page2));
			page2.addDecision(new Decision("Go first", page1));
			
			//Create story
			Story story1 = new Story();
			story1.setId("testUpdate");
			story1.addPage(page1);
			story1.addPage(page2);
			
			//Update story
			ESHandler esHandler = new ESHandler();
			esHandler.updateStory(story1);
			
		
			//Get same story
			Story story2 = esHandler.getStory("testUpdate");
		
			//Compare
			assertTrue(story1.equals(story2));
		}
		catch (HandlerException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	/**
	 * Tests adding a comment to a page
	 */
	@Test
	public void addCommentTest() {
		
		try {
			//Get a page
			ESHandler esHandler = new ESHandler();
			Story story1 = esHandler.getStory("testUpdate");
			
			//Add a comment to a page
			Comment comment = new Comment("TEST COMMENT");
			esHandler.addComment(story1, story1.getPages().get(0), comment);
			
			//Retrieve the story, get newest comment and compare for equality
			Story story2 = esHandler.getStory("testUpdate");
			ArrayList<Comment> comments =  story2.getPages().get(0).getComments();
			assertTrue(comments.get(comments.size() - 1).equals(comment));
		}
		catch(HandlerException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	/**
	 * Tests the updateStory function
	 */
	@Test
	public void addStoryTest() {

		try {
			//Create 2 pages
			Page page1 = new Page();
			page1.addTile(new TextTile("test1"));
			page1.addTile(new TextTile("test2"));

			Page page2 = new Page();
			page2.addTile(new TextTile("test3"));
			page2.addTile(new TextTile("test4"));

			//Create story
			Story story1 = new Story();
			story1.addPage(page1);
			story1.addPage(page2);

			//Add story
			ESHandler esHandler = new ESHandler();
			esHandler.addStory(story1);

			//Get same story
			Story story2 = esHandler.getStory(story1.getId());

			//Compare
			assertTrue(story1.equals(story2));
		}
		catch (HandlerException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	/**
	 * Test the getAllStories method
	 */
	@Test
	public void getAllStoriesTest() {
		ESHandler handler = new ESHandler();
		try {
			handler.getAllStories();
		}
		catch (HandlerException e) {
			e.printStackTrace();
			fail();
		}
	}
}
