package com.zftlive.android.sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.zftlive.android.R;
import com.zftlive.android.base.BaseActivity;
import com.zftlive.android.base.BaseAdapter;

/**
 * Sample列表集合界面--自动收集AndroidManifest.xml配置
 * <per>
 *	<intent-filter>
 *		<action android:name="android.intent.action.MAIN" />
 *		<category android:name="com.zftlive.android.SAMPLE_CODE" />
 *	</intent-filter>
 *</per>
 * 的Activity
 * @author 曾繁添
 * @version 1.0
 *
 */
public class MainActivity extends BaseActivity {

	private ListView mListView;
	public final static String SAMPLE_CODE = "com.zftlive.android.SAMPLE_CODE";
	
	@Override
	public int bindLayout() {
		return R.layout.activity_main;
	}

	@Override
	public void initView(View view) {
		mListView = (ListView)findViewById(R.id.lv_demos);
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				 Map<String, Object> map = (Map<String, Object>)parent.getItemAtPosition(position);
			     Intent intent = (Intent) map.get("intent");
			     startActivity(intent);
			}
		});
		
		//构造适配器
		DemoActivityAdapter mAdapter = new DemoActivityAdapter(this);
		mAdapter.addItem(getListData());
		mListView.setAdapter(mAdapter);
	}

	@Override
	public void doBusiness(Context mContext) {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void destroy() {
		
	}

	protected List<Map<String, Object>> getListData(){
		List<Map<String, Object>> mListViewData = new ArrayList<Map<String, Object>>();
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(SAMPLE_CODE);
        List<ResolveInfo> mActivityList = getPackageManager().queryIntentActivities(mainIntent, 0);
        for (int i = 0; i < mActivityList.size(); i++) 
        {
            ResolveInfo info = mActivityList.get(i);
            String label = info.loadLabel(getPackageManager()) != null? info.loadLabel(getPackageManager()).toString() : info.activityInfo.name;
        
            Map<String, Object> temp = new HashMap<String, Object>();
            temp.put("title", label);
            temp.put("intent", buildIntent(info.activityInfo.applicationInfo.packageName,info.activityInfo.name));
            mListViewData.add(temp);
        }
        
        return mListViewData;
	}
	
    protected Intent buildIntent(String packageName, String componentName) {
        Intent result = new Intent();
        result.setClassName(packageName, componentName);
        return result;
    }
	
	/**
	 * 列表适配器
	 */
	protected class DemoActivityAdapter extends BaseAdapter{

		public DemoActivityAdapter(Activity mContext) {
			super(mContext);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			Holder mHolder = null;
			if(null == convertView){
				convertView = LayoutInflater.from(getActivity()).inflate(R.layout.activity_main_list_item, null);
				mHolder = new Holder();
				mHolder.label = (TextView)convertView.findViewById(R.id.tv_label);
				convertView.setTag(mHolder);
			}else{
				mHolder = (Holder) convertView.getTag();
			}
			
			//设置数据
			mHolder.label.setText((String)((Map<String,Object>)getItem(position)).get("title"));
			
			return convertView;
		}
		
		class Holder{
			TextView label;
		}
	}

}
