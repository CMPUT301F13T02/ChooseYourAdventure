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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class ViewPageActivity extends Activity {
	
	private Page page;
	private ArrayList<Tile> tiles;
	private ArrayList<Decision> decisions;
	private ArrayList<Comment> comments;
	
	private TileAdapter tilesAdapter;
	private DecisionAdapter decisionsAdapter;
	private CommentsAdapter commentsAdapter;
    
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_page_activity);
    }

	@Override
	public void onResume() {
        super.onResume();
        /**
         * Pull all the information we need from the page.
         */
        getPage();
        tiles = page.getTiles();
        decisions = page.getDecisions();
        comments = page.getComments();
        
        displayPage();
	}
	
	/**
	 * Create an options menu.
	 */
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        makeMenu(menu);
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
		editPage.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
	}
	
	/**
	 * Handles what to do when an item of the action bar is pressed.
	 * @param item
	 * @return
	 */
	private boolean menuItemClicked(MenuItem item) {
		switch (item.getItemId()) {
		case 0:
			break;
		}
		return true;
	}
	
	/**
	 * Pulls page from the intent object.
	 */
	private void getPage() {
		Intent intent = getIntent();
		
		// TODO TAKE THIS OUT when pages are actually passed in intent
		page = createFakePage();
		
		//page = (Page) intent.getParcelableExtra("currentPage");
	}
	
	/**
	 * Puts each list in an adapter that will display it properly and hands
	 * the adapter to the proper ListView.
	 */
	private void displayPage() {
		// First ListView. Used for the tiles.
		tilesAdapter = new TileAdapter(tiles, this);
		ListView tilesView = (ListView) findViewById(R.id.tilesView);
		tilesView.setAdapter(tilesAdapter);
		
		// Second ListView. Used for the Decisions.
		decisionsAdapter = new DecisionAdapter(decisions, this);
		ListView decisionsView = (ListView) findViewById(R.id.decisionsView);
		decisionsView.setAdapter(decisionsAdapter);
		
		// Third ListView. Used for the Comments.
		commentsAdapter = new CommentsAdapter(comments, this);
		ListView commentsView = (ListView) findViewById(R.id.commentsView);
		commentsView.setAdapter(commentsAdapter);
	}

	private Page createFakePage() {
		Page newPage = new Page();
		TextTile newTile = new TextTile("This is my experiment TextTile");
		newPage.addTile(newTile);
		Comment newComment = new Comment("This is my experiment Comment");
		newPage.addComment(newComment);
		return newPage;
	}
	
}


