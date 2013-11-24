package ca.ualberta.CMPUT301F13T02.chooseyouradventure;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class CommentGUIs {
	private ControllerApp app;
	private ViewPageActivity pageActivity;
	private StoryController storyController; 
	private PageController pageController; 
	private TileView tileView;
	private DecisionView decisionView;
	
	public CommentGUIs(ControllerApp app, ViewPageActivity pageActivity) {
		super();
		this.app = app;
		this.pageActivity = pageActivity;
		storyController = app.getStoryController();
        pageController = app.getPageController();
        tileView = new TileView(app, pageActivity);
        decisionView = new DecisionView(app, pageActivity);
	}
	
	protected AlertDialog onCallCommentGUI(){
		final String[] titlesPhoto = { app.getString(R.string.noImage), app.getString(R.string.fromFile),
				app.getString(R.string.takePhoto) };
		final AlertDialog.Builder photoSelector = 
				new AlertDialog.Builder(pageActivity); 
		photoSelector.setTitle(app.getString(R.string.usePhotoComment));
		photoSelector.setItems(titlesPhoto, 
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, 
					int item) {
				switch(item){


				case(0):
					pageActivity.onEditComment();
				break;
				case(1):
					pageActivity.grabPhoto();	            		
				break;
				case(2):
					pageActivity.addPhoto();            		
				break;
				}

			}
		}
		);
		return photoSelector.create();
	}
	
	protected AlertDialog onEditCommentGUI(){
		AlertDialog.Builder builder = new AlertDialog.Builder(pageActivity);
    	builder.setTitle(app.getString(R.string.whatToSay));
    	
    	final LinearLayout layout = new LinearLayout(pageActivity);
    	layout.setOrientation(LinearLayout.VERTICAL);
    	
    	final EditText alertEdit = new EditText(pageActivity);
    	layout.addView(alertEdit);
    	
    	final ImageView alertImage = new ImageView(pageActivity);
    	
    	final PhotoTile photoAdd = (PhotoTile) app.getTempSpace();
		app.setTempSpace(null);

		if(photoAdd != null)
			alertImage.setImageBitmap(photoAdd.getImage());

    	layout.addView(alertImage);
    	
		builder.setView(layout);
    	builder.setPositiveButton(app.getString(R.string.save), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            	app.addComment(alertEdit.getText().toString(),photoAdd );
            }
        })
        .setNegativeButton(app.getString(R.string.cancel), null);
    	return builder.create();
	}


}
