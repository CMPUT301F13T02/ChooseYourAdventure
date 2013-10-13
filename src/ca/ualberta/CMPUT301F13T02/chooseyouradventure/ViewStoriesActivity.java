package ca.ualberta.CMPUT301F13T02.chooseyouradventure;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

/**
 * @uml.dependency   supplier="ca.ualberta.CMPUT301F13T02.chooseyouradventure.EditStoryActivity"
 * @uml.dependency   supplier="ca.ualberta.CMPUT301F13T02.chooseyouradventure.ViewPageActivity"
 */
public class ViewStoriesActivity extends Activity {
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_stories_activity);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.view_stories, menu);
        return true;
    }



    
}
