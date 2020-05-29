package com.example.smallpigeon.Chat.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smallpigeon.Chat.ChatHelper;
import com.example.smallpigeon.Chat.activity.ChatActivity;
import com.example.smallpigeon.R;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.model.EaseAtMessageHelper;
import com.hyphenate.easeui.model.EaseDingMessageHelper;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.hyphenate.util.NetUtils;

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

import static com.hyphenate.easeui.utils.EaseUserUtils.getUserInfo;

public class ConversationListFragment extends EaseConversationListFragment {
    private TextView errorText;
    private List<EaseUser> map = new ArrayList<>();
    private Handler handlerCon = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(map.size()!=0) {
                map.clear();
            }
//            list.clear();
            String friends = msg.obj.toString();
            if (friends.equals("false")) {
                Log.e("没有查到朋友数据", "朋友数据");
            } else {
                try {
                    JSONArray jsonArray = new JSONArray(friends);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
//                        JSONObject jsonObject1 = new JSONObject(jsonObject.getString("attrs"));
                        EaseUser contact = new EaseUser(jsonObject.get("user_nickname").toString());
                        contact.setId(Integer.parseInt(jsonObject.get("id").toString()));
                        contact.setUserEmail(jsonObject.get("user_email").toString());
                        contact.setNickname(jsonObject.get("user_nickname").toString());
                        contact.setUserSex(jsonObject.get("user_sex").toString());
                        contact.setUserPoints(Integer.parseInt(jsonObject.get("user_points").toString()));
                        map.add(contact);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    };
    @Override
    protected void initView() {
        super.initView();
        View errorView = (LinearLayout) View.inflate(getActivity(),R.layout.fragment_conversation_list, null);
        errorItemContainer.addView(errorView);
        errorText = (TextView) errorView.findViewById(R.id.tv_connect_errormsg);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        SharedPreferences pre = getContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
//        String myId = pre.getString("user_id","");
//        if(myId!=null && !myId.equals("")) {
//            sendMessageToGetContactList(Integer.parseInt(myId));
//        }
        sendMessageToGetContactList();
    }
    @Override
    protected void setUpView() {
        super.setUpView();
        registerForContextMenu(conversationListView);
        conversationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EMConversation conversation = conversationListView.getItem(position);
                String username = conversation.conversationId();
                if (username.equals(EMClient.getInstance().getCurrentUser()))
                   Toast.makeText(getActivity(), R.string.Cant_chat_with_yourself, Toast.LENGTH_SHORT).show();

                else {
                    String nickName ="";
                    for (int i=0;i<map.size();i++){
                        if(map.get(i).getId()==Integer.parseInt(username)){
                            nickName = map.get(i).getNickname();
                            Log.e("Conversation nickname",nickName);
                            break;
                        }
                    }
                    // start chat acitivity
                    Intent intent = new Intent(getActivity(), ChatActivity.class);
                    if(conversation.isGroup()){
                        if(conversation.getType() == EMConversation.EMConversationType.ChatRoom){
                            // it's group chat
                            intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_CHATROOM);
                        }else{
                            intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_GROUP);
                        }

                    }
                    // it's single chat
                    intent.putExtra(EaseConstant.EXTRA_USER_ID, username).putExtra(EaseConstant.EXTRA_FRIEND_NICKNAME,nickName);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onConnectionDisconnected() {
        super.onConnectionDisconnected();
        if (NetUtils.hasNetwork(getActivity())){
            errorText.setText(R.string.can_not_connect_chat_server_connection);
        } else {
            errorText.setText(R.string.the_current_network);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.em_delete_message, menu);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        boolean deleteMessage = false;
        if (item.getItemId() == R.id.delete_message) {
            deleteMessage = true;
        } else if (item.getItemId() == R.id.delete_conversation) {
            deleteMessage = false;
        }
        EMConversation tobeDeleteCons = conversationListView.getItem(((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position);
        if (tobeDeleteCons == null) {
            return true;
        }
        if(tobeDeleteCons.getType() == EMConversation.EMConversationType.GroupChat){
            EaseAtMessageHelper.get().removeAtMeGroup(tobeDeleteCons.conversationId());
        }
        try {
            // delete conversation
            EMClient.getInstance().chatManager().deleteConversation(tobeDeleteCons.conversationId(), deleteMessage);
//            InviteMessgeDao inviteMessgeDao = new InviteMessgeDao(getActivity());
//            inviteMessgeDao.deleteMessage(tobeDeleteCons.conversationId());
//             To delete the native stored adked users in this conversation.
            if (deleteMessage) {
                EaseDingMessageHelper.get().delete(tobeDeleteCons);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        refresh();

        // update unread count
//        ((MainActivity) getActivity()).updateUnreadLabel();
        return true;
    }
    //向服务器发送查找我的好友的数据
//    public void sendMessageToGetContactList(int myId){
    public void sendMessageToGetContactList(){
        new Thread(){
            @Override
            public void run() {
                try {
//                    URL url = new URL("http://"+getResources().getString(R.string.ip_address)
//                            +":8080/smallpigeon/friend/getContactList?myId="+myId);
                    URL url = new URL("http://"+getResources().getString(R.string.ip_address)
                            +":8080/smallpigeon/friend/searchAllUser");
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String result = reader.readLine();
                    Message message = new Message();
                    message.obj = result;
                    handlerCon.sendMessage(message);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
