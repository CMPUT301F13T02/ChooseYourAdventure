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

import ca.ualberta.CMPUT301F13T02.chooseyouradventure.elasticsearch.ESHandler;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

/**
 * The main activity of the application. Displays a list of stories to read. <br />
 * <br />
 * In this activity a reader can:
 * <ol>
 *     <li> Click a story to begin reading at the first page </li>  
 *     <li> Long click a story to cache it to local storage </li>
 *     <li> Search for stories </li>
 * </ol>
 * In this activity an author can: 
 * <ol>
 *     <li> Add a new story </li>
 *     <li> Long click a story to edit the story </li>
 * </ol>
 */

public class ViewStoriesActivity extends Activity {
	private ListView mainPage;
	private Button createNew;
	ArrayList<String> storyText = new ArrayList<String>();
	ArrayList<Story> storyList = new ArrayList<Story>();
	private ControllerApp app; 
	private ESHandler eshandler = new ESHandler();
	
	ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_stories_activity);
        mainPage = (ListView) findViewById(R.id.mainView);
        createNew = (Button) findViewById(R.id.createButton);
        createNew.setOnClickListener(new OnClickListener() {
           
            public void onClick(View v) {
                createStory();
            }
        });
        
		
		
		updateTitles();
		adapter = new ArrayAdapter<String>(this,
				R.layout.list_item_base, storyText);
		mainPage.setAdapter(adapter);
		
		/**
		 * Activity to restructure Click and longClick listeners to work in a list view
		 *  directly based on http://android.konreu.com/developer-how-to/click-long-press-event-listeners-list-activity/
		 */
		mainPage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		    @Override
		    public void onItemClick(AdapterView<?> av, View v, int pos, long listNum) {
		        try {
					onListItemClick(v,pos,listNum);
				} catch (HandlerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		});

		mainPage.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
		    @Override
		    public boolean onItemLongClick(AdapterView<?> av, View v, int pos, long listNum) {
		        return onLongListItemClick(v,pos,listNum);
		    }
		});
		
        app = (ControllerApp) getApplication();
    }
    
    @Override
	public void onStart() {
        super.onStart();
        updateTitles();
        adapter.notifyDataSetChanged();
    }
        


    /**
     * Inflate the options menu; this adds items to the action bar if it is present 
     * 
     *  @param menu The menu to inflate
     *  @retun Success
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_stories, menu);
        return true;
    }
    
    
	
	
	
	
	protected void onListItemClick(View v, int pos, long id) throws HandlerException {	
	    jumpPage(v, pos);
	}
	
	protected boolean onLongListItemClick(View v, int pos, long id) { 
    	storyMenu(v, pos);
        return true;
    }
    
	
	/**
	 * Opens EditStoryActivity
	 * @param view Unused
	 */
    
    public void jumpEdit(View view) {
		Intent intent = new Intent(this, EditStoryActivity.class);
		startActivity(intent);
	}
    
    /**
     * Opens ViewPageActivity
     * 
     * @throws HandlerException
     * @param view Unused
     * @param pos The position of the story to open
     */
    public void jumpPage(View view, int pos) throws HandlerException {
    	Story story = storyList.get(pos);
		app.setStory(story);
		Page firstPage = story.getFirstpage();		
		Intent intent = new Intent(this, ViewPageActivity.class);	
		app.setPage(firstPage);		
		startActivity(intent);
	}
    
    /**
     * This function is for jumping to a new page after creating a new story, 
     * so it has to initialize some objects you wouldn't want to initialize inside the click listener
     * @param storyTitle
     * @param newPage
     * @param newStory
     * @throws HandlerException 
     */
    private void jumpEditNew(String storyTitle, Page newPage, Story newStory) throws HandlerException{
    	Intent intent = new Intent(this, EditStoryActivity.class);
    	newStory.setTitle(storyTitle);
    	newPage.setTitle("First Page");
    	newPage.setRefNum(1);
    	newStory.addPage(newPage);
    	newStory.setFirstpage(newPage.getId());
	    try

		{	
	    	storyList.add(newStory);
	    	updateTitles();
	    	adapter.notifyDataSetChanged();
			eshandler.addStory(newStory);
<<<<<<< HEAD
			
=======
			//eshandler.addPage(newPage); //This method is gone -- Konrad -- 11/02
>>>>>>> 5315fb4a0b7dd7220d13a93651950894fbd2de8b
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    app.setStory(newStory);
	    startActivity(intent);
	    
    	
    	
    	
    }
    
    
    /**
     * The options menu displayed when the user longClicks a story
     * @param v The view of the longClicked story
     */
	public void storyMenu(final View v, int pos){
			final String[] titles = {"Edit","{Placeholder} Upload","{Placeholder} Cache","{Placeholder} Delete","Cancel"};
			final Story story = storyList.get(pos);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.story_options);
            builder.setItems(titles, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                	switch(item){
                	case(0):
                		
        				app.setStory(story);
                		jumpEdit(v);
                		break;
                	case(1):
                		
                		break;
                	case(2):
                		
                		break;
                	case(3):
                		eshandler.deleteStory(story);
                		adapter.notifyDataSetChanged();
                		break;
                	}
                        
                    }});
            builder.show();
        }


    
    /**
     * A pop up menu for creating a new story. it Simply asks for a title and then builds some framework before passing off to the Edit Story mode.
     */
    private void createStory(){

    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle("Create New");
    	final Page newPage = new Page();
    	final Story newStory = new Story();
    	final EditText alertEdit = new EditText(this);
    	builder.setView(alertEdit);
    	builder.setMessage("Enter the title of your story")
    	.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            	String storyTitle = alertEdit.getText().toString();
            	try
				{
					jumpEditNew(storyTitle, newPage, newStory);
				} catch (HandlerException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	
            	
            }
        })
        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                
            }
        });
        builder.show();
    }
    
    private void updateTitles(){
    	try
		{
			storyList = eshandler.getAllStories();
		} catch (HandlerException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	storyText.clear();
    	if(storyList.size() != 0)
		{
			for (int i = 0; i < storyList.size(); i++) {
				storyText.add(storyList.get(i).getTitle());
			}
		}
    }

    

}
