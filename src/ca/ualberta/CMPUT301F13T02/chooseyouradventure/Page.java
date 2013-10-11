package ca.ualberta.CMPUT301F13T02.chooseyouradventure;

/*
 * A page represents a physical page of a story. 
 */

import java.util.ArrayList;
import java.util.Collection;

public class Page {
	
	public int id;
	public ArrayList<Comment> comments = new ArrayList<Comment>();
	public ArrayList<Segment> segments = new ArrayList<Segment>();
	
	public Page() {
	}
	
	public void addSegment(Segment segment) {
		segments.add(segment);
	}
	
	public void deleteSegment(Segment segment) {
		
	}
	
	public void addComment(Comment comment) {
		
	}
	
	/**
	 * Compares this page for deep equality with another page
	 */
	public boolean equals(Page page) {

		//Fail if different number of comments of segments
		if (comments.size() != page.comments.size() ||
			segments.size() != page.segments.size())
			return false;

		//Check that all comments are the same
		for (int i = 0; i < comments.size(); i++) {
			if (!comments.get(i).equals(page.comments.get(i))) 
				return false;
		}

		//Check that all segments are the same
		for (int i = 0; i < segments.size(); i++) {
			if (!segments.get(i).equals(page.segments.get(i))) 
				return false;
		}
		
		//Check that the id's are the same
		if (id != page.id)
			return false;
		
		return true;
	}
	
	public String toString() {
		return "" + id + comments + segments;
	}
	
	public void setId(int id) {
		this.id = id;
	}
}
