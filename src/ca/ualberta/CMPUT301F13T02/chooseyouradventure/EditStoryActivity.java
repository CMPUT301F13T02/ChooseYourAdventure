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
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

/**
 * This Activity allows a story's author to edit a story by adding adding pages,
 * deleting pages, setting the first page, or deleting the story.
 * 
 * This class is part of the view of the application.
 */

public class EditStoryActivity extends Activity {
	private ListView treePage;
	private Button createNew2;
	private Button deleteStory;
	private ArrayList<String> pageText = new ArrayList<String>();
	private ArrayList<Page> pageList = new ArrayList<Page>();
	private ArrayAdapter<String> adapter;
	private ApplicationController app;
	private PageView gui;
	private StoryController storyController; 
	private static final int HELP_INDEX = 0;

	/**
	 * This binds the buttons the the views to this activity
	 * and sets the appropriate onclick listeners
	 */
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_story_activity);
        treePage = (ListView) findViewById(R.id.treeView);
        createNew2 = (Button) findViewById(R.id.createButton2);
        deleteStory = (Button) findViewById(R.id.deleteButton);
        app = (ApplicationController) getApplication();
        gui = new PageView(app, this);
        storyController = app.getStoryController();
        createNew2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
              gui.createPageGUI();
              adapter.notifyDataSetChanged();
            }
        });
        deleteStory.setOnClickListener(new OnClickListener() {
            
            public void onClick(View v) {
            	Story story = storyController.getStory();
            	try {
					story.getHandler().deleteStory(story);
				} catch (HandlerException e) {
					e.printStackTrace();
				}
            	finish();
            }
        });       
        
        
		pageList = storyController.getStory().getPages();
		pageText = app.updateView(pageList, pageText);
		/**
		 * Activity to restructure Click and longClick listeners to work in a list view
		 *  directly based on http://android.konreu.com/developer-how-to/click-long-press-event-listeners-list-activity/
		 */
		treePage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		    @Override
		    public void onItemClick(AdapterView<?> av, View v, int pos, long listNum) {
		        onListItemClick(v,pos,listNum);
		    }
		});
		adapter = new ArrayAdapter<String>(this,
				R.layout.list_item_base, pageText);
		treePage.setAdapter(adapter);
    }
	
	@Override
	public void onStart() {
        super.onStart();
        refresh();
    }
	
	/**
	 * Inflate the options menu; this adds items to the action bar if it is present 
	 * 
	 *  @param menu The menu to inflate
	 *  @return Success
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
	
		MenuItem help = menu.add(0, HELP_INDEX, HELP_INDEX, getString(R.string.help));
		help.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
	
	    return true;
	}

	/**
	 * Callback for clicking an item in the menu.
	 * 
	 * @param item The item that was clicked
	 * @return Success
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		switch (item.getItemId()) {
		case HELP_INDEX:
	
			AlertDialog dialog = HelpDialogFactory.create(R.string.edit_story_help, this);
			dialog.show();
	        
			break;
		}
		return true;
	}

	/**
	 * Click listener for the list of pages. It simply calls pageOptions
	 * and passes it the position of the selected page. 
	 * @param v
	 * @param pos
	 * @param id
	 */
	protected void onListItemClick(View v, int pos, long id) {
		gui.pageOptionsGUI(pos);;
	}
	

	
	/**
	 * This rebuilds the ListView by recollecting data from the controller
	 */
	public void refresh(){
		pageList = storyController.getStory().getPages();
		pageText = app.updateView(pageList, pageText);
		adapter.notifyDataSetChanged();
	}	
}
