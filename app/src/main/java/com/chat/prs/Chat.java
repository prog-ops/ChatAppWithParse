package com.chat.prs;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chat.prs.databinding.ChatBinding;
import com.chat.prs.databinding.ChatItemRcvBinding;
import com.chat.prs.databinding.ChatItemSentBinding;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Chat extends CustomActivity {
    ChatBinding b;
    private ArrayList<Conversation> convList;

    private ChatAdapter adp;
    private String buddy;//username of buddy
    private Date lastMsgDate;//last message in conv
    private boolean isRunning;//if the activity is running or not
    private static Handler handler;//handler

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ChatBinding.inflate(getLayoutInflater());
        View view = b.getRoot();
        setContentView(view);

        convList = new ArrayList<>();
        adp = new ChatAdapter();
//        b.RVCHAT.setAdapter(adp);
        b.LIST.setAdapter(adp);
        b.LIST.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        b.LIST.setStackFromBottom(true);

        b.EDITCHAT.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);

        setTouchNClick(b.BTNSEND.getId());

        buddy = getIntent().getStringExtra(Const.EXTRA_DATA);
        getSupportActionBar().setTitle(buddy);

        handler = new Handler();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isRunning = true;
        loadConversationList();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isRunning = false;
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        if (view.getId() == b.BTNSEND.getId()) {
            sendMessage();
        }
    }

    /**
     * * LOAD CONV LIST FROM PARSE SERVER AND
     * SAVE THE DATE OF LAST MESSAGE THAT WILL BE USED TO LOAD ONLY
     * RECENT NEW MESSAGES
     */
    private void loadConversationList() {
        ParseQuery<ParseObject> q = ParseQuery.getQuery("Chat");

        if (convList.size() == 0) {
            //* LOAD MESSAGES
            ArrayList<String> messages = new ArrayList<>();
            messages.add(buddy);
            messages.add(MainActivity.user.getUsername());
            q.whereContainedIn("sender", messages);
            q.whereContainedIn("receiver", messages);
        } else {
            //* LOAD ONLY NEWLY RECEIVED MESSAGES
            if (lastMsgDate != null) {
                q.whereGreaterThan("createdAt", lastMsgDate);
            }
            q.whereEqualTo("sender", buddy);
            q.whereEqualTo("receiver", MainActivity.user.getUsername());
        }
        q.orderByDescending("createdAt");
        q.setLimit(30);
        q.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (objects != null & objects.size() > 0) {

                    for (int i = objects.size() - 1; i >= 0; i--) {

                        ParseObject po = objects.get(i);
                        Conversation c = new Conversation(
                                po.getString("message"),
                                po.getCreatedAt(),
                                po.getString("sender")
                        );
                        convList.add(c);

                        if (lastMsgDate == null |
                                lastMsgDate.before(c.getDate())) {
                            lastMsgDate = c.getDate();
                        }
                        adp.notifyDataSetChanged();
                    }
                }

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (isRunning) {
                            loadConversationList();
                        }
                    }
                }, 1000);
            }
        });
    }

    /**
     * * SEND MESSAGE BY MAKING A PARSE OBJECT FOR CHAT MESSAGE
     * AND SEND IT TO PARSE SERVER
     * IT DOES NOTHING IF THE TEXT IS EMPTY
     */
    private void sendMessage() {
        if (b.EDITCHAT.length() == 0) {
            return;
        }

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(b.EDITCHAT.getWindowToken(), 0);

        String s = b.EDITCHAT.getText().toString();

        final Conversation c = new Conversation(s, new Date(), MainActivity.user.getUsername());
        c.setStatus(Conversation.STATUS_SENDING);
        convList.add(c);
        adp.notifyDataSetChanged();
        b.EDITCHAT.setText(null);

        ParseObject po = new ParseObject("Chat");
        po.put("sender", MainActivity.user.getUsername());
        po.put("receiver", buddy);
//        po.put("createdAt", "");
        po.put("message", s);
        po.saveEventually(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    c.setStatus(Conversation.STATUS_SENT);
                } else {
                    c.setStatus(Conversation.STATUS_FAILED);
                }
                adp.notifyDataSetChanged();
            }
        });
    }

    /**
     * * ADAPTER FOR CHAT LIST VIEW
     * SHOWS THE SENT OR RECEIVED CHAT MESSAGE IN EACH LIST ITEM
     */
    private class ChatAdapter extends BaseAdapter {
        private ChatItemSentBinding itemSentB;
        private ChatItemRcvBinding itemRcvB;

        @Override
        public int getCount() {
            return convList.size();
        }

        @Override
        public Conversation getItem(int i) {
            return convList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            Conversation conversation = getItem(position);
            if (conversation.isSent()) {
                view = getLayoutInflater().inflate(
                        R.layout.chat_item_sent, null);
            }
            if (conversation.isSent()) {
                view = getLayoutInflater().inflate(
                        R.layout.chat_item_rcv, null);
            }

            TextView label = view.findViewById(R.id.TV_LABEL1);
            label.setText(DateUtils.getRelativeDateTimeString(
                    Chat.this,
                    conversation.getDate().getTime(),
                    DateUtils.SECOND_IN_MILLIS,
                    DateUtils.DAY_IN_MILLIS,
                    0));

            label = view.findViewById(R.id.TV_LABEL2);
            label.setText(conversation.getMessage());

            label = view.findViewById(R.id.TV_LABEL3);
            if (conversation.isSent()) {
                if (conversation.getStatus() == Conversation.STATUS_SENT) {
                    label.setText("Delivered");
                } else if (conversation.getStatus() == Conversation.STATUS_SENDING) {
                    label.setText("Sending...");
                } else {
                    label.setText("Failed");
                }
            } else {
                label.setText("");
            }

            return view;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
