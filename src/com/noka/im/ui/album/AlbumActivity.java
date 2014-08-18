/**
 * 
 */
package com.noka.im.ui.album;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.widget.ListView;
import cn.bmob.v3.listener.FindListener;

import com.noka.im.R;
import com.noka.im.album.PhotoPicActivity;
import com.noka.im.bean.album.Album;
import com.noka.im.service.album.AlbumService;
import com.noka.im.ui.BaseActivity;
import com.noka.im.view.HeaderLayout.onRightImageButtonClickListener;

/**
 * @author Administrator
 *
 */
public class AlbumActivity extends BaseActivity {
	private ListView mListView;
	private ListViewAdapter mListViewAdapter;
	private List<Album> listData = new ArrayList<Album>();
	private String username;
	private String from;
	private static int count = 0;
	private static final int skip = 5;
	AlbumService albumService = new AlbumService();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.album_activity_list);
		username = getIntent().getStringExtra("username");
		from = getIntent().getStringExtra("from");
		if("me".equals(from)){
			initTopBarForBoth("个人相册", R.drawable.base_action_bar_true_bg_n,
				new onRightImageButtonClickListener() {
					@Override
					public void onClick() {
						startAnimActivity(PhotoPicActivity.class);
						finish();
					}
			});
		}else{
			initTopBarForLeft("个人相册");
		}
		
		init();
	}
	
	
	private void init() {
		mListView = (ListView) findViewById(R.id.albumlist);
		mListViewAdapter = new ListViewAdapter(listData, AlbumActivity.this);
		mListView.setAdapter(mListViewAdapter);
		// 获取album
		initData();
	}
	
	private void initData() {
		albumService.queryAlbum(getApplicationContext(), username, count, skip, new FindListener<Album>() {
			@Override
			public void onSuccess(List<Album> arg0) {
				count+=arg0.size();
				mListViewAdapter.addAlbums(arg0);
			}
			
			@Override
			public void onError(int arg0, String arg1) {
				
			}
		});
	}
}
