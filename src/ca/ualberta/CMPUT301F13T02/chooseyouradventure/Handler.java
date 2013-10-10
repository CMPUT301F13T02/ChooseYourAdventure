package ca.ualberta.CMPUT301F13T02.chooseyouradventure;

import java.util.ArrayList;
import java.util.Collection;

public class Handler {

	private ArrayList<Story> stories;
	
	/**
	 * uml.property name="stories"
	 * uml.associationEnd inverse="story:
	 */
	
	public Handler() {
		
	}
	
	public void updateStory(Story aStory) {
		
	}
	
	public void deleteStory(Story aStory) {
		
	}
	
	public void addStory(Story newStory) {
		
	}
	
	public void updatePage(Page aPage) {
		
	}

	/**
	 * @uml.property  name="stories"
	 * @uml.associationEnd  multiplicity="(0 -1)" ordering="true" aggregation="shared" inverse="handler:ca.ualberta.CMPUT301F13T02.chooseyouradventure.Story"
	 */
	private ArrayList stories1;

}
