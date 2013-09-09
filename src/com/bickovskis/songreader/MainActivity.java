package com.bickovskis.songreader;

import java.util.ArrayList;
import java.util.List;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

public class MainActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor>  {

	List<Song> groupList;
	ExpandableListView expListView;
	private SongAdapter expListAdapter;
	private LoaderManager.LoaderCallbacks<Cursor> callbacks;
	
	private static final int LOADER_ID = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final String iTunesUrl="http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=10/xml";
		ITunesXMLParser parser=new ITunesXMLParser(this);
		parser.execute(iTunesUrl);
		
	}

	public void showListData(){

		createGroupList();
		expListView = (ExpandableListView) findViewById(R.id.song_list);
        this.expListAdapter = new SongAdapter(this, groupList);
        
		callbacks = this;
	    LoaderManager lm = getLoaderManager();
	    lm.initLoader(LOADER_ID, null, callbacks);		
        
        expListView.setAdapter(expListAdapter);		
		
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
    private void createGroupList() {
        groupList = new ArrayList<Song>();
    }

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		
		String[] PROJECTION = new String[] { SongDbHelper.COLUMN_ID,
				SongDbHelper.COL_SONGNAME,SongDbHelper.COL_SONGIMAGE, SongDbHelper.COL_SONGURL};
		
		Uri CONTENT_URI = Uri.parse("content://com.bickovskis.songreader.songprovider/songs");
		String[] mSelectionArgs = {""};
		CursorLoader cursorLoader = new CursorLoader(this,CONTENT_URI, PROJECTION, null, null, null);
		
		return cursorLoader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
		String songName;
		String url;
		String songImageUrl;
		while (cursor.moveToNext()) {
			
			songName= cursor.getString(cursor.getColumnIndex(SongDbHelper.COL_SONGNAME));
			songImageUrl= cursor.getString(cursor.getColumnIndex(SongDbHelper.COL_SONGURL ));
			url= cursor.getString(cursor.getColumnIndex(SongDbHelper.COL_SONGIMAGE ));
			
			//Log.d("main",songName);
			//FIXME: add multiple songs at once
			expListAdapter.addItem(new Song(songName, url, songImageUrl) );
		}
		
		expListAdapter.notifyDataSetChanged();
		Log.d("main","onLoadFinished");
		
		
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub
		
	}	

}
