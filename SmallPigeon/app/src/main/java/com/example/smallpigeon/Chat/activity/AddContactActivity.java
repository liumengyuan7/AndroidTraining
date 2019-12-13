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
import com.example.smallpigeon.Adapter.FindfriendAdapter;
import com.example.smallpigeon.Chat.ChatHelper;
import com.example.smallpigeon.R;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseBaseActivity;
import com.hyphenate.easeui.widget.EaseAlertDialog;

import java.util.ArrayList;
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
	private int myId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.em_activity_add_contact);
		SharedPreferences pre = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
		String myEmail = pre.getString("user_email","");
		myId = Integer.parseInt(pre.getString("user_id",""));
		TextView mTextView = (TextView) findViewById(R.id.add_list_friends);
		listView = findViewById(R.id.addFriendList);
		adapter = new FindfriendAdapter(getApplicationContext(),R.layout.list_item_addcontact,userList,myId,myEmail);
		listView.setAdapter(adapter);
		editText = (EditText) findViewById(R.id.edit_note);

		ChatHelper.getInstance().sendMessageToSearchAllUser(getApplicationContext());

		Log.e("得到的好友数据",userList.toString());
		editText.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				editText.getText().toString();
				userList.clear();
				Map<String, EaseUser> users = ChatHelper.getInstance().getAllUser();
				for (int i=0;i<users.size();i++){
					EaseUser user = users.get("easeUI"+i);
					userList.add(user);
				}
				Log.e("出来的好友列表",userList.toString());
				adapter.notifyDataSetChanged();
//				searchedUserLayout.setVisibility(View.VISIBLE);

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
		String strAdd = getResources().getString(R.string.add_friend);
		mTextView.setText(strAdd);
		String strUserName = getResources().getString(R.string.user_email);
		editText.setHint(strUserName);
		searchedUserLayout = (RelativeLayout) findViewById(R.id.ll_user);
		nameText = (TextView) findViewById(R.id.name);
		searchBtn = (Button) findViewById(R.id.search);
		searchBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!editText.getText().toString().equals("") && editText.getText().toString()!=null) {
				}

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
//			toAddUsername = name;
			if(TextUtils.isEmpty(name)) {
				new EaseAlertDialog(this, R.string.Please_enter_a_username).show();
				return;
			}
//			Log.e("添加好友界面",ChatHelper.getInstance().getContactList().toString());
//			// TODO you can search the user from your app server here.
//			if(ChatHelper.getInstance().getContactList().containsValue(toAddUsername)){
//				searchedUserLayout.setVisibility(View.VISIBLE);
//				nameText.setText(toAddUsername);
//			}

			//show the userame and add button if user exist

//			searchedUserLayout.setVisibility(View.VISIBLE);
//			nameText.setText(toAddUsername);
			
		} 
	}	
	
	public void back(View v) {
		ChatHelper.getInstance().sendMessageToGetContactList(getApplicationContext(), myId);
		finish();
	}
}
