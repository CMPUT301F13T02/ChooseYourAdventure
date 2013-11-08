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

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.entity.StringEntity;

import ca.ualberta.CMPUT301F13T02.chooseyouradventure.HandlerException;

/**
 * Wraps all HTTP Post requests to the Elastic Search service
 */

public class ESHttpPost extends ESHttpRequest {

	/**
	 * Create an Elastic Search post
	 * 
	 * @param url The URL of the request
	 */
	public ESHttpPost(String url) {
		super(url);
	}
	
	/**
	 * Post data to ES
	 * @throws IOException
	 * @throws HandlerException 
	 * @param data The data to post
	 * @return A String representation of Elastic Search's response
	 */
	public String execute(String data) throws IOException, HandlerException {

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
		
		return super.execute();
	}

	/**
	 * Returns the type of request this is
	 */
	@Override
	public String getMethod() {
		return "POST";
	}
}
