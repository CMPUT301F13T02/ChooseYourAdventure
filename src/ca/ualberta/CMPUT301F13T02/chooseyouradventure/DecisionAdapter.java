package ca.ualberta.CMPUT301F13T02.chooseyouradventure;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DecisionAdapter extends BaseAdapter {
	
	private ArrayList<Decision> decisions;
	private Context context;
	
	public DecisionAdapter(ArrayList<Decision> decisionList, Context ctx) {
		decisions = decisionList;
		context = ctx;
	}
	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return decisions.size();
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
		View row = inflater.inflate(R.layout.decision_adapter_layout, parent, false); // Layout of row in ListView
		TextView decisionTextView = (TextView) row.findViewById(R.id.decisionTextView);
		
		Decision decision = decisions.get(position);
		decisionTextView.setText(decision.getText());
		return row;
	}

}
