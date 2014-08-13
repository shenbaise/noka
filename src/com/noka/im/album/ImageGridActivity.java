package com.noka.im.album;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.noka.im.R;
import com.noka.im.album.ImageGridAdapter.TextCallback;
import com.noka.im.config.NokaConstants;

public class ImageGridActivity extends Activity {
	
	private static String toastMsg = "最多选择"+NokaConstants.MAX_PIC_TO_UPLOAD+"张图片";
	public static final String EXTRA_IMAGE_LIST = "imagelist";

	List<ImageItem> dataList;
	GridView gridView;
	ImageGridAdapter adapter;
	AlbumHelper helper;
	Button bt;
	
	/**
	 * @author shenbai
	 * handle msg
	 */
	static class MyHandler extends Handler {
		WeakReference<ImageGridActivity> mActivity;
		MyHandler(ImageGridActivity activity) {
			mActivity = new WeakReference<ImageGridActivity>(activity);
		}
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				Toast.makeText(mActivity.get(), toastMsg, Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		}
	}
		
	private Handler handler = new MyHandler(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_image_grid);

		helper = AlbumHelper.getHelper();
		helper.init(getApplicationContext());

		dataList = (List<ImageItem>) getIntent().getSerializableExtra(EXTRA_IMAGE_LIST);

		initView();
		bt = (Button) findViewById(R.id.btn_ok);
		bt.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				ArrayList<String> list = new ArrayList<String>();
				Collection<String> c = adapter.map.values();
				Iterator<String> it = c.iterator();
				for (; it.hasNext();) {
					list.add(it.next());
				}

				if (BitmapUtils.act_bool) {
					Intent intent = new Intent(ImageGridActivity.this,PublishedActivity.class);
					startActivity(intent);
					BitmapUtils.act_bool = false;
				}
				for (int i = 0; i < list.size(); i++) {
					if (BitmapUtils.selectedImages.size() < NokaConstants.MAX_PIC_TO_UPLOAD) {
						BitmapUtils.selectedImages.add(list.get(i));
					}
				}
				finish();
			}
		});
	}

	private void initView() {
		gridView = (GridView) findViewById(R.id.gridview);
		gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter = new ImageGridAdapter(ImageGridActivity.this, dataList,
				handler);
		gridView.setAdapter(adapter);
		adapter.setTextCallback(new TextCallback() {
			public void onListen(int count) {
				bt.setText("完成" + "(" + count + ")");
			}
		});

		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				adapter.notifyDataSetChanged();
			}
		});

	}
}
