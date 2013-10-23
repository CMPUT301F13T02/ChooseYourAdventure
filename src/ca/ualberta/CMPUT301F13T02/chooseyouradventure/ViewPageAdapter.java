package ca.ualberta.CMPUT301F13T02.chooseyouradventure;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ViewPageAdapter extends BaseAdapter {
	
	private ArrayList<Tile> tiles;
	private Context context;

	public ViewPageAdapter(ArrayList<Tile> tilesList, Context ctx) {
		
		tiles = tilesList;
		context = ctx;
		
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return tiles.size();
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
		View row = inflater.inflate(R.layout.view_page_adapter_layout, parent, false); // Layout of row in ListView
		TextView tileTextView = (TextView) row.findViewById(R.id.TextTiletextView);
		
		Tile tile = tiles.get(position);
		if (tile.getType() == "text") { // is a TextTile
			TextTile textTile = (TextTile) tile; // cast to TextTile
			tileTextView.setText(textTile.getText());
		}
		
		return row;
		
	}

}
