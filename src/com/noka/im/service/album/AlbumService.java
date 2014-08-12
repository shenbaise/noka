/**
 * 
 */
package com.noka.im.service.album;

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
	/**
	 * 查找用户上传照片的所有日期，每个日期当做一个相册名，逐日显示。
	 * @param cxt
	 * @param userId
	 * @param listener 每天可能有多个Album创建，查出后应该用date字段做个排重。
	 */
	public void queryAlbum(Context cxt, String userId,FindListener<Album> listener){
		BmobQuery<Album> bq = new BmobQuery<Album>();
		bq.addWhereEqualTo("userId", userId);
		bq.findObjects(cxt, listener);
	}
	/**
	 * 查找用户上传照片的所有日期，每个日期当做一个相册名，逐日显示。<b>带翻页条件</b>
	 * @param cxt
	 * @param userId
	 * @param skip
	 * @param size
	 * @param listener
	 */
	public void queryAlbum(Context cxt, String userId,int skip,int size,FindListener<Album> listener){
		BmobQuery<Album> bq = new BmobQuery<Album>();
		bq.addWhereEqualTo("userId", userId);
		bq.setSkip(skip);
		bq.setSkip(size);
		bq.findObjects(cxt, listener);
	}
	/**
	 * 查找用户某个日期上传的照片
	 * @param cxt
	 * @param userId
	 * @param albumDate
	 * @param listener
	 */
	public void queryAlbumPhotos(Context cxt, String userId,String albumDate, FindListener<NokaPhoto> listener){
		BmobQuery<NokaPhoto> bq = new BmobQuery<NokaPhoto>();
		bq.addWhereEqualTo("userId", userId);
		bq.addWhereEqualTo("albumDate", albumDate);
		bq.findObjects(cxt, listener);
	}
	/**
	 * 查找用户某个日期上传的照片
	 * @param cxt
	 * @param userId
	 * @param albumDate
	 * @param listener
	 */
	public void queryAlbumPhotos(Context cxt, String userId,String albumDate ,int skip,int size, FindListener<NokaPhoto> listener){
		BmobQuery<NokaPhoto> bq = new BmobQuery<NokaPhoto>();
		bq.addWhereEqualTo("userId", userId);
		bq.addWhereEqualTo("albumDate", albumDate);
		bq.setSkip(skip);
		bq.setSkip(size);
		bq.findObjects(cxt, listener);
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
}
