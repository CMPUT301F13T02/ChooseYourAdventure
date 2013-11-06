package ca.ualberta.CMPUT301F13T02.chooseyouradventure.test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.ControllerApp;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.EditStoryActivity;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.Page;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.Story;

public class TestEditStoryActivity extends ActivityInstrumentationTestCase2<EditStoryActivity> {
	
	private EditStoryActivity activity;
	
	private Button addPageButton;
	private Button deleteStoryButton;
	
	private Story story;
	
	private static final ControllerApp app = ControllerApp.getInstance();
	
	private static int numPages = 1;

	public TestEditStoryActivity() {
		super(EditStoryActivity.class);
		// TODO Auto-generated constructor stub
	}
	
	protected void setUp() throws Exception {
		story = new Story();
		app.setStory(story);
		
		activity = getActivity();
		
		addPageButton = (Button) activity.findViewById(ca.ualberta.CMPUT301F13T02.chooseyouradventure.R.id.createButton2);
		deleteStoryButton = (Button) activity.findViewById(ca.ualberta.CMPUT301F13T02.chooseyouradventure.R.id.deleteButton);
		
	}
	
	public void testStateDestroy() {
		story = app.getStory();
		story.addPage(new Page());
		
		activity.finish();
		activity = getActivity();
		
		int np = story.getPages().size();
		assertEquals(numPages, np);
		//assertFalse(numPages == np);
	}
	
	public void testAddPage() {
		story = app.getStory();
		story.getPages().clear();
		
		story.addPage(new Page());
		int np = story.getPages().size();
		assertEquals(np, 1);
		//assertFalse(np == 1);
	}
	
	public void testDeletePage() {
		story = app.getStory();
		story.getPages().clear();
		
		story.addPage(new Page());
		int np = story.getPages().size();
		
		story.getPages().remove(0);
		assertFalse(np == story.getPages().size());
		//assertTrue(np == story.getPages().size());
		
	}
	
	public void testLayout() {
		assertNotNull(addPageButton);
		assertNotNull(deleteStoryButton);
	}

}
