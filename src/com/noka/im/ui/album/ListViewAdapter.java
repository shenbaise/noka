package com.noka.im.ui.album;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.bmob.v3.listener.FindListener;

import com.noka.im.R;
import com.noka.im.album.NoScrollGridView;
import com.noka.im.bean.album.Album;
import com.noka.im.bean.album.NokaPhoto;
import com.noka.im.service.album.AlbumService;

public class ListViewAdapter extends BaseAdapter {
	private List<Album> listData = new ArrayList<Album>();
	private Context mContext;
	private static final Set<String> mark = new HashSet<String>(); // 标记是否已经绘过对象制
	public ListViewAdapter(List<Album> mList,Context mContext) {
		super();
		this.listData = mList;
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		if (listData == null) {
			return 0;
		} else {
			return this.listData.size();
		}
	}

	@Override
	public Object getItem(int position) {
		if (listData == null) {
			return null;
		} else {
			return this.listData.get(position);
		}
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, final ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(this.mContext).inflate(
						R.layout.album_listview_item, null, false);
				holder.albumName = (TextView) convertView
						.findViewById(R.id.album_name);
				holder.describe = (TextView) convertView.findViewById(R.id.album_describe);
				holder.gridView = (NoScrollGridView) convertView
						.findViewById(R.id.photos);
				holder.gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));	// 无色
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			final ViewHolder fholder = holder;
			if (this.listData != null) {
				Album album = listData.get(position);
				if (holder.albumName != null) {
					holder.albumName.setText(album.getDate().replace(" ", "\n"));
				}
				
				if(holder.describe != null){
					holder.describe.setText(album.getDesc());
				}
				if (holder.gridView != null) {
					if(!mark.contains(parent.getId() + "#" + position)){
						mark.add(parent.getId() + "#" + position);
						Album data = this.listData.get(position);
						AlbumService albumService = new AlbumService();
						albumService.queryAlbumPhotos(mContext, data.getUsername(), data.getDate(), new FindListener<NokaPhoto>() {
							@Override
							public void onSuccess(List<NokaPhoto> arg0) {
								GridViewAdapter gridViewAdapter = new GridViewAdapter(mContext, arg0);
								fholder.gridView.setAdapter(gridViewAdapter);
							}
							@Override
							public void onError(int arg0, String arg1) {
								
							}
						});
						
					}
				}
			}
		Log.e("#list view", parent.getId() + "#" + position + "#"+ parent.getChildCount());
		return convertView;
	}
	
	private class ViewHolder {
		TextView albumName;
		TextView describe;
		NoScrollGridView gridView;
	}
	
	public void addAlbum(Album album){
		this.listData.add(album);
		this.notifyDataSetChanged();
	}
	
	public void addAlbums(List<Album> albums){
		this.listData.addAll(albums);
		this.notifyDataSetChanged();
	}
}