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
	 */
	@Override
	public void updateStory(Story story) {
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
	 */
	@Override
	public void addStory(Story story) {
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
	 */
	@Override
	public Story getStory(String id) {
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
	 */
	@Override
	public void addPage(Page page) {
		
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
	/**
	 * Updates the passed page of passed story by adding passed comment
	 */
	@Override
	public void addComment(Story story, Page page, Comment comment) {
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
