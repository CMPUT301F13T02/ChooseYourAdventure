/*
* Copyright (c) 2013, TeamCMPUT301F13T02
* All rights reserved.
* 
* Redistribution and use in source and binary forms, with or without modification,
* are permitted provided that the following conditions are met:
* 
* Redistributions of source code must retain the above copyright notice, this
* list of conditions and the following disclaimer.
* 
* Redistributions in binary form must reproduce the above copyright notice, this
* list of conditions and the following disclaimer in the documentation and/or
* other materials provided with the distribution.
* 
* Neither the name of the {organization} nor the names of its
* contributors may be used to endorse or promote products derived from
* this software without specific prior written permission.
* 
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
* ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
* WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
* DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
* ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
* (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
* LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
* (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
* SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package ca.ualberta.CMPUT301F13T02.chooseyouradventure;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * This class displays the data pertaining to tiles in the form of a GUI. 
 *
 */
public class TileView {
	private ViewPageActivity pageActivity;
	private PageController pageController; 
	private TileLayoutBuilder tileBuilder;
	
	
	public TileView(PageController pageController, ViewPageActivity pageActivity) {
		super();
		
		this.pageActivity = pageActivity;
        this.pageController = pageController;
        tileBuilder = new TileLayoutBuilder(pageActivity);

	}
	
	/**
	 * This menu lets you choose the type of tile you'd like
	 * @param tilesLayout
	 */
	public void addTileMenuGUI(final LinearLayout tilesLayout){
		AlertDialog.Builder builder = new AlertDialog.Builder(pageActivity);
		final String[] titles = { pageActivity.getString(R.string.textTile), 
								   pageActivity.getString(R.string.photoTile),
				                   pageActivity.getString(R.string.cancel) };   
        builder.setItems(titles, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
            	switch(item){
            	case(0):
            		TextView textView = new TextView(pageActivity);
            		textView.setText("New Text Block");
            		onEditTextTileGUI(textView, tilesLayout);
            		break;
            	case(1):
            		ImageView imageView = new ImageView(pageActivity);
            		photoSelectorGUI(imageView, tilesLayout);
            		
            		break;
            		
            		
            	case(2):
            		/** This is for the implementation of video tiles in project
            		 * part 5.
            		 */
            		break;
            	case(3):
            		/** This is for the implementation of audio tile in project
            		 * part 5.
            		 */
            		break;
            	}
                    
                }});
        builder.show();
	}
	
	/**
	 * The menu where you edit the text in a tile
	 * @param view
	 * @param tilesLayout
	 */
	protected void onEditTextTileGUI(View view, final LinearLayout tilesLayout){
		final TextView textView = (TextView) view;
    	AlertDialog.Builder builder = new AlertDialog.Builder(pageActivity);
    	final EditText alertEdit = new EditText(pageActivity);
    	alertEdit.setText(textView.getText().toString());
    	builder.setView(alertEdit);
    	builder.setPositiveButton(pageActivity.getString(R.string.done), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            	int whichTile = tilesLayout.indexOfChild(textView);
            	String text = alertEdit.getText().toString();
            	if (whichTile == -1) {
            		TextTile tile = new TextTile(text);
            		pageController.addTile(tile);
            	} else {
            		pageController.updateTile(text, whichTile);
            	}
            }
        })
        .setNegativeButton(pageActivity.getString(R.string.cancel), null);
    	builder.show();
	}
	
	protected void photoSelectorGUI(View view, final LinearLayout tilesLayout) {
		final AlertDialog.Builder photoSelector = 
				new AlertDialog.Builder(pageActivity);
		final String[] titlesPhoto = { pageActivity.getString(R.string.fromFile),
										pageActivity.getString(R.string.takePhoto), 
										pageActivity.getString(R.string.cancel) };
		final int whichTile = tilesLayout.indexOfChild(view);
		photoSelector.setItems(titlesPhoto,
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,
					int item) {
				switch(item){
				case(0):
					pageActivity.getPhoto(whichTile);
				break;
				case(1):
					pageActivity.takePhoto(whichTile);
				break;
				}
			}});
		photoSelector.show();
	}
	
	/**
	 * What appears when you click on a textTile
	 * @param view
	 * @param tilesLayout
	 */
	protected void editTileMenuGUI(final View view, final LinearLayout tilesLayout){
		final String[] titles = { pageActivity.getString(R.string.edit), 
								  pageActivity.getString(R.string.delete) };
		
        AlertDialog.Builder builder = new AlertDialog.Builder(pageActivity);
        builder.setItems(titles, new DialogInterface.OnClickListener() {
        	
            public void onClick(DialogInterface dialog, int item) {
            	int whichTile = tilesLayout.indexOfChild(view);
            	switch(item){
            	case(0):
            		if (view instanceof TextView) {
            			onEditTextTileGUI(view, tilesLayout);
            		} else if (view instanceof ImageView) {
            			photoSelectorGUI(view, tilesLayout);
            		}
            		break;
            	case(1):
            		pageController.deleteTile(whichTile);
            		break;
            	}
            }
            
        });
        builder.show();
	}
	
	/**
	 * Edits the ending caption of a page.
	 * @param view
	 */
	protected void onEditPageEndingGUI(View view){
		TextView textView = (TextView) view;
		AlertDialog.Builder builder = new AlertDialog.Builder(pageActivity);
		final EditText alertEdit = new EditText(pageActivity);
		alertEdit.setText(textView.getText().toString());
		builder.setView(alertEdit);
		builder.setPositiveButton(pageActivity.getString(R.string.done), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				pageController.setEnding(alertEdit.getText().toString());
			}
		})
		.setNegativeButton(pageActivity.getString(R.string.cancel), null);
		builder.show();


	}

}
