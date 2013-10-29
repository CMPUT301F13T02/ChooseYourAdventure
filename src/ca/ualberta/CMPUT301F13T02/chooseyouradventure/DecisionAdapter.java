package ca.ualberta.CMPUT301F13T02.chooseyouradventure;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
/**
 * This is the adapter class for decisions 
 * that allows them to be displayed in listviews
 */
public class DecisionAdapter extends BaseAdapter {
	private ArrayList<Decision> decisions;
	private Context context;
	/**
	 * Constructor for this Adapter to take an array of decisions
	 * @param commentsList The Decisions from a page
	 * @param ctx The android Context for this adapter
	 */
	public DecisionAdapter(ArrayList<Decision> decisionList, Context ctx) {
		decisions = decisionList;
		context = ctx;
	}
	/**
	 * This gets the current number of decisions
	 * @return The number of decisions
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return decisions.size();
	}
	/**
	 * This gets the current decision at the provided position
	 * @param Position in list
	 * @return The corresponding decision
	 */
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	/**
	 * This gets the ID of the item at the provided position
	 * @param Position in the list
	 * @return The ID
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	/**
	 * @param Input from the listview
	 * @return The row of the requested decision
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View row = inflater.inflate(R.layout.decision_adapter_layout, parent, false); // Layout of row in ListView
		TextView decisionTextView = (TextView) row.findViewById(R.id.decisionTextView);
		Decision decision = decisions.get(position);
		decisionTextView.setText(decision.getText());
		return row;
	}

}
