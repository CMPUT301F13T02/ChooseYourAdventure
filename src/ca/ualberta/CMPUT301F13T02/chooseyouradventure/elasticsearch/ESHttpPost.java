package ca.ualberta.CMPUT301F13T02.chooseyouradventure.elasticsearch;

/*
 * Wraps all HTTP Post request to the Elastic Search service
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

public class ESHttpPost extends HttpPost {

	/**
	 * Create Elastic Search post
	 */
	public ESHttpPost(String url) {
		super(ESHandler.serviceURL + url);
		setHeader("Accept", "application/json");
	}
	
	/**
	 * Post data to ES
	 * @throws IOException
	 */
	public String post(String data) throws IOException {

		/* This method with inspiration from https://github.com/rayzhangcl/ESDemo */
		
		//Set-up passed string to be posted
		StringEntity stringEntity = null;
		try {
			stringEntity = new StringEntity(data);
		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		setEntity(stringEntity);

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
		
		HttpEntity entity = response.getEntity();
		BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent()));

		//Build string from response body
		String output;
		StringBuilder sb = new StringBuilder();
		while ((output = br.readLine()) != null) {
			sb.append(output);
		}
		
		System.out.println(sb.toString());

		//Close connection
		try {
			entity.consumeContent();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return sb.toString();
	}
}
