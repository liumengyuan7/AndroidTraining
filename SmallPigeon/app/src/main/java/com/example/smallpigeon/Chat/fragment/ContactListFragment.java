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
package com.example.smallpigeon.Chat.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.example.smallpigeon.Chat.ChatHelper;
import com.example.smallpigeon.Chat.activity.ChatActivity;
import com.example.smallpigeon.R;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseContactListFragment;
import com.hyphenate.util.EMLog;

import java.util.Hashtable;
import java.util.Map;

/**
 * contact list
 * 
 */
public class ContactListFragment extends EaseContactListFragment {
	
    private static final String TAG = ContactListFragment.class.getSimpleName();
//    private ContactSyncListener contactSyncListener;//同步联系人监听
//    private BlackListSyncListener blackListSyncListener;//同步黑名单监听
//    private ContactInfoSyncListener contactInfoSyncListener;//同步联系人信息监听
    private View loadingView;
    private ContactItemView applicationItem;
    private String myId;
//    private InviteMessgeDao inviteMessgeDao;
    private Map<String, EaseUser> m;
    @SuppressLint("InflateParams")
    @Override
    protected void initView() {
        super.initView();
        @SuppressLint("InflateParams") View headerView = LayoutInflater.from(getActivity()).inflate(R.layout.em_contacts_header, null);
        HeaderItemClickListener clickListener = new HeaderItemClickListener();
        applicationItem = (ContactItemView) headerView.findViewById(R.id.application_item);
        applicationItem.setOnClickListener(clickListener);
        listView.addHeaderView(headerView);
        //add loading view
        loadingView = LayoutInflater.from(getActivity()).inflate(R.layout.em_layout_loading_data, null);
        contentContainer.addView(loadingView);
        SharedPreferences pre = getContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        myId = pre.getString("user_id","");
//        ChatHelper.getInstance().sendMessageToGetContactList(getContext(), myId);
        if(!myId.equals("") && myId!=null){
            ChatHelper.getInstance().sendMessageToGetContactList(getContext(), Integer.parseInt(myId));
        }else{

        }
        registerForContextMenu(listView);
    }

//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        if (myId != 0) {
//            ChatHelper.getInstance().sendMessageToGetContactList(getContext(), myId);
//        }
//    }

    @Override
    public void refresh() {
        m = ChatHelper.getInstance().getContactList();
        if (m instanceof Hashtable<?, ?>) {
            //noinspection unchecked
            m = (Map<String, EaseUser>) ((Hashtable<String, EaseUser>)m).clone();
        }
        setContactsMap(m);
        super.refresh();
//        if(inviteMessgeDao == null){
//            inviteMessgeDao = new InviteMessgeDao(getActivity());
//        }
//        if(inviteMessgeDao.getUnreadMessagesCount() > 0){
//            applicationItem.showUnreadMsgView();
//        }else{
//            applicationItem.hideUnreadMsgView();
//        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void setUpView() {
        //设置联系人数据
        m = ChatHelper.getInstance().getContactList();
        if (m instanceof Hashtable<?, ?>) {
            m = (Map<String, EaseUser>) ((Hashtable<String, EaseUser>)m).clone();
        }
        setContactsMap(m);
        super.setUpView();
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EaseUser user = (EaseUser)listView.getItemAtPosition(position);
                if (user != null) {
//                    String username = user.getUsername();
                    String userNickname = user.getNickname();
                    String userId = String.valueOf(user.getId());
                    // demo中直接进入聊天页面，实际一般是进入用户详情页
                    startActivity(new Intent(getActivity(), ChatActivity.class).putExtra("userId", userId)
                            .putExtra("userNickName",userNickname)
                            .putExtra("myId",myId));
                }
            }
        });
//        contactSyncListener = new ContactSyncListener();
//        DemoHelper.getInstance().addSyncContactListener(contactSyncListener);
//        if (DemoHelper.getInstance().isContactsSyncedWithServer()) {
//            loadingView.setVisibility(View.GONE);
//        } else if (DemoHelper.getInstance().isSyncingContactsWithServer()) {
//            loadingView.setVisibility(View.VISIBLE);
//        }
    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        if (contactSyncListener != null) {
//            DemoHelper.getInstance().removeSyncContactListener(contactSyncListener);
//            contactSyncListener = null;
//        }
//    }


	protected class HeaderItemClickListener implements OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
            case R.id.application_item:
                // 进入申请与通知页面
//                startActivity(new Intent(getActivity(), NewFriendsMsgActivity.class));
                break;
            default:
                break;
            }
        }

	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
	    toBeProcessUser = (EaseUser) listView.getItemAtPosition(((AdapterContextMenuInfo) menuInfo).position);
	    toBeProcessUsername = toBeProcessUser.getUsername();
		getActivity().getMenuInflater().inflate(R.menu.em_context_contact_list, menu);
	}

//	@Override
//	public boolean onContextItemSelected(MenuItem item) {
//		if (item.getItemId() == R.id.delete_contact) {
//			try {
//                // delete contact
//                deleteContact(toBeProcessUser);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//			return true;
//		}else if(item.getItemId() == R.id.add_to_blacklist){
//			moveToBlacklist(toBeProcessUsername);
//			return true;
//		}
//		return super.onContextItemSelected(item);
//	}


//	/**
//	 * delete contact
//	 *
//	 * @param tobeDeleteUser
//	 */
//	public void deleteContact(final EaseUser tobeDeleteUser) {
//		String st1 = getResources().getString(R.string.deleting);
//		final String st2 = getResources().getString(R.string.Delete_failed);
//		final ProgressDialog pd = new ProgressDialog(getActivity());
//		pd.setMessage(st1);
//		pd.setCanceledOnTouchOutside(false);
//		pd.show();
//		new Thread(new Runnable() {
//			public void run() {
//				try {
//					EMClient.getInstance().contactManager().deleteContact(tobeDeleteUser.getId()+"");
//					ChatHelper.getInstance().deleteContact(getContext(),myId,tobeDeleteUser.getId());
//					getActivity().runOnUiThread(new Runnable() {
//						public void run() {
//							pd.dismiss();
//							contactList.remove(tobeDeleteUser);
//							contactListLayout.refresh();
//						}
//					});
//				} catch (final Exception e) {
//					getActivity().runOnUiThread(new Runnable() {
//						public void run() {
//							pd.dismiss();
//							Toast.makeText(getActivity(), st2 + e.getMessage(), Toast.LENGTH_LONG).show();
//						}
//					});
//
//				}
//
//			}
//		}).start();
//
//	}

	class ContactSyncListener implements ChatHelper.DataSyncListener {
        @Override
        public void onSyncComplete(final boolean success) {
            EMLog.d(TAG, "on contact list sync success:" + success);
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    getActivity().runOnUiThread(new Runnable(){
                        @Override
                        public void run() {
                            if(success){
                                loadingView.setVisibility(View.GONE);
                                refresh();
                            }else{
                                String s1 = getResources().getString(R.string.get_failed_please_check);
                                Toast.makeText(getActivity(), s1, Toast.LENGTH_LONG).show();
                                loadingView.setVisibility(View.GONE);
                            }
                        }
                    });
                }
            });
        }
    }

//    class ContactInfoSyncListener implements DataSyncListener{
//
//        @Override
//        public void onSyncComplete(final boolean success) {
//            EMLog.d(TAG, "on contactinfo list sync success:" + success);
//            getActivity().runOnUiThread(new Runnable() {
//
//                @Override
//                public void run() {
//                    loadingView.setVisibility(View.GONE);
//                    if(success){
//                        refresh();
//                    }
//                }
//            });
//        }
//
//    }
	
}
