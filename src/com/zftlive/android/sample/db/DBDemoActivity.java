package com.zftlive.android.sample.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.j256.ormlite.android.AndroidDatabaseConnection;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.zftlive.android.R;
import com.zftlive.android.base.BaseActivity;
import com.zftlive.android.base.BaseAdapter;
import com.zftlive.android.sample.db.entity.User;
import com.zftlive.android.tools.ToolAlert;
import com.zftlive.android.tools.ToolDatabase;
import com.zftlive.android.tools.ToolString;

/**
 * 数据库操作示例
 * 
 * @author 曾繁添
 * @version 1.0
 * 
 */
public class DBDemoActivity extends BaseActivity {

	private EditText et_username, et_email, et_u_username, et_u_email;
	private Button btn_add, btn_update, btn_first_page, btn_per_page,btn_next_page, btn_end_page;
	private ListView lv_userlist;
	private UserListAdapter mUserListAdapter;
	private ToolDatabase dbHelper;
	private Dao<User, String> userDao;
	public static final String DB_NAME = "zftlive";
	public static final int DB_VERSION = 1;
	private User selectItem = null;

	@Override
	public int bindLayout() {
		return R.layout.activity_db_demo;
	}

	@Override
	public void initView(View view) {
		et_username = (EditText) findViewById(R.id.et_username);
		et_email = (EditText) findViewById(R.id.et_email);
		et_u_username = (EditText) findViewById(R.id.et_u_username);
		et_u_email = (EditText) findViewById(R.id.et_u_email);

		btn_add = (Button) findViewById(R.id.btn_add);
		btn_update = (Button) findViewById(R.id.btn_update);

		btn_first_page = (Button) findViewById(R.id.btn_first_page);
		btn_per_page = (Button) findViewById(R.id.btn_per_page);
		btn_next_page = (Button) findViewById(R.id.btn_next_page);
		btn_end_page = (Button) findViewById(R.id.btn_end_page);

		lv_userlist = (ListView) findViewById(R.id.lv_userlist);
	}

	@Override
	public void doBusiness(Context mContext) {
		//设置列表适配器
		mUserListAdapter = new UserListAdapter();
		lv_userlist.setAdapter(mUserListAdapter);
		lv_userlist.setOnItemClickListener(mUserListItemClickListener);
		
		//实例化dbHelper
		dbHelper = ToolDatabase.gainInstance(DB_NAME, DB_VERSION);
		dbHelper.createTable(User.class);
		try {
			userDao = (Dao<User, String>)dbHelper.getDao(User.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//设置添加数据按钮监听器
		btn_add.setOnClickListener(mAddUserClickListener);
		
		//设置修改按钮点击监听器
		btn_update.setOnClickListener(mUpdateUserClickListener);
		
		//查询用户
		queryUserList();
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void destroy() {
		if(null != dbHelper) dbHelper.releaseAll();
	}
	
	/**
	 * 查询用户
	 */
	private void queryUserList(){
		try {
			
			//先清除数据
			mUserListAdapter.clear();
			List<User> userList = userDao.queryForAll();
			for (int i = 0; i < userList.size(); i++) {
				User user = userList.get(i);
				user.setOrderNo(String.valueOf(i+1));
				mUserListAdapter.addItem(user);
			}
			//通知数据改变
			mUserListAdapter.notifyDataSetChanged();
		} catch (SQLException e) {
			e.printStackTrace();
			Log.e(TAG, "查询用户列表失败，原因："+e.getMessage());
		}
	}
	
	/**
	 * 请求翻页
	 * @param pageNo 页码
	 * @return 指定页数据集合
	 */
	public List<User> requestPage(int pageNo){
		List<User> result = new ArrayList<User>();
		QueryBuilder<User, String> qb;
		try {
			qb = userDao.queryBuilder();
			//qb.where().eq("parentId", "1111");
			//qb.prepare();
//			qb.limit(maxRows);
//			qb.offset(startRow);
			List<User> subItems = qb.query();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * 添加用户按钮点击事件监听器
	 */
	public View.OnClickListener mAddUserClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			AndroidDatabaseConnection conn = null;
			try {
				String username = et_username.getText().toString();
				String email = et_email.getText().toString();
				if(!ToolString.isNoBlankAndNoNull(username) || !ToolString.isNoBlankAndNoNull(email)){
					ToolAlert.showShort(getContext(), "请输入用户名和邮箱");
					return ;
				}
				
				//获取数据库连接
				conn = new AndroidDatabaseConnection(dbHelper.getWritableDatabase(), true);
	            conn.setAutoCommit(false);
	            
	            //组装实体
				User user = new User();
				user.setId(ToolString.gainUUID());
				user.setUsername(username);
				user.setEmail(email);
				userDao.create(user);
				
				//提交事务
				conn.commit(null);
				ToolAlert.showShort(getContext(), "添加成功");
				
				//刷新列表
				queryUserList();
				
				//清空添加框
				et_username.setText("");
				et_email.setText("");
				
			} catch (Exception e) {
				e.printStackTrace();
				try {
					if (null != conn)
					conn.rollback(null);
					conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
	};
	
	/**
	 * 修改用户按钮点击事件监听器
	 */
	public View.OnClickListener mUpdateUserClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			AndroidDatabaseConnection conn = null;
			try {
				String username = et_u_username.getText().toString();
				String email = et_u_email.getText().toString();
				if(!ToolString.isNoBlankAndNoNull(username) || !ToolString.isNoBlankAndNoNull(email)){
					ToolAlert.showShort(getContext(), "请输入更新用户名和邮箱");
					return ;
				}
	           
	            if(null != selectItem){
	            	//获取数据库连接
					conn = new AndroidDatabaseConnection(dbHelper.getWritableDatabase(), true);
		            conn.setAutoCommit(false);
		            //组装实体
		            selectItem.setUsername(username);
					selectItem.setEmail(email);
					userDao.update(selectItem);
					
					//提交事务
					conn.commit(null);
					
					ToolAlert.showShort(getContext(), "修改成功");
					
					//刷新列表
					queryUserList();
					
	            }else{
	            	ToolAlert.showShort(getContext(), "没有选择用户");
	            }
			} catch (Exception e) {
				e.printStackTrace();
				try {
					if (null != conn)
					conn.rollback(null);
					conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
	};
	
	/**
	 * 用户列表点击事件监听器
	 * 
	 */
	private AdapterView.OnItemClickListener mUserListItemClickListener = new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
			//获取点击的Item
			User item = (User)mUserListAdapter.getItem(position);
			et_u_username.setText(item.getUsername());
			et_u_email.setText(item.getEmail());
		}
	};

	/**
	 * 用户列表适配器
	 * 
	 */
	public class UserListAdapter extends BaseAdapter {

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {

			Holder holder = null;
			// 查找控件
			if (null == convertView) {
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_db_demo_listview_item, null);
				holder = new Holder();
				holder.tv_order_no = (TextView) convertView.findViewById(R.id.tv_order_no);
				holder.tv_username = (TextView) convertView.findViewById(R.id.tv_username);
				holder.tv_email = (TextView) convertView.findViewById(R.id.tv_email);
				holder.btn_update = (Button) convertView.findViewById(R.id.btn_update);
				holder.btn_del = (Button) convertView.findViewById(R.id.btn_del);

				// 缓存Holder
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}

			// 设置数据
			final User rowData = (User) getItem(position);
			holder.tv_order_no.setText(rowData.getOrderNo());
			holder.tv_username.setText(rowData.getUsername());
			holder.tv_email.setText(rowData.getEmail());
			holder.btn_update.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					selectItem = rowData;
					et_u_username.setText(rowData.getUsername());
					et_u_email.setText(rowData.getEmail());
				}
			});
			
			holder.btn_del.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					
					ToolAlert.dialog(getContext(), "删除确认", "确定要删除吗?",
						   new DialogInterface.OnClickListener(){
								@Override
								public void onClick(DialogInterface dialog, int which) {
									mUserListAdapter.removeItem(position);
									mUserListAdapter.notifyDataSetChanged();
									try {
										userDao.delete(rowData);
										ToolAlert.showShort(getContext(), "删除成功");
									} catch (SQLException e) {
										e.printStackTrace();
										ToolAlert.showShort(getContext(), "删除失败，原因："+e.getMessage());
									}
								}
							},
							new DialogInterface.OnClickListener(){
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									dialog.dismiss();
								}
							}
						);
				}
			});

			return convertView;
		}

		public class Holder {
			TextView tv_order_no;
			TextView tv_username;
			TextView tv_email;
			Button btn_update;
			Button btn_del;
		}
	}

}
