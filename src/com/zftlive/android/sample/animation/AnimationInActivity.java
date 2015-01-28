package com.zftlive.android.sample.animation;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.zftlive.android.R;
import com.zftlive.android.base.BaseActivity;

/**
 * 动画启动进入界面
 * @author 曾繁添
 * @version 1.0
 *
 */
public class AnimationInActivity extends BaseActivity {

	private Spinner mAnimSp;
	private Button mButton;
	
	@Override
	public int bindLayout() {
		return R.layout.activity_animation_in;
	}

	@Override
	public void initView(View view) {
        mAnimSp = (Spinner) findViewById(R.id.animation_sp);
        mButton=(Button) findViewById(R.id.other_button);
	}

	@Override
	public void doBusiness(Context mContext) {
		String[] ls = getResources().getStringArray(R.array.anim_type);
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < ls.length; i++) {
			list.add(ls[i]);
		}
		ArrayAdapter<String> animType = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);
		animType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mAnimSp.setAdapter(animType);
		mAnimSp.setSelection(0);
		
		mButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				//跳转界面
				getOperation().forward(AnimationOutActivity.class);

				/**
				 * 设置过场动画:注意此方法只能在startActivity和finish方法之后调用
				 * 第一个参数为第一个Activity离开时的动画，第二参数为所进入的Activity的动画效果
				 */
				switch (mAnimSp.getSelectedItemPosition()) {
				case 0:
					//淡入淡出效果
					overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
					//Android内置的
					//overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
					break;
				case 1:
					//放大淡出效果
					overridePendingTransition(R.anim.scale_in,R.anim.alpha_out);
					break;
				case 2:
					//转动淡出效果1
					overridePendingTransition(R.anim.scale_rotate_in,R.anim.alpha_out);
					break;
				case 3:
					//转动淡出效果2
					overridePendingTransition(R.anim.scale_translate_rotate,R.anim.alpha_out);
					break;
				case 4:
					//左上角展开淡出效果
					overridePendingTransition(R.anim.scale_translate,R.anim.alpha_out);
					break;
				case 5:
					//压缩变小淡出效果
					overridePendingTransition(R.anim.hyperspace_in,R.anim.hyperspace_out);
					break;
				case 6:
					//右往左推出效果
					overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
					//Android内置的
					//overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
					break;
				case 7:
					//下往上推出效果
					overridePendingTransition(R.anim.push_up_in,R.anim.push_up_out);
					break;
				case 8:
					//左右交错效果
					overridePendingTransition(R.anim.slide_left,
							R.anim.slide_right);
					break;
				case 9:
					//放大淡出效果
					overridePendingTransition(R.anim.wave_scale,R.anim.alpha_out);
					break;
				case 10:
					//缩小效果
					overridePendingTransition(R.anim.zoom_enter,
							R.anim.zoom_exit);
					break;
				case 11:
					//上下交错效果
					overridePendingTransition(R.anim.slide_up_in,R.anim.slide_down_out);
					break;
				}
			}
		});
	}

	@Override
	public void resume() {

	}

	@Override
	public void destroy() {

	}

}
