package ca.ualberta.CMPUT301F13T02.chooseyouradventure;

import java.util.UUID;

public class Decision {

	private String text;
	private UUID pageID;
	
	public Decision(String text, Page page) {
		this.text = text;
		this.pageID = page.getId();
	}
	
	public UUID getPageID() {
		return pageID;
	}
	
	public String getText() {
		return text;
	}
}
