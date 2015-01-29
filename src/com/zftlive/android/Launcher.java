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
		animation.setDuration(2000);
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
				//右往左推出效果
//				overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
				//转动淡出效果1
//				overridePendingTransition(R.anim.scale_rotate_in,R.anim.alpha_out);
				//下往上推出效果
				overridePendingTransition(R.anim.push_up_in,R.anim.push_up_out);
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