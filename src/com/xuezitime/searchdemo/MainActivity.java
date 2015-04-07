package com.xuezitime.searchdemo;

import java.util.HashMap;
import java.util.Random;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	
	private Button bt_sms;
	
	private RegisterPage registerPage;
	
	// 短信注册，随机产生头像
		private static final String[] AVATARS = {
			"http://tupian.qqjay.com/u/2011/0729/e755c434c91fed9f6f73152731788cb3.jpg",
			"http://99touxiang.com/public/upload/nvsheng/125/27-011820_433.jpg",
			"http://img1.touxiang.cn/uploads/allimg/111029/2330264224-36.png",
			"http://img1.2345.com/duoteimg/qqTxImg/2012/04/09/13339485237265.jpg",
			"http://diy.qqjay.com/u/files/2012/0523/f466c38e1c6c99ee2d6cd7746207a97a.jpg",
			"http://img1.touxiang.cn/uploads/20121224/24-054837_708.jpg",
			"http://img1.touxiang.cn/uploads/20121212/12-060125_658.jpg",
			"http://img1.touxiang.cn/uploads/20130608/08-054059_703.jpg",
			"http://diy.qqjay.com/u2/2013/0422/fadc08459b1ef5fc1ea6b5b8d22e44b4.jpg",
			"http://img1.2345.com/duoteimg/qqTxImg/2012/04/09/13339510584349.jpg",
			"http://img1.touxiang.cn/uploads/20130515/15-080722_514.jpg",
			"http://diy.qqjay.com/u2/2013/0401/4355c29b30d295b26da6f242a65bcaad.jpg"
		};
	
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_main);
			//启动项目时调用
			SMSSDK.initSDK(this, "696edd0cb632", "abfccbc0aa3c3f402d7f6f3bbc6f0614");
			 
			bt_sms = (Button) findViewById(R.id.bt_sms);
			
			//设置按钮点击事件
			bt_sms.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//当按钮被点击时应跳入输入号码及验证码界面
					 //启动项目时 
					registerPage = new RegisterPage();
					registerPage.setRegisterCallback(new EventHandler(){
						
						 public void afterEvent(int event, int result, Object data) {
						        // 解析注册结果
						        if (result == SMSSDK.RESULT_COMPLETE) {
						        @SuppressWarnings("unchecked")
						        HashMap<String,Object> phoneMap = (HashMap<String, Object>) data;
						        String country = (String) phoneMap.get("country");
						        String phone = (String) phoneMap.get("phone"); 

						        // 提交用户信息
						        registerUser(country, phone);
						        }
						        }
						
					});
					registerPage.show(getApplicationContext());
					
				}
			});
		}
		
		private void registerUser(String country, String phone) {
			Random rnd = new Random();
			int id = Math.abs(rnd.nextInt());
			String uid = String.valueOf(id);
			String nickName = "SmsSDK_User_" + uid;
			String avatar = AVATARS[id % 12];
			SMSSDK.submitUserInfo(uid, nickName, avatar, country, phone);
		}
}
