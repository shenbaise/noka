package com.noka.im.ui.album;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.noka.im.R;
import com.noka.im.bean.album.Album;
import com.noka.im.bean.album.NokaPhoto;

public class GridViewAdapter extends BaseAdapter {
	private Context mContext;
	private List<NokaPhoto> gridData;
	
	private Album album;
	
	public GridViewAdapter(Context mContext,List<NokaPhoto> mList) {
		super();
		this.mContext = mContext;
		this.gridData = mList;
	}

	@Override
	public int getCount() {
		if (gridData == null) {
			return 0;
		} else {
			return this.gridData.size();
		}
	}

	@Override
	public Object getItem(int position) {
		if (gridData == null) {
			return null;
		} else {
			return this.gridData.get(position);
		}
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(this.mContext).inflate(
					R.layout.album_grid_image, null, false);
			holder.image = (ImageView) convertView.findViewById(R.id.item_grida_image);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (this.gridData != null) {
			NokaPhoto nokaPhoto = this.gridData.get(position);
			if (holder.image != null) {
				nokaPhoto.getImage().loadImageThumbnail(mContext, holder.image, 80, 80);
			}
		}
		return convertView;
	}

	private class ViewHolder {
		ImageView image;
	}
}