package ca.ualberta.CMPUT301F13T02.chooseyouradventure;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ViewPageMenus {
	
	private ControllerApp app;
	private ViewPageActivity pageActivity;
	private StoryController storyController; 
	private PageController pageController; 
	private TileView tileView;
	private DecisionView decisionView;
	
	public ViewPageMenus(ControllerApp app, ViewPageActivity pageActivity) {
		super();
		this.app = app;
		this.pageActivity = pageActivity;
		storyController = app.getStoryController();
        pageController = app.getPageController();
        tileView = new TileView(app, pageActivity);
        decisionView = new DecisionView(app, pageActivity);
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
