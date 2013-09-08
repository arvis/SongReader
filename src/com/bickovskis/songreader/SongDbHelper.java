package com.bickovskis.songreader;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SongDbHelper extends SQLiteOpenHelper {
	
	public static final String COLUMN_ID = "id";
	public static final String TABLE_SONGLIST= "songs";
	public static final String COL_SONGNAME= "song_name";
	public static final String COL_SONGIMAGE= "image_url";
	
	public static final String COL_SONGURL= "song_url";
	
	
	public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "itunes_collection.sqlite";
	
	

	public SongDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}



	@Override
	public void onCreate(SQLiteDatabase db) {
		final String CREATE_STRING = "CREATE TABLE " + TABLE_SONGLIST + "("
                + "id INTEGER PRIMARY KEY, song_name TEXT, image_url TEXT, song_url TEXT )";
        db.execSQL(CREATE_STRING);		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SONGLIST);
        onCreate(db);		
	}
	
	public void addSong(Song song){
		SQLiteDatabase db = this.getWritableDatabase();
		 
	    ContentValues values = new ContentValues();
	    values.put(COL_SONGNAME, song.getSongName()); // Contact Name
	    values.put(COL_SONGURL , song.getUrl() ); // Contact Phone Number
	 
	    db.insert(TABLE_SONGLIST, null, values);
	    db.close(); //		
	}
	
	public Cursor getSongs(){
		
		
		   SQLiteDatabase db = this.getReadableDatabase();
		   
		    Cursor cursor = db.query(TABLE_SONGLIST, new String[] { "id",
		            COL_SONGIMAGE, COL_SONGNAME,COL_SONGURL }, null,
		            new String[] {  }, null, null, null, null);
		 
		    return cursor;		
		
	}
	
	

}
