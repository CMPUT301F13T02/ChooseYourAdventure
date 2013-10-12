package ca.ualberta.CMPUT301F13T02.chooseyouradventure;

import android.view.View;

public abstract class Tile {

	/** 
	 * @uml.property name="page"
	 * @uml.associationEnd inverse="segments:ca.ualberta.CMPUT301F13T02.chooseyouradventure.Page"
	 */
	private Page page;
	
	public View getEditView() {
		return null;
	}
	
	public View getReadView() {
		return null;	
	}
	
	abstract String getType();
	abstract boolean equals(Tile tile);
}
