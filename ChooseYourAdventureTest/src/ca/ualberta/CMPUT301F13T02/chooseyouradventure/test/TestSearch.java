package ca.ualberta.CMPUT301F13T02.chooseyouradventure.test;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import ca.ualberta.CMPUT301F13T02.chooseyouradventure.HandlerException;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.Story;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.elasticsearch.ESHandler;
import android.test.InstrumentationTestCase;
import android.util.Log;

public class TestSearch extends InstrumentationTestCase {
	
	private ESHandler esHandler = new ESHandler();
	
	public void testSearch() throws HandlerException, UnsupportedEncodingException {
		String title = "Search Story";
		Story story = new Story(title);
		
		esHandler.addStory(story);
		String searchKey = "Search Story";
		ArrayList<Story> stories = esHandler.search(searchKey);
		assertNotNull(stories);
		int size = stories.size();
		assertTrue(size > 0);
		
		
		Log.d("Num returned", String.valueOf(size));
		/*
		for (Story s : stories) {
			System.out.println(s.getTitle());
		}
		
		searchKey = "st";
		stories = esHandler.search(searchKey);
		assertNotNull(stories);
		size = stories.size();
		assertTrue(size > 0);
		
		Log.d("Num returned", String.valueOf(size));
		for (Story s : stories) {
			System.out.println(s.getTitle());
		}
		*/
		
		
	}
	

}
