package ca.ualberta.CMPUT301F13T02.chooseyouradventure;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

public class PageGUIs {
	private ControllerApp app;
	private EditStoryActivity storyActivity;
	private StoryController storyController; 
	private PageController pageController;
	

	
	public PageGUIs(ControllerApp app, EditStoryActivity storyActivity) {
		super();
		this.app = app;
		this.storyActivity = storyActivity;
		storyController = app.getStoryController();
        pageController = app.getPageController();
        
	}
	
	protected AlertDialog pageOptionsGUI(final int pos){
		final Story story = storyController.getStory();
        final AlertDialog.Builder titleEditor = new AlertDialog.Builder(storyActivity);
		final Page currentPage = story.getPages().get(pos);
		final Page FP = storyController.grabFirstPage();

		String[] titlesA = { storyActivity.getString(R.string.gotoEdit), storyActivity.getString(R.string.pageProperties), storyActivity.getString(R.string.cancel) };
		String[] titlesB = { storyActivity.getString(R.string.gotoEdit), storyActivity.getString(R.string.pageProperties), storyActivity.getString(R.string.assignFirst),
							storyActivity.getString(R.string.delete), storyActivity.getString(R.string.cancel) };

		final String[] titles;

		if(currentPage == FP)
			titles = titlesA;
		else
			titles = titlesB;
        
        AlertDialog.Builder builder = new AlertDialog.Builder(storyActivity);
        builder.setTitle(R.string.page_options);
        builder.setItems(titles, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {
            	
            	switch(item){
            	case(0):
            		app.setEditing(true);
            		app.jump(ViewPageActivity.class,story,story.getPages().get(pos));
            		
            	break;
            	case(1):
            		
                	final LinearLayout layout = (LinearLayout) View.inflate(titleEditor.getContext(), R.layout.create_page_dialog, null);
	            	final LinearLayout fightingLayout = (LinearLayout) layout.findViewById(R.id.create_page_dialog_fighting_options);
	            	final EditText titleEdit = (EditText) layout.findViewById(R.id.create_page_dialog_edittext);
	            	final EditText healthEdit = (EditText) layout.findViewById(R.id.create_page_dialog_health_edittext);
                	final EditText nameEdit = (EditText) layout.findViewById(R.id.create_page_dialog_name_edittext);
                	final CheckBox check = (CheckBox) layout.findViewById(R.id.create_page_dialog_checkbox);
	            	
	            	if(!story.isUsesCombat()) {
	            		fightingLayout.setVisibility(View.GONE);
	            	}
	            	else {
	            		
	                	

	            		check.setChecked(currentPage.isFightingFrag());
	            		healthEdit.setText("" + currentPage.getEnemyHealth());
	            		nameEdit.setText(currentPage.getEnemyName());
	            	}
            		
	            	titleEdit.setText(currentPage.getTitle());
	            	
            		titleEditor.setTitle(storyActivity.getString(R.string.createNew));
            		titleEditor.setView(layout);
            		titleEditor.setMessage(storyActivity.getString(R.string.enterPageTitle))
            		.setPositiveButton(storyActivity.getString(R.string.save), new DialogInterface.OnClickListener() {

            			public void onClick(DialogInterface dialog, int id) {
            				String pageTitle = titleEdit.getText().toString();
            				if(story.isUsesCombat() == true){
            					storyController.updateFightTitle(pageTitle, check.isChecked(), healthEdit.getText().toString(), nameEdit.getText().toString(), currentPage); 
            				}
            				else{
            					storyController.updateTitle(pageTitle, currentPage);    
            				}
            				storyActivity.refresh();
            			}
            		})
            		.setNegativeButton(storyActivity.getString(R.string.cancel), null);

            		titleEditor.show();
            		
            		break;

            	case(2):
            		storyController.updateFP(currentPage);
            		storyActivity.refresh();
            		break;
            	case(3):
            		storyController.removePage(currentPage);
            		storyActivity.refresh();
            		break;
            	}

            }	
                });
        return builder.create();
	}
	
	protected AlertDialog createPageGUI(){
		final LinearLayout layout = (LinearLayout) View.inflate(storyActivity, R.layout.create_page_dialog, null);
    	final EditText titleEdit = (EditText) layout.findViewById(R.id.create_page_dialog_edittext);
    	final EditText healthEdit = (EditText) layout.findViewById(R.id.create_page_dialog_health_edittext);
    	final EditText nameEdit = (EditText) layout.findViewById(R.id.create_page_dialog_name_edittext);
    	final CheckBox check = (CheckBox) layout.findViewById(R.id.create_page_dialog_checkbox);
    	final LinearLayout fightingLayout = (LinearLayout) layout.findViewById(R.id.create_page_dialog_fighting_options);
    	
    	if(!storyController.getStory().isUsesCombat())
    		fightingLayout.setVisibility(View.GONE);
    	nameEdit.setText("Enemy");
    	healthEdit.setText("0");
    	AlertDialog.Builder builder = new AlertDialog.Builder(storyActivity);
    	builder.setView(layout);
    	builder.setTitle(storyActivity.getString(R.string.createNew));
    	builder.setMessage(storyActivity.getString(R.string.enterPageTitle))
    	.setPositiveButton(storyActivity.getString(R.string.save), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            	storyController.newTitle(titleEdit.getText().toString(), check.isChecked(), healthEdit.getText().toString(), nameEdit.getText().toString());         	
            	storyActivity.refresh();
            }
        })
        .setNegativeButton(storyActivity.getString(R.string.cancel), null);
    	return builder.create();
	}

}
