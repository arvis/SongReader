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
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

public class MainActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor>, OnScrollListener {

	List<Song> groupList;
	ExpandableListView expListView;
	private SongAdapter expListAdapter;
	private LoaderManager.LoaderCallbacks<Cursor> callbacks;
	
	private static final int LOADER_ID = 1;
	private int firstVisibleItem;
	private int totalItemcount;
	private int visibleItemCount;
	private Cursor songCursor;
	private int itemsLoaded=0;
	
	
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
        expListView.setOnScrollListener(this);
        
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
		CursorLoader cursorLoader = new CursorLoader(this,CONTENT_URI, PROJECTION, null, null, null);
		
		return cursorLoader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		songCursor=cursor;
		Log.d("main","onLoadFinished");
		songCursor.moveToFirst();
		addSongsToList();
		
	}
	
	
	/**
	 * adds songs from cursor to list
	 * */
	private void addSongsToList(){
		
		String songName;
		String url;
		String songImageUrl;
		
		if (songCursor==null){
			return;
		}
		
		if (this.totalItemcount== songCursor.getCount()){
			return;
		}
		
		//songCursor.moveToPosition(itemsLoaded);
		
		int size=10;
		for (int i = 0; i < size; i++) {
			songName= songCursor.getString(songCursor.getColumnIndex(SongDbHelper.COL_SONGNAME));
			songImageUrl= songCursor.getString(songCursor.getColumnIndex(SongDbHelper.COL_SONGURL ));
			url= songCursor.getString(songCursor.getColumnIndex(SongDbHelper.COL_SONGIMAGE ));
			
			expListAdapter.addItem(new Song(songName, url, songImageUrl) );
			
			if (songCursor.isLast()) break;
			songCursor.moveToNext();
			itemsLoaded++;
		}
		
		
		expListAdapter.notifyDataSetChanged();
		
		
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		
	}

	@Override
	public void onScroll(AbsListView arg0, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		this.firstVisibleItem=firstVisibleItem;
		this.totalItemcount=totalItemCount;
		this.visibleItemCount=visibleItemCount;
		Log.d("onScroll","first "+firstVisibleItem+" visible count "+visibleItemCount + " total " + totalItemCount);
		
		boolean loadMore =  firstVisibleItem + visibleItemCount >= totalItemCount;

	    if (loadMore) {
	    	addSongsToList();
	    }
		
		
		
	}

	@Override
	public void onScrollStateChanged(AbsListView arg0, int arg1) {
		Log.d("onScrollStateChanged","Scroll changed "+arg1+" visible "+this.visibleItemCount+" total "+this.totalItemcount );
		
		
	}	

}
