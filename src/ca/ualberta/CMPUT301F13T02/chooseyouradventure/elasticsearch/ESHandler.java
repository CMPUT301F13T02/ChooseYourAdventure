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

import ca.ualberta.CMPUT301F13T02.chooseyouradventure.Comment;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.Handler;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.HandlerException;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.Page;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.Story;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.Tile;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.TileGsonMarshal;

public class ESHandler implements Handler{

	public static final String serviceURL = "http://cmput301.softwareprocess.es:8080/cmput301f13t02/";
	public static final HttpClient client = new DefaultHttpClient();
	private Gson gson = new GsonBuilder().registerTypeAdapter(Tile.class, new TileGsonMarshal()).create();
	
	/**
	 * Updates passed story
	 * @throws HandlerException 
	 */
	@Override
	public void updateStory(Story story) throws HandlerException {
		ESHttpPost post = new ESHttpPost("story/" + story.getId());

		try {
			post.post(gson.toJson(story));
		} 
		catch (IOException e) {
			e.printStackTrace();
		}	
	}

	@Override
	public void deleteStory(Story story) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Adds the passed story, sets its ID
	 * @throws HandlerException 
	 */
	@Override
	public void addStory(Story story) throws HandlerException {
		ESHttpPost post = new ESHttpPost("story/");

		String response = null;
		try {
			response = post.post(gson.toJson(story));
		} 
		catch (IOException e) {
			e.printStackTrace();
		}	
		
		//Retrieve new ID
		Type responseType = new TypeToken<ESResponse<Story>>(){}.getType();
		ESResponse<Story> esResponse = gson.fromJson(response, responseType);
		
		//Set ID
		story.setId(esResponse.getId());
	}
	
	/**
	 * Retrieves the story with passed ID
	 * @throws HandlerException 
	 */
	@Override
	public Story getStory(String id) throws HandlerException {
		ESHttpGet get = new ESHttpGet("story/" + id);
		
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
		
		Type responseType = new TypeToken<ESResponse<Story>>(){}.getType();
		ESResponse<Story> esResponse = gson.fromJson(response, responseType);
		
		return esResponse.getSource();	
	}

	@Override
	public void updatePage(Page page) {

	}

	/**
	 * Adds the passed page to the system
	 * @throws HandlerException 
	 */
	@Override
	public void addPage(Page page) throws HandlerException {
		
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
	 * @throws HandlerException 
	 */
	@Override
	public Page getPage(int id) throws HandlerException {
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
	/**
	 * Updates the passed page of passed story by adding passed comment
	 * @throws HandlerException 
	 */
	@Override
	public void addComment(Story story, Page page, Comment comment) throws HandlerException {
		ESHttpPost post = new ESHttpPost("story/" + story.getId() + "/_update");

		try {
			post.post(
			"{" +
			    "\"script\" : \"foreach (page : ctx._source.pages) { " +
				                   "if (page.id == id) { " +
					                   "page.comments.add(comment)" +
					                "}" +
						  		"}\"," +
			    "\"params\" : {" +
							  	"\"id\": \"" + page.getId() + "\"," + 
						      	"\"comment\": {" +
						      		"\"text\": \"" + comment.getText() + "\"" +
						    	"}" +	
							 "}" +
			"}");
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
