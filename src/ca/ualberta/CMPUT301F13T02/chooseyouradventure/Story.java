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


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import android.os.Parcel;
import android.os.Parcelable;

public class Story implements Parcelable{
	
	/** 
	 * @uml.property name="pages"
	 * @uml.associationEnd aggregation="composite" inverse="story:ca.ualberta.CMPUT301F13T02.chooseyouradventure.Page"
	 */
    private ArrayList<Page> pages = new ArrayList<Page>();
    private String id;
    private String title;
    private UUID firstpage;
    
    private Gson gson = new GsonBuilder().registerTypeAdapter(Tile.class, new TileGsonMarshal()).create();
    public static final Parcelable.Creator<Story> CREATOR = new Creator<Story>() {

		
	    public Story createFromParcel(Parcel source) {
	        return new Story(source);
	    }
  
	    public Story[] newArray(int size) {
	        return new Story[size];
	    }
	};
	
	public Story(Parcel parcel) {
		String pageRetrieve = parcel.readString();
		Type responseType = new TypeToken<Story>(){}.getType();
		pages = gson.fromJson(pageRetrieve, responseType);
		id = parcel.readString();
		title = parcel.readString();
		String tempfirstpage = parcel.readString();
		firstpage = UUID.fromString(tempfirstpage);
	}
    
  
    /**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	public Story() {
    	
    }
    
    public ArrayList<Page> getPages() {
    	return pages;
    }
    
    public void addPage(Page newPage) {
    	pages.add(newPage);
    }
    
    public void deletePage(Page aPage) {
    	
    }
    
    public void setId(String id) {
    	this.id = id;
    }
    
    public String getId() {
    	return id;
    }
    
	/**
	 * Compares this story for deep equality with another story
	 */
	public boolean equals(Story story) {

		if (pages.size() != story.getPages().size())
			return false;

		//Check that all comments are the same
		for (int i = 0; i < pages.size(); i++) {
			if (!pages.get(i).equals(story.getPages().get(i))) 
				return false;
		}
		
		return true;
	}

	public UUID getFirstpage() {
		return firstpage;
	}

	public void setFirstpage(UUID firstpage) {
		this.firstpage = firstpage;
	}
	
	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		String pagesFormatted = gson.toJson(pages);
		parcel.writeString(pagesFormatted);
		parcel.writeString(id);
		parcel.writeString(title);
		parcel.writeString(firstpage.toString());
		
		
	}


	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
}
