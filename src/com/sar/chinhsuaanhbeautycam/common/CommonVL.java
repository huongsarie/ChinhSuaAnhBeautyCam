package com.sar.chinhsuaanhbeautycam.common;

import android.os.Environment;

public class CommonVL{

	public static final String IMAGE_OBJECT = "image_object";
	public static final String PARENT_PATH_CAMERA = Environment.getExternalStorageDirectory()+"/DCIM/Camera/";
	public static final String REQUEST_EDIT = "request_image";
	public static final int RESULT_OK = 1000;
	public static final int REQUEST_GET_IMAGE_GALLERY = 1002;
	public static final int REQUEST_CAPTURE_CAMERA = 1001;
	public static final String PATH_IMAGE_TO_EDIT = "path to edit";
	public static final String CASE_EDITTING = "case edit";
	public static final int REQUEST_CROP = 1;
	public static final int PIC_CROP = 1003;
	public static final String  PARENT_PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath()
			+"/BeautyCam/";
	public static final String REQUEST_ROTATE = "request_image_rotate";
	public static final int REQUEST_ROTATE_RESULT = 1004;
	public static final String REQUEST_EFFECT = "request_effect";
	public static final String BITMAP_TO_EDITTING = "bitmap_to_edit";
	public static final int REQUEST_CODE_ROTATE = 1005;
	public static final int REQUEST_CODE_EFFECT = 1006;
	public static final int REQUEST_BRIGHTNESS = 10001;
	public static final String EFFECT_CODE = "EFFECT_CODE";
	
}