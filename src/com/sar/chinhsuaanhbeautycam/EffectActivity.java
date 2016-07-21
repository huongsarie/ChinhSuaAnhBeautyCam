package com.sar.chinhsuaanhbeautycam;

import com.sar.chinhsuaanhbeautycam.common.CommonVL;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class EffectActivity extends Activity{

	private Context context;
	private Bitmap bitmapPic;
	private int code;
	private ImageButton btn_ok,btn_back;
	private ImageView imagePic;
	private RelativeLayout rl_brightness,rl_contrast,rl_colordepth;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = EffectActivity.this;
		byte[] byteArray = getIntent().getByteArrayExtra(CommonVL.BITMAP_TO_EDITTING);
		bitmapPic = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
	}
	
	private void init(){
//		btn_ok=(ImageButton)findViewById(R.id.b)
	}
	

	
	private void initBright(){
		
	}
}