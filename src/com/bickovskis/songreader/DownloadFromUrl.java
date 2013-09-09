package com.bickovskis.songreader;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.util.ByteArrayBuffer;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

public class DownloadFromUrl extends AsyncTask<String, Void, Void>{

	Activity activity;
	public DownloadFromUrl(Activity context){
		this.activity=context;
	}
	
	
    public void download(String imageURL) { 
        try {
                URL url = new URL(imageURL); 
                //File file = new File(fileName);
                File file = File.createTempFile("img_", ".jpg"); 
                

                URLConnection ucon = url.openConnection();

                InputStream is = ucon.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);

                ByteArrayBuffer baf = new ByteArrayBuffer(50);
                int current = 0;
                while ((current = bis.read()) != -1) {
                        baf.append((byte) current);
                }

                FileOutputStream fos = new FileOutputStream(file);
                fos.write(baf.toByteArray());
                fos.close();
                Log.d("DownloadFromUrl", "download completed");
                
                //activity.onDownloadFinished();
                

        } catch (IOException e) {
                Log.d("DownloadFromUrl", "Error: " + e);
        }

    }

	@Override
	protected Void doInBackground(String... urls) {
		
        String urldisplay = urls[0];
        try {
        	download(urldisplay);
        	
        	
        	
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
		
		
		
		return null;
	}
	
	
}
