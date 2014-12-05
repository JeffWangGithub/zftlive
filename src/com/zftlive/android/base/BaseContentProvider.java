package com.zftlive.android.base;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

/**
 * android 系统中的四大组件之一ContentProvider基类
 * @author 曾繁添
 * @version 1.0
 *
 */
public class BaseContentProvider extends ContentProvider {

	@Override
	public boolean onCreate() {
		
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		
		return null;
	}

	@Override
	public String getType(Uri uri) {
		
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		
		return null;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		
		return 0;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		
		return 0;
	}

}
