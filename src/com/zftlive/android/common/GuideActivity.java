package com.zftlive.android.common;

import android.content.Context;
import android.view.View;

import com.zftlive.android.R;
import com.zftlive.android.base.BaseActivity;
import com.zftlive.android.view.imageindicator.ImageIndicatorView;

/**
 * 引导界面
 * @author 曾繁添
 * @version 1.0
 *
 */
public class GuideActivity extends BaseActivity {

	private ImageIndicatorView imageIndicatorView;
	@Override
	public int bindLayout() {
		return R.layout.activity_guide;
	}

	@Override
	public void initView(View view) {
		imageIndicatorView = (ImageIndicatorView) findViewById(R.id.guide_indicate_view);
		//滑动监听器
		imageIndicatorView.setOnItemChangeListener(new ImageIndicatorView.OnItemChangeListener() {
			@Override
			public void onPosition(int position, int totalCount) {
				if(position==totalCount){
					finish();
				}
			}
		});
	}

	@Override
	public void doBusiness(Context mContext) {
		
		final Integer[] resArray = new Integer[] { R.drawable.guide01, R.drawable.guide02, R.drawable.guide03 };
		imageIndicatorView.setupLayoutByDrawable(resArray);
		imageIndicatorView.setIndicateStyle(ImageIndicatorView.INDICATE_USERGUIDE_STYLE);
		imageIndicatorView.show();
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void destroy() {
		
	}

}
