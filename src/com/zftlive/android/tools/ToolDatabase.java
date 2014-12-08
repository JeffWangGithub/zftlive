package com.zftlive.android.tools;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.zftlive.android.MApplication;

/**
 * 数据库访问帮助类
 * 
 * @author 曾繁添
 * @version 1.0
 */
public class ToolDatabase extends OrmLiteSqliteOpenHelper {

	private static String databaseName;
	private static int databaseVersion;
	private static List<Class> table = new ArrayList<Class>();
	private static ToolDatabase dbHelper = null;
	
	 /**
	  * 必须对外提供Public构造函数（实例化不用该方法）
	  * @param context 上下文
	  */
	 public ToolDatabase(Context context) {
		 super(context, databaseName, null, databaseVersion);
	 }

	 /**
	  * 实例化对象
	  * @param dbName 数据库名称
	  * @param version  数据库版本
	  * @return
	  */
	public static ToolDatabase gainInstance(String dbName, int version) {
		if (dbHelper == null) {
			databaseName = dbName;
			databaseVersion = version;
			//会隐式调用public构造方法
			dbHelper = OpenHelperManager.getHelper(
					MApplication.gainContext(), ToolDatabase.class);
		}
		return dbHelper;
	}

	/**
	 * 释放数据库连接
	 */
	public void releaseAll() {
		if (dbHelper != null) {
			OpenHelperManager.releaseHelper();
			dbHelper = null;
		}
	}

	/**
	 * 配置实体
	 * @param cls 实体
	 */
	public void addEntity(Class cls) {
		table.add(cls);
	}

	/**
	 * 删除表
	 * @param entity 实体
	 */
	public void dropTable(Class entity) {
		try {
			TableUtils.dropTable(getConnectionSource(), entity, true);
		} catch (SQLException e) {
			Log.e(ToolDatabase.class.getName(), "Unable to drop datbases", e);
		}
	}

	/**
	 * 创建表
	 * @param entity 实体
	 */
	public void createTable(Class entity) {
		try {
			TableUtils.createTableIfNotExists(getConnectionSource(), entity);
		} catch (SQLException e) {
			Log.e(ToolDatabase.class.getName(), "Unable to drop datbases", e);
		}
	}

	/**
	 * 创建SQLite数据库
	 */
	@Override
	public void onCreate(SQLiteDatabase sqliteDatabase,
			ConnectionSource connectionSource) {
		try {
			for (Class entity : table) {
				TableUtils.createTableIfNotExists(connectionSource, entity);
			}
		} catch (SQLException e) {
			Log.e(ToolDatabase.class.getName(), "Unable to create datbases", e);
		}
	}

	/**
	 * 更新SQLite数据库
	 */
	@Override
	public void onUpgrade(SQLiteDatabase sqliteDatabase,
			ConnectionSource connectionSource, int oldVer, int newVer) {
		try {
			for (Class entity : table) {
				TableUtils.dropTable(connectionSource, entity, true);
			}
			onCreate(sqliteDatabase, connectionSource);
		} catch (SQLException e) {
			Log.e(ToolDatabase.class.getName(),
					"Unable to upgrade database from version " + oldVer
							+ " to new " + newVer, e);
		}
	}
}
