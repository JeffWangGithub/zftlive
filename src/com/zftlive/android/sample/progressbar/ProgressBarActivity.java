package com.zftlive.android.sample.progressbar;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.zftlive.android.R;
import com.zftlive.android.base.BaseActivity;
import com.zftlive.android.view.progressbar.RoundProgressBar;

/**
 * 进度条示例（水平进度条+垂直进度条+圆形进度条）
 * @author 曾繁添
 * @version 2.0
 *
 */
public class ProgressBarActivity extends BaseActivity {
	private RoundProgressBar mRoundProgressBar1 ,mRoundProgressBar3, mRoundProgressBar4, mRoundProgressBar5;
	private Button button1;
	private int progress = 0;

	@Override
	public int bindLayout() {
		return R.layout.activity_progressbar;
	}

	@Override
	public void initView(View view) {
		mRoundProgressBar1 = (RoundProgressBar) findViewById(R.id.roundProgressBar1);
		mRoundProgressBar3 = (RoundProgressBar) findViewById(R.id.roundProgressBar3);
		mRoundProgressBar4 = (RoundProgressBar) findViewById(R.id.roundProgressBar4);
		mRoundProgressBar5 = (RoundProgressBar) findViewById(R.id.roundProgressBar5);
		
		button1 = (Button)findViewById(R.id.button1);
	}

	@Override
	public void doBusiness(Context mContext) {
		
		//设置进度监听器
		mRoundProgressBar1.setOnLoadFinishListener(new RoundProgressBar.OnLoadFinishListener() {
			
			@Override
			public void onLoadFinished() {
				Toast.makeText(getApplicationContext(), "进度条加载完成事件触发了", Toast.LENGTH_LONG).show();
			}
		});
		
		button1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						while(progress <= 100){
							progress += 3;
							mRoundProgressBar1.setProgress(progress);
							mRoundProgressBar3.setProgress(progress);
							mRoundProgressBar4.setProgress(progress);
							mRoundProgressBar5.setProgress(progress);
							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						
					}
				}).start();
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
