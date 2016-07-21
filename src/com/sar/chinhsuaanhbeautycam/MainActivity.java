package com.sar.chinhsuaanhbeautycam;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;











import com.sar.chinhsuaanhbeautycam.common.CommonVL;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.provider.MediaStore.MediaColumns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends Activity {

	// private Button btnCamera,btnGallery;
	private RelativeLayout rlCamBtn, rlGalBtn;
	private String imageName;
	private Uri imageUri;
	private int isExit;
	private ImageInfor imageOb;
	private int REQUEST_CAMERA = 0, SELECT_FILE = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		isExit=0;
		init();
	}

	private void init() {
		createPathSavePhoto();
		imageOb= new ImageInfor();
		rlCamBtn = (RelativeLayout) findViewById(R.id.re_btn_camera);
		rlCamBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				ContentValues values = new ContentValues();
				values.put(MediaStore.Images.Media.TITLE, "BeautyCamera");
				values.put(MediaStore.Images.Media.DESCRIPTION, "developer HuongSarie");
				getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                startActivityForResult(intent, REQUEST_CAMERA);
			}
		});

		rlGalBtn = (RelativeLayout) findViewById(R.id.re_btn_gallery);
		rlGalBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) && !Environment.getExternalStorageState().equals(Environment.MEDIA_CHECKING)) {
				Intent intent = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				intent.setType("image/*");
				startActivityForResult(
						Intent.createChooser(intent, "Select File"),
						SELECT_FILE);
				}
				else {
					Toast.makeText(MainActivity.this, "No media mounted",Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	private void createPathSavePhoto(){
		
		File folder= new File(CommonVL.PARENT_PATH);
		if(!folder.exists())
			folder.mkdir();
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == REQUEST_CAMERA)
				onCaptureImageResult(data);
			else if (requestCode == SELECT_FILE)
				onSelectFromGalleryResult(data);
		}
	}

	private void onCaptureImageResult(Intent data) {
		imageUri=data.getData();
		Bitmap thumbnail=null;
		try {
			thumbnail = MediaStore.Images.Media.getBitmap(this.getContentResolver(),imageUri);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
		File fold= new File(CommonVL.PARENT_PATH_CAMERA);
		if(!fold.exists()) fold.mkdirs();
		imageName="BeautyCam"+System.currentTimeMillis() + ".jpg";
		imageOb= new ImageInfor(imageName,imageUri);
		File destination = new File(CommonVL.PARENT_PATH_CAMERA,imageName);
		
		MediaStore.Images.Media.insertImage(getContentResolver(),thumbnail,destination.getName(),destination.getName());

		Intent intent= new Intent(MainActivity.this,EditActivity.class);
		intent.setAction(CommonVL.REQUEST_EDIT);
		intent.putExtra(CommonVL.PATH_IMAGE_TO_EDIT, destination.getAbsolutePath());
		intent.setData(imageUri);
		startActivity(intent);
	}
	
	@SuppressWarnings("deprecation")
	private void onSelectFromGalleryResult(Intent data) {
		imageUri = data.getData();
		String[] projection = { MediaColumns.DATA };
		Cursor cursor = managedQuery(imageUri, projection, null, null,
				null);
		int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
		cursor.moveToFirst();
		String selectedImagePath = cursor.getString(column_index);
		Intent intent= new Intent(MainActivity.this,EditActivity.class);
		intent.setAction(CommonVL.REQUEST_EDIT);
		intent.putExtra(CommonVL.PATH_IMAGE_TO_EDIT, selectedImagePath);
		intent.setData(imageUri);
		startActivity(intent);
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		isExit++;
		if(isExit==2)
			{
				finish();
			}
		else
			Toast.makeText(MainActivity.this, "Press again to exit!", Toast.LENGTH_SHORT).show();
	}

}


class ImageInfor{
	private String imageName;
	private Uri imageUri;
	public ImageInfor(String imageName, Uri imageUri) {
		super();
		this.imageName = imageName;
		this.imageUri = imageUri;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public Uri getImageUri() {
		return imageUri;
	}
	public void setImageUri(Uri imageUri) {
		this.imageUri = imageUri;
	}
	public ImageInfor() {
		super();
	}
	
	public void saveImageToStorage(Context context,String nameImage, Bitmap bitmap){
		File destination = new File(CommonVL.PARENT_PATH,nameImage);
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
		MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, nameImage, nameImage);
		Toast.makeText(context, "Saving image complete\n"+destination.getAbsolutePath(), Toast.LENGTH_SHORT).show();
		
	}
	
	
	
}