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
import android.os.StrictMode;
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
 * @uml.dependency   supplier="ca.ualberta.CMPUT301F13T02.chooseyouradventure.EditStoryActivity"
 * @uml.dependency   supplier="ca.ualberta.CMPUT301F13T02.chooseyouradventure.ViewPageActivity"
 */
public class ViewStoriesActivity extends Activity {
	private ListView mainPage;
	private Story[] tempListText;
	private Button createNew;
	ArrayList<String> listText = new ArrayList<String>();
	ArrayList<Story> tempStoryList = new ArrayList<Story>();
	private ControllerApp controller; 
	private ESHandler eshandler = new ESHandler();
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
        /**
		 * Temporary Initializer to test ListViews
		 */
        /*
		Story tempStory = new Story();
		tempStory.setTitle("Magical Giraffe Mamba");
		tempStoryList.add(tempStory);
		*/
        /*
		
		try {
			tempStoryList = eshandler.getAllStories();
		} catch (HandlerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		int counter = 0;
		tempListText = tempStoryList.toArray(new Story[tempStoryList.size()]);
		if(tempListText.length != 0)
		{
			do{
				listText.add(tempListText[counter].getTitle());
				counter++;
			} while (counter < tempStoryList.size());
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.list_item_base, listText);
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
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy); 
        controller = (ControllerApp) getApplication();
    }


  
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
	 * "jump" functions are just the shorthand for functions that switch between activities
	 * @param view
	 */
    
    public void jumpEdit(View view) {
		Intent intent = new Intent(this, EditStoryActivity.class);
		startActivity(intent);
	}
    
    public void jumpPage(View view, int pos) throws HandlerException {
    	Story story = tempListText[pos];
		controller.setStory(story);
		String FP = story.getFirstpage().toString();
		Page storyFP = eshandler.getPage(FP);
		controller.setPage(storyFP);
    	Intent intent = new Intent(this, ViewPageActivity.class);	
		startActivity(intent);
	}
    /**
     * This function is for jumping to a new page after creating a new story, 
     * so it has to initialize some objects you wouldn't want to initialize insid ethe click listener
     * @param storyTitle
     * @param newPage
     * @param newStory
     * @throws HandlerException 
     */
    private void jumpEditNew(String storyTitle, Page newPage, Story newStory) throws HandlerException{
    	Intent intent = new Intent(this, EditStoryActivity.class);
    	newStory.setTitle(storyTitle);
    	newPage.setTitle("First Page");
    	newStory.addPage(newPage);
    	newStory.setFirstpage(newPage.getId());

    
	    try
		{	
	    	
			eshandler.addStory(newStory);
			eshandler.addPage(newPage);
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    controller.setStory(newStory);
	    startActivity(intent);
	    
    	
    	
    	
    }
    
    
    /**
     * The options menu displayed when the user longClicks a story
     * @param v
     */
	public void storyMenu(final View v, int pos){
			final String[] titles = {"Edit","{Placeholder} Upload","{Placeholder} Cache","Delete","Cancel"};
			final Story story = tempListText[pos];
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.story_options);
            builder.setItems(titles, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                	switch(item){
                	case(0):
                		
        				controller.setStory(story);
                		jumpEdit(v);
                		break;
                	case(1):
                		
                		break;
                	case(2):
                		
                		break;
                	case(3):
                		eshandler.deleteStory(null);
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

    

}
