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

public class TileView {
	
	
	private ControllerApp app;
	private ViewPageActivity pageActivity;
	
	public TileView(ControllerApp app, ViewPageActivity pageActivity) {
		super();
		this.app = app;
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
			
			if (app.getEditing()) {
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
			view = new TextView(app);
		} else {
			view = new ImageView(app);
		}
		
		// Set what the tiles look like
		view.setPadding(0, 5, 0, 6);
		if (app.getEditing()) {
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
