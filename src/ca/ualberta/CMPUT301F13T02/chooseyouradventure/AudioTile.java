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

import android.util.Log;

/**
 * An audio Tile for use in stories. AudioTile is a concrete implementation of the abstract
 * class Tile.
 * 
 * AudioTile is a member of this application's model, it is included in the JSON when the story
 * is serialized and stored via the use of the handlers. An AudioTile will contain audio to be played
 * in the story.
 * 
 * TODO Currently this class is unused. The member "Object audio" will need to be replaced, likely
 *      with a URL to an embeddable audio clip on the internet.
 */

public class AudioTile extends Tile{

	private String audioURL;
	private final String type = "audio";
	
	/**
	 * Returns the type of this Tile
	 * 
	 * @return The type of this Tile
	 */

	public String getType() {
		return type;
	}
	
	/**
	 * Sets the content of this Tile to content
	 * 
	 * HTML for showing only the control bar of youtube video was taken from
	 * http://rcdewebmasters.wordpress.com/2012/04/19/embed-audio-only-youtube-video/
	 * 
	 * @param content The content to set for this Tile
	 */
	@Override
	public void setContent(Object content) {
		String beginningHtml = "<html><body><center><div style=\"position:relative;width:267px;height:0px;overflow:hidden;\">" +
    " <iframe width=\"300\" height=\"300\"" + 
      " src=\"";
		String endingHtml = "?rel=0&autohide=0\" frameborder=\"0\">" +
    " </iframe>" +
" </div>";
		
		String url = (String) content;
		
		if(content == null || !url.contains("youtube.com/watch?v=")) {
			audioURL = "<html><body><center><p>Sorry, we couldn't load that video</p></center></body></html>";
			return;
		} 
		
		Log.d("url to parse", url);
		String urlStart = url.substring(0, 7);
		String urlMiddle = url.substring(8, 21);
		String urlEnd = url.substring(29);
		
		String embed = "embed/";
		String www = "www";
		
		audioURL = beginningHtml + urlStart + www + urlMiddle + embed + urlEnd + endingHtml;
		Log.d("video url", audioURL);
	}
	
	/**
	 * Returns the audio of this Tile
	 * 
	 * @return The audio of this Tile
	 */
	@Override
	public Object getContent() {
		return audioURL;
	}

	/**
	 * This tests for equality with another tile
	 * @param The tile to check equality with
	 */
	@Override
	boolean equals(Tile segment) {
		// TODO Auto-generated method stub
		return false;
	}

}

