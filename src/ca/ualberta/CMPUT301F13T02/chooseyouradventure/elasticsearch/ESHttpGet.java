package ca.ualberta.CMPUT301F13T02.chooseyouradventure.elasticsearch;

/*
 * Wraps all HTTP get requests from Elastic Search
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;

import ca.ualberta.CMPUT301F13T02.chooseyouradventure.HandlerException;

public class ESHttpGet extends HttpGet {

	/**
	 * Create an Elastic Search get
	 */
	public ESHttpGet(String url) {
		super(ESHandler.serviceURL + url);
		addHeader("Accept", "application/json");
	}
	
	/**
	 * Get data from ES
	 * @throws IllegalStateException
	 * @throws IOException
	 * @throws HandlerException 
	 */
	public String get() throws IllegalStateException, IOException, HandlerException {
		
		/* This method with inspiration from https://github.com/rayzhangcl/ESDemo */
		
		HttpResponse response = null;

		try {
			response = ESHandler.client.execute(this);
		} 
		catch (ClientProtocolException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		String status = response.getStatusLine().toString();
		System.out.println(status);
		
		if (response.getStatusLine().getStatusCode() != 200)
			throw new HandlerException("ESHttpGet " + getURI() + " returned " + status);

		HttpEntity entity = response.getEntity();
		BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent()));

		//Build string from response body
		String output;
		StringBuilder sb = new StringBuilder();
		while ((output = br.readLine()) != null) {
			sb.append(output);
		}
		
		System.out.println(sb.toString());

		//Close the connection
		try {
			entity.consumeContent();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return sb.toString();
	}
}
