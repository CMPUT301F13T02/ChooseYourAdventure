package ca.ualberta.CMPUT301F13T02.chooseyouradventure.test;

import ca.ualberta.CMPUT301F13T02.chooseyouradventure.ControllerApp;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.Page;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.ViewPageActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.TextView;

public class TestViewPageActivity extends ActivityInstrumentationTestCase2<ViewPageActivity> {
	
	private ViewPageActivity activity;
	
	private Button addTileButton;
	private Button addDecisionButton;
	
	private TextView pageEnding;
	private TextView addComment;
	private TextView commentsTitle;
	
	private ControllerApp app;
	
	private Page page;
	

	public TestViewPageActivity() {
		super(ViewPageActivity.class);
		// TODO Auto-generated constructor stub
	}
	
	
	protected void setUp() throws Exception{
		super.setUp();
		page = new Page();
		app = app.getInstance();
		app.setPage(page);
		
		activity = getActivity();
		
		addTileButton = (Button) activity.findViewById(ca.ualberta.CMPUT301F13T02.chooseyouradventure.R.id.addTile);
		addDecisionButton = (Button) activity.findViewById(ca.ualberta.CMPUT301F13T02.chooseyouradventure.R.id.addDecision);
		
		pageEnding = (TextView) activity.findViewById(ca.ualberta.CMPUT301F13T02.chooseyouradventure.R.id.pageEnding);
		addComment = (TextView) activity.findViewById(ca.ualberta.CMPUT301F13T02.chooseyouradventure.R.id.addComment);
		commentsTitle = (TextView) activity.findViewById(ca.ualberta.CMPUT301F13T02.chooseyouradventure.R.id.commentTitle);
		
	}
	
	public void testLayout() {
		assertTrue(addTileButton != null);
		assertTrue(addDecisionButton != null);
		assertTrue(pageEnding != null);
		assertTrue(addComment != null);
		assertTrue(commentsTitle != null);
	}
	/*
	public void testdisplayPage() {
		activity.runOnUiThread(
				new Runnable() {
					public void run() {
						activity.displayPage();
					}
				});
	}
	*/
	
	public void testAddTile() {
		activity.runOnUiThread(
				new Runnable() {
					public void run() {
						addTileButton.requestFocus();
						addTileButton.performClick();
						int l = page.getTiles().size();
						assertTrue(l == 1);
						//assertTrue(l == 5);
						//assertTrue(1 == 6);
						
					}
				});
	}
	
	public void testAddDecision() {
		
	}
	
	public void testAddComment() {
		
	}

}
