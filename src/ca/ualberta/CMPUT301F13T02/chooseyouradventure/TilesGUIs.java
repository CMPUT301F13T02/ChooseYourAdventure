package ca.ualberta.CMPUT301F13T02.chooseyouradventure;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TilesGUIs {
	private ControllerApp app;
	private ViewPageActivity pageActivity;
	private PageController pageController; 
	private TileController tileView;
	
	
	public TilesGUIs(ControllerApp app, ViewPageActivity pageActivity) {
		super();
		this.app = app;
		this.pageActivity = pageActivity;
        pageController = app.getPageController();
        tileView = new TileController(app, pageActivity);

	}
	
	public AlertDialog addTileMenuGUI(final LinearLayout tilesLayout){
		AlertDialog.Builder builder = new AlertDialog.Builder(pageActivity);
		final AlertDialog.Builder photoSelector = 
				new AlertDialog.Builder(pageActivity);
		final String[] titles = { app.getString(R.string.textTile), app.getString(R.string.photoTile),
				                   app.getString(R.string.videoTile), app.getString(R.string.audioTile), app.getString(R.string.cancel) };   
		final String[] titlesPhoto = { app.getString(R.string.fromFile), app.getString(R.string.takePhoto), app.getString(R.string.cancel) };
        builder.setItems(titles, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
            	switch(item){
            	case(0):
            		//TODO fix this to be MVC and observer pattern
            		TextTile tile = new TextTile();
					pageController.getPage().addTile(tile);
					tileView.addTile(pageController.getPage().getTiles().size() - 1, tile, tilesLayout);   				
            		break;
            	case(1):
            		photoSelector.setItems(titlesPhoto, 
            				new DialogInterface.OnClickListener() {
            			 public void onClick(DialogInterface dialog, 
            					              int item) {
            	            	switch(item){
	            	            	case(0):
	            	            		pageActivity.getPhoto();
	            	            		
	            	            		
	            	            		break;
	            	            	case(1):
	            	            		pageActivity.takePhoto();
	            	            		break;
            	            	}
            	                }});
            	       	photoSelector.show();
            		
            		break;
            		
            		
            	case(2):
            		break;
            	case(3):
            		break;
            	}
                    
                }});
        return builder.create();
	}
	
	protected AlertDialog onEditTileGUI(View view, final LinearLayout tilesLayout){
		final TextView textView = (TextView) view;
    	AlertDialog.Builder builder = new AlertDialog.Builder(pageActivity);
    	final EditText alertEdit = new EditText(pageActivity);
    	alertEdit.setText(textView.getText().toString());
    	builder.setView(alertEdit);
    	builder.setPositiveButton(app.getString(R.string.done), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            	int whichTile = tilesLayout.indexOfChild(textView);
            	pageController.updateTile(alertEdit.getText().toString(), whichTile);
            }
        })
        .setNegativeButton(app.getString(R.string.done), null);
    	return builder.create();
	}
	
	protected AlertDialog editTileMenuGUI(final View view, final LinearLayout tilesLayout){
		final String[] titles = { app.getString(R.string.edit), app.getString(R.string.delete) };
		
        AlertDialog.Builder builder = new AlertDialog.Builder(pageActivity);
        builder.setTitle(R.string.story_options);
        builder.setItems(titles, new DialogInterface.OnClickListener() {
        	
            public void onClick(DialogInterface dialog, int item) {
            	int whichTile = tilesLayout.indexOfChild(view);
            	switch(item){
            	case(0):
            		pageActivity.onEditTile(view);
            		break;
            	case(1):
            		pageController.deleteTile(whichTile);
            		break;
            	}
            }
            
        });
        return builder.create();
	}
	
	protected AlertDialog onEditPageEndingGUI(View view){
		TextView textView = (TextView) view;
		AlertDialog.Builder builder = new AlertDialog.Builder(pageActivity);
		final EditText alertEdit = new EditText(pageActivity);
		alertEdit.setText(textView.getText().toString());
		builder.setView(alertEdit);
		builder.setPositiveButton(app.getString(R.string.done), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				pageController.setEnding(alertEdit.getText().toString());
			}
		})
		.setNegativeButton(app.getString(R.string.cancel), null);
		return builder.create();


	}

}
