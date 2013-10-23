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








import android.os.Bundle;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * @uml.dependency   supplier="ca.ualberta.CMPUT301F13T02.chooseyouradventure.EditStoryActivity"
 * @uml.dependency   supplier="ca.ualberta.CMPUT301F13T02.chooseyouradventure.ViewPageActivity"
 */
public class ViewStoriesActivity extends Activity {
	private ListView mainPage;
	private String[] listText;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_stories_activity);
        mainPage = (ListView) findViewById(R.id.mainView);
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.view_stories, menu);
        return true;
    }
    
    
	
	
	
	//Setting up the ListView for use
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();	
		
		//Temporary Initializer to test Listviews
		ArrayList<String> newList = new ArrayList<String>();
		newList.add("Hello World");
		listText = newList.toArray(new String[newList.size()]);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.list_item_base, listText);
		mainPage.setAdapter(adapter);
		
		
		//based on http://android.konreu.com/developer-how-to/click-long-press-event-listeners-list-activity/
		mainPage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		    @Override
		    public void onItemClick(AdapterView<?> av, View v, int pos, long listNum) {
		        onListItemClick(v,pos,listNum);
		    }
		});

		mainPage.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
		    @Override
		    public boolean onItemLongClick(AdapterView<?> av, View v, int pos, long listNum) {
		        return onLongListItemClick(v,pos,listNum);
		    }
		});


		
		
	}
    
    public void jumpEdit(View view) {
		Intent intent = new Intent(this, EditStoryActivity.class);
		startActivity(intent);
	}
    
    public void jumpPage(View view) {
		Intent intent = new Intent(this, ViewPageActivity.class);
		startActivity(intent);
	}
    
    protected void onListItemClick(View v, int pos, long id) {
	    jumpPage(v);
	}
    
    

    
    
   
	public void storyMenu(final View v){
			final String[] titles = {"Edit","Upload","Cache","Delete","Cancel"};
			
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.story_options);
            builder.setItems(titles, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                	switch(item){
                	case(0):
                		jumpEdit(v);
                		break;
                	case(1):
                		
                		break;
                	case(2):
                		
                		break;
                	case(3):
                		
                		break;
                	}
                        
                    }});
            builder.show();
        }


    protected boolean onLongListItemClick(View v, int pos, long id) { 
    	storyMenu(v);
        return true;
    }


	


	


    
}
