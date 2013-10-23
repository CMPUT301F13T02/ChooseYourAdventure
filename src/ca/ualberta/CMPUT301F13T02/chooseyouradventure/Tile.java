package ca.ualberta.CMPUT301F13T02.chooseyouradventure;

import android.view.View;

public abstract class Tile {

	public View getEditView() {
		return null;
	}
	
	public View getReadView() {
		return null;	
	}
	
	abstract String getType();
	abstract boolean equals(Tile tile);
}
