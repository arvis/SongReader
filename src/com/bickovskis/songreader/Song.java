package com.bickovskis.songreader;


public class Song {

	private String songName;
	private String url;
	private String songImageUrl;
	
	
	public Song(String songName, String url, String songImageUrl ){
		this.url=url;
		this.songName=songName;
		this.songImageUrl=songImageUrl;
	}
	
	public String getSongName() {
		return songName;
	}
	public void setSongName(String songName) {
		this.songName = songName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getSongImageUrl() {
		return songImageUrl;
	}
	public void setSongImageUrl(String songImageUrl) {
		this.songImageUrl = songImageUrl;
	}
	
	
}
