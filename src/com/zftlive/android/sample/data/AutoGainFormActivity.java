package com.zftlive.android.sample.data;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.zftlive.android.R;
import com.zftlive.android.base.BaseActivity;
import com.zftlive.android.data.DTO;
import com.zftlive.android.model.Option;
import com.zftlive.android.tools.ToolAlert;
import com.zftlive.android.tools.ToolData;
import com.zftlive.android.view.SingleSpinner;
import com.zftlive.android.view.SpinnerAdapter;

/**
 * 自动获取表单示例
 * <per>
 * 自动获取输入框、单选框、下拉框表单元素值：
 * 1、使用com.zftlive.android.view.SingleSpinner/RadioButton/CheckBox自定义控件
 * 2、设置android:tag为封装表单键值对的key
 * 3、声明命名空间xmlns:custom="http://schemas.android.com/apk/res/com.zftlive.android"，并追加自定义属性custom:value="male"
 * 4、如下代码即可获取表单数据，复选框选项结果已##连接				
 * 		DTO<String, Object> formData = new DTO<String, Object>();
 * 		formData = ToolData.gainForm((RelativeLayout) findViewById(R.id.frame_root),formData);
 * </per>
 * 
 * @author 曾繁添
 * @version 1.0
 *
 */
public class AutoGainFormActivity extends BaseActivity {
	private SingleSpinner sp_school;
	private Button btn_login;

	@Override
	public int bindLayout() {
		return R.layout.activity_auto_gain_form;
	}

	@Override
	public void initView(View view) {
		btn_login = (Button)findViewById(R.id.btn_login);
		sp_school = (SingleSpinner) findViewById(R.id.sp_school);
		List<Option> data = new ArrayList<Option>();
		data.add(new Option("tjdx","天津大学"));
		data.add(new Option("nkdx","南开大学"));
		data.add(new Option("bjdx","北京大学"));
		data.add(new Option("qhdx","清华大学"));
		SpinnerAdapter mSpinnerAdapter = new SpinnerAdapter(this, R.drawable.view_spinner_drop_list_hover, data);
		mSpinnerAdapter.setDropDownViewResource(R.drawable.view_spinner_drop_list_ys);
		sp_school.setAdapter(mSpinnerAdapter);
	}


	@Override
	public void doBusiness(Context mContext) {
		btn_login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DTO<String, Object> formData = new DTO<String, Object>();
				formData = ToolData.gainForm((RelativeLayout) findViewById(R.id.frame_root),formData);

				ToolAlert.dialog(AutoGainFormActivity.this,"自动获取表单数据结果", formData.toString(),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,int which) {
								dialog.dismiss();
							}
						}, 
						null);
			}
		});
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void destroy() {
		
	}
}
