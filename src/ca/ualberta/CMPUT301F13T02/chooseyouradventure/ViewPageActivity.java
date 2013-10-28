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


import java.util.ArrayList;
import java.util.UUID;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class ViewPageActivity extends Activity {
	
    private ControllerApp app;
    private Menu menu;
    private boolean isEditing;
    
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_page_activity);
        app = (ControllerApp) this.getApplication();
        
        //For until actual stories are being passed to the app
		Story story = app.createFakeStory();
		app.setStory(story);
		Page page = story.getPages().get(0);
		app.setPage(page);
        
		this.isEditing = false;
    }

	@Override
	public void onResume() {
        super.onResume();
        
        displayPage();
		
		//commentsAdapter.notifyDataSetChanged();
        
        Button addComment = (Button) findViewById(R.id.addComment);
        addComment.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View view) {
        		onAddComment(view);
        	}
        });
        
	}
	
	/**
	 * Create an options menu.
	 */
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
		this.menu = menu;
		super.onCreateOptionsMenu(menu);
        makeMenu(menu);
		if (this.isEditing) {
			MenuItem editButton = menu.findItem(0);
			editButton.setVisible(false);
		} else {
			MenuItem doneButton = menu.findItem(1);
			doneButton.setVisible(false);
		}
        return true;
    }
	
	/**
	 * Callback for clicking an item in the menu.
	 */
    public boolean onOptionsItemSelected(MenuItem item) 
    {
    	return menuItemClicked(item);
    }
	
    /**
     * Puts button for changing to edit mode in the action bar.
     * @param menu
     */
	public void makeMenu(Menu menu) {
		MenuItem editPage = menu.add(0, 0, 0, "Edit");
		{
			editPage.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		}
		MenuItem savePage = menu.add(0, 1, 1, "Done");
		{
			savePage.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		}
	}
	
	/**
	 * Handles what to do when an item of the action bar is pressed.
	 * @param item
	 * @return
	 */
	private boolean menuItemClicked(MenuItem item) {
		MenuItem editButton = menu.findItem(0);
		MenuItem doneButton = menu.findItem(1);
		switch (item.getItemId()) {
		case 0:
			displayEditablePage();
			doneButton.setVisible(true);
			editButton.setVisible(false);
			break;
		case 1:
			displayPage();
			doneButton.setVisible(false);
			editButton.setVisible(true);
			break;
		}
		return true;
	}
	
	private void displayPage() {
		LinearLayout tilesLayout = (LinearLayout) findViewById(R.id.tilesLayout);
		LinearLayout decisionsLayout = (LinearLayout) findViewById(R.id.decisionsLayout);
		LinearLayout commentsLayout = (LinearLayout) findViewById(R.id.commentsLayout);
		
		tilesLayout.removeAllViews();
		decisionsLayout.removeAllViews();
		commentsLayout.removeAllViews();
		
		Page page = app.getPage();
		
		ArrayList<Tile> tiles = page.getTiles();
		for (int i = 0; i < tiles.size(); i++) {
			addTileView(i, tiles.get(i), tilesLayout);
		}
		
		ArrayList<Decision> decisions = page.getDecisions();
		for (int i = 0; i < decisions.size(); i++) {
			addDecisionView(i, decisions.get(i), decisionsLayout);
		}
		
		// Third ListView. Used for the Comments. Maybe don't show while editing
		ArrayList<Comment> comments = page.getComments();
		for (int i = 0; i < comments.size(); i++) {
			addCommentView(comments.get(i), commentsLayout);
		}
	}
	
	/**
	 * Puts each list in an adapter that will display it properly and hands
	 * the adapter to the proper ListView.
	 */
	private void displayEditablePage() {		
		LinearLayout tilesLayout = (LinearLayout) findViewById(R.id.tilesLayout);
		LinearLayout decisionsLayout = (LinearLayout) findViewById(R.id.decisionsLayout);
		LinearLayout commentsLayout = (LinearLayout) findViewById(R.id.commentsLayout);
		
		tilesLayout.removeAllViews();
		decisionsLayout.removeAllViews();
		commentsLayout.removeAllViews();
		
		Page page = app.getPage();
		
		ArrayList<Tile> tiles = page.getTiles();
		for (int i = 0; i < tiles.size(); i++) {
			addEditableTileView(i, tiles.get(i), tilesLayout);
		}
		
		ArrayList<Decision> decisions = page.getDecisions();
		for (int i = 0; i < decisions.size(); i++) {
			addEditableDecisionView(i, decisions.get(i), decisionsLayout);
		}
		
		// Third ListView. Used for the Comments. Maybe don't show while editing
		ArrayList<Comment> comments = page.getComments();
		for (int i = 0; i < comments.size(); i++) {
			addCommentView(comments.get(i), commentsLayout);
		}

	}

	private void addDecisionView(int i, Decision decision, LinearLayout layout) {
		LayoutParams lparams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		TextView view = new TextView(this);
		view.setLayoutParams(lparams);
		view.setText(decision.getText());
		layout.addView(view, i);
	
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				decisionClicked(v);
			}
		});
	}
	
	private void addEditableDecisionView(int i, Decision decision, LinearLayout layout) {
		LayoutParams lparams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		TextView view = new TextView(this);
		view.setLayoutParams(lparams);
		view.setText(decision.getText());
		layout.addView(view, i);
	
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				editDecisionClicked(v);
			}
		});
	}
	
	private void decisionClicked(View view) {
		LinearLayout decisionsLayout = (LinearLayout) findViewById(R.id.decisionsLayout);
		int whichDecision = decisionsLayout.indexOfChild(view);
		Decision decision = app.getPage().getDecisions().get(whichDecision);
		
		UUID toPageId = decision.getPageID();
		ArrayList<Page> pages = app.getStory().getPages();
		Page toPage = app.getPage();
		for (int i = 0; i < pages.size(); i++) {
			if (toPageId == pages.get(i).getId()) {
				toPage = pages.get(i);
				break;
			}
		}
		
		app.setPage(toPage);
		this.onResume();
	}
	
	private void editDecisionClicked(View view) {
		LinearLayout decisionsLayout = (LinearLayout) findViewById(R.id.decisionsLayout);
		int whichDecision = decisionsLayout.indexOfChild(view);
		Decision decision = app.getPage().getDecisions().get(whichDecision);
		
		UUID toPageId = decision.getPageID();
		ArrayList<Page> pages = app.getStory().getPages();
		int toPagePosition = -1;
		for (int i = 0; i < pages.size(); i++) {
			if (toPageId == pages.get(i).getId()) {
				toPagePosition = i;
				break;
			}
		}
		
		final TextView decisionView = (TextView) view;
		
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle("Set text and next page");
    	
    	final LinearLayout layout = new LinearLayout(this);
    	
    	final EditText alertEdit = new EditText(this);
    	alertEdit.setText(decision.getText());
    	layout.addView(alertEdit);
    	
    	final Spinner pageSpinner = new Spinner(this);
    	ArrayList<String> pageStrings = getPageStrings(pages);
    	ArrayAdapter<String> pagesAdapter = new ArrayAdapter<String>(this, 
    			R.layout.list_item_base, pageStrings);
    	pageSpinner.setAdapter(pagesAdapter);
    	pageSpinner.setSelection(toPagePosition);
    	layout.addView(pageSpinner);
    	
    	builder.setView(layout);
    	builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            	onDoneDecision(alertEdit.getText().toString(), 
            			pageSpinner.getSelectedItemPosition(), decisionView);
            }
        })
        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                
            }
        });
        builder.show();
	}
	
	private void onDoneDecision(String text, int position, View view) {
		ControllerApp app = (ControllerApp) getApplication();
		ArrayList<Page> pages = app.getStory().getPages();
		LinearLayout layout = (LinearLayout) findViewById(R.id.decisionsLayout);
		int decisionNumber = layout.indexOfChild(view);
		app.getPage().updateDecision(text, pages.get(position), decisionNumber);
		TextView textView = (TextView) view;
		textView.setText(text);
	}
	
	private ArrayList<String> getPageStrings(ArrayList<Page> pages) {
		ArrayList<String> pageNames = new ArrayList<String>();
		for (int i = 0; i < pages.size(); i++) {
			pageNames.add(pages.get(i).getId().toString());
		}
		return pageNames;
	}
	
	/**
	 * called when the add comment button is clicked. It creates a dialog that
	 * allows the user to input text and then save the comment.
	 * @param view
	 */
	private void onAddComment(View view) {
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle("What to Say");
    	final EditText alertEdit = new EditText(this);
    	builder.setView(alertEdit);
    	builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            	onSaveComment(alertEdit.getText().toString());
            	
            }
        })
        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                
            }
        });
        builder.show();
	}
	
	/**
	 * Called when the user choses to save a comment. Tells the controller to 
	 * add the comment to the model and displays the new comment.
	 * @param text
	 */
	private void onSaveComment(String text) {

		String poster = Secure.getString(
				getBaseContext().getContentResolver(), Secure.ANDROID_ID);
		Comment comment = new Comment(text, poster);
		
		LinearLayout layout = (LinearLayout) findViewById(R.id.commentsLayout);
		
		app.addComment(comment);
		addCommentView(comment, layout);
	}
	
	/**
	 * Called to display a new comment at position i.
	 * @param comment
	 */
	public void addCommentView(Comment comment, LinearLayout layout) {
		LayoutParams lparams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		TextView view = new TextView(this);
		view.setLayoutParams(lparams);
		view.setText(comment.getText());
	    layout.addView(view);
	}
	
	/**
	 * Called to display a new tile at position i
	 * @param i
	 * @param tile
	 */
	public void addTileView(int i, Tile tile, LinearLayout layout) {
		if (tile.getType() == "text") {
			TextTile textTile = (TextTile) tile;
			LayoutParams lparams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			TextView view = new TextView(this);
			view.setLayoutParams(lparams);
			view.setText(textTile.getText());
			layout.addView(view, i);
		} else {
			Log.d("no such tile", "no tile of type " + tile.getType());
		}
	}
	
	/**
	 * Called to display a new tile at position i
	 * @param i
	 * @param tile
	 */
	public void addEditableTileView(int i, Tile tile, LinearLayout layout) {
		if (tile.getType() == "text") {
			TextTile textTile = (TextTile) tile;
			LayoutParams lparams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			TextView view = new TextView(this);
			view.setLayoutParams(lparams);
			view.setText(textTile.getText());
			layout.addView(view, i);
		
			view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					tileClicked(v);
				}
			});
		} else {
			Log.d("no such tile", "no tile of type " + tile.getType());
		}
	}
	
	private void tileClicked(View view) {
		final TextView textView = (TextView) view;
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	final EditText alertEdit = new EditText(this);
    	alertEdit.setText(textView.getText().toString());
    	builder.setView(alertEdit);
    	builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            	onDoneTile(textView, alertEdit.getText().toString());
            	
            }
        })
        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                
            }
        });
        builder.show();
	}
	
	private void onDoneTile(View view, String text) {
		ControllerApp app = (ControllerApp) getApplication();
		LinearLayout layout = (LinearLayout) findViewById(R.id.tilesLayout);
		int i = layout.indexOfChild(view);
		app.getPage().updateTile(text, i);
		TextView textView = (TextView) view;
		textView.setText(text);
	}
}


