package com.zftlive.android.sample.soap;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.serialization.SoapObject;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.zftlive.android.R;
import com.zftlive.android.base.BaseActivity;
import com.zftlive.android.tools.ToolAlert;
import com.zftlive.android.tools.ToolSOAP;


/**
 * 调用WebService接口获取省份Activity
 * @author 曾繁添
 * @version 1.0
 *
 */
public class ProviceActivity extends BaseActivity {
	
	/**显示省份Listview**/
	private ListView mProvinceList;
	/***省份数据集合***/
	private List<String> provinceList = new ArrayList<String>();
	/***获取天气WebService**/
	public static final String WEB_SERVER_URL = "http://www.webxml.com.cn/WebServices/WeatherWebService.asmx";
	/***获取天气WebService命名空间**/
	public static final String NAME_SPACE = "http://WebXml.com.cn/";

	@Override
	public int bindLayout() {
		return R.layout.activity_soap_provice_city;
	}

	@Override
	public void initView(View view) {
		mProvinceList = (ListView) findViewById(R.id.province_list);
		mProvinceList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				getOperation().addParameter("province", provinceList.get(position));
				getOperation().forward(CityActivity.class);
			}
		});
		
	}

	@Override
	public void doBusiness(final Context mContext) {
		//等待对话框
		ToolAlert.showLoading(this, "数据加载中...");
		
		//呼叫WebService接口
		ToolSOAP.callService(ProviceActivity.WEB_SERVER_URL,ProviceActivity.NAME_SPACE,"getSupportProvince", null, new ToolSOAP.WebServiceCallBack() {
			
			@Override
			public void onSucced(SoapObject result) {
				
				//关闭等待对话框
				ToolAlert.closeLoading();
				if(result != null){
					provinceList = parseSoapObject(result);
					mProvinceList.setAdapter(new ArrayAdapter<String>(ProviceActivity.this, android.R.layout.simple_list_item_1, provinceList));
				}else{
					ToolAlert.showShort(mContext, "呼叫WebService-->getSupportProvince失败");
				}
			}

			@Override
			public void onFailure(String result) {
				//关闭等待对话框
				ToolAlert.closeLoading();
				
				ToolAlert.showShort(mContext, "呼叫WebService-->getSupportProvince失败，原因："+result);
			}
		});
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void destroy() {
		
	}
	
	/**
	 * 解析SoapObject对象
	 * @param result SoapObject查询结果对象
	 * @return
	 */
	private List<String> parseSoapObject(SoapObject result){
		List<String> list = new ArrayList<String>();
		SoapObject provinceSoapObject = (SoapObject) result.getProperty("getSupportProvinceResult");
		if(provinceSoapObject == null) {
			return null;
		}
		for(int i=0; i<provinceSoapObject.getPropertyCount(); i++){
			list.add(provinceSoapObject.getProperty(i).toString());
		}
		
		return list;
	}

}
