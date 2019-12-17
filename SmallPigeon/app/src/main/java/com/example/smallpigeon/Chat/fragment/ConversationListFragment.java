package com.example.smallpigeon.Chat.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.hyphenate.util.NetUtils;

import java.util.List;

import static com.hyphenate.easeui.utils.EaseUserUtils.getUserInfo;

public class ConversationListFragment extends EaseConversationListFragment {
    private TextView errorText;
    @Override
    protected void initView() {
        super.initView();
        View errorView = (LinearLayout) View.inflate(getActivity(),R.layout.fragment_conversation_list, null);
        errorItemContainer.addView(errorView);
        errorText = (TextView) errorView.findViewById(R.id.tv_connect_errormsg);

    }

    @Override
    protected void setUpView() {
        super.setUpView();
        registerForContextMenu(conversationListView);
        conversationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EMConversation conversation = conversationListView.getItem(position);
                List<EMMessage> list = conversation.getAllMessages();
               for(int i = 0;i<list.size();i++){
                   Log.e("ConversationLi",list.get(i).getMsgId());
                   Log.e("Second",list.get(i).conversationId());
                   Log.e("Thried",list.get(i).getUserName());
               }
                String username = conversation.conversationId();
//                String nickName = user.getNickname();
                Log.e("EaseCon username",getUserInfo(username)+",");
                if (username.equals(EMClient.getInstance().getCurrentUser()))
                    Toast.makeText(getActivity(), R.string.Cant_chat_with_yourself, Toast.LENGTH_SHORT).show();
                else {
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
                    intent.putExtra(EaseConstant.EXTRA_USER_ID, username);
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
}
