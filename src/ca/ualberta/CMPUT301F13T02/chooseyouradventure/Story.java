package ca.ualberta.CMPUT301F13T02.chooseyouradventure;

import java.util.ArrayList;

public class Story {
	
	/**
	 * @uml.property name="pages"
	 * @uml.associationEnd multiplicity="(0-1)" ordering="true" aggregation="composite" inverse="story:ca.ualberta.CMPUT301F13T02.chooseyouradventure.Page"
	 */
    private ArrayList<Page> pages;
    private Reader reader;
    
    public Story() {
    	
    }
    
    public void addPage(Page newPage) {
    	
    }
    
    public void deletePage(Page aPage) {
    	
    }

}
