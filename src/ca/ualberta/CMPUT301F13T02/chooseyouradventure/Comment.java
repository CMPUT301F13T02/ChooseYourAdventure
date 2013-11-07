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

package ca.ualberta.CMPUT301F13T02.chooseyouradventure;

import java.util.Calendar;

/**
 * This class represents Comments that readers add to a story. It is part of the 
 * model of the application.
 * 
 * Comments are left by users on pages and are therefore aggregated by the Page
 * class. Comments are serialized with the rest of the story into JSON to be 
 * stored by a handler. 
 */
public class Comment {

	private String poster;
	private String text;
	private String timestamp;

	/**
	 * This is a constructor for a comment with no user
	 * @param text The comment
	 */
	public Comment(String text) {
		this.text = text;
		
		Calendar calendar = Calendar.getInstance(); 
		String dayField = "" + calendar.get(Calendar.DAY_OF_MONTH) + "\\" + calendar.get(Calendar.MONTH) + "\\" + calendar.get(Calendar.YEAR);
		this.setTimestamp(dayField);
		
	}

	/**
	 * This is a constructor for a comment with a user
	 * @param text The comment
	 * @param poster Who left the comment
	 */
	public Comment(String text, String poster) {
		this.text = text;
		this.poster = poster;
		
		
		Calendar calendar = Calendar.getInstance(); 
		String dayField = "" + calendar.get(Calendar.DAY_OF_MONTH) + "\\" + calendar.get(Calendar.MONTH) + "\\" + calendar.get(Calendar.YEAR);
		this.setTimestamp(dayField);
		
	}
	/**
	 * 
	 * @return The text from the comment
	 */
	public String getText() {
		return text;
	}
	/**
	 * @return Who posted the comment
	 */
	public String getPoster() {
		return poster;
	}
	
	/**
	 * Compares the equality of this Comment and the passed Comment
	 * 
	 * @param comment The comment to compare against
	 * @return Whether or not the two comments are considered equal
	 */
	public boolean equals(Comment comment) {
		return text.equals(comment.getText()) &&
				poster.equals(comment.getPoster());
	}
	/**
	 * @return the timestamp
	 */
	public String getTimestamp()
	{

		return timestamp;
	}
	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(String timestamp)
	{

		this.timestamp = timestamp;
	}
	
}
