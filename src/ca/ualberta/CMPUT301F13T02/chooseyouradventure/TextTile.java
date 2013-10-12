package ca.ualberta.CMPUT301F13T02.chooseyouradventure;

public class TextTile extends Tile{
    private String text;
    private final String type = "text";
    
    public TextTile(String text) {
    	this.text = text;
    }
    
    public String getText() {
    	return text;
    }
    
    public boolean equals(Tile tile) {
    	if (tile instanceof TextTile)
	    	return text.equals(((TextTile)tile).getText());
    	else
    		return false;
    }

	String getType() {
		return type;
	}
}
