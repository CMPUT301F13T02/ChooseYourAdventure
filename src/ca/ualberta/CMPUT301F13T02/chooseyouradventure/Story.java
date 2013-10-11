package ca.ualberta.CMPUT301F13T02.chooseyouradventure;

import java.util.ArrayList;

public class Story {
	
	/** 
	 * @uml.property name="pages"
	 * @uml.associationEnd aggregation="composite" inverse="story:ca.ualberta.CMPUT301F13T02.chooseyouradventure.Page"
	 */
    private Page pages;
    private Reader reader;
    
    public Story() {
    	
    }
    
    public void addPage(Page newPage) {
    	
    }
    
    public void deletePage(Page aPage) {
    	
    }

	/**
	 * @uml.property  name="reader"
	 * @uml.associationEnd  aggregation="shared" inverse="story:ca.ualberta.CMPUT301F13T02.chooseyouradventure.Reader"
	 */
	private Reader reader1;

}
