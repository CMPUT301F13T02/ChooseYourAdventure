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

import android.view.View;

/**
 * An abstract tile class that will be implemented to provide text, audio, video, and picture tiles.
 * 
 * A tile is the fundemental unit of a page; they are aggregated by Page and displayed vertically in
 * ViewPageActivity.  
 * 
 * Tiles are part of the model of the application and are serialized and stored with a Story by
 * a Handler implementation
 */

public abstract class Tile {

	/**
	 * Gets the View that will be used to visualize this tile while editing
	 * 
	 * @return A View object to visualize this tile while editing
	 */
	public View getEditView() {
		return null;
	}
	
	/**
	 * Gets the View that will be used to visualize this tile while reading
	 * 
	 * @return A View object to visualize this tile while reading
	 */
	public View getReadView() {
		return null;	
	}
	
	public abstract String getType();
	public abstract void setContent(Object content);
}
