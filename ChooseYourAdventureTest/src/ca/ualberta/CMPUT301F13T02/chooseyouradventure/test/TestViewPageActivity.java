package ca.ualberta.CMPUT301F13T02.chooseyouradventure.test;

import ca.ualberta.CMPUT301F13T02.chooseyouradventure.AudioTile;
import com.jayway.android.robotium.solo.Solo;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.Comment;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.ControllerApp;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.Decision;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.Page;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.PhotoTile;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.Story;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.TextTile;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.Tile;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.VideoTile;
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
	
	private static final ControllerApp app = ControllerApp.getInstance();
	
	private Page page;
	private Story story;
	
	private static int numComments = 1;
	private static int numTiles = 1;
	private static int numDecisions = 1;
	

	public TestViewPageActivity() {
		super(ViewPageActivity.class);
		// TODO Auto-generated constructor stub
	}
	
	
	protected void setUp() throws Exception{
		super.setUp();
		page = new Page();
		story = new Story();
		story.setId("25");
		app.setPage(page);
		app.setStory(story);
		
		activity = getActivity();
		
		addTileButton = (Button) activity.findViewById(ca.ualberta.CMPUT301F13T02.chooseyouradventure.R.id.addTile);
		addDecisionButton = (Button) activity.findViewById(ca.ualberta.CMPUT301F13T02.chooseyouradventure.R.id.addDecision);
		
		pageEnding = (TextView) activity.findViewById(ca.ualberta.CMPUT301F13T02.chooseyouradventure.R.id.pageEnding);
		addComment = (TextView) activity.findViewById(ca.ualberta.CMPUT301F13T02.chooseyouradventure.R.id.addComment);
		commentsTitle = (TextView) activity.findViewById(ca.ualberta.CMPUT301F13T02.chooseyouradventure.R.id.commentTitle);
		
	}
	
	
	public void testLayout() {
		assertNotNull(addTileButton);
		assertNotNull(addDecisionButton);
		assertNotNull(pageEnding);
		assertNotNull(addComment);
		assertNotNull(commentsTitle);
	}
	
	
	
	
	public void testStateDestroy() {
		page = app.getPage();
		page.addComment(new Comment("A comment"));
		page.addTile(new TextTile());
		page.addDecision(new Decision("A decision", new Page()));
		
		activity.finish();
		activity = getActivity();
		
		int c = page.getComments().size();
		int t = page.getTiles().size();
		int d = page.getDecisions().size();
		
		assertEquals(numComments, c);
		assertEquals(numTiles, t);
		assertEquals(numDecisions, d);
		
	}
	
	
	/*
	public void testAddTextTile() {
		page = app.getPage();
		page.getTiles().clear();
		page.addTile(new TextTile());
		int t = page.getTiles().size();
		assertEquals(t, 1);
		Tile tile = page.getTiles().get(0);
		assertTrue(tile.getType() == "text");
	}
	
	
	public void testAddPhotoTile() {
		page = app.getPage();
		page.getTiles().clear();
		page.addTile(new PhotoTile());
		int t = page.getTiles().size();
		assertEquals(t, 1);
		Tile tile = page.getTiles().get(0);
		assertTrue(tile.getType() == "photo");
	}

	public void testAddAudioTile() {
		page = app.getPage();
		page.getTiles().clear();
		page.addTile(new AudioTile());
		int t = page.getTiles().size();
		assertEquals(t, 1);
		Tile tile = page.getTiles().get(0);
		assertTrue(tile.getType() == "audio");
	}
	
	public void testAddVideoTile() {
		page = app.getPage();
		page.getTiles().clear();
		page.addTile(new VideoTile());
		int t = page.getTiles().size();
		assertEquals(t, 1);
		Tile tile = page.getTiles().get(0);
		assertTrue(tile.getType() == "video");
	}
	*/
	
	
	
	public void testAddTextTile() {
		page.getTiles().clear();
		
	    Solo solo = new Solo(getInstrumentation(), getActivity());
	    getInstrumentation().waitForIdleSync();
	    
		activity.runOnUiThread(
				new Runnable() {
					public void run() {
						addTileButton.requestFocus();
						addTileButton.performClick();
					}
				});
		solo.clickOnText("TextTile");
		page = app.getPage();
		int l = page.getTiles().size();
		assertEquals(l, 1);
		
	}
	
	public void testAddVideoTile() {
		page.getTiles().clear();
		
	    Solo solo = new Solo(getInstrumentation(), getActivity());
	    getInstrumentation().waitForIdleSync();
	    
		activity.runOnUiThread(
				new Runnable() {
					public void run() {
						addTileButton.requestFocus();
						addTileButton.performClick();
					}
				});
		solo.clickOnText("{Placeholder} VideoTile");
		page = app.getPage();
		int l = page.getTiles().size();
		assertEquals(l, 1);
		
	}
	
	public void testAddAudioTile() {
		page.getTiles().clear();
		
	    Solo solo = new Solo(getInstrumentation(), getActivity());
	    getInstrumentation().waitForIdleSync();
	    
		activity.runOnUiThread(
				new Runnable() {
					public void run() {
						addTileButton.requestFocus();
						addTileButton.performClick();
					}
				});
		solo.clickOnText("{Placeholder} AudioTile");
		page = app.getPage();
		int l = page.getTiles().size();
		assertEquals(l, 1);
		
	}
	
	public void testAddPhotoTile() {
		page.getTiles().clear();
		
	    Solo solo = new Solo(getInstrumentation(), getActivity());
	    getInstrumentation().waitForIdleSync();
	    
		activity.runOnUiThread(
				new Runnable() {
					public void run() {
						addTileButton.requestFocus();
						addTileButton.performClick();
					}
				});
		solo.clickOnText("PhotoTile");
		page = app.getPage();
		int l = page.getTiles().size();
		assertEquals(l, 1);
		
	}
	
	
	
	public void testAddComment() {
		
	    Solo solo = new Solo(getInstrumentation(), getActivity());
	    getInstrumentation().waitForIdleSync();
	    
		activity.runOnUiThread(
				new Runnable() {
					public void run() {
						addComment.requestFocus();
						addComment.performClick();
					}
				});
		
	    solo.enterText(0, "New Comment");
	    solo.clickOnButton("Save");
	    page = app.getPage();
	    int l = page.getComments().size();
	    assertEquals(l, 1);
				
	}
	
	
	
	
	public void testAddDecision() {
		page.getDecisions().clear();
		
	    Solo solo = new Solo(getInstrumentation(), getActivity());
	    getInstrumentation().waitForIdleSync();
	    
		activity.runOnUiThread(
				new Runnable() {
					public void run() {
						addDecisionButton.requestFocus();
						addDecisionButton.performClick();
					}
				});
		page = app.getPage();
		int l = page.getDecisions().size();
		assertEquals(l, 1);
	}
	
	/*
	public void testAddComment() {
		activity.runOnUiThread(
				new Runnable() {
					public void run() {
						addComment.requestFocus();
						addComment.performClick();
						page = app.getPage();
						Comment c = new Comment("Hello");
						page.addComment(c);
						ArrayList<Comment> comments = page.getComments();
						int l = comments.size();
						//int l = app.getPage().getTiles().size();
						//page.getTiles().size();
						assertTrue(l>0);
						//Log.d("Size of tiles", String.valueOf(l));
						//assertTrue(false);
						//assertTrue(l == 6);
						
					}
				});
				
	}
	
	*/
	

}