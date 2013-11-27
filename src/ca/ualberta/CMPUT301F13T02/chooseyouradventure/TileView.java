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
	 * This menu lets you choose the type of tile youd like
	 * @param tilesLayout
	 */
	public void addTileMenuGUI(final LinearLayout tilesLayout){
		AlertDialog.Builder builder = new AlertDialog.Builder(pageActivity);
		final AlertDialog.Builder photoSelector = 
				new AlertDialog.Builder(pageActivity);
		final String[] titles = { pageActivity.getString(R.string.textTile), pageActivity.getString(R.string.photoTile),
				                   pageActivity.getString(R.string.videoTile), pageActivity.getString(R.string.audioTile), pageActivity.getString(R.string.cancel) };   
		final String[] titlesPhoto = { pageActivity.getString(R.string.fromFile), pageActivity.getString(R.string.takePhoto), pageActivity.getString(R.string.cancel) };
        builder.setItems(titles, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
            	switch(item){
            	case(0):
            		//TODO fix this to be MVC and observer pattern
            		TextTile tile = new TextTile();
					pageController.getPage().addTile(tile);
					tileBuilder.addTile(pageController.getPage().getTiles().size() - 1, tile, tilesLayout);   				
            		break;
            	case(1):
            		photoSelector.setItems(titlesPhoto, 
            				new DialogInterface.OnClickListener() {
            			 public void onClick(DialogInterface dialog, 
            					              int item) {
            	            	switch(item){
	            	            	case(0):
	            	            		pageActivity.getPhoto();
	            	            		
	            	            		
	            	            		break;
	            	            	case(1):
	            	            		pageActivity.takePhoto();
	            	            		break;
            	            	}
            	                }});
            	       	photoSelector.show();
            		
            		break;
            		
            		
            	case(2):
            		break;
            	case(3):
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
	protected void onEditTileGUI(View view, final LinearLayout tilesLayout){
		final TextView textView = (TextView) view;
    	AlertDialog.Builder builder = new AlertDialog.Builder(pageActivity);
    	final EditText alertEdit = new EditText(pageActivity);
    	alertEdit.setText(textView.getText().toString());
    	builder.setView(alertEdit);
    	builder.setPositiveButton(pageActivity.getString(R.string.done), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            	int whichTile = tilesLayout.indexOfChild(textView);
            	pageController.updateTile(alertEdit.getText().toString(), whichTile);
            }
        })
        .setNegativeButton(pageActivity.getString(R.string.done), null);
    	builder.show();
	}
	
	/**
	 * What appears when you click on a textTile
	 * @param view
	 * @param tilesLayout
	 */
	protected void editTileMenuGUI(final View view, final LinearLayout tilesLayout){
		final String[] titles = { pageActivity.getString(R.string.edit), pageActivity.getString(R.string.delete) };
		
        AlertDialog.Builder builder = new AlertDialog.Builder(pageActivity);
        builder.setTitle(R.string.story_options);
        builder.setItems(titles, new DialogInterface.OnClickListener() {
        	
            public void onClick(DialogInterface dialog, int item) {
            	int whichTile = tilesLayout.indexOfChild(view);
            	switch(item){
            	case(0):
            		onEditTileGUI(view, tilesLayout);
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
