package com.zftlive.android.sample.zxing;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.zftlive.android.base.BaseActivity;
import com.zftlive.android.zxing.CaptureActivity;

/**
 * 二维码扫描示例代码
 * @author 曾繁添
 * @version 1.0
 *
 */
public class ZxingSacnnerActivity extends BaseActivity {

	@Override
	public int bindLayout() {
		return 0;
	}

	@Override
	public void initView(View view) {
		
	}

	@Override
	public void doBusiness(Context mContext) {
		Intent intent = new Intent(this,CaptureActivity.class);
		startActivity(intent);
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void destroy() {
		
	}
}
