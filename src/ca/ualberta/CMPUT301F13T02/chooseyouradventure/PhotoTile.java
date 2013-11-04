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
/**
 * A Photo tile for use in the stories
 */
public class PhotoTile extends Tile{

	private Object photo;
	private final String type = "photo";
	
	/**
	 * Get the type
	 * @return The type
	 */
	@Override
	String getType() {
		return type;
	}
	/**
	 * Check if two tiles are equal
	 * @param tile What to test equality with
	 * @return The equality truth
	 */
	@Override
	boolean equals(Tile tile) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void setContent(Object content) {
		photo = content;
	}
	
	public Object getPhoto() {
		return photo;
	}
    
}
