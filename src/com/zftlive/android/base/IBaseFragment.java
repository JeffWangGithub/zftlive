package com.zftlive.android.base;

import android.content.Context;
import android.view.View;

/**
 * Fragment接口
 * @author 曾繁添
 * @version 1.0
 *
 */
public interface IBaseFragment {

	/**
	 * 绑定渲染视图的布局文件
	 * @return 布局文件资源id
	 */
	public int bindLayout();
	
	/**
	 * 初始化控件
	 */
	public void initView(final View view);
	
	/**
	 * 业务处理操作（onCreateView方法中调用）
	 * @param mContext  当前Activity对象
	 */
	public void doBusiness(Context mContext);
	
}
