package com.sar.chinhsuaanhbeautycam;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.sar.chinhsuaanhbeautycam.common.CommonVL;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class EditActivity extends Activity {

	private Context context;
	private boolean isSave;
	private String imagePath;
	private String nameImage;
	private ImageView imgView;
	private LinearLayout ln_top,ln_top_effect;
	private ImageButton btn_crop, btn_rotate, btn_effect;
	private ImageButton btn_ok, btn_back;
	private Uri uri;
	private Bitmap bitmapPic;
	private Intent inteRotate,intentEffect;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_image);
		isSave=false;
		context = EditActivity.this;
		nameImage="beautycam.jpg";
		imagePath=null;
		
		inteRotate= new Intent(context,RotateActivity.class);
		inteRotate.setAction(CommonVL.REQUEST_ROTATE);
		
		intentEffect=new Intent(context, EffectActivity.class);
		intentEffect.setAction(CommonVL.REQUEST_EFFECT);
		
		Intent i = getIntent();
		if (i.getAction().equals(CommonVL.REQUEST_EDIT)) {
			uri=i.getData();
			imagePath = i.getStringExtra(CommonVL.PATH_IMAGE_TO_EDIT);
			nameImage = imagePath.substring(imagePath.lastIndexOf("/") + 1);
			Toast.makeText(context, nameImage, Toast.LENGTH_SHORT).show();
			}
		showImage(uri);
		init();
		ln_top_effect.setVisibility(View.INVISIBLE);
		btn_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
					imagePath=saveImage(nameImage, bitmapPic);
					isSave=true;
			}
		});
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
					onBackPressed();
			}
		});
		btn_crop.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				isSave = false;
//				ly_contain_button.setVisibility(View.VISIBLE);
				Intent intent = new Intent("com.android.camera.action.CROP");  
				intent.setDataAndType(uri, "image/*");
				intent.setData(uri);  
				intent.putExtra("crop", "true");  
				intent.putExtra("aspectX", 1);  
				intent.putExtra("aspectY", 1);  
				intent.putExtra("outputX", 96);  
				intent.putExtra("outputY", 96);  
				intent.putExtra("noFaceDetection", true);  
				intent.putExtra("return-data", true);                                  
				startActivityForResult(intent, CommonVL.REQUEST_CROP);
			}
		});
		btn_rotate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				isSave=false;
				sendIntent(RotateActivity.class, CommonVL.REQUEST_CODE_ROTATE, bitmapPic);
			}
		});
		btn_effect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				isSave=false;
				sendIntent(EffectActivity.class, CommonVL.REQUEST_CODE_EFFECT,bitmapPic);
			}
		});
	}

	private void init() {
		imagePath=null;
		ln_top = (LinearLayout) findViewById(R.id.lineartop_edit);
		btn_ok = (ImageButton) findViewById(R.id.btn_ok);
		btn_back = (ImageButton) findViewById(R.id.btn_back);
		btn_crop = (ImageButton) findViewById(R.id.btn_crop);
		btn_rotate = (ImageButton) findViewById(R.id.btn_rotate);
		btn_effect = (ImageButton) findViewById(R.id.btn_effect_edit);
		imgView = (ImageView) findViewById(R.id.img_editting);
	}
	
	private void sendIntent(Class<?> className,int request,Bitmap bitmap){
		Intent intentSend= new Intent(EditActivity.this, className);
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
		byte[] byteArray = stream.toByteArray();
		intentSend.putExtra(CommonVL.BITMAP_TO_EDITTING,byteArray);
		startActivityForResult(intentSend, request);
	}

	private void showImage(Uri imageUri) {
		imgView = (ImageView) findViewById(R.id.img_editting);
		WindowManager window = (WindowManager) getBaseContext()
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics metric = new DisplayMetrics();
		window.getDefaultDisplay().getMetrics(metric);
		int width_S = metric.widthPixels;
		int height_S = metric.heightPixels;
		bitmapPic=null;
		try {
			bitmapPic=MediaStore.Images.Media.getBitmap(this.getContentResolver(),imageUri);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
			
//		    BitmapFactory.Options options = new BitmapFactory.Options();
//		    options.inJustDecodeBounds = true;
//		    BitmapFactory.decodeFile(imagePath, options);
//		    int photoW = options.outWidth;
//		    int photoH = options.outHeight;
//
//		    // Determine how much to scale down the image
//		    int scaleFactor = Math.min(photoW/width_S, photoH/height_S);
//			
//			options.inJustDecodeBounds = false;
//			options.inSampleSize = scaleFactor;
//			options.inPurgeable = true;
//			bitmapPic = BitmapFactory.decodeFile(imagePath, options);
			imgView.setImageBitmap(bitmapPic);
	}
	
		
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(data!=null){
			if (requestCode == CommonVL.REQUEST_CROP) {
				Bundle extras = data.getExtras();
				bitmapPic = extras.getParcelable("data");
				}
		 if(requestCode==CommonVL.REQUEST_CODE_ROTATE || requestCode==CommonVL.REQUEST_CODE_EFFECT)
			 if( resultCode==CommonVL.RESULT_OK){
				 byte[] byteArray = data.getByteArrayExtra(CommonVL.BITMAP_TO_EDITTING);
				 bitmapPic = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
			 }
		 imgView.setImageBitmap(bitmapPic);
			isSave=false;
		}
	}

	private String saveImage(String nameImage, Bitmap bitmap) {
		int count= 0;
		File destination = new File(CommonVL.PARENT_PATH,nameImage);
		while(destination.exists()){
			destination= new File(CommonVL.PARENT_PATH,
					nameImage.substring(0, nameImage.indexOf("."))+"("+count+++")"
					+nameImage.substring(nameImage.indexOf(".")+1));
		}
		FileOutputStream fo;
		try {
			destination.createNewFile();
			fo = new FileOutputStream(destination);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fo);
			fo.flush();
			fo.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		//add image to gallery
		Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
	    File f = new File(destination.getAbsolutePath());
	    Uri contentUri = Uri.fromFile(f);
	    mediaScanIntent.setData(contentUri);
	    this.sendBroadcast(mediaScanIntent);
		Toast.makeText(context, "Saving image complete\n"+destination.getAbsolutePath(), Toast.LENGTH_SHORT).show();
		return destination.getAbsolutePath();
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		
		if (isSave) {
			finish();
		}
		else {
		
		final AlertDialog alert = new AlertDialog.Builder(context)
		.create();
alert.setTitle("Save ?");

alert.setCanceledOnTouchOutside(false);
alert.setCancelable(false);

alert.setMessage("Do you want to save image ?");
alert.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
		new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//MainActivity.this.finish();
				saveImage(nameImage, bitmapPic);
				finish();
			}
		});
alert.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
		new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		});
alert.setButton(AlertDialog.BUTTON_NEUTRAL, "Cancel",
		new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				alert.cancel();
			}
		});
alert.show();
	}
	}

}
