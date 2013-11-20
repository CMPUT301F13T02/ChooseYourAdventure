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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * This class implements the Handler interface which specifies a contract for a class that will
 * store and retrieve Story objects from some sort of storage.
 * 
 * The DBHandler will use SQLite to store stories so that users may store stories locally (cache stories).
 */
public class DBHandler extends SQLiteOpenHelper implements Handler  {

	static final int dbVersion = 1;	
	static final String dbName="storyDB";
	static final String storyTable="Story";
	static final String colID = "ID";
	static final String colContents = "contents";

	private Gson gson = new GsonBuilder().registerTypeAdapter(Tile.class, new TileGsonMarshal()).create();
	/**
	 * This is the default constructor for a SQLite Database class
	 */
	public DBHandler(Context context) {
		super(context, dbName, null, dbVersion);
	}
	/**
	 * onCreate initializes the database for use. 
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + storyTable + " (" +
				colID + " TEXT PRIMARY KEY, " + 
				colContents + " TEXT)");	
	}
	/**
	 * onUpgrade defines the functionality if we update the Database
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS "+ storyTable);
		onCreate(db);
	}
	/**
	 * This updates a local copy of a story.
	 * @param aStory the story to update
	 */
	@Override
	public void updateStory(Story aStory) throws HandlerException{
		String id = aStory.getId();
		//Try to turn aStory into a json representable string
		String story = gson.toJson(aStory);	
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		//This assumes the getId returns a unique identifier
		values.put(colID, id);
		values.put(colContents, story);
		//db.insert(storyTable, null, values);
		db.update(storyTable, values, "id = \"" + id + "\"", null);
	}
	/**
	 * This deletes a local copy of a story.
	 *  @param aStory the story to delete
	 */
	@Override
	public void deleteStory(Story aStory) {
		SQLiteDatabase db = this.getWritableDatabase();
        db.delete(storyTable, colID + " = ?",
        		new String[] { aStory.getId() });
	}
	/**
	 * This adds a local copy of a story.
	 *  @param aStory the story to add
	 */
	@Override
	public void addStory(Story newStory) throws HandlerException{
		String id = newStory.getId();
		String story = gson.toJson(newStory);	
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		//This assumes the getId returns a unique value
		values.put(colID, id);
		values.put(colContents, story);
		
		if (db.insert(storyTable, null, values) == -1)
			throw new HandlerException("Failed to insert story");

	}
	/**
	 * This returns a local copy of a story from the database
	 *  @param id The ID of the story to retrieve
	 *  @return The Story requested
	 */
	@Override
	public Story getStory(String id) throws HandlerException{
		Story story = new Story();
		//Select * from story where ID = supplied ID
		String selectQuery = "SELECT  * FROM " + storyTable + 
								" WHERE " + colID + " = \"" + id + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        
        if (!cursor.moveToFirst())
        	throw new HandlerException("No story with matching ID");
        	
        	
        story.setId(cursor.getString(0)); //will the next line make this redundant?
        story = gson.fromJson(cursor.getString(1), Story.class);
        story.setHandler(this);
        
		return story;
	}
	/** 
	 * This updates a story after a user adds a comment. 
	 * @param story The story where the comment was added
	 * @param page The page with the comment
	 * @param comment The comment
	 * @see updateStory
	 * For a local copy, updating a story is fine.
	 */
	@Override
	public void addComment(Story story, Page page, Comment comment)
			throws HandlerException {
		//Story story1 = this.getStory(story.getId());
		this.updateStory(story);
	}
	/**
	 * This returns a list of all the stories stored locally
	 *  @return The list of all local stories.
	 */
	@Override
	public ArrayList<Story> getAllStories() throws HandlerException {
		ArrayList<Story> storyList = new ArrayList<Story>();
        // select all stories
        String selectQuery = "SELECT  * FROM " + storyTable;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to storyList
        if (cursor.moveToFirst()) {
        	do {
        		Story story = new Story();
        		story.setId(cursor.getString(0));
        		story = gson.fromJson(cursor.getString(1), Story.class);
        		story.setHandler(this);
        		storyList.add(story);
        	} while (cursor.moveToNext());
        }
        return storyList;
	}
}
