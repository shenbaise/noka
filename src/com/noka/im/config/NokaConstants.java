package com.noka.im.config;

import android.annotation.SuppressLint;
import android.os.Environment;


/** 
  * @ClassName: BmobConstants
  * @Description: TODO
  * @author smile
  * @date 2014-6-19 下午2:48:33
  */
@SuppressLint("SdCardPath")
public class NokaConstants {

	/**
	 * 存放图片的目录
	 */
	public static String NOKA_IMAGE_PATH = Environment.getExternalStorageDirectory()	+ "/noka/image/";
	
	/**
	 * 头像保存目录
	 */
	public static String AVATAR_PATH = Environment.getExternalStorageDirectory()	 + "/noka/avatar/";
	/**
	 * 图像缩略图目录
	 */
	public static String NOKA_IMAGE_THUMB_PATH = NOKA_IMAGE_PATH + "thumb/";
	/**
	 * 上传图片的最大张数
	 */
	public static int MAX_PIC_TO_UPLOAD = 10;
	/**
	 * 拍照回调
	 */
	public static final int REQUESTCODE_UPLOADAVATAR_CAMERA = 1;//拍照修改头像
	public static final int REQUESTCODE_UPLOADAVATAR_LOCATION = 2;//本地相册修改头像
	public static final int REQUESTCODE_UPLOADAVATAR_CROP = 3;//系统裁剪头像
	
	public static final int REQUESTCODE_TAKE_CAMERA = 0x000001;//拍照
	public static final int REQUESTCODE_TAKE_LOCAL = 0x000002;//本地图片
	public static final int REQUESTCODE_TAKE_LOCATION = 0x000003;//位置
	public static final String EXTRA_STRING = "extra_string";
	
	
	public static final String ACTION_REGISTER_SUCCESS_FINISH ="register.success.finish";//注册成功之后登陆页面退出
}
