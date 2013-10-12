package com.its.spiders;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Vector;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class TagActivity extends Activity {
	
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	private Uri fileUri;
	private Vector<GalleryListItem> MLIST = new Vector<GalleryListItem>();
	private ArrayAdapter<GalleryListItem> ADAPTER;
	private File image;
	private static int PIC_REQUEST;
	private Context CTX;
	public static final int MEDIA_TYPE_IMAGE = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tag);
		
		CTX = this;
		
		ADAPTER = new GalleryAdapter(this, R.layout.inflator_gallery, MLIST);
		
		Button btnCamera = (Button) findViewById(R.id.btn_camera);
		ListView llGallery = (ListView) findViewById(R.id.lv_photos);
		
		llGallery.setAdapter(ADAPTER);
		
		btnCamera.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				PIC_REQUEST = (int)System.currentTimeMillis()/1000;
				// create Intent to take a picture and return control to the calling application
			    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			    
			    /*String dirPath = getFilesDir().getAbsolutePath() + File.separator + "newfoldername";
			    File projDir = new File(dirPath);
			    if (!projDir.exists())
			        projDir.mkdirs();
				}*/
			    
			    File dir = new File(Environment.getExternalStorageDirectory(), "Spiders");
			    dir.mkdirs();
			    image = new File(dir.getPath() + File.separator + PIC_REQUEST + ".jpg");
			    fileUri = Uri.fromFile(image); // create a file to save the image
			    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name
			    
			    // start the image capture Intent
			    startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tag, menu);
		return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
	        if (resultCode == RESULT_OK) {
	        	File dir = new File(Environment.getExternalStorageDirectory(), "Spiders");
			    File reimage = new File(dir.getPath() + File.separator + PIC_REQUEST + ".jpg");
	        	GalleryListItem item = new GalleryListItem(reimage);
	        	MLIST.add(item);
	        	ADAPTER.notifyDataSetChanged();
	        }else if (resultCode == RESULT_CANCELED) {
	            // User cancelled the image capture
	        } else {
	            // Image capture failed, advise user
	        }
	    }
	}
}
