package com.noka.im.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.AbsListView.OnScrollListener;
import cn.bmob.im.bean.BmobChatUser;

import com.noka.im.R;
import com.noka.im.adapter.base.BaseListAdapter;
import com.noka.im.adapter.base.ViewHolder;
import com.noka.im.util.ImageLoadOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 
 * @author shenbai
 *
 */
public class PhotoWallAdapter extends BaseListAdapter<BmobChatUser> implements OnScrollListener {

	public PhotoWallAdapter(Context context, List<BmobChatUser> list) {
		super(context, list);
	}

	@Override
	public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
		
	}

	@Override
	public void onScrollStateChanged(AbsListView arg0, int arg1) {
		
	}

	@Override
	public View bindView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.photo_view, null);
		}
		ImageView photo = ViewHolder.get(convertView, R.id.photo);
		final BmobChatUser me = getList().get(position);
		
		if (photo != null && !photo.equals("")) {
			ImageLoader.getInstance().displayImage(me.getAvatar(), photo, ImageLoadOptions.getOptions());
		} else {
			photo.setImageResource(R.drawable.default_head);
		}
		return convertView;
	}
}
