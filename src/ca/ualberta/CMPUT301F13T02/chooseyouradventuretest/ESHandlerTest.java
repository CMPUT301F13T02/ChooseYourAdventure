package ca.ualberta.CMPUT301F13T02.chooseyouradventuretest;

/*
 * Tests various functions of the ESHandler class
 */ 

import static org.junit.Assert.*;

import org.junit.Test;

import ca.ualberta.CMPUT301F13T02.chooseyouradventure.Page;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.TextSegment;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.elasticsearch.ESHandler;

public class ESHandlerTest {

	/**
	 * Tests the addPage function
	 */
	@Test
	public void addPageTest() {

		Page page1 = new Page();
		page1.setId(1);
		page1.addSegment(new TextSegment("test1"));
		page1.addSegment(new TextSegment("test2"));
		
		//Add a page
		ESHandler esHandler = new ESHandler();
		esHandler.addPage(page1);
		
		//Retrieve same page
		Page page2 = esHandler.getPage(1);

		//Check they are the same
		assertTrue(page1.equals(page2));
	}

}
