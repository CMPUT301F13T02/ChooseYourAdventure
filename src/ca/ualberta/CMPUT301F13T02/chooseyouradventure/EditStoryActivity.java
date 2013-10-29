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

import ca.ualberta.CMPUT301F13T02.chooseyouradventure.elasticsearch.ESHandler;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class EditStoryActivity extends Activity {
	
	private Story currentStory;
	private ListView treePage;
	private Button createNew2;
	private Button deleteStory;
	private Page[] tempListText;
	private ArrayList<String> listText = new ArrayList<String>();
	private ArrayList<Page> tempPageList = new ArrayList<Page>();
	private ArrayAdapter<String> adapter;
	private ESHandler eshandler = new ESHandler();
	private ControllerApp controller;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_story_activity);
        treePage = (ListView) findViewById(R.id.treeView);
        createNew2 = (Button) findViewById(R.id.createButton2);
        deleteStory = (Button) findViewById(R.id.deleteButton);
        createNew2.setOnClickListener(new OnClickListener() {
           
            public void onClick(View v) {
              try
			{
				createPage();
			} catch (HandlerException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
              adapter.notifyDataSetChanged();
            }
        });
        deleteStory.setOnClickListener(new OnClickListener() {
            
            public void onClick(View v) {
              deleteCurrentStory();
            }
        });
        
        controller = (ControllerApp) getApplication();
		currentStory = controller.getStory();
		updateLists();
		
		
		
		
		
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
				R.layout.list_item_base, listText);
		treePage.setAdapter(adapter);
		
    }
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();	
		
		
		
	}
	
	
	protected void onListItemClick(View v, int pos, long id) {
		pageOptions(v, pos);
	}
	
	public void jumpPage(View view, int pos) throws HandlerException {
		/*
		String FP = currentStory.getFirstpage().toString();
		Page storyFP = eshandler.getPage(FP);*/
		Story story = controller.getStory();
		Page toPage = story.getPages().get(pos);
		controller.setPage(toPage);

    	Intent intent = new Intent(this, ViewPageActivity.class);
    	startActivity(intent);
	}
	
	private void createPage() throws HandlerException{

    	Page newPage = new Page();
    	//temp
    	newPage.setTitle("NEWPAGE");
    	currentStory.addPage(newPage);
    	eshandler.updateStory(currentStory);
    	updateLists();
    	adapter.notifyDataSetChanged();
    	
    }
	
	private void deleteCurrentStory(){
		
		eshandler.deleteStory(currentStory);
		
		finish();
	}
	
	private void updateLists(){
		tempPageList = currentStory.getPages();
		listText.clear();
		int counter = 0;
		tempListText = tempPageList.toArray(new Page[tempPageList.size()]);
		if (tempListText.length != 0){
			do{
				listText.add(tempListText[counter].getTitle());
				counter++;
			} while (counter < tempPageList.size());
		}
		
		

	}
	public void pageOptions(final View v, final int pos){
		final Page currentPage = tempListText[pos];
		final Page FP = currentStory.getFirstpage();
		String[] titlesA = {"Goto/Edit","Cancel"};
		String[] titlesB = {"Goto/Edit","Assign as First Page","Delete","Cancel"};
		final String[] titles;
		if(currentPage == FP){
			titles = titlesA;
		}
		else
		{
			titles = titlesB;
		}
		
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.page_options);
        builder.setItems(titles, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
            	if(currentPage == FP)
				{
            		switch(item){
    	            	case(0):
    	            		try {
    							jumpPage(v, pos);
    						} catch (HandlerException e1) {
    							// TODO Auto-generated catch block
    							e1.printStackTrace();
    						}
    	            		break;
            		}
				}
            	else
            	{
	            	switch(item){
	            	case(0):
	            		try {
							jumpPage(v, pos);
						} catch (HandlerException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
	            		break;
	            	case(1):
	            		UUID newID = currentPage.getId();
	            		currentStory.setFirstpage(newID);
	            		break;
	            	case(2):
						try
						{	
							tempPageList.remove(currentPage);
							updateLists();
							currentStory.deletePage(currentPage);
							eshandler.updateStory(currentStory);
							adapter.notifyDataSetChanged();
							
							break;
						} catch (HandlerException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	            		break;
	            	          	
	            	}
            	}
                    
                }});
        builder.show();
    }

}
