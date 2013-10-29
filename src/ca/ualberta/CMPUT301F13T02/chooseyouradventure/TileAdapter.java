package ca.ualberta.CMPUT301F13T02.chooseyouradventure;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * An Adapter that will be used with list-views to display lists of Tiles
 */

public class TileAdapter extends BaseAdapter {
	
	private ArrayList<Tile> tiles;
	private Context context;

	/**
	 * Constructor for TileAdapter taking a list of tiles
	 * 
	 * @param tilesList An ArrayList of Tiles that are managed by this adapter
	 * @param ctx The Android context of this adapter
	 */
	public TileAdapter(ArrayList<Tile> tilesList, Context ctx) {
		
		tiles = tilesList;
		context = ctx;
		
	}
	
	/**
	 * Returns the number of tiles in the adapter
	 * 
	 * @return The number of tiles in the adapter
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return tiles.size();
	}

	/**
	 * Returns the Tile at the passed position
	 * 
	 * @param position The position of the Tile to return
	 * @return The Tile at the passed position
	 */
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	/**
	 * Returns the ID of the Tile at the passed position
	 * 
	 * @param position The position of the Tile whose ID will be returned
	 * @return The ID of the Tile at the passed position
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	/**
	 * Returns the View for the Tile at the passed position with the passed parent
	 * 
	 * @param position The position of the Tile whose View is being requested
	 * @param convertView Unused
	 * @param parent The parent of the to-be-created View
	 * @return The View for the Tile at the passed position with the passed parent
	 */
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
