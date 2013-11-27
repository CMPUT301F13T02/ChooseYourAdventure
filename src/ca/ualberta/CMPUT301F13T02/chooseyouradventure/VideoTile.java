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
 * A video Tile for use in stories. VideoTile is a concrete implementation of the abstract
 * class Tile.
 * 
 * VideoTile is a member of this application's model, it is included in the JSON when the story
 * is serialized and stored via the use of the handlers. A VideoTile will contain a video to be displayed
 * in the story.
 * 
 * TODO This class is mostly unimplemented. The "Object video" member is a place holder.
 * TODO This class will probably contain the URL of an embeddable video on the Internet
 */

public class VideoTile extends Tile{

	private String videoURL;
	private final String type = "video";
	
	public String getType() {
		return type;
	}

	@Override
	public void setContent(Object content) {
		
		String beginningHtml = "<html><body><center><iframe src=\"http://www.youtube.com/embed/";
		String endingHtml = "\" frameborder=\"0\"/></center></body></html>";
		
		String url = (String) content;
		
		if (content == null || (!url.contains("?v=") && !url.contains("&v="))) {
			videoURL = "<html><body><center><p>Sorry, we couldn't load that video</p></center></body></html>";
			return;
		}
		
		Log.d("url to parse", url);
		
		String pattern = ".*[&?]v=([0-9A-Za-z\\-]+).*";
		String updated = url.replaceAll(pattern, "$1");
		
		videoURL = beginningHtml +  updated + endingHtml;
		Log.d("video url", videoURL);
		Log.d("video url", url);
	}

	public Object getContent() {
		return videoURL;
	}

	@Override
	boolean equals(Tile tile) {
		if (!(tile instanceof VideoTile))
			return false;

		return true;
	}

}
