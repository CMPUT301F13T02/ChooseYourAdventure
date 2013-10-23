package ca.ualberta.CMPUT301F13T02.chooseyouradventure;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CommentsAdapter extends BaseAdapter {
	
	private ArrayList<Comment> comments;
	private Context context;
	
	public CommentsAdapter(ArrayList<Comment> commentsList, Context ctx) {
		
		comments = commentsList;
		context = ctx;
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return comments.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

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
