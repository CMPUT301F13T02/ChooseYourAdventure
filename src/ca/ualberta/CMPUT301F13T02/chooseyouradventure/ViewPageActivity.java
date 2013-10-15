package ca.ualberta.CMPUT301F13T02.chooseyouradventure;

import android.app.Activity;
import android.os.Bundle;

public class ViewPageActivity extends Activity {
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
	}
	
	@Override
	public void onResume() {
		MyApplication app = (MyApplication) getApplication();
		displayPage(app);
	}
	
	private void displayPage(MyApplication app) {
		Page page = app.getPage();
		
		for (Tile tile : page.getTilesList()) {
			
		}
	}
}
