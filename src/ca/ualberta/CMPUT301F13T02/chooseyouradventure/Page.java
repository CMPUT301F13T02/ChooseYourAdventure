package ca.ualberta.CMPUT301F13T02.chooseyouradventure;

import java.util.ArrayList;
import java.util.Collection;

public class Page {
	/**
	 * @uml.property  name="comments"
	 * @uml.associationEnd  multiplicity="(0 -1)" ordering="true" aggregation="composite" inverse="pageCommented:ca.ualberta.CMPUT301F13T02.chooseyouradventure.Comment"
	 */
	private ArrayList<Comment> comments;
	/**
	 * @uml.property   name="segments"
	 * @uml.associationEnd   multiplicity="(0 -1)" ordering="true" aggregation="composite" inverse="page:ca.ualberta.CMPUT301F13T02.chooseyouradventure.Tile"
	 */
	private ArrayList<Tile> tiles;
	/**
	 * @uml.property  name="story"
	 * @uml.associationEnd  inverse="pages:ca.ualberta.CMPUT301F13T02.chooseyouradventure.Story"
	 */
	private Story story;
	
	public Page() {
		
	}
	
	public void addSegment(Tile newSegment) {
		
	}
	
	public void deleteSegment(Tile aSegment) {
		
	}
	
	public void addComment(Comment newComment) {
		
	}
	
}
