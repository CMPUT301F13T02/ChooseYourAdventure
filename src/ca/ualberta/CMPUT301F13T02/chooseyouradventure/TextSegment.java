package ca.ualberta.CMPUT301F13T02.chooseyouradventure;

public class TextSegment extends Segment{
    private String text;
    private final String type = "text";
    
    public TextSegment(String text) {
    	this.text = text;
    }
    
    public String getText() {
    	return text;
    }
    
    public boolean equals(Segment segment) {
    	if (segment instanceof TextSegment)
	    	return text.equals(((TextSegment)segment).getText());
    	else
    		return false;
    }

	String getType() {
		return type;
	}
}
