package com.zftlive.android;

import android.content.Context;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

import com.zftlive.android.base.BaseActivity;
import com.zftlive.android.sample.MainActivity;

/**
 * 程序启动界面
 * @author 曾繁添
 * @version 1.0
 *
 */
public class Launcher extends BaseActivity {

	@Override
	public int bindLayout() {
		return R.layout.activity_launcher;
	}

	@Override
	public void initView(View view) {
		
		//添加动画效果
		AlphaAnimation animation = new AlphaAnimation(0.3f, 1.0f);
		animation.setDuration(1000);
		animation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				//跳转界面
				getOperation().forward(MainActivity.class);
				finish();
			}
		});
		view.setAnimation(animation);
	}

	@Override
	public void doBusiness(Context mContext) {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void destroy() {
		
	}
}