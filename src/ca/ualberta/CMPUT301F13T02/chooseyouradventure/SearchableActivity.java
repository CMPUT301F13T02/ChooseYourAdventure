package ca.ualberta.CMPUT301F13T02.chooseyouradventure;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import ca.ualberta.CMPUT301F13T02.chooseyouradventure.elasticsearch.ESHandler;
import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.view.Menu;
import android.widget.ArrayAdapter;

public class SearchableActivity extends ListActivity {
	
	private ESHandler handler = new ESHandler();
	private ArrayList<Story> stories = new ArrayList<Story>();
	ArrayList<String> titles = new ArrayList<String>();
	private ControllerApp app;
	private ArrayAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_search_stories);
		
		adapter = new ArrayAdapter<String>(this, R.layout.list_item_base, titles);
		setListAdapter(adapter);
		
		
		Intent intent = getIntent();
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);
			try {
				performSearch(query);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (HandlerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	

	private void performSearch(String query) throws UnsupportedEncodingException, HandlerException {
		stories = handler.search(query);
		displayResults(stories);
	}


	private void displayResults(ArrayList<Story> results) {
		titles = app.updateView(stories, titles);
		adapter.notifyDataSetChanged();
		
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_stories, menu);
		return true;
	}

}
