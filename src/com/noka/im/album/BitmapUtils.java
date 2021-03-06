package com.noka.im.album;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import cn.bmob.im.util.BmobLog;

import com.noka.im.config.NokaConstants;
import com.noka.im.util.MD5;
/**
 * @author shenbai
 * 图片管理工具类
 */
public class BitmapUtils {
	private final static String tag = BitmapUtils.class.getSimpleName();
	public static int max = 0;
	public static boolean act_bool = true;
	/**
	 * 要上传的图片集合
	 */
	public static List<Bitmap> bmp = new ArrayList<Bitmap>();
	
	public static List<String> selectedImages = new ArrayList<String>();
	
	/**
	 * 上传服务器时把图片调用下面方法压缩后 保存到临时文件夹 图片压缩后小于100KB，失真度不明显
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static Bitmap fixImageSize(String path) {
		String thumb = MD5.encode(path);
		Bitmap bitmap =  null;
		File f = new File(NokaConstants.NOKA_IMAGE_PATH, thumb);
		if(f.exists()){
			try {
				FileInputStream fis = new FileInputStream(f);
				bitmap = BitmapFactory.decodeStream(fis);
				return bitmap;
			} catch (FileNotFoundException e) {
				System.out.println(e.getMessage());
			}
		}
		return revitionImageSize(path, 1024, 1024);
	}
	
	/**
	 * 压缩图片
	 * @param imagePaht
	 * @param w
	 * @param h
	 * @return
	 */
	public static Bitmap revitionImageSize(String imagePaht,int w,int h){
		Bitmap bitmap = null;
		try {
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(new File(imagePaht)));
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(in, null, options);
			in.close();
			int i = 0;
			bitmap = null;
			while (true) {
				if ((options.outWidth >> i <= w)
						&& (options.outHeight >> i <= h)) {
					in = new BufferedInputStream(new FileInputStream(new File(imagePaht)));
					options.inSampleSize = (int) Math.pow(2.0D, i);
					options.inJustDecodeBounds = false;
					bitmap = BitmapFactory.decodeStream(in, null, options);
					break;
				}
				i += 1;
			}
		} catch (FileNotFoundException e) {
			BmobLog.e(tag, e.getMessage());
		} catch (IOException e) {
			BmobLog.e(tag, e.getMessage());
		}
		return bitmap;
	}
	/**
	 * 压缩做缩略图
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static Bitmap makeThumb(String path) {
		String thumb = MD5.encode(path);
		Bitmap bitmap =  null;
		File f = new File(NokaConstants.NOKA_IMAGE_THUMB_PATH, thumb);
		if(f.exists()){
			try {
				FileInputStream fis = new FileInputStream(f);
				bitmap = BitmapFactory.decodeStream(fis);
				return bitmap;
			} catch (FileNotFoundException e) {
				System.out.println(e.getMessage());
			}
		}
		bitmap = revitionImageSize(path, 256, 256);
		FileUtils.saveThumb(bitmap, "" + path);
		return bitmap;
	}
	
	public static String getThumbPath(String path) {
		String thumb = MD5.encode(path);
		Bitmap bitmap =  null;
		File f = new File(NokaConstants.NOKA_IMAGE_THUMB_PATH, thumb);
		if(f.exists()){
			return f.getAbsolutePath();
		}
		bitmap = revitionImageSize(path, 256, 256);
		return FileUtils.saveThumb(bitmap,  path);
	}
	
	public static String getCompressedImgPath(String path) {
		String thumb = MD5.encode(path);
		Bitmap bitmap =  null;
		File f = new File(NokaConstants.NOKA_IMAGE_PATH, thumb);
		if(f.exists()){
			return f.getAbsolutePath();
		}
		bitmap = revitionImageSize(path, 1024, 1024);
		return FileUtils.saveThumb(bitmap,  path);
	}
}
