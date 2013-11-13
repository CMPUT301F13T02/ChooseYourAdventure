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
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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
 * 
 * The ViewPageActivity is a view of the application.
 * 
 * TODO This activity will need to be able to display and edit Audio-, Video-, and Photo- Tiles
 */

public class ViewPageActivity extends Activity {
	
	private static final int RESULT_LOAD_IMAGE = 1;
	private final int EDIT_INDEX = 0;
	private final int SAVE_INDEX = 1;
	private final int HELP_INDEX = 2;
	
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
        app.setActivity(this);
        update(app.getPage());
        
        /* Set up onClick listeners for buttons on screen, even if some aren't
         * shown at the time.
         */
		Button addTileButton = (Button) findViewById(R.id.addTile);
		addTileButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				addTileMenu();

			}
		});
		
		Button addDecisionButton = (Button) findViewById(R.id.addDecision);
		addDecisionButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				app.addDecision();
			}
		});
		
        TextView addComment = (TextView) findViewById(R.id.addComment);
        addComment.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View view) {
        		onEditComment(view);
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
		super.onPause();
		app.deleteActivity();
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
        changeActionBarButtons();
        return true;
    }
	
    /**
     * Puts button for changing to edit mode in the action bar.
     * @param menu The Menu to make
     */
	public void makeMenu(Menu menu) {
	
		MenuItem editPage = menu.add(0, EDIT_INDEX, EDIT_INDEX, "Edit");
		MenuItem savePage = menu.add(0, SAVE_INDEX, SAVE_INDEX, "Done");
		MenuItem help = menu.add(0, HELP_INDEX, HELP_INDEX, "Help");

		editPage.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		savePage.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		help.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

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
    	return true;

    }
	
	/**
	 * Handles what to do when an item of the action bar is pressed.
	 * @param item The clicked item
	 * @return
	 */
	private boolean menuItemClicked(MenuItem item) throws HandlerException {
		switch (item.getItemId()) {
		case EDIT_INDEX:

			final String myId = Secure.getString(getBaseContext().getContentResolver(), Secure.ANDROID_ID);
			final String storyID = app.getStory().getAuthor();
			if(myId.equals(storyID)){
				app.setEditing(true);
				app.reloadPage();
				changeActionBarButtons();
				setButtonVisibility();
			}

			break;

		case SAVE_INDEX:

			app.setEditing(false);
			app.saveStory();
			app.reloadPage();
			changeActionBarButtons();
			setButtonVisibility();

			break;

		case HELP_INDEX:

			ScrollView scrollView = new ScrollView(this);
			WebView view = new WebView(this);

	        if (app.getEditing())
				view.loadData(getString(R.string.edit_page_help), "text/html", "UTF-8");
	        else
	        	view.loadData(getString(R.string.read_page_help), "text/html", "UTF-8");
	        
	        scrollView.addView(view);
	        scrollView.setPadding(10, 10, 10, 10);
	        
	        AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        builder.setTitle(R.string.help);
	        builder.setPositiveButton(R.string.ok, null);
	        builder.setView(scrollView);
	        builder.show();
	        
			break;
		}
		return true;
	} 
	
	/**
	 * Sets which buttons are visible in the action bar.
	 */
	public void changeActionBarButtons() {
		MenuItem editButton = menu.findItem(EDIT_INDEX);
		MenuItem saveButton = menu.findItem(SAVE_INDEX);
		
		final String myId = Secure.getString(
				getBaseContext().getContentResolver(), Secure.ANDROID_ID);
		final String storyID = app.getStory().getAuthor();
		if(myId.equals(storyID)){
			if (app.getEditing()) {
				saveButton.setVisible(true);
				editButton.setVisible(false);
			} else {
				saveButton.setVisible(false);
				editButton.setVisible(true);
			}
		} else {
			saveButton.setVisible(false);
			editButton.setVisible(false);
		}
	}
	
	/**
	 * Show the dialog that allows users to pick which type of tile they would 
	 * like to add.
	 */
	public void addTileMenu(){		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		final AlertDialog.Builder photoSelector = 
				new AlertDialog.Builder(this);
		final String[] titles = {"Text Tile","Photo Tile",
				                  "{Placeholder} Video Tile",
				                  "{Placeholder} Audio Tile","Cancel"};   
		final String[] titlesPhoto = {"From File","Take New Photo","Cancel"};
        builder.setItems(titles, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
            	switch(item){
            	case(0):
            		TextTile tile = new TextTile();
					app.getPage().addTile(tile);
					addTile(app.getPage().getTiles().size() - 1, tile);   				
            		break;
            	case(1):
            		photoSelector.setItems(titlesPhoto, 
            				new DialogInterface.OnClickListener() {
            			 public void onClick(DialogInterface dialog, 
            					              int item) {
            	            	switch(item){
	            	            	case(0):
	            	            		Intent i = new Intent(
	            	            		Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
	            	            				 
	            	            		startActivityForResult(i, RESULT_LOAD_IMAGE);	
	            	            		
	            	            		
	            	            		break;
	            	            	case(1):
	            	            		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	            	            		startActivityForResult(intent, 100);
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
	
	 @Override
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	        super.onActivityResult(requestCode, resultCode, data);
	         
	        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
	        	Uri selectedImage = data.getData();
	        	String[] filePathColumn = { MediaStore.Images.Media.DATA };

	        	Cursor cursor = getContentResolver().query(selectedImage,
	        			filePathColumn, null, null, null);
	        	cursor.moveToFirst();

	        	int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
	        	String picturePath = cursor.getString(columnIndex);
	        	cursor.close();       	
	        	Bitmap pickedPhoto = BitmapFactory.decodeFile(picturePath);
	        	PhotoTile newPhoto = new PhotoTile();
	        	newPhoto.setImageFile(pickedPhoto);
	        	app.addTile(newPhoto);
	        }
	     
	     
	    }
	
	/**
	 * Updates a page to show any changes that have been made. These
	 * changes can also include whether the page is in view mode or
	 * edit mode.
	 * @param page The current page
	 */
	public void update(Page page) {
		
		setButtonVisibility();
		
		if (app.haveTilesChanged()) {
			updateTiles(page);
		}
		
		if (app.haveDecisionsChanged()) {
			updateDecisions(page);
		}
		
		if (app.haveCommentsChanged()) {
			updateComments(page);
		}
		
		if (app.hasEndingChanged()) {
			updateEnding(page);
		}
		
		app.finishedUpdating();
	}
		
	/**
	 * Handles removing or showing the proper buttons in both the action bar
	 * and the in the page.
	 */
	private void setButtonVisibility() {
		Button addTileButton = (Button) findViewById(R.id.addTile);
		Button addDecisionButton = (Button) findViewById(R.id.addDecision);
		
		final String myId = Secure.getString(
				getBaseContext().getContentResolver(), Secure.ANDROID_ID);
		final String storyID = app.getStory().getAuthor();
		if(myId.equals(storyID)){
		
			int visibility = 0;
		
			if (app.getEditing()) {
				visibility = View.VISIBLE;
			} else {
				visibility = View.GONE;
			}
				
			addTileButton.setVisibility(visibility);
			addDecisionButton.setVisibility(visibility);
		} else {
			addTileButton.setVisibility(View.GONE);
			addDecisionButton.setVisibility(View.GONE);
		}
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
		
		
		
		if (tile.getType() == "text") {
			TextView view = makeTileView();
			TextTile textTile = (TextTile) tile;
			//TextView textView = (TextView) view;
			
			view.setText(textTile.getText());

			tilesLayout.addView(view, i);
			
			if (app.getEditing()) {
				view.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						editTileMenu(v);
					}
				});
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
			
		} else if (tile.getType() == "photo") {
			
			PhotoTile photoTile = (PhotoTile) tile;
			ImageView imageView = new ImageView(app);
			imageView.setImageBitmap(photoTile.getImage());
			tilesLayout.addView(imageView, i);
	
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
	private TextView makeTileView() {
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
		TextView view = new TextView(this);

		// Set what the tiles look like
		view.setPadding(0, 5, 0, 6);
		if (app.getEditing()) {
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
	public void editTileMenu(final View view){
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
		
		if (app.getEditing()) {
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
		int whichDecision = decisionsLayout.indexOfChild(view);
		Decision decision = app.getPage().getDecisions().get(whichDecision);
		
		UUID toPageId = decision.getPageID();
		ArrayList<Page> pages = app.getStory().getPages();
		int toPagePosition = -1;
		for (int i = 0; i < pages.size(); i++) {

			UUID comparePage = pages.get(i).getId();
			System.out.println("toPageID: " + toPageId + "\ncomparePage: " + comparePage + "\nPage: " + app.getPage() + "\nDecision: " + decision.getPageID() + decision.getText());
			if (toPageId.equals(comparePage)) {
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
        		int decisionNumber = decisionsLayout.indexOfChild(decisionView);
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
		view.setText(comment.getTimestamp() + " - '" + comment.getText() + "'");
	    commentsLayout.addView(view);
	}
	
	/**
	 * Called when the add comment button is clicked. It creates a dialog that
	 * allows the user to input text and then save the comment.
	 * @param view
	 */
	private void onEditComment(View view) {
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
		if (app.getEditing()) {
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
