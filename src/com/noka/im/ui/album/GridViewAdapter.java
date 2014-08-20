package com.noka.im.ui.album;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.noka.im.R;
import com.noka.im.bean.album.NokaPhoto;
import com.noka.im.util.AsyncImageLoader;
import com.noka.im.util.AsyncImageLoader.ImageCallback;

public class GridViewAdapter extends BaseAdapter {
	private Context mContext;
	private List<NokaPhoto> gridData;
	private int listPosition;
	AsyncImageLoader asyncImageLoader = new AsyncImageLoader();
//	private static final Set<String> mark = new HashSet<String>();	// 标记是否更新了image
	
	public GridViewAdapter(Context mContext,List<NokaPhoto> mList,int listPosition) {
		super();
		this.mContext = mContext;
		this.gridData = mList;
		this.listPosition = listPosition;
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
				if(null==holder.image.getDrawable()){
					nokaPhoto.getImage().loadImageThumbnail(mContext, holder.image, 90, 90);
				}
				asyncImageLoader.loadDrawable(
					nokaPhoto.getImage().getFileUrl(), new ImageCallback() {
						@Override
						public void imageLoaded(Drawable imageDrawable,
								String imageUrl) {
							if(imageDrawable!=null){
								Bitmap b = ((BitmapDrawable)imageDrawable).getBitmap();
								AlbumActivity.photos.get(listPosition).put(position,b);
							}
						}
					}
				);
			}
		}
		return convertView;
	}

	private class ViewHolder {
		ImageView image;
	}
}