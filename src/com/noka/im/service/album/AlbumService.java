/**
 * 
 */
package com.noka.im.service.album;

import java.util.List;

import android.content.Context;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

import com.noka.im.bean.album.Album;
import com.noka.im.bean.album.NokaPhoto;

/**
 * @author shenbai
 *	album的管理类。保存相册信息、上次相册信息等
 */
public class AlbumService {
	
	BmobQuery<NokaPhoto> photoBq = new BmobQuery<NokaPhoto>();
	BmobQuery<Album> albumBq = new BmobQuery<Album>();
	
	/**
	 * 查找用户上传照片的所有日期，每个日期当做一个相册名，逐日显示。
	 * @param cxt
	 * @param username
	 * @param listener 每天可能有多个Album创建，查出后应该用date字段做个排重。
	 */
	public void queryAlbum(Context cxt, String username,FindListener<Album> listener){
		albumBq.addWhereEqualTo("username", username);
		albumBq.findObjects(cxt, listener);
	}
	/**
	 * 查找用户上传照片的所有日期，每个日期当做一个相册名，逐日显示。<b>带翻页条件</b>
	 * @param cxt
	 * @param username
	 * @param skip
	 * @param size
	 * @param listener
	 */
	public void queryAlbum(Context cxt, String username,int skip,int size,FindListener<Album> listener){
		albumBq.addWhereEqualTo("username", username);
		albumBq.setSkip(skip);
		albumBq.setLimit(size);
//		albumBq.order("date");
		albumBq.findObjects(cxt, listener);
	}
	/**
	 * 查找用户某个日期上传的照片
	 * @param cxt
	 * @param username
	 * @param albumDate
	 * @param listener
	 */
	public void queryAlbumPhotos(Context cxt, String username,String albumDate, FindListener<NokaPhoto> listener){
		photoBq.addWhereEqualTo("username", username);
		photoBq.addWhereEqualTo("albumDate", albumDate);
		photoBq.findObjects(cxt, listener);
	}
	/**
	 * 查找用户某个日期上传的照片
	 * @param cxt
	 * @param username
	 * @param albumDate
	 * @param listener
	 */
	public void queryAlbumPhotos(Context cxt, String username,String albumDate ,int skip,int size, FindListener<NokaPhoto> listener){
		photoBq.addWhereEqualTo("username", username);
		photoBq.addWhereEqualTo("albumDate", albumDate);
		photoBq.setSkip(skip);
		photoBq.setLimit(size);
		photoBq.findObjects(cxt, listener);
	}
	/**
	 * 上传用户照片，在调用该方法前先做相册创建。
	 * @param cxt
	 * @param photo
	 */
	public void uploadNokaPhoto(final Context cxt,final NokaPhoto photo){
		photo.getImage().upload(cxt, new UploadFileListener() {
			@Override
			public void onSuccess() {
				photo.save(cxt, new SaveListener() {
					@Override
					public void onSuccess() {
					}
					@Override
					public void onFailure(int arg0, String arg1) {
					}
				});
			}
			@Override
			public void onProgress(Integer arg0) {
			}
			@Override
			public void onFailure(int arg0, String arg1) {
			}
		});
	}
	
	/**
	 * 以同步方式检测指定的album是否存在。album对象需要设置username和date属性。
	 * @param cxt
	 * @param album
	 * @return
	 */
	public void saveIfNotExists(final Context cxt,final Album album){
		albumBq.addWhereEqualTo("username", album.getUsername());
		albumBq.addWhereEqualTo("albumDate", album.getDate());
		FindListener<Album> fl = new FindListener<Album>() {
			@Override
			public void onError(int arg0, String arg1) {
				
			}
			@Override
			public void onSuccess(List<Album> arg0) {
				if(null == arg0 || !(arg0.size()>0)){
					album.save(cxt);
				}
			}
		};
		albumBq.findObjects(cxt, fl);
	}
	
	public void sleep(int milliseconds){
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
