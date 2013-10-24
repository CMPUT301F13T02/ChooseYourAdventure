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

/*
 * A page represents a physical page of a story. 
 */

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import android.os.Parcel;
import android.os.Parcelable;

public class Page implements Parcelable {
	
	public UUID id;
	/**
	 * @uml.property  name="comments"
	 * @uml.associationEnd  multiplicity="(0 -1)" ordering="true" aggregation="composite" inverse="pageCommented:ca.ualberta.CMPUT301F13T02.chooseyouradventure.Comment"
	 */
	private ArrayList<Comment> comments = new ArrayList<Comment>();
	/**
	 * @uml.property   name="segments"
	 * @uml.associationEnd   multiplicity="(0 -1)" ordering="true" aggregation="composite" inverse="page:ca.ualberta.CMPUT301F13T02.chooseyouradventure.Tile"
	 */
	private ArrayList<Tile> tiles = new ArrayList<Tile>();
	/**
	 * @uml.property  name="story"
	 * @uml.associationEnd  inverse="pages:ca.ualberta.CMPUT301F13T02.chooseyouradventure.Story"
	 */
	private ArrayList<Decision> decisions = new ArrayList<Decision>();
	
	private Gson gson = new GsonBuilder().registerTypeAdapter(Tile.class, new TileGsonMarshal()).create();
    public static final Parcelable.Creator<Story> CREATOR = new Creator<Story>() {

		
	    public Story createFromParcel(Parcel source) {
	        return new Story(source);
	    }
  
	    public Story[] newArray(int size) {
	        return new Story[size];
	    }
	};
	
	public Page(Parcel parcel) {
		String tempId = parcel.readString();
		id = UUID.fromString(tempId);
		
		String commentRetrieve = parcel.readString();
		String tileRetrieve = parcel.readString();
		String decisionRetrieve = parcel.readString();
		
		Type responseType = new TypeToken<Story>(){}.getType();
		
		comments = gson.fromJson(commentRetrieve, responseType);
		tiles = gson.fromJson(tileRetrieve, responseType);
		decisions = gson.fromJson(decisionRetrieve, responseType);
		
		
		
	}
	
	
	
	
	
	
	
	public Page() {
		id = UUID.randomUUID();
		tiles = new ArrayList<Tile>();
		decisions = new ArrayList<Decision>();
		comments = new ArrayList<Comment>();
	}
	
	public void addTile(Tile tile) {
		tiles.add(tile);
	}
	
	public void addDecision(Decision decision) {
		decisions.add(decision);
	}
	
	public void deleteSegment(Tile tile) {
		
	}
	
	public void addComment(Comment comment) {
		comments.add(comment);
	}
	/**
	 * Compares this page for deep equality with another page
	 */
	public boolean equals(Page page) {

		//Fail if different number of comments of segments
		if (comments.size() != page.getComments().size() ||
			tiles.size() != page.getTiles().size())
			return false;

		//Check that all comments are the same
		for (int i = 0; i < comments.size(); i++) {
			if (!comments.get(i).equals(page.getComments().get(i))) 
				return false;
		}

		//Check that all segments are the same
		for (int i = 0; i < tiles.size(); i++) {
			if (!tiles.get(i).equals(page.getTiles().get(i))) 
				return false;
		}
		
		//Check that the id's are the same
		if (!id.equals(page.id))
			return false;
		
		return true;
	}
	
	public String toString() {
		return "" + id + comments + tiles;
	}
	
	public ArrayList<Comment> getComments() {
		return comments;
	}
	
	public ArrayList<Tile> getTiles() {
		return tiles;
	}

	public ArrayList<Decision> getDecisions() {
		return decisions;
	}
	
	public UUID getId() {
		return id;
	}
	
	public String getIdString(){
		return id.toString();
	}
	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		String commentsFormatted = gson.toJson(comments);
		String tilesFormatted = gson.toJson(tiles);
		String decisionsFormatted = gson.toJson(decisions);
		
		parcel.writeString(id.toString());
		parcel.writeString(commentsFormatted);
		parcel.writeString(tilesFormatted);
		parcel.writeString(decisionsFormatted);
		
		
		
	}


	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
}
