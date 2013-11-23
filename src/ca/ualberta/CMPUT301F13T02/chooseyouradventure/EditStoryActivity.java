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
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
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
	private ControllerApp app;
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
        app = (ControllerApp) getApplication();
        storyController = app.getStoryController();
        createNew2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
              try
			{
				createPage();
			} catch (HandlerException e)
			{
				e.printStackTrace();
			}
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
		pageOptions(pos);
	}
	
	/**
	 * This creates a page
	 * @throws HandlerException
	 */
	private void createPage() throws HandlerException{

    	final LinearLayout layout = (LinearLayout) View.inflate(this, R.layout.create_page_dialog, null);
    	final EditText titleEdit = (EditText) layout.findViewById(R.id.create_page_dialog_edittext);
    	final EditText healthEdit = (EditText) layout.findViewById(R.id.create_page_dialog_health_edittext);
    	final EditText nameEdit = (EditText) layout.findViewById(R.id.create_page_dialog_name_edittext);
    	final CheckBox check = (CheckBox) layout.findViewById(R.id.create_page_dialog_checkbox);
    	final LinearLayout fightingLayout = (LinearLayout) layout.findViewById(R.id.create_page_dialog_fighting_options);
    	
    	if(!storyController.getStory().isUsesCombat())
    		fightingLayout.setVisibility(View.GONE);
    	nameEdit.setText("Enemy");
    	healthEdit.setText("0");
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setView(layout);
    	builder.setTitle(getString(R.string.createNew));
    	builder.setMessage(getString(R.string.enterPageTitle))
    	.setPositiveButton(getString(R.string.save), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            	storyController.newTitle(titleEdit.getText().toString(), check.isChecked(), healthEdit.getText().toString(), nameEdit.getText().toString());         	
            	refresh();
            }
        })
        .setNegativeButton(getString(R.string.cancel), null);
        builder.show();
    }

	/**
	 * This shows the user a list of options on a story
	 * @param Input from longclick
	 */
	public void pageOptions(final int pos){
		final Story story = storyController.getStory();
        final AlertDialog.Builder titleEditor = new AlertDialog.Builder(this);
		final Page currentPage = story.getPages().get(pos);
		final Page FP = storyController.grabFirstPage();

		String[] titlesA = { getString(R.string.gotoEdit), getString(R.string.pageProperties), getString(R.string.cancel) };
		String[] titlesB = { getString(R.string.gotoEdit), getString(R.string.pageProperties), getString(R.string.assignFirst),
							getString(R.string.delete), getString(R.string.cancel) };

		final String[] titles;

		if(currentPage == FP)
			titles = titlesA;
		else
			titles = titlesB;
        
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.page_options);
        builder.setItems(titles, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {
            	
            	switch(item){
            	case(0):
            		app.setEditing(true);
            		app.jump(ViewPageActivity.class,story,story.getPages().get(pos));
            		
            	break;
            	case(1):
            		
                	final LinearLayout layout = (LinearLayout) View.inflate(titleEditor.getContext(), R.layout.create_page_dialog, null);
	            	final LinearLayout fightingLayout = (LinearLayout) layout.findViewById(R.id.create_page_dialog_fighting_options);
	            	final EditText titleEdit = (EditText) layout.findViewById(R.id.create_page_dialog_edittext);
	            	final EditText healthEdit = (EditText) layout.findViewById(R.id.create_page_dialog_health_edittext);
                	final EditText nameEdit = (EditText) layout.findViewById(R.id.create_page_dialog_name_edittext);
                	final CheckBox check = (CheckBox) layout.findViewById(R.id.create_page_dialog_checkbox);
	            	
	            	if(!story.isUsesCombat()) {
	            		fightingLayout.setVisibility(View.GONE);
	            	}
	            	else {
	            		
	                	

	            		check.setChecked(currentPage.isFightingFrag());
	            		healthEdit.setText("" + currentPage.getEnemyHealth());
	            		nameEdit.setText(currentPage.getEnemyName());
	            	}
            		
	            	titleEdit.setText(currentPage.getTitle());
	            	
            		titleEditor.setTitle(getString(R.string.createNew));
            		titleEditor.setView(layout);
            		titleEditor.setMessage(getString(R.string.enterPageTitle))
            		.setPositiveButton(getString(R.string.save), new DialogInterface.OnClickListener() {

            			public void onClick(DialogInterface dialog, int id) {
            				String pageTitle = titleEdit.getText().toString();
            				if(story.isUsesCombat() == true){
            					storyController.updateFightTitle(pageTitle, check.isChecked(), healthEdit.getText().toString(), nameEdit.getText().toString(), currentPage); 
            				}
            				else{
            					storyController.updateTitle(pageTitle, currentPage);    
            				}
            				refresh();
            			}
            		})
            		.setNegativeButton(getString(R.string.cancel), null);

            		titleEditor.show();
            		
            		break;

            	case(2):
            		storyController.updateFP(currentPage);
            		refresh();
            		break;
            	case(3):
            		storyController.removePage(currentPage);
            		refresh();
            		break;
            	}

            }	
                });
        builder.show();
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
