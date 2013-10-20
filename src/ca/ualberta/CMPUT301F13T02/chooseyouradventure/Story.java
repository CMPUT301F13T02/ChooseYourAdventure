package ca.ualberta.CMPUT301F13T02.chooseyouradventure;


import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class Story implements Parcelable{
	
	/** 
	 * @uml.property name="pages"
	 * @uml.associationEnd aggregation="composite" inverse="story:ca.ualberta.CMPUT301F13T02.chooseyouradventure.Page"
	 */
    private ArrayList<Page> pages = new ArrayList<Page>();
    private Reader reader;
    private String id;
    private String title;
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

	/**
	 * @uml.property  name="reader"
	 * @uml.associationEnd  aggregation="shared" inverse="story:ca.ualberta.CMPUT301F13T02.chooseyouradventure.Reader"
	 */
	private Reader reader1;
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

}
