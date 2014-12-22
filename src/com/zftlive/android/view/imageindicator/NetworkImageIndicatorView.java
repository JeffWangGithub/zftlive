package com.zftlive.android.view.imageindicator;

import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView.ScaleType;

import com.android.http.WebImageView;
import com.zftlive.android.MApplication;
import com.zftlive.android.tools.ToolResource;

/**
 * Network ImageIndicatorView, by urls
 * 
 * @author steven-pan
 * 
 */
public class NetworkImageIndicatorView extends ImageIndicatorView {

	/**默认资源图片ID**/
	private int default_image = -1;
	
	public NetworkImageIndicatorView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public NetworkImageIndicatorView(Context context) {
		super(context);
	}

	/**
	 * 设置显示图片URL列表
	 * 
	 * @param urlList
	 *            URL列表
	 */
	public void setupLayoutByImageUrl(final List<String> urlList) {
		if (urlList == null)
			throw new NullPointerException();

		final int len = urlList.size();
		if (len > 0) {
			for (int index = 0; index < len; index++) {
				final WebImageView pageItem = new WebImageView(getContext());
				pageItem.setScaleType(ScaleType.FIT_XY);
				if(-1 != default_image){
					pageItem.setDefaultImageResId(default_image);
				}else{
					pageItem.setDefaultImageResId(ToolResource.getDrawableId("ic_launcher"));
				}
				pageItem.setImageUrl(urlList.get(index),
						MApplication.getImageLoader());
				addViewItem(pageItem);
			}
		}
	}
	
	/**
	 * 设置默认图片
	 * @param resId 默认图片资源ID
	 */
	public void setDefaultImage(int resId){
		this.default_image = resId;
	}

}
