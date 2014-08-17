/**
 * 
 */
package com.noka.im.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import cn.bmob.im.BmobUserManager;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.FindListener;

import com.noka.im.R;
import com.noka.im.album.PhotoActivity;
import com.noka.im.album.PhotoPicActivity;
import com.noka.im.bean.album.NokaPhoto;
import com.noka.im.util.AsyncImageLoader;
import com.noka.im.util.AsyncImageLoader.ImageCallback;
import com.noka.im.view.HeaderLayout.onRightImageButtonClickListener;

/**
 * @author shenbai
 *
 */
public class AlbumActivity extends BaseActivity {

	private GridView noScrollgridview;
	private GridAdapter adapter;
	public static HashMap<Integer,Bitmap> pics = new HashMap<Integer,Bitmap>();
	public String username,from;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_album);
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
	
	public void init() {
		noScrollgridview = (GridView) findViewById(R.id.last_photo);
		noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter = new GridAdapter(this);
		adapter.update();
		noScrollgridview.setAdapter(adapter);
		noScrollgridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(AlbumActivity.this,
						PhotoActivity.class);
				intent.putExtra("ID", arg2);
				startActivity(intent);
			}
		});
	}

	public class GridAdapter extends BaseAdapter {
		private LayoutInflater inflater; // 视图容器
		private int selectedPosition = -1;// 选中的位置
		private boolean shape;
		Context context;
		AsyncImageLoader asyncImageLoader;
		public List<String> imgs = new ArrayList<String>();

		public boolean isShape() {
			return shape;
		}

		public void setShape(boolean shape) {
			this.shape = shape;
		}

		public GridAdapter(Context context) {
			this.context = context;
			inflater = LayoutInflater.from(context);
			userManager = BmobUserManager.getInstance(context);
			asyncImageLoader = new AsyncImageLoader();
		}

		public void update() {
			loading();
		}

		public int getCount() {
			return (imgs.size());
		}

		public Object getItem(int arg0) {
			return null;
		}

		public long getItemId(int arg0) {
			return 0;
		}

		public void setSelectedPosition(int position) {
			selectedPosition = position;
		}

		public int getSelectedPosition() {
			return selectedPosition;
		}

		/**
		 * 下载网络图片并显示
		 */
		public View getView(final int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.item_published_grida,
						parent, false);
				holder = new ViewHolder();
				holder.image = (ImageView) convertView
						.findViewById(R.id.item_grida_image);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			final View v = convertView;

			if (position == imgs.size()) {
				holder.image.setImageBitmap(BitmapFactory.decodeResource(
						getResources(), R.drawable.icon_addpic_unfocused));
			} else {
				Drawable drawable = asyncImageLoader.loadDrawable(
						imgs.get(position), new ImageCallback() {
							@Override
							public void imageLoaded(Drawable imageDrawable,
									String imageUrl) {
								ImageView imageViewByTag = (ImageView) v
										.findViewById(R.id.item_grida_image);
								if (imageViewByTag != null) {
									imageViewByTag
											.setImageDrawable(imageDrawable);
									Bitmap b = ((BitmapDrawable)imageDrawable).getBitmap();
										pics.put(position, b);
								} else {
									// load image failed from Internet
								}
							}
						});
				if (drawable == null) {
					Bitmap bitmap = BitmapFactory.decodeResource(
							getResources(), R.drawable.icon_addpic_unfocused);
					holder.image.setImageBitmap(bitmap);
				} else {
					holder.image.setImageDrawable(drawable);
				}
			}
			return convertView;
		}

		public class ViewHolder {
			public ImageView image;
		}

		Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					adapter.notifyDataSetChanged();
					break;
				}
				super.handleMessage(msg);
			}
		};

		/**
		 * 从服务器获取图片信息
		 */
		public void loading() {
			new Thread(new Runnable() {
				public void run() {
					BmobQuery<NokaPhoto> bq = new BmobQuery<NokaPhoto>();
					bq.addWhereEqualTo("username", username);
					bq.setSkip(0);
					bq.setLimit(100);
					bq.findObjects(getApplicationContext(),
							new FindListener<NokaPhoto>() {
								@Override
								public void onSuccess(List<NokaPhoto> list) {
									
									for (NokaPhoto nokaPhoto : list) {
										BmobFile img = nokaPhoto.getImage();
										String fileUrl = img.getFileUrl();
										imgs.add(fileUrl);
										Message message = new Message();
										message.what = 1;
										handler.sendMessage(message);
									}
								}

								@Override
								public void onError(int arg0, String arg1) {
									Message message = new Message();
									message.what = 1; // 绘制一个表示无法显示的图标
									imgs.add(null);
									handler.sendMessage(message);
								}
							});
				}
			}).start();
		}
	}

	protected void onRestart() {
		super.onRestart();
	}
	
}