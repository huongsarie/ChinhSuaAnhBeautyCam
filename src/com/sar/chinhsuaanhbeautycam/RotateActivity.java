package com.sar.chinhsuaanhbeautycam;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.sar.chinhsuaanhbeautycam.common.CommonVL;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class RotateActivity extends Activity {

	private Context context;
	private String imagePath;
	private String nameImage;
	private ImageView imgView;
	private RelativeLayout ly_contain_button;
	private ImageButton btn_rotate_left, btn_rotate_right;
	private ImageButton btn_ok, btn_cancel;
	private Uri uri;
	private Bitmap bitmapPic;
	private Intent inte;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rotate_layout);
		context = RotateActivity.this;
		byte[] byteArray = getIntent().getByteArrayExtra(CommonVL.BITMAP_TO_EDITTING);
		bitmapPic = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
		
		showImage(bitmapPic);
		init();
		btn_ok=(ImageButton)findViewById(R.id.btn_ok_rotate);
		btn_ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				inte= new Intent();
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				bitmapPic.compress(Bitmap.CompressFormat.PNG, 100, stream);
				byte[] byteArray = stream.toByteArray();
				inte.putExtra(CommonVL.BITMAP_TO_EDITTING,byteArray);
				setResult(CommonVL.RESULT_OK,inte);
				finish();
			}
		});
		
		btn_cancel=(ImageButton)findViewById(R.id.btn_cancel_rotate);
		btn_cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
	}

	private void init() {
		context = RotateActivity.this;

		btn_rotate_left = (ImageButton) findViewById(R.id.btn_rotateleft);
		btn_rotate_right = (ImageButton) findViewById(R.id.btn_rotateright);

		btn_rotate_left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				bitmapPic=performRotate(-90);
				showImage(bitmapPic);
			}
		});
		btn_rotate_right.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				bitmapPic=performRotate(90);
				showImage(bitmapPic);
			}
		});
	}

	private void showImage(Bitmap bitmap) {
		imgView = (ImageView) findViewById(R.id.img_rotate);
		imgView.setImageBitmap(bitmapPic);
	}

	private Bitmap performRotate(int degreeToRotate) {
		int width = bitmapPic.getWidth();
	    int height = bitmapPic.getHeight();
	    int newWidth = bitmapPic.getHeight();
	    int newHeight = bitmapPic.getWidth();
	    float scaleWidth = ((float) newWidth) / width;
	    float scaleHeight = ((float) newHeight) / height;
		Matrix rotateMatrix = new Matrix();
		rotateMatrix.postScale(scaleWidth, scaleHeight);
		rotateMatrix.postRotate(degreeToRotate);
		Bitmap rotatedBitmap = Bitmap.createBitmap(bitmapPic, 0, 0, width,
				height, rotateMatrix, false);
		return rotatedBitmap;
	}
}