package ca.ualberta.CMPUT301F13T02.chooseyouradventure;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
/**
 * This creates the adapter for the comments that come from
 * the server. 
 */
public class CommentsAdapter extends BaseAdapter {
	
	private ArrayList<Comment> comments;
	private Context context;
	/**
	 * Constructor for this Adapter to take an array of comments
	 * @param commentsList The comments from a page
	 * @param ctx The android Context for this adapter
	 */
	public CommentsAdapter(ArrayList<Comment> commentsList, Context ctx) {
		comments = commentsList;
		context = ctx;
		
	}
	/**
	 * Gets the number of comments
	 * @return The number of comments on a page
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return comments.size();
	}
	/**
	 * Gets an item from the comment list
	 * @param The position of the comment
	 * @return The comment at the provided position
	 */
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	/**
	 * @param The position of the comment
	 * @return The ID of the item at the provided position
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	/**
	 * @param Input from the listview
	 * @return The row of the requested comment
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View row = inflater.inflate(R.layout.comments_adapter_layout, parent, false); // Layout of row in ListView
		TextView commentTextView = (TextView) row.findViewById(R.id.commentsTextView);
		
		Comment comment = comments.get(position);
		commentTextView.setText(comment.getText());
		return row;
	}

}
