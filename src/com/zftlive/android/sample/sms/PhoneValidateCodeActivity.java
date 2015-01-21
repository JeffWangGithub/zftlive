package com.zftlive.android.sample.sms;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zftlive.android.R;
import com.zftlive.android.base.BaseActivity;
import com.zftlive.android.tools.ToolAlert;
import com.zftlive.android.tools.ToolSMS;


/**
 * 手机短信验证码示例，支持移动/联通/电信三网大陆手机号
 * @author 曾繁添
 * @version 1.0
 *
 */
public class PhoneValidateCodeActivity extends BaseActivity {

	private EditText et_phone;
	private EditText et_phone_code;
	private Button btn_gain_smscode;
	private Button btn_validate;
	private Timer mTimer = null;
	private TimerTask mTimerTask = null;
	private static int delay = 1 * 1000;  //1s
	private static int period = 1 * 1000;  //1s
	private static int count = 60;  
	private static final int UPDATE_TEXTVIEW = 99;
	
	@Override
	public int bindLayout() {
		return R.layout.activity_phonevalidate_code;
	}

	@Override
	public void initView(View view) {
		et_phone = (EditText)findViewById(R.id.et_phone);
		et_phone_code = (EditText)findViewById(R.id.et_phone_code);
		btn_gain_smscode = (Button)findViewById(R.id.btn_gain_smscode);
		btn_validate = (Button)findViewById(R.id.btn_validate);
	}

	@Override
	public void doBusiness(Context mContext) {
		
		//注册SMSDK
		ToolSMS.initSDK(ToolSMS.APPKEY, ToolSMS.APPSECRET);
		
		//验证不可用
		et_phone_code.setEnabled(false);
		btn_validate.setEnabled(false);
		btn_gain_smscode.setText(String.format(getResources().getString(R.string.sms_timer), count));
		
		
		//获取验证码
		btn_gain_smscode.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(et_phone.getText().toString() != ""){
					ToolSMS.getVerificationCode(et_phone.getText().toString());
					startTimer();
				}else{
					ToolAlert.showShort("请输入大陆的手机号码");
				}
			}
		});
		
		//校验验证码是否正确
		btn_validate.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(et_phone_code.getText().toString() != ""){
					ToolSMS.submitVerificationCode(et_phone.getText().toString(),et_phone_code.getText().toString(),new ToolSMS.IValidateSMSCode() {
						
						@Override
						public void onSucced() {
							ToolAlert.showShort(PhoneValidateCodeActivity.this, "验证成功");
							//释放监听器
							ToolSMS.release();
						}
						
						@Override
						public void onFailed(Throwable e) {
							ToolAlert.showShort(PhoneValidateCodeActivity.this, "验证失败，原因："+e.getMessage());
						}
					});
				}else{
					ToolAlert.showShort("请输入手机验证码");
				}
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
	 * 启动Timer
	 */
	private void startTimer(){
		
		stopTimer();
		
		//输入框不可用，获取验证码按钮不可用
		et_phone.setEnabled(false);
		btn_gain_smscode.setEnabled(false);
		btn_validate.setEnabled(true);
		et_phone_code.setEnabled(true);
		
		if (mTimer == null) {
			mTimer = new Timer();
		}
		if (mTimerTask == null) {
			mTimerTask = new TimerTask() {
				@Override
				public void run() {
					Message message = Message.obtain(handler, UPDATE_TEXTVIEW);     
		            handler.sendMessage(message);   
					count --;
				}
			};
		}

		if(mTimer != null && mTimerTask != null )
			mTimer.schedule(mTimerTask, delay, period);
	}
	
	/**
	 * 停止Timer
	 */
	private void stopTimer(){
		
		btn_gain_smscode.setEnabled(true);
		et_phone.setEnabled(true);
		
		if (mTimer != null) {
			mTimer.cancel();
			mTimer = null;
		}

		if (mTimerTask != null) {
			mTimerTask.cancel();
			mTimerTask = null;
		}	
		
		count = 60;
		btn_gain_smscode.setText(String.format(getResources().getString(R.string.sms_timer), count));
		
	}
	
	/**
	 * 更新倒计时
	 */
	private void updateTextView(){
		
		//停止Timer
		if(count == 0){
			stopTimer();
			return ;
		}
		
		if(count < 10){
			btn_gain_smscode.setText(String.format(getResources().getString(R.string.sms_timer), "0"+String.valueOf(count)));
		}else{
			btn_gain_smscode.setText(String.format(getResources().getString(R.string.sms_timer), count));
		}
	}
	
	/***处理UI线程更新Handle**/
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) 
		{
							
			switch (msg.what) {
			case UPDATE_TEXTVIEW:
				updateTextView();
				break;
			
			default:
				break;
			}
		};
	};
}
