package ca.ualberta.CMPUT301F13T02.chooseyouradventure;



import java.util.ArrayList;



import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * @uml.dependency   supplier="ca.ualberta.CMPUT301F13T02.chooseyouradventure.EditStoryActivity"
 * @uml.dependency   supplier="ca.ualberta.CMPUT301F13T02.chooseyouradventure.ViewPageActivity"
 */
public class ViewStoriesActivity extends Activity {
	private ListView lv;
	private String[] listText;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_stories_activity);
        lv = (ListView) findViewById(R.id.mainView);
        
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
		lv.setAdapter(adapter);
		
		
	}
    
    public void jumpEdit(View view) {
		Intent intent = new Intent(this, EditStoryActivity.class);
		startActivity(intent);
	}
    
    public void jumpPage(View view) {
		Intent intent = new Intent(this, ViewPageActivity.class);
		startActivity(intent);
	}



    
}
