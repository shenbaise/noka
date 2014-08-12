/**
 * 
 */
package com.noka.im.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * @author shenbai
 * 个人相册
 */
public class Album extends BmobObject {

	private static final long serialVersionUID = 3721719545353507228L;
	/**
	 * 相册所有者
	 */
	private String userId;
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
}
