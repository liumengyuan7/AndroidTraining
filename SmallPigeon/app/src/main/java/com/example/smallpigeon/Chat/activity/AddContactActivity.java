/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.smallpigeon.Chat.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smallpigeon.Adapter.FindfriendAdapter;
import com.example.smallpigeon.Chat.ChatHelper;
import com.example.smallpigeon.R;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseBaseActivity;
import com.hyphenate.easeui.widget.EaseAlertDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddContactActivity extends EaseBaseActivity {
	private EditText editText;
	private RelativeLayout searchedUserLayout;
	private TextView nameText;
	private Button searchBtn;
	private String toAddUsername;
	private ProgressDialog progressDialog;
	private List<EaseUser> userList=new ArrayList<>();
	private ListView listView;
	private FindfriendAdapter adapter;
	private String myId;
	private Map<String,EaseUser> contactLikeList = new HashMap<>();
	private Handler handlerLogin;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.em_activity_add_contact);

		SharedPreferences pre = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
		String myEmail = pre.getString("user_email","");
		myId = pre.getString("user_id","");

		TextView mTextView = (TextView) findViewById(R.id.add_list_friends);
		listView = findViewById(R.id.addFriendList);
		adapter = new FindfriendAdapter(getApplicationContext(), R.layout.list_item_addcontact, userList, Integer.parseInt(myId), myEmail);
		listView.setAdapter(adapter);

		handlerLogin = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				userList.clear();
				String like = msg.obj.toString();
				if (like.equals("false")) {
					Toast.makeText(getApplicationContext(),"该用户不存在,请再次确认！",Toast.LENGTH_SHORT).show();
				} else {
					try {
						JSONArray jsonArray = new JSONArray(like);
						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject jsonObject = jsonArray.getJSONObject(i);
							JSONObject jsonObject1 = new JSONObject(jsonObject.getString("attrs"));
							EaseUser likeContent = new EaseUser(jsonObject1.get("user_nickname").toString());
							likeContent.setId(Integer.parseInt(jsonObject1.get("id").toString()));
							likeContent.setUserEmail(jsonObject1.get("user_email").toString());
							likeContent.setNickname(jsonObject1.get("user_nickname").toString());
							likeContent.setUserSex(jsonObject1.get("user_sex").toString());
							likeContent.setUserPoints(Integer.parseInt(jsonObject1.get("user_points").toString()));
	//							contactLikeList.put("easeUI"+i,likeContent);
							userList.add(likeContent);
							adapter.notifyDataSetChanged();
						}
						Log.e("模糊查询得到数据", userList.toString());
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		};
		editText = (EditText) findViewById(R.id.edit_note);
		String strAdd = getResources().getString(R.string.add_friend);
		mTextView.setText(strAdd);
		String strUserName = getResources().getString(R.string.user_email);
		editText.setHint(strUserName);
		nameText = (TextView) findViewById(R.id.name);
		searchBtn = (Button) findViewById(R.id.search);
		searchBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!editText.getText().toString().equals("") && editText.getText().toString()!=null) {
					sendMessageToGetLikeContactList(editText.getText().toString());
					Log.e("出来的好友列表",userList.toString());}
				searchContact(v);
			}
		});
	}


	/**
	 * search contact
	 * @param v
	 */
	public void searchContact(View v) {
		final String name = editText.getText().toString();
		String saveText = searchBtn.getText().toString();
		if (getString(R.string.button_search).equals(saveText)) {
			if(TextUtils.isEmpty(name)) {
				new EaseAlertDialog(this, R.string.Please_enter_a_username).show();
				return;
			}
		}
	}
	public void back(View v) {
		ChatHelper.getInstance().sendMessageToGetContactList(getApplicationContext(), Integer.parseInt(myId));
		finish();
	}
	//向服务器发送模糊查找好友的数据
	public void sendMessageToGetLikeContactList(String userEmail){
		new Thread(){
			@Override
			public void run() {
//                String s = utils.getConnectionResult("friend","getContactList");
				try {
					URL url = new URL("http://"+getResources().getString(R.string.ip_address)
							+":8080/smallpigeon/friend/getLikeContactList?userEmail="+userEmail);
					Log.e("url",url.toString());
					URLConnection conn = url.openConnection();
					InputStream in = conn.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
					String result = reader.readLine();
					Message message = new Message();
					message.obj = result;

					Log.e("模糊查询得到数据",result);
					handlerLogin.sendMessage(message);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
}
