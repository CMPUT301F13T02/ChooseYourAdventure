package ca.ualberta.CMPUT301F13T02.chooseyouradventure;

/*
 * A page represents a physical page of a story. 
 */

import java.util.ArrayList;
import java.util.Collection;

public class Page {
	
	public int id;
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
	private Story story;
	
	public Page() {
	}
	
	public void addTile(Tile tile) {
		tiles.add(tile);
	}
	
	public void deleteSegment(Tile tile) {
		
	}
	
	public void addComment(Comment comment) {
	
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
		if (id != page.id)
			return false;
		
		return true;
	}
	
	public String toString() {
		return "" + id + comments + tiles;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public ArrayList<Comment> getComments() {
		return comments;
	}
	
	public ArrayList<Tile> getTiles() {
		return tiles;
	}
}
