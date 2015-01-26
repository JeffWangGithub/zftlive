package com.zftlive.android.sample.image;

import java.util.LinkedHashMap;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.zftlive.android.R;
import com.zftlive.android.base.BaseActivity;
import com.zftlive.android.base.BaseAdapter;
import com.zftlive.android.tools.ToolImage;

/**
 * 异步加载图片示例DEMO，防止图片错位
 * @author 曾繁添
 * @version 1.0
 *
 */
public class ImageListviewActivity extends BaseActivity {

	private ListView mListView;
	private MyListViewAdapter mMyListViewAdapter;
	private String imageURLs[] = new String[]{
			"http://www.daqianduan.com/wp-content/uploads/2014/12/kanjian.jpg",
			"http://www.daqianduan.com/wp-content/uploads/2014/11/capinfo.jpg",
			"http://www.daqianduan.com/wp-content/uploads/2014/11/mi-2.jpg",
			"http://www.daqianduan.com/wp-content/uploads/2014/10/dxy.cn_.png",
			"http://www.daqianduan.com/wp-content/uploads/2014/10/xinhua.jpg",
			"http://www.daqianduan.com/wp-content/uploads/2014/09/job.jpg",
			"http://www.daqianduan.com/wp-content/uploads/2013/06/ctrip.png",
			"http://www.daqianduan.com/wp-content/uploads/2014/09/ideabinder.png",
			"http://www.daqianduan.com/wp-content/uploads/2014/05/ymatou.png",
			"http://www.daqianduan.com/wp-content/uploads/2014/03/west_house.jpg",
			"http://www.daqianduan.com/wp-content/uploads/2014/03/youanxianpin.jpg",
			"http://www.daqianduan.com/wp-content/uploads/2014/02/jd.jpg",
			"http://www.daqianduan.com/wp-content/uploads/2013/11/wealink.png",
			"http://www.daqianduan.com/wp-content/uploads/2013/09/exmail.jpg",
			"http://www.daqianduan.com/wp-content/uploads/2013/09/alipay.png",
			"http://www.daqianduan.com/wp-content/uploads/2013/08/huaqiangbei.png",
			"http://www.daqianduan.com/wp-content/uploads/2013/06/ctrip.png",
			"http://www.daqianduan.com/static/img/thumbnail.png",
			"http://www.daqianduan.com/wp-content/uploads/2013/06/bingdian.png",
			"http://www.daqianduan.com/wp-content/uploads/2013/04/ctrip-wireless.png"
	};
	private String titles[] = new String[]{
			"看见网络科技（上海）有限公司招前端开发工程师",
			"首都信息发展股份有限公司招Web前端工程师(北京-海淀)",
			"小米邀靠谱前端一起玩，更关注用户前端体验(北京)",
			"丁香园求多枚Web前端工程师(杭州滨江 8-15K)",
			"新华网招中高级Web前端开发工程师（北京 8-20K）",
			"好声音母公司梦响强音文化传播招前端、交互和UI设计师(上海)",
			"携程网国际业务部招靠谱前端(HTML+CSS+JS)(上海总部)",
			"ideabinder招聘Web前端开发工程师（JS方向 北京 6-12K）",
			"海外购物公司洋码头招Web前端开发工程师（上海）",
			"金山软件-西山居(珠海)招募前端开发工程师、PHP开发工程师",
			"优安鲜品招Web前端开发工程师(上海)",
			"京东招聘Web前端开发工程师(中/高/资深) 8-22K",
			"若邻网(上海)急聘资深前端工程师",
			"腾讯广州研发线邮箱部门招聘前端开发工程师（内部直招）",
			"支付宝招募资深交互设计师、视觉设计师（内部直招）",
			"华强北商城招聘前端开发工程师",
			"携程(上海)框架研发部招开发工程师(偏前端)",
			"阿里巴巴中文站招聘前端开发",
			"多途网络科技 15K 招聘前端开发工程师",
			"携程无线前端团队招聘 直接内部推荐（携程上海总部）"
	};
	private com.nostra13.universalimageloader.core.ImageLoader universalimageloader;
	
	@Override
	public int bindLayout() {
		return R.layout.activity_image_listview;
	}

	@Override
	public void initView(View view) {

		mMyListViewAdapter = new MyListViewAdapter();
		mListView = (ListView)findViewById(R.id.lv_list);
		
		//图片异步加载器
		universalimageloader = ToolImage.initImageLoader(getApplicationContext());
	}

	@Override
	public void doBusiness(Context mContext) {
		//构造数据
		for (int i = 0; i < 20; i++) {
			Map<String,Object> rowData = new LinkedHashMap<String,Object>();
			rowData.put("imageUrl", imageURLs[i]);
			rowData.put("title", i+1+" "+titles[i]);
			mMyListViewAdapter.addItem(rowData);
		}
		mListView.setAdapter(mMyListViewAdapter);
		mMyListViewAdapter.notifyDataSetChanged();
	}

	@Override
	public void resume() {

	}

	@Override
	public void destroy() {

		//清除缓存
		universalimageloader.clearMemoryCache();
	}
	
	public class MyListViewAdapter extends BaseAdapter{
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			ViewHolder mViewHolder;
			if(null == convertView){
				convertView = getLayoutInflater().inflate(R.layout.activity_image_listview_item, null);
				mViewHolder = new ViewHolder();
				mViewHolder.iv_icon = (ImageView)convertView.findViewById(R.id.iv_icon);
				mViewHolder.tv_title = (TextView)convertView.findViewById(R.id.tv_title);
				convertView.setTag(mViewHolder);
			}else{
				mViewHolder = (ViewHolder)convertView.getTag();
			}
			//设置数据
			Map<String,Object> rowData =  (Map)getItem(position);
			//异步加载图片防止错位方法一：com.android.volley.toolbox.ImageLoader
//			ImageLoader mImageLoader = MApplication.getImageLoader();
//			ImageListener mImageListener = mImageLoader.getImageListener(mViewHolder.iv_icon, R.drawable.default_icon, R.drawable.ic_launcher);
//			mImageLoader.get((String)rowData.get("imageUrl"), mImageListener);
			
			//异步加载图片防止错位方法二：com.nostra13.universalimageloader.core.ImageLoader
			universalimageloader.displayImage((String)rowData.get("imageUrl"), mViewHolder.iv_icon, ToolImage.getFadeOptions(R.drawable.default_icon,R.drawable.ic_launcher,R.drawable.ic_launcher));
			mViewHolder.tv_title.setText((String)rowData.get("title"));
			return convertView;
		}
		
		class ViewHolder{
			ImageView iv_icon;
			TextView tv_title;
		}
	}
}
