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
import android.content.ClipData;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
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
	
	private LinearLayout tilesLayout;
	private LinearLayout decisionsLayout;
	private LinearLayout commentsLayout;
	
    private ControllerApp app;
    private Menu menu;
    private boolean isEditing;
    
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
        this.isEditing = false;
        displayPage();
		
		//commentsAdapter.notifyDataSetChanged();
		Button addTileButton = (Button) findViewById(R.id.addTile);
		addTileButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				TextTile tile = new TextTile();
				app.getPage().addTile(tile);
				addTile(app.getPage().getTiles().size() - 1, tile);
			}
		});
		
		Button addDecisionButton = (Button) findViewById(R.id.addDecision);
		addDecisionButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Decision decision = new Decision();
				app.getPage().addDecision(decision);
				addDecision(app.getPage().getDecisions().size() - 1, decision);
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
	 * 
	 * @param item The item that was clicked
	 * @return Success
	 */
    public boolean onOptionsItemSelected(MenuItem item) 
    {
    	return menuItemClicked(item);
    }
	
    /**
     * Puts button for changing to edit mode in the action bar.
     * @param menu The Menu to make
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
	 * @param item The clicked item
	 * @return
	 */
	private boolean menuItemClicked(MenuItem item) {
		MenuItem editButton = menu.findItem(0);
		MenuItem doneButton = menu.findItem(1);
		switch (item.getItemId()) {
		case 0:
			this.isEditing = true;
			displayPage();
			doneButton.setVisible(true);
			editButton.setVisible(false);
			break;
		case 1:
			this.isEditing = false;
			displayPage();
			doneButton.setVisible(false);
			editButton.setVisible(true);
			break;
		}
		return true;
	}
	
	/**
	 * Displays a page
	 */
	private void displayPage() {
        
		setButtonVisibility();
		clearPage();
		
		Page page = app.getPage();
		
		displayPageEnding(page);
		
		ArrayList<Tile> tiles = page.getTiles();
		for (int i = 0; i < tiles.size(); i++) {
			addTile(i, tiles.get(i));
		}
		
		ArrayList<Decision> decisions = page.getDecisions();
		for (int i = 0; i < decisions.size(); i++) {
			addDecision(i, decisions.get(i));
		}
		
		// Third ListView. Used for the Comments. Maybe don't show while editing
		ArrayList<Comment> comments = page.getComments();
		for (int i = 0; i < comments.size(); i++) {
			addComment(comments.get(i));
		}
	}
	
	/**
	 * Adds the pageEnding from the passed page object to the pageEnding
	 * view of ViewPageActivity.
	 * @param page
	 */
	public void displayPageEnding(Page page) {
		TextView pageEnding = (TextView) findViewById(R.id.pageEnding);
		pageEnding.setText(page.getPageEnding());
	}
	
	/**
	 * Clears all the layouts on the page.
	 */
	private void clearPage() {
		tilesLayout.removeAllViews();
		decisionsLayout.removeAllViews();
		commentsLayout.removeAllViews();
	}
	
	/**
	 * Handles removing or showing add tile and add decision buttons
	 */
	private void setButtonVisibility() {
		int visibility = 0;
		
		if (isEditing) {
			visibility = View.VISIBLE;
		} else {
			visibility = View.GONE;
		}
		
		Button addTileButton = (Button) findViewById(R.id.addTile);
		Button addDecisionButton = (Button) findViewById(R.id.addDecision);
		
		addTileButton.setVisibility(visibility);
		addDecisionButton.setVisibility(visibility);
	}

	private void onEditPageEnding(View view) {
		if (isEditing) {
			final TextView textView = (TextView) view;
	    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    	final EditText alertEdit = new EditText(this);
	    	alertEdit.setText(textView.getText().toString());
	    	builder.setView(alertEdit);
	    	builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int id) {
	            	onDonePageEnding(textView, alertEdit.getText().toString());
	            }
	        })
	        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int id) {
	                
	            }
	        });
	        builder.show();
		}
	}

	
	private void onDonePageEnding(TextView view, String text) {
		app.getPage().setPageEnding(text);
		view.setText(text);
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
		
		if (isEditing) {
			view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					onEditDecision(v);
				}
			});
			
			view.setOnLongClickListener(new OnLongClickListener() {
				@Override
				public boolean onLongClick(View v) {
					decisionMenu(v);
					return true;
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
            	switch(item){
            	case(0):
            		onEditDecision(view);
            		break;
            	case(1):
            		deleteDecision(view);
            		break;
            	}
            }
        });
        builder.show();
    }
	
	/**
	 * Removes the decision view and the decision in the model.
	 * @param view
	 */
	private void deleteDecision(View view) {
		int whichDecision = decisionsLayout.indexOfChild(view);
		
        app.getPage().getDecisions().remove(whichDecision);
        decisionsLayout.removeView(view);
	}
	
	/**
	 * Changes the view so that the next page is showing.
	 * @param view
	 */
	private void decisionClicked(View view) {
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
			if (toPageId == pages.get(i).getId()) {
				toPagePosition = i;
				break;
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
	
	/**
	 * Called when a user clicks done on a decision editing dialog. Saves the
	 * changes to the model and makes the necessary changes to the view.
	 * @param text
	 * @param position
	 * @param view
	 */
	private void onDoneDecision(String text, int position, View view) {
		ControllerApp app = (ControllerApp) getApplication();
		ArrayList<Page> pages = app.getStory().getPages();
		LinearLayout layout = (LinearLayout) findViewById(R.id.decisionsLayout);
		int decisionNumber = layout.indexOfChild(view);
		app.getPage().updateDecision(text, pages.get(position), decisionNumber);
		TextView textView = (TextView) view;
		textView.setText(text);
	}
	
	/**
	 * Returns a list of strings for each page to be displayed in the Spinner
	 * for editing a decision.
	 * @param pages
	 * @return A list of Strings, one representing each page in the story
	 */
	private ArrayList<String> getPageStrings(ArrayList<Page> pages) {
		ArrayList<String> pageNames = new ArrayList<String>();
		/*
		for (int i = 0; i < pages.size(); i++) {
			pageNames.add(pages.get(i).getId().toString());
		}
		*/
		for (int i = 0; i < pages.size(); i++) {
			pageNames.add("(" + pages.get(i).getRefNum() + ") " + pages.get(i).getTitle());
		}
		
		return pageNames;
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
		
		app.addComment(comment);
		addComment(comment);
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
	 * Called to display a new tile at position i. If we are in editing mode,
	 * add a click listener to allow user to edit the tile
	 * @param i
	 * @param tile
	 */
	public void addTile(int i, Tile tile) {
		if (tile.getType() == "text") {
			TextTile textTile = (TextTile) tile;
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
					                                LinearLayout.LayoutParams.WRAP_CONTENT);
			TextView view = new TextView(this);
			
			// Set what the tiles look like
			view.setPadding(0, 5, 0, 6);
			if (isEditing) {
				/* Background to the layout is grey, so adding margins adds 
				 * separators.
				 */
				lp.setMargins(0, 0, 0, 3);
			} else {
				view.setPadding(0, 5, 0, 9);
			}
			view.setBackgroundColor(0xFFFFFFFF);
			view.setText(textTile.getText());
			view.setLayoutParams(lp);

			tilesLayout.addView(view, i);
			
			if (isEditing) {
				view.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						onEditTile(v);
					}
				});
				
				//view.setOnDragListener(new MyDragListener());
				
				view.setOnLongClickListener(new OnLongClickListener() {
					@Override
					public boolean onLongClick(View v) {
						ClipData data = ClipData.newPlainText("", "");
						DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
						v.startDrag(data, shadowBuilder, v, 0);
						return true;
					}
				});
			}
		} else {
			Log.d("no such tile", "no tile of type " + tile.getType());
		}
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
            	switch(item){
            	case(0):
            		onEditTile(view);
            		break;
            	case(1):
            		deleteTile(view);
            		break;
            	}
            }
        });
        builder.show();
    }
	
	/**
	 * Removes the decision view and the decision in the model.
	 * @param view
	 */
	private void deleteTile(View view) {
		int whichTile = tilesLayout.indexOfChild(view);

        app.getPage().getTiles().remove(whichTile);
        tilesLayout.removeView(view);
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
            	onDoneTile(textView, alertEdit.getText().toString());
            	
            }
        })
        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                
            }
        });
        builder.show();
	}
	
	/**
	 * Called when user selects done on a tile editing dialog. Updates the 
	 * view and the model.
	 * @param view
	 * @param text
	 */
	private void onDoneTile(View view, String text) {
		ControllerApp app = (ControllerApp) getApplication();
		LinearLayout layout = (LinearLayout) findViewById(R.id.tilesLayout);
		int i = layout.indexOfChild(view);
		app.getPage().updateTile(text, i);
		TextView textView = (TextView) view;
		textView.setText(text);
	}

}


