package ca.ualberta.CMPUT301F13T02.chooseyouradventure;

import java.util.ArrayList;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CommentController {
	
	
	private ControllerApp app;
	private ViewPageActivity pageActivity;
	private StoryController storyController; 
	private PageController pageController; 
	
	
	public CommentController(ControllerApp app, ViewPageActivity pageActivity) {
		super();
		this.app = app;
		this.pageActivity = pageActivity;
		storyController = app.getStoryController();
        pageController = app.getPageController();
	}
	
	/**
	 * Called to display a new comment at position i.
	 * @param comment
	 */
	public void addComment(Comment comment, LinearLayout commentsLayout) {
		final LinearLayout layout = new LinearLayout(pageActivity);
    	layout.setOrientation(LinearLayout.VERTICAL);
    	
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
		lp.setMargins(0, 5, 0, 0);
		TextView view = new TextView(pageActivity);
		view.setBackgroundColor(0xFFFFFFFF);
		view.setPadding(10, 5, 10, 5);
		view.setLayoutParams(lp);
		view.setText(comment.getTimestamp() + " - '" + comment.getText() + "'");
		layout.addView(view);
		
		if(comment.getAnnotation() != null){
			ImageView imageView = new ImageView(pageActivity);
			imageView.setImageBitmap(comment.getAnnotation().getImage());
			imageView.setBackgroundColor(0xFFFFFFFF);
			layout.addView(imageView);
		}
	    commentsLayout.addView(layout);
	}
	
	/**
	 * Removes the comments from commentsLayout and repopulates it with the
	 * current comments.
	 * @param page
	 */
	protected void updateComments(Page page, LinearLayout commentsLayout) {
		commentsLayout.removeAllViews();
		
		//For each comment in the page, add it to commentsLayout
		ArrayList<Comment> comments = page.getComments();
		for (int i = 0; i < comments.size(); i++) {
			addComment(comments.get(i), commentsLayout);
		}
	}

}
