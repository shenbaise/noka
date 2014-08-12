/**
 * 
 */
package com.noka.im.bean.album;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * @author shenbai
 * 	个人照片，照片已日起聚合，一天作为一个条目的展示。
 */
public class NokaPhoto extends BmobObject {

	private static final long serialVersionUID = 3721719545353507228L;
	/**
	 * 相册所有者
	 */
	private String userId;
	/**
	 * 相册日期
	 */
	private String albumDate;
	/**
	 * 照片
	 */
	private BmobFile image;
	
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public BmobFile getImage() {
		return image;
	}
	public void setImage(BmobFile image) {
		this.image = image;
	}

	public String getAlbumDate() {
		return albumDate;
	}

	public void setAlbumDate(String albumDate) {
		this.albumDate = albumDate;
	}
}
