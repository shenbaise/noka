/**
 * 
 */
package com.noka.im.ui.album;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ListView;
import cn.bmob.v3.listener.FindListener;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
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
	private PullToRefreshListView mListView;
	private ListViewAdapter mListViewAdapter;
	private List<Album> listData = new ArrayList<Album>();
	private String username;
	private String from;
	private static int count = 0;
	private static final int skip = 5;
	public static final Map<Integer,Map<Integer,Bitmap>> photos = new HashMap<Integer,Map<Integer,Bitmap>>();
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
		mListView = (PullToRefreshListView) findViewById(R.id.albumlist);
		mListViewAdapter = new ListViewAdapter(listData, AlbumActivity.this);
		mListView.setAdapter(mListViewAdapter);
		// 获取album
		initData();
		// 上拉刷新
		
		mListView.setMode(Mode.PULL_FROM_END);
		mListView.getLoadingLayoutProxy(false, true).setPullLabel(getString(R.string.pull_up_to_load));  
		mListView.getLoadingLayoutProxy(false, true).setRefreshingLabel(getString(R.string.xlistview_header_hint_loading));  
		mListView.getLoadingLayoutProxy(false, true).setReleaseLabel(getString(R.string.xlistview_header_hint_ready));  
		mListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				pullData();
			}
		});
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
	
	private void pullData() {
		albumService.queryAlbum(getApplicationContext(), username, count, skip, new FindListener<Album>() {
			@Override
			public void onSuccess(List<Album> arg0) {
				count+=arg0.size();
				mListViewAdapter.addAlbums(arg0);
				mListView.onRefreshComplete();
			}
			
			@Override
			public void onError(int arg0, String arg1) {
				mListView.onRefreshComplete();
			}
		});
	}
}
