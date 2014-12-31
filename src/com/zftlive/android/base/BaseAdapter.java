package com.zftlive.android.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.zftlive.android.MApplication;
import com.zftlive.android.config.SysEnv;
import com.zftlive.android.data.DTO;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Adapter基类
 * 
 * @author 曾繁添
 * @version 1.0
 * 
 */
public abstract class BaseAdapter extends android.widget.BaseAdapter {

	/** 数据存储集合 **/
	protected List<Object> mDataList = new ArrayList<Object>();
	/** Context上下文 **/
	protected Context mContext;

	public BaseAdapter() {
		
	}
	
	public BaseAdapter(Context mContext) {
		this.mContext = mContext;
	}
	
	@Override
	public int getCount() {
		return mDataList.size();
	}

	@Override
	public Object getItem(int position) {
		if (position < mDataList.size())
			return mDataList.get(position);
		else
			return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	/**
	 * 添加数据
	 * @param item 数据项
	 */
	protected boolean addItem(Object object){
		return mDataList.add(object);
	}
	
	/**
	 * 在指定索引位置添加数据
	 * @param location 索引
	 * @param object 数据
	 */
	protected void addItem(int location,Object object){
	     mDataList.add(location, object);
	}
	
	/**
	 * 集合方式添加数据
	 * @param collection 集合
	 */
	protected boolean addItem(Collection<? extends Object> collection){
		return mDataList.addAll(collection);
	}
	
	/**
	 * 在指定索引位置添加数据集合
	 * @param location 索引
	 * @param collection 数据集合
	 */
	protected boolean addItem(int location,Collection<? extends Object> collection){
		return mDataList.addAll(location,collection);
	}
	
	/**
	 * 移除指定对象数据
	 * @param object 移除对象
	 * @return 是否移除成功
	 */
	protected boolean removeItem(Object object){
		return mDataList.remove(object);
	}
	
	/**
	 * 移除指定索引位置对象
	 * @param location 删除对象索引位置
	 * @return 被删除的对象
	 */
	protected Object removeItem(int location){
	    return mDataList.remove(location);
	}
	
	/**
	 * 移除指定集合对象
	 * @param collection 待移除的集合
	 * @return 是否移除成功
	 */
	protected boolean removeAll(Collection<? extends Object> collection){
		return mDataList.removeAll(collection);
	}
	
	/**
	 * 清空数据
	 */
	protected void clear() {
		mDataList.clear();
	}
	
	/**
	 * 获取BaseActivity方法
	 * @return BaseActivity
	 */
	protected BaseActivity getActivity(){
		if(null == mContext ) return null;
		
		if(mContext instanceof BaseActivity)
			return (BaseActivity)mContext;
		
		return null;
	}
}
