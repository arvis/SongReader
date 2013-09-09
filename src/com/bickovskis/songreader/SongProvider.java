package com.bickovskis.songreader;


import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class SongProvider extends ContentProvider {

	static final String TAG = "SongProvider";

	static final String AUTHORITY = "content://com.bickovskis.songreader.songprovider";
	public static final Uri CONTENT_URI = Uri.parse(AUTHORITY);
	static final String SINGLE_RECORD_MIME_TYPE = "vnd.android.cursor.item/vnd.bickovskis.songprovider";
	static final String MULTIPLE_RECORDS_MIME_TYPE = "vnd.android.cursor.dir/vnd.bickovskis.songprovider";	

	
	private SongDbHelper songDb;
	
	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri arg0, ContentValues arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean onCreate() {
		songDb=new SongDbHelper(getContext());
		return false;
	}

	@Override
	public Cursor query(Uri arg0, String[] arg1, String arg2, String[] arg3,
			String arg4) {
		
		Log.d("song_provider","query");
		return songDb.getSongs();
	}

	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		return 0;
	}

}
