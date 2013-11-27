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

import android.content.ClipData;
import android.util.Log;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * This class stores all of the layout builders needed for interacting with tiles. 
 * This prepares data and the view, but does not display anything. Its sister class TileView creates the GUI 
 * From data set in this class
 */
public class TileLayoutBuilder {
	private ViewPageActivity pageActivity;
	
	public TileLayoutBuilder(ViewPageActivity pageActivity) {
		super();
		this.pageActivity = pageActivity;
	}

	/**
	 * Removes all the tiles from the tilesLayout and repopulates it with 
	 * the current state of the tiles.
	 * @param page
	 */
	protected void updateTiles(Page page, LinearLayout tilesLayout) {
		tilesLayout.removeAllViews();
		
		//For each tile in the page, add the tile to tilesLayout
		ArrayList<Tile> tiles = page.getTiles();
		for (int i = 0; i < tiles.size(); i++) {
			addTile(i, tiles.get(i), tilesLayout);
		}
	}
	
	/**
	 * Called to display a new tile at position i. If we are in editing mode,
	 * add a click listener to allow user to edit the tile
	 * @param i
	 * @param tile
	 */
	public void addTile(int i, Tile tile, LinearLayout tilesLayout) {
		
		if (tile.getType() == "text") {
			View view = makeTileView("text");
			TextTile textTile = (TextTile) tile;
			TextView textView = (TextView) view;
			
			textView.setText(textTile.getText());

			tilesLayout.addView(view, i);
			
			if (pageActivity.getEditing()) {
				view.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						pageActivity.editTileMenu(v);
					}
				});
				view.setOnLongClickListener(new OnLongClickListener() {
					@Override
					public boolean onLongClick(View v) {
						ClipData data = ClipData.newPlainText("", "");
						DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
						v.startDrag(data, shadowBuilder, v, 0);
						return true;
					}
				});

			}
			
		} else if (tile.getType() == "photo") {
			
			View view = makeTileView("photo");
			PhotoTile photoTile = (PhotoTile) tile;
			ImageView imageView = (ImageView) view;
			imageView.setImageBitmap(photoTile.getImage());
			
			tilesLayout.addView(imageView, i);

		} else if (tile.getType() == "video") {
			// TODO Implement for part 4
		} else if (tile.getType() == "audio") {
			// TODO Implement for part 4
		} else {
			Log.d("no such tile", "no tile of type " + tile.getType());
		}
	}
	
	/**
	 * Create a view that has the proper padding, and if we are in editing
	 * mode, adds a small margin to the bottom so we can see a little of 
	 * the layout background which makes a line separating the tile views.
	 * @return
	 */
	private View makeTileView(String type) {
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
		
		View view;
		
		if (type == "text") {
			view = new TextView(pageActivity);
		} else {
			view = new ImageView(pageActivity);
		}
		
		// Set what the tiles look like
		view.setPadding(0, 5, 0, 6);
		if (pageActivity.getEditing()) {
			/* Background to the layout is grey, so adding margins adds 
			 * separators.
			 */
			lp.setMargins(0, 0, 0, 3);
		} else {
			view.setPadding(0, 5, 0, 9);
		}
		view.setBackgroundColor(0xFFFFFFFF);
		view.setLayoutParams(lp);
		
		return view;
	}


}
