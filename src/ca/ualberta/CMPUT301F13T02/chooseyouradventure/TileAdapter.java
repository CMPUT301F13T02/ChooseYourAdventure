/*
* Copyright (c) 2013, TeamCMPUT301F13T02
* All rights reserved.
* 
* Redistribution and use in source and binary forms, with or without modification,
* are permitted provided that the following conditions are met:
* 
* Redistributions of source code must retain the above copyright notice, this
* list of conditions and the following disclaimer.
* 
* Redistributions in binary form must reproduce the above copyright notice, this
* list of conditions and the following disclaimer in the documentation and/or
* other materials provided with the distribution.
* 
* Neither the name of the {organization} nor the names of its
* contributors may be used to endorse or promote products derived from
* this software without specific prior written permission.
* 
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
* ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
* WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
* DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
* ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
* (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
* LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
* (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
* SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

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
