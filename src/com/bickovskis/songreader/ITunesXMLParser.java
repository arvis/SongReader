package com.bickovskis.songreader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;

public class ITunesXMLParser extends AsyncTask<String, Void, Document> {

	private final MainActivity cntx;
	
	public ITunesXMLParser(MainActivity cntx){
		this.cntx=cntx;
	}
	
	
	@Override
	protected Document doInBackground(String... params) {
		URL url;
		
		
		try {
			//url = new URL("http://www.androidpeople.com/wp-content/uploads/2010/06/example.xml");
			SongDbHelper songsDb=new SongDbHelper(this.cntx);
			
			url= new URL(params[0]);
	        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	        DocumentBuilder db = dbf.newDocumentBuilder();
	        Document doc = db.parse(new InputSource(url.openStream()));
	        doc.getDocumentElement().normalize();
	        
	        NodeList nodeList = doc.getElementsByTagName("entry");
	        
	        for (int i = 0; i < nodeList.getLength(); i++) {
	        	
	        	Node node = nodeList.item(i);
	        	
	        	Element fstElmnt = (Element) node;
                NodeList nameList = fstElmnt.getElementsByTagName("title");
                NodeList imgList = fstElmnt.getElementsByTagName("im:image");
                NodeList songList = fstElmnt.getElementsByTagName("link");
                
                Element nameElement = (Element) nameList.item(0);
                nameList = nameElement.getChildNodes();
                String name=""+((Node) nameList.item(0)).getNodeValue();
                Log.i("XML","Name = " + ((Node) nameList.item(0)).getNodeValue());
                
                Element imgElement = (Element) imgList.item(0);
                imgList = imgElement.getChildNodes();
                String img=""+((Node) imgList.item(0)).getNodeValue();
                Log.i("XML","Img = " + ((Node) imgList.item(0)).getNodeValue());
                
                Element songElement = (Element) songList.item(0);
                String songName=songElement.getAttribute("href");
                Log.i("XML","song = " + songName );
                
                Song song=new Song(name,img,songName);
                songsDb.addSong(song);
                
	        }
			
	        return null;
	        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(Document result) {
		Log.d("XML_READ", "onPostExecute");
		cntx.showListData();
    }
	
	public InputStream getUrlData(String url) throws URISyntaxException, ClientProtocolException, IOException {

	    DefaultHttpClient client = new DefaultHttpClient();
	    HttpGet method = new HttpGet(new URI(url));
	    HttpResponse res = client.execute(method);
	    return res.getEntity().getContent();
	}
	
	
	
	
}
