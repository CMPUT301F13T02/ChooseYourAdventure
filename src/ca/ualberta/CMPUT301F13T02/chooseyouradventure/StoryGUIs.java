package ca.ualberta.CMPUT301F13T02.chooseyouradventure;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.provider.Settings.Secure;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StoryGUIs {
	private ControllerApp app;
	private ViewStoriesActivity storyActivity;
	private StoryController storyController; 
	private PageController pageController;
	

	
	public StoryGUIs(ControllerApp app, ViewStoriesActivity storyActivity) {
		super();
		this.app = app;
		this.storyActivity = storyActivity;
		storyController = app.getStoryController();
        pageController = app.getPageController();
        
	}
	
	protected AlertDialog storyMenuGUI(ArrayList<Story> storyList, int pos , final Handler eshandler, final Handler dbhandler){
		final Story story = storyList.get(pos);
		AlertDialog.Builder builder = new AlertDialog.Builder(storyActivity);
		final String[] titles;
		final String[] titlesA = { storyActivity.getString(R.string.cache), storyActivity.getString(R.string.upload), storyActivity.getString(R.string.edit), 
								   storyActivity.getString(R.string.delete), storyActivity.getString(R.string.cancel) };
		final String[] titlesB = { storyActivity.getString(R.string.cache), storyActivity.getString(R.string.uploadCopy), storyActivity.getString(R.string.cancel) };
		final String myId = Secure.getString(
				app.getBaseContext().getContentResolver(), Secure.ANDROID_ID);
		final String storyID = story.getAuthor();
		if(myId.equals(storyID)){
			titles = titlesA;
			builder.setTitle(R.string.story_options_author);
		}
		else {
			titles = titlesB;
			builder.setTitle(R.string.story_options_user);
		}
        builder.setItems(titles, new DialogInterface.OnClickListener() {
        	public void onClick(DialogInterface dialog, int item) {
            	switch(item){

            	case(0): //cache
            		//set to local handler, 1 means it is local
            		story.setHandler(dbhandler);
                	story.setAuthor(myId);

            		try {
            			story.getHandler().addStory(story);
            		} catch (HandlerException e) {
            			e.printStackTrace();
            		}

            		storyActivity.refresh();
            		break;
            	case(1): //upload
            		// the 0 passed means it isn't local
            		story.setHandler(eshandler);
        			//create a new story because you have to change author ID
        			story.setAuthor(myId);
        			//set it to be online initially
					try {
						eshandler.addStory(story);
					} catch (HandlerException e) {
						e.printStackTrace();
					}
					storyActivity.refresh();
            		break;
            	case(2): //edit story
            		if(myId.equals(storyID)){          			
                		app.jump(EditStoryActivity.class, story, null);
            		}
            		else{}
            		break;
            	case(3): //delete
            		try {
						story.getHandler().deleteStory(story);
					} catch (HandlerException e) {
						e.printStackTrace();
					}
            		storyActivity.refresh();
            		break;
            	}
            }

			
			
        });
        return builder.create();
	}
	
	protected AlertDialog createStoryGUI(){
		AlertDialog.Builder builder = new AlertDialog.Builder(storyActivity);
    	builder.setTitle(storyActivity.getString(R.string.createNew));
    	final LinearLayout layout = new LinearLayout(storyActivity);
    	layout.setOrientation(LinearLayout.VERTICAL);
    	
    	final EditText alertEdit = new EditText(storyActivity);
    	alertEdit.setSingleLine(true);
    	layout.addView(alertEdit);
    	
    	final TextView alertText = new TextView(storyActivity);
    	alertText.setText(storyActivity.getString(R.string.useCountersAndCombat));
    	layout.addView(alertText);
    	
    	final CheckBox check = new CheckBox(storyActivity);
    	layout.addView(check);
    	
    	
    	builder.setView(layout);
    	builder.setMessage(storyActivity.getString(R.string.enterStoryTitle))
    	.setPositiveButton(storyActivity.getString(R.string.save), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            	
					try {
						
						Counters baseCount = new Counters();
						baseCount.setBasic("0", "100");
						app.initializeNewStory(alertEdit.getText().toString(), baseCount, check.isChecked());
						
						
						
						storyActivity.refresh();
					} catch (HandlerException e) {
						e.printStackTrace();
					}

            }
        })
        .setNegativeButton(storyActivity.getString(R.string.cancel), null);
    	return builder.create();
	}
}

			
	
     

