package com.noka.im.album;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.os.Environment;
import cn.bmob.im.util.BmobLog;

import com.noka.im.config.NokaConstants;
import com.noka.im.util.MD5;
/**
 * @author shenbai
 * 照片缩略图文件管理
 */
public class FileUtils {
	private static final String tag = FileUtils.class.getSimpleName();
	
	public static String saveThumb(Bitmap bm, String picName) {
		String fileName = MD5.encode(picName);
		File f = null;
		try {
			createSDDir(NokaConstants.NOKA_IMAGE_THUMB_PATH);
			f = new File(NokaConstants.NOKA_IMAGE_THUMB_PATH, fileName); 
			if (f.exists()) {
				return f.getAbsolutePath();
			}
			FileOutputStream out = new FileOutputStream(f);
			bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
			out.close();
			return f.getAbsolutePath();
		} catch (FileNotFoundException e) {
			BmobLog.e(tag, e.getMessage());
		} catch (IOException e) {
			BmobLog.e(tag, e.getMessage());
		}
		return "";
	}
	
	public static String saveCompressedImg(Bitmap bm, String picName) {
		String fileName = MD5.encode(picName);
		File f = null;
		try {
			createSDDir(NokaConstants.NOKA_IMAGE_PATH);
			f = new File(NokaConstants.NOKA_IMAGE_PATH, fileName); 
			if (f.exists()) {
				return f.getAbsolutePath();
			}
			FileOutputStream out = new FileOutputStream(f);
			bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
			out.close();
			return f.getAbsolutePath();
		} catch (FileNotFoundException e) {
			BmobLog.e(tag, e.getMessage());
		} catch (IOException e) {
			BmobLog.e(tag, e.getMessage());
		}
		return "";
	}

	public static File createSDDir(String dirName) throws IOException {
		File dir = new File(dirName);
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			if(!dir.exists()){
				dir.mkdirs();
			}
			BmobLog.i(tag, "createSDDir:" + dir.getAbsolutePath());
		}
		return dir;
	}

	public static boolean isFileExist(String fileName) {
		File file = new File(NokaConstants.NOKA_IMAGE_PATH + fileName);
		file.isFile();
		return file.exists();
	}
	
	public static void delFile(String fileName){
		File file = new File(NokaConstants.NOKA_IMAGE_PATH + fileName);
		if(file.isFile()){
			file.delete();
        }
		file.exists();
	}

	public static boolean isExists(String path) {
		try {
			File f = new File(path);
			if (!f.exists()) {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
