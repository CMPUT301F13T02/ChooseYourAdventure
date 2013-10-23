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
