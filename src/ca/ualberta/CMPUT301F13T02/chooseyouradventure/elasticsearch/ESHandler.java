package ca.ualberta.CMPUT301F13T02.chooseyouradventure.elasticsearch;

/*
 * Handles all interactions with the Elastic Search web DB. 
 */

/* The file with inspiration from https://github.com/rayzhangcl/ESDemo */

import java.io.IOException;
import java.lang.reflect.Type;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import ca.ualberta.CMPUT301F13T02.chooseyouradventure.Handler;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.Page;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.Story;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.Tile;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.TileGsonMarshal;

public class ESHandler implements Handler{

	public static final String serviceURL = "http://cmput301.softwareprocess.es:8080/cmput301f13t02/";
	public static final HttpClient client = new DefaultHttpClient();
	private Gson gson = new GsonBuilder().registerTypeAdapter(Tile.class, new TileGsonMarshal()).create();
	
	@Override
	public void updateStory(Story story) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteStory(Story story) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addStory(Story story) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updatePage(Page page) {

	}

	/**
	 * Adds the passed page to the system
	 */
	@Override
	public void addPage(Page page) {
		ESHandler es = new ESHandler();
		Story story = new Story();
		es.updateStory(story);
		
		ESHttpPost post = new ESHttpPost("page/1");

		try {
			post.post(gson.toJson(page));
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Retrieves the page at passed id
	 */
	@Override
	public Page getPage(int id) {
		ESHttpGet get = new ESHttpGet("page/" + id);
		
		String response = null;
		try {
			response = get.get();
		}
		catch (IllegalStateException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		Type responseType = new TypeToken<ESResponse<Page>>(){}.getType();
		ESResponse<Page> esResponse = gson.fromJson(response, responseType);
		
		return esResponse.getSource();
	}
    
}
