package com.its.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Utils {
	public static Bitmap decodeFile(File f, Integer thumbnail_size) {
		Bitmap b = null;
		try {
			//Decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			
			FileInputStream fis = new FileInputStream(f);
			BitmapFactory.decodeStream(fis, null, o);
			fis.close();
			
			int scale = 1;
			if (o.outHeight > thumbnail_size || o.outWidth > thumbnail_size) {
				scale = (int)Math.pow(2, (int) Math.round(Math.log(thumbnail_size / 
						(double) Math.max(o.outHeight, o.outWidth)) / Math.log(0.5)));
			}
			
			//Decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			fis = new FileInputStream(f);
			b = BitmapFactory.decodeStream(fis, null, o2);
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return b;
	}
}
