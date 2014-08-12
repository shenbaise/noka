package com.noka.im.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


//import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * 异步图片下载
 * 
 * @author shenbai
 *
 */
//@SuppressLint("HandlerLeak")
public class AsyncImageLoader {

	private HashMap<String, SoftReference<Drawable>> imageCache;
	ExecutorService pool = Executors.newFixedThreadPool(2);

	public AsyncImageLoader() {
		imageCache = new HashMap<String, SoftReference<Drawable>>();
	}

	public Drawable loadDrawable(final String imageUrl,final ImageCallback imageCallback) {
		
		if (imageCache.containsKey(imageUrl)) {
			SoftReference<Drawable> softReference = imageCache.get(imageUrl);
			Drawable drawable = softReference.get();
			if (drawable != null) {
				return drawable;
			}
		}

		final Handler handler = new Handler() {
			public void handleMessage(Message message) {
				imageCallback.imageLoaded((Drawable) message.obj, imageUrl);
			}
		};

		pool.submit(new Thread() {
			@Override
			public void run() {
				Drawable drawable = loadImageFromUrl(imageUrl);
				Log.e("==", imageUrl);
				imageCache.put(imageUrl, new SoftReference<Drawable>(drawable));
				Message message = handler.obtainMessage(0, drawable);
				handler.sendMessage(message);
			}
		});
		return null;
	}

	public static Drawable loadImageFromUrl(String url) {
		URL m;
		InputStream i = null;
		try {
			m = new URL(url);
			i = (InputStream) m.getContent();
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
//			if (i != null)
//				try {
//					i.close();
//				} catch (IOException e) {
//				}
		}
		Drawable d = Drawable.createFromStream(i, "src");
		return d;
	}

	public interface ImageCallback {
		public void imageLoaded(Drawable imageDrawable, String imageUrl);
	}

}