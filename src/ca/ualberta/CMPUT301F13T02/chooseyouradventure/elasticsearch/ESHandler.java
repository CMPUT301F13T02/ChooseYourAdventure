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

package ca.ualberta.CMPUT301F13T02.chooseyouradventure.elasticsearch;

/* The file with inspiration from https://github.com/rayzhangcl/ESDemo */

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import org.apache.http.client.HttpClient;
import org.apache.http.entity.StringEntity;
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

/**
 * This class implements the Handler interface which specifies a contract for a class that will
 * store and retrieve Story objects from some sort of storage.
 * 
 * The ESHandler will use Elastic Search to store stories online so that users may interact
 * with one-another.
 */

public class ESHandler implements Handler{

	public static final String serviceURL = "http://cmput301.softwareprocess.es:8080/cmput301f13t02/";
	public static final HttpClient client = new DefaultHttpClient();
	private Gson gson = new GsonBuilder().registerTypeAdapter(Tile.class, new TileGsonMarshal()).create();
	
	protected String getStoryPath() {
		return "story/";
	}
	/**
	 * Updates passed story
	 * 
	 * @param story The story to update
	 * @throws HandlerException 
	 */
	@Override
	public void updateStory(Story story) throws HandlerException {
		System.err.println(getStoryPath());
		ESHttpPost post = new ESHttpPost(getStoryPath() + story.getId());

		try {
			post.execute(gson.toJson(story));
		} 
		catch (IOException e) {
			e.printStackTrace();
		}	
	}

	/**
	 * Deletes the passed story in the DB
	 * 
	 * @param story The story to delete
	 * @throws HandlerException 
	 */
	@Override
	public void deleteStory(Story story) throws HandlerException {
		ESHttpDelete delete = new ESHttpDelete(getStoryPath() + story.getId());

		try {
			delete.execute();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}	
	}
	

	/**
	 * Adds the passed story, sets its ID
	 * @throws HandlerException 
	 * @param story The story to add
	 */
	@Override
	public void addStory(Story story) throws HandlerException {
		ESHttpPost post = new ESHttpPost(getStoryPath());

		String response = null;
		try {
			response = post.execute(gson.toJson(story));
		} 
		catch (IOException e) {
			e.printStackTrace();
		}	
		
		//Retrieve new ID
		Type responseType = new TypeToken<ESResponse<Story>>(){}.getType();
		ESResponse<Story> esResponse = gson.fromJson(response, responseType);
		
		//Set ID
		story.setId(esResponse.getId());
		updateStory(story);
	}
	
	/**
	 * Retrieves the story with passed ID
	 * @throws HandlerException
	 * @param id The ID of the story to retrieve
	 * @return The story with the passed ID 
	 */
	@Override
	public Story getStory(String id) throws HandlerException {
		ESHttpGet get = new ESHttpGet(getStoryPath() + id);
		
		String response = null;
		try {
			response = get.execute();
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

	/**
	 * Updates the passed page of passed story by adding passed comment
	 * @param story The story the page is in
	 * @param page The page the comment is in
	 * @param comment The comment to add
	 * @throws HandlerException 
	 */
	@Override
	public void addComment(Story story, Page page, Comment comment) throws HandlerException {
		ESHttpPost post = new ESHttpPost(getStoryPath() + story.getId() + "/_update");

		try {
			post.execute(
			"{" +
			    "\"script\" : \"foreach (page : ctx._source.pages) { " +
				                   "if (page.id == id) { " +
					                   "page.comments.add(comment)" +
					                "}" +
						  		"}\"," +
			    "\"params\" : {" +
							  	"\"id\": \"" + page.getId() + "\"," + 
						      	"\"comment\": " + gson.toJson(comment) +
							 "}" +
			"}");
		} 
		
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
    /**
     * Get all stories
     * 
     * @return An ArrayList containing all stories in the DB
     */
	@Override
    public ArrayList<Story> getAllStories() throws HandlerException {
		ESHttpGet get = new ESHttpGet(getStoryPath() + "_search");

		String response = null;
		try {
			response = get.execute();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		ArrayList<Story> stories = new ArrayList<Story>();

		/* For each hit, add it to the list */
		Type esSearchResponseType = new TypeToken<ESSearchResponse<Story>>(){}.getType();
		ESSearchResponse<Story> esResponse = gson.fromJson(response, esSearchResponseType);
		for (ESResponse<Story> s : esResponse.getHits()) {
			stories.add(s.getSource());
		}
		
		return stories;
	}
	
	/**
	 * 
	 * @param searchKey
	 * @return An ArrayList containing the stories whose titles match or are similar to the search key
	 * @throws HandlerException
	 * @throws UnsupportedEncodingException
	 */
	public ArrayList<Story> search(String searchKey) throws HandlerException, UnsupportedEncodingException {
		/*
		 * Will want a query involving the searchKey.
		 * Pass query to elastic search.
		 * See https://github.com/rayzhangcl/ESDemo/blob/master/ESDemo/src/ca/ualberta/cs/CMPUT301/chenlei/ESClient.java
		 */
	
		ESHttpGet get = new ESHttpGet(getStoryPath() + "_search");
		String query = "{\"query\" : {\"query_string\" : {\"default_field\" : \"title\",\"query\" : \"" + searchKey + "*" + "\"}}}";
		StringEntity stringentity = new StringEntity(query);
		
		get.setHeader("Accept", "application/json");
		get.setEntity(stringentity);
		
		String response = null;
		try {
			response = get.execute();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		ArrayList<Story> stories = new ArrayList<Story>();
		
		/* For each hit, add it to the list */
		Type esSearchResponseType = new TypeToken<ESSearchResponse<Story>>(){}.getType();
		ESSearchResponse<Story> esResponse = gson.fromJson(response, esSearchResponseType);
		for (ESResponse<Story> s : esResponse.getHits()) {
			stories.add(s.getSource());
		}
		
		return stories;
		
	}
}
