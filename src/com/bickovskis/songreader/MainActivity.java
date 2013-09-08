package com.bickovskis.songreader;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

public class MainActivity extends Activity {

	List<String> groupList;
	ExpandableListView expListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final String iTunesUrl="http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=10/xml";
		ITunesXMLParser parser=new ITunesXMLParser(this);
		parser.execute(iTunesUrl);
		
	}

	public void showListData(){
		Log.d("main_activity", "showListData");

		createGroupList();
		expListView = (ExpandableListView) findViewById(R.id.song_list);
        final SongAdapter expListAdapter = new SongAdapter(this, groupList);
        expListView.setAdapter(expListAdapter);		
		
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
    private void createGroupList() {
        groupList = new ArrayList<String>();
        groupList.add("HP");
        groupList.add("Dell");
        groupList.add("Lenovo");
        groupList.add("Sony");
        groupList.add("HCL");
        groupList.add("Samsung");
    }	

}
