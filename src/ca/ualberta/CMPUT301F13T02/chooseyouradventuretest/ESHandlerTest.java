package ca.ualberta.CMPUT301F13T02.chooseyouradventuretest;

/*
 * Tests various functions of the ESHandler class
 */ 

import static org.junit.Assert.*;

import org.junit.Test;

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
		page1.setId(1);
		page1.addTile(new TextTile("test1"));
		page1.addTile(new TextTile("test2"));
		
		//Add a page
		ESHandler esHandler = new ESHandler();
		esHandler.addPage(page1);
		
		//Retrieve same page
		Page page2 = esHandler.getPage(1);

		//Check they are the same
		assertTrue(page1.equals(page2));
	}

	/**
	 * Tests the updateStory function
	 */
	@Test
	public void updateStoryTest() {

		//Create  2 pages
		Page page1 = new Page();
		page1.setId(1);
		page1.addTile(new TextTile("test1"));
		page1.addTile(new TextTile("test2"));
		
		Page page2 = new Page();
		page2.setId(2);
		page2.addTile(new TextTile("test3"));
		page2.addTile(new TextTile("test4"));
		
		//Create story
		Story story1 = new Story();
		story1.setId("test");
		story1.addPage(page1);
		story1.addPage(page2);
		
		//Update story
		ESHandler esHandler = new ESHandler();
		esHandler.updateStory(story1);
		
		//Get same story
		Story story2 = esHandler.getStory("test");
		
		//Compare
		assertTrue(story1.equals(story2));
	}
	
	/**
	 * Tests the updateStory function
	 */
	@Test
	public void addStoryTest() {

		//Create 2 pages
		Page page1 = new Page();
		page1.setId(1);
		page1.addTile(new TextTile("test1"));
		page1.addTile(new TextTile("test2"));
		
		Page page2 = new Page();
		page2.setId(2);
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
}
