package com.zftlive.android.base;

import java.lang.ref.WeakReference;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.zftlive.android.MApplication;

/**
 * android 系统中的四大组件之一Activity基类
 * @author 曾繁添
 * @version 1.0
 *
 */
public abstract class BaseActivity extends Activity implements IBaseActivity{

	/***整个应用Applicaiton**/
	private MApplication mApplication = null;
	/**当前Activity的弱引用，防止内存泄露**/
	private WeakReference<Activity> context = null;
	/**当前Activity渲染的视图View**/
	private View mContextView = null;
	/**共通操作**/
	private Operation mBaseOperation = null;
	/**日志输出标志**/
	private final String TAG = BaseActivity.class.getSimpleName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "BaseActivity-->onCreate()");
		
		//获取应用Application
		mApplication = (MApplication)getApplicationContext();
		
		//将当前Activity压入栈
		context = new WeakReference<Activity>(this);
		mApplication.pushTask(context);
		
		//实例化共通操作
		mBaseOperation = new Operation(this);
		
		//设置渲染视图View
		mContextView = LayoutInflater.from(this).inflate(bindLayout(), null);
		setContentView(mContextView);
		
		//初始化控件
		initView(mContextView);
		
		//业务操作
		doBusiness(this);
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
		Log.d(TAG, "BaseActivity-->onRestart()");
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.d(TAG, "BaseActivity-->onStart()");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.d(TAG, "BaseActivity-->onResume()");
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.d(TAG, "BaseActivity-->onPause()");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.d(TAG, "BaseActivity-->onStop()");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "BaseActivity-->onDestroy()");
		mApplication.removeTask(context);
	}
	
	/**
	 * 获取共通操作机能
	 */
	public Operation getOperation(){
		return this.mBaseOperation;
	}
}
