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
