package com.bickovskis.songreader;



import java.util.List;
import java.util.Map;
 
//import com.theopentutorials.expandablelist.R;
 




import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.sax.StartElementListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
 
public class SongAdapter extends BaseExpandableListAdapter {
 
    private Activity context;
    private List<Song> songs;
 
    public SongAdapter(Activity context, List<Song> songs) {
        this.context = context;
        this.songs = songs;
    }
 
    public void addItem(Song item ){
    	this.songs.add(item);
    }
    
    
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }
 
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
 
    public View getChildView(final int groupPosition, final int childPosition,
            boolean isLastChild, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
 
        
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.child_item, null);
        }
        
       final Song songData = (Song) getGroup(groupPosition);
        
        Button downloadButton=(Button) convertView.findViewById(R.id.download);
        Button iTunesButton=(Button) convertView.findViewById(R.id.view_itunes);
        
        downloadButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				DownloadFromUrl down=new DownloadFromUrl(context);
				down.execute(songData.getSongImageUrl());
			}
		} );
        
        iTunesButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context,ITunesData.class );
				intent.putExtra("PAGE_URL",songData.getUrl());
				context.startActivity(intent);
				
			}
		});

        return convertView;
    }
 
    public int getChildrenCount(int groupPosition) {
    	return 1;
    }
 
    
    public Object getGroup(int groupPosition) {
    	return songs.get(groupPosition);
    }
 
    public int getGroupCount() {
        return songs.size();
    }
 
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
 
    public View getGroupView(int groupPosition, boolean isExpanded,
            View convertView, ViewGroup parent) {
    	
        final Song songData = (Song) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.group_item,
                    null);
        }
        TextView item = (TextView) convertView.findViewById(R.id.song_name);
        
        final String songUrl=songData.getSongImageUrl();
        new DownloadImageTask((ImageView) 
        		convertView.findViewById(R.id.song_image)).
        		execute(songUrl);
        
        
        item.setText(songData.getSongName());
        return convertView;
    }
 
    public boolean hasStableIds() {
        return true;
    }
 
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
