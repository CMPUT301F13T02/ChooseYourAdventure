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
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * The Activity in the application that is responsible for viewing and editing
 * a page within a story.  <br />
 * <br />
 * In this activity a reader can:
 * <ol>
 *     <li> Read the page </li>
 *     <li> Follow decisions at the bottom </li>
 *     <li> Comment on the page </li>
 * </ol>
 * In this activity an author can: 
 * <ol>
 *     <li> Edit the tiles on this page (add, edit, reorder, delete) </li>
 * </ol>
 */

public class ViewPageActivity extends Activity {
	
	private final int EDIT_INDEX = 0;
	private final int SAVE_INDEX = 1;
	
	private LinearLayout tilesLayout;
	private LinearLayout decisionsLayout;
	private LinearLayout commentsLayout;
	
    private ControllerApp app;
    private Menu menu;
    
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_page_activity);
        app = (ControllerApp) this.getApplication();
        
        tilesLayout = (LinearLayout) findViewById(R.id.tilesLayout);
        decisionsLayout = (LinearLayout) findViewById(R.id.decisionsLayout);
        commentsLayout = (LinearLayout) findViewById(R.id.commentsLayout);
        
    }

	/**
	 * Called when the Activity resumes
	 */
	@Override
	public void onResume() {
        super.onResume();
        app.setPageActivity(this);
        update(app.getPage());
        setButtonVisibility();
		
        /* Set up onClick listeners for buttons on screen, even if some aren't
         * shown at the time.
         */
		Button addTileButton = (Button) findViewById(R.id.addTile);
		addTileButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				TextTile tile = new TextTile();
				app.addTile(tile);
			}
		});
		
		Button addDecisionButton = (Button) findViewById(R.id.addDecision);
		addDecisionButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Decision decision = new Decision();
				app.addDecision(decision);
			}
		});
		
        TextView addComment = (TextView) findViewById(R.id.addComment);
        addComment.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View view) {
        		onAddComment(view);
        	}
        });
        
        TextView pageEnding = (TextView) findViewById(R.id.pageEnding);
        pageEnding.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View view) {
        		onEditPageEnding(view);
        	}
        });
	}
	
	@Override
	public void onPause() {
		app.removeActivity();
	}
	
	/**
	 * Create an options menu.
	 * 
	 * @param menu The menu to create
	 * @return Success
	 */
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
		this.menu = menu;
		super.onCreateOptionsMenu(menu);
        makeMenu(menu);
		if (app.isEditing()) {
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
	 * 
	 * @param item The item that was clicked
	 * @return Success
	 */
    public boolean onOptionsItemSelected(MenuItem item) 
    {
		try {
			return menuItemClicked(item);
		} catch (HandlerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;	
    }
	
    /**
     * Puts button for changing to edit mode in the action bar.
     * @param menu The Menu to make
     */
	public void makeMenu(Menu menu) {
		MenuItem editPage = menu.add(0, EDIT_INDEX, EDIT_INDEX, "Edit");
		{
			editPage.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		}
		MenuItem savePage = menu.add(0, SAVE_INDEX, SAVE_INDEX, "Save");
		{
			savePage.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		}
	}
	
	/**
	 * Handles what to do when an item of the action bar is pressed.
	 * @param item The clicked item
	 * @return
	 * @throws HandlerException 
	 */
	private boolean menuItemClicked(MenuItem item) throws HandlerException {
		switch (item.getItemId()) {
		case 0:
			app.setEditing(true);
			update(app.getPage());
			setButtonVisibility();
			break;
		case 1:
			app.setEditing(false);
			app.saveStory();
			update(app.getPage());
			setButtonVisibility();
			break;
		}
		return true;
	} 
	
	/**
	 * Updates a page to show any changes that have been made. These
	 * changes can also include whether the page is in view mode or
	 * edit mode.
	 * @param page The current page
	 */
	public void update(Page page) {
        
		setButtonVisibility();
		
		if (page.haveTilesChanged()) {
			updateTiles(page);
		}
		
		if (page.haveDecisionsChanged()) {
			updateDecisions(page);
		}
		
		if (page.haveCommentsChanged()) {
			updateComments(page);
		}
		
		if (page.hasEndingChanged()) {
			updateEnding(page);
		}
		
		app.finishedUpdating();
	}
	
	/**
	 * Handles removing or showing the proper buttons in both the action bar
	 * and the in the page.
	 */
	private void setButtonVisibility() {
		MenuItem editButton = menu.findItem(0);
		MenuItem saveButton = menu.findItem(1);
		Button addTileButton = (Button) findViewById(R.id.addTile);
		Button addDecisionButton = (Button) findViewById(R.id.addDecision);
		
		int visibility = 0;
		
		if (app.isEditing()) {
			saveButton.setVisible(true);
			editButton.setVisible(false);
			visibility = View.VISIBLE;
		} else {
			saveButton.setVisible(false);
			editButton.setVisible(true);
			visibility = View.GONE;
		}
				
		addTileButton.setVisibility(visibility);
		addDecisionButton.setVisibility(visibility);
	}
	
	/**
	 * Removes all the tiles from the tilesLayout and repopulates it with 
	 * the current state of the tiles.
	 * @param page
	 */
	private void updateTiles(Page page) {
		tilesLayout.removeAllViews();
		
		//For each tile in the page, add the tile to tilesLayout
		ArrayList<Tile> tiles = page.getTiles();
		for (int i = 0; i < tiles.size(); i++) {
			addTile(i, tiles.get(i));
		}
	}
	
	/**
	 * Removes all the decisions from the decisionsLayout and repopulates it
	 * with the current state of the decisions.
	 * @param page
	 */
	private void updateDecisions(Page page) {
		decisionsLayout.removeAllViews();
		
		//For each decision in the page, add it to decisionsLayout
		ArrayList<Decision> decisions = page.getDecisions();
		for (int i = 0; i < decisions.size(); i++) {
			addDecision(i, decisions.get(i));
		}
	}
	
	/**
	 * Removes the comments from commentsLayout and repopulates it with the
	 * current comments.
	 * @param page
	 */
	private void updateComments(Page page) {
		commentsLayout.removeAllViews();
		
		//For each comment in the page, add it to commentsLayout
		ArrayList<Comment> comments = page.getComments();
		for (int i = 0; i < comments.size(); i++) {
			addComment(comments.get(i));
		}
	}
	
	/**
	 * Updates the pageEnding from the passed page object.
	 * @param page
	 */
	private void updateEnding(Page page) {
		TextView pageEnding = (TextView) findViewById(R.id.pageEnding);
		pageEnding.setText(page.getPageEnding());
	}
		
	/**
	 * Called to display a new tile at position i. If we are in editing mode,
	 * add a click listener to allow user to edit the tile
	 * @param i
	 * @param tile
	 */
	public void addTile(int i, Tile tile) {
		
		View view = makeTileView();
		
		if (tile.getType() == "text") {
			TextTile textTile = (TextTile) tile;
			TextView textView = (TextView) view;
			
			textView.setText(textTile.getText());

			tilesLayout.addView(textView, i);
			
			if (app.isEditing()) {
				textView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						tileMenu(v);
					}
				});
			}
			
		} else if (tile.getType() == "photo") {
			// TODO Implement for part 4
		} else if (tile.getType() == "video") {
			// TODO Implement for part 4
		} else if (tile.getType() == "audio") {
			// TODO Implement for part 4
		} else {
			Log.d("no such tile", "no tile of type " + tile.getType());
		}
	}
	
	/**
	 * Create a view that has the proper padding, and if we are in editing
	 * mode, adds a small margin to the bottom so we can see a little of 
	 * the layout background which makes a line separating the tile views.
	 * @return
	 */
	private View makeTileView() {
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
		View view = new View(this);

		// Set what the tiles look like
		view.setPadding(0, 5, 0, 6);
		if (app.isEditing()) {
			/* Background to the layout is grey, so adding margins adds 
			 * separators.
			 */
			lp.setMargins(0, 0, 0, 3);
		} else {
			view.setPadding(0, 5, 0, 9);
		}
		view.setBackgroundColor(0xFFFFFFFF);
		view.setLayoutParams(lp);
		
		return view;
	}
	
	/**
	 * Brings up a menu with options of what to do to the decision.
	 * @param view
	 */
	public void tileMenu(final View view){
		final String[] titles = {"Edit","Delete"};
		
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.story_options);
        builder.setItems(titles, new DialogInterface.OnClickListener() {
        	
            public void onClick(DialogInterface dialog, int item) {
            	int whichTile = tilesLayout.indexOfChild(view);
            	switch(item){
            	case(0):
            		onEditTile(view);
            		break;
            	case(1):
            		app.deleteTile(whichTile);
            		break;
            	}
            }
            
        });
        builder.show();
    }
	
	/**
	 * Displays a dialog for editing a tile.
	 * @param view
	 */
	private void onEditTile(View view) {
		final TextView textView = (TextView) view;
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	final EditText alertEdit = new EditText(this);
    	alertEdit.setText(textView.getText().toString());
    	builder.setView(alertEdit);
    	builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            	int whichTile = tilesLayout.indexOfChild(textView);
            	app.updateTile(alertEdit.getText().toString(), whichTile);
            }
        })
        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                
            }
        });
        builder.show();
	}
	
	/**
	 * Adds a decision to the page. If we are in editing mode, give the view a
	 * onClickListener to allow you to edit the decision. If we are in 
	 * viewing mode add an onClickListener to go to the next page.
	 * 
	 * @param i
	 * @param decision
	 */
	private void addDecision(int i, Decision decision) {
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, 
				LinearLayout.LayoutParams.WRAP_CONTENT
				);
		TextView view = new TextView(this);
		lp.setMargins(0, 0, 0, 3);
		view.setPadding(20, 5, 0, 5);
		view.setBackgroundColor(0xFFFFFFFF);
		view.setLayoutParams(lp);
		view.setText(decision.getText());
		decisionsLayout.addView(view, i);
		
		if (app.isEditing()) {
			view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					decisionMenu(v);
				}
			});
		} else {
			view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					decisionClicked(v);
				}
			});
		}
	}
	
	/**
	 * Brings up a menu with options of what to do to the decision.
	 * @param view
	 */
	public void decisionMenu(final View view){
		final String[] titles = {"Edit","Delete"};
		
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.story_options);
        builder.setItems(titles, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
            	int whichDecision = decisionsLayout.indexOfChild(view);
            	switch(item){
            	case(0):
            		onEditDecision(view);
            		break;
            	case(1):
            		app.deleteDecision(whichDecision);
            		break;
            	}
            }
        });
        builder.show();
    }
	
	/**
	 * Changes the view so that the next page is showing.
	 * @param view
	 */
	private void decisionClicked(View view) {
		int whichDecision = decisionsLayout.indexOfChild(view);
		app.followDecision(whichDecision);
	}
	
	/**
	 * Brings up a dialog for editing the decision clicked.
	 * @param view
	 */
	private void onEditDecision(View view) {
		LinearLayout decisionsLayout = (LinearLayout) findViewById(R.id.decisionsLayout);
		int whichDecision = decisionsLayout.indexOfChild(view);
		Decision decision = app.getPage().getDecisions().get(whichDecision);
		
		UUID toPageId = decision.getPageID();
		ArrayList<Page> pages = app.getStory().getPages();
		int toPagePosition = -1;
		for (int i = 0; i < pages.size(); i++) {
			if (toPageId.equals(pages.get(i).getId())) {
				toPagePosition = i;
			}
		}
		
		final TextView decisionView = (TextView) view;
		
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle("Set text and next page");
    	
    	final LinearLayout layout = new LinearLayout(this);
    	layout.setOrientation(LinearLayout.VERTICAL);
    	
    	final EditText alertEdit = new EditText(this);
    	alertEdit.setText(decision.getText());
    	layout.addView(alertEdit);
    	
    	final Spinner pageSpinner = new Spinner(this);
    	ArrayList<String> pageStrings = app.getPageStrings(pages);
    	ArrayAdapter<String> pagesAdapter = new ArrayAdapter<String>(this, 
    			R.layout.list_item_base, pageStrings);
    	pageSpinner.setAdapter(pagesAdapter);
    	pageSpinner.setSelection(toPagePosition);
    	layout.addView(pageSpinner);
    	
    	builder.setView(layout);
    	builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
        		int decisionNumber = layout.indexOfChild(decisionView);
            	app.updateDecision(alertEdit.getText().toString(), 
            			pageSpinner.getSelectedItemPosition(), decisionNumber);
            }
        })
        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                
            }
        });
        builder.show();
	}
	
	/**
	 * Called to display a new comment at position i.
	 * @param comment
	 */
	public void addComment(Comment comment) {
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
		lp.setMargins(0, 0, 0, 5);
		TextView view = new TextView(this);
		view.setBackgroundColor(0xFFFFFFFF);
		view.setPadding(10, 5, 10, 5);
		view.setLayoutParams(lp);
		view.setText(comment.getText());
	    commentsLayout.addView(view);
	}
	
	/**
	 * Called when the add comment button is clicked. It creates a dialog that
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
            	app.addComment(alertEdit.getText().toString());
            }
        })
        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                
            }
        });
        builder.show();
	}
	
	/**
	 * Opens a dialog that allows the user to edit the pageEnding.
	 * @param view
	 */
	private void onEditPageEnding(View view) {
		if (app.isEditing()) {
			TextView textView = (TextView) view;
	    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    	final EditText alertEdit = new EditText(this);
	    	alertEdit.setText(textView.getText().toString());
	    	builder.setView(alertEdit);
	    	builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int id) {
	            	app.setEnding(alertEdit.getText().toString());
	            }
	        })
	        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int id) {
	                
	            }
	        });
	        builder.show();
		}
	}

}
