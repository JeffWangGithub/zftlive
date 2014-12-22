package com.zftlive.android.base;

import java.lang.ref.WeakReference;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.zftlive.android.MApplication;
import com.zftlive.android.data.DTO;
import com.zftlive.android.tools.ToolAlert;
import com.zftlive.android.tools.ToolAlert.ILoadingOnKeyListener;

/**
 * android 系统中的四大组件之一Activity基类
 * @author 曾繁添
 * @version 1.0
 *
 */
public abstract class BaseActivity extends Activity {
	
	/**激活Activity组件意图**/
	protected Intent mIntent = new Intent();
	/***整个应用Applicaiton**/
	protected MApplication mApplication = null;
	/***数据传输对象Key**/
	private final static String DTO_KEY = "dtoKey";
	/**当前Activity的弱引用，防止内存泄露**/
	private WeakReference<Activity> context = null;
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
	 * 跳转Activity
	 * @param activity需要跳转至的Activity
	 */
	protected void forward(Class activity) {
		mIntent.setClass(this, activity);
		startActivity(mIntent);
	}
	
	/**
	 * 设置传递参数
	 * @param key 参数key
	 * @param value 数据传输对象
	 */
	protected void assinAttriblte(DTO value) {
		mIntent.putExtra(DTO_KEY, value);
	}
	
	/**
	 * 获取跳转时设置的参数
	 * @param key
	 * @return
	 */
	protected Object gainAttriblte(String key) {
		DTO parms = gainAttribltes();
		if(null != parms){
			return parms.get(key);
		}
		return null;
	}
	
	/**
	 * 获取跳转参数集合
	 * @return
	 */
	protected DTO gainAttribltes() {
		DTO parms = (DTO)getIntent().getExtras().getSerializable(DTO_KEY);
		return parms;
	}
	
	/**
	 * 设置全局Application传递参数
	 * @param key 参数key
	 * @param value 数据传输对象
	 */
	protected void assinGloableAttriblte(String strKey,Object value) {
		mApplication.assignData(strKey, value);
	}
	
	/**
	 * 获取跳转时设置的参数
	 * @param key
	 * @return
	 */
	protected Object gainGloableAttriblte(String strKey) {
		return mApplication.gainData(strKey);
	}
	
	/**
	 * 弹出等待对话框
	 * @param message 提示信息
	 */
	protected void showLoading(String message){
		ToolAlert.showLoading(this, message);
	}
	
	/**
	 * 弹出等待对话框
	 * @param message 提示信息
	 * @param listener 按键监听器
	 */
	protected void showLoading(String message,ILoadingOnKeyListener listener){
		ToolAlert.showLoading(this, message, listener);
	}
	
	/**
	 * 关闭等待对话框
	 */
	protected void closeLoading(){
		ToolAlert.closeLoading();
	}
}
