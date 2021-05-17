package com.example.peerchat.mainChat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import com.example.peerchat.DrawingActivity;

import com.example.peerchat.customAdapters.ChatAdaptor;

import com.example.peerchat.R;
import com.example.peerchat.SendChatInterface;
import com.example.peerchat.roomData.MessageData;
import com.example.peerchat.chatManager.ChatHandle;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageActivity extends AppCompatActivity {

    CircleImageView profile_image;
    TextView username;
    Intent intent;
    ImageButton btn_send;
    EditText textMsg;
    SendChatInterface delegate;
    ListView chatListView;
    ChatAdaptor adapter;
    SearchView search;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener((view) -> {
            finish();
        });
        chatListView = (ListView) findViewById(R.id.listView);
        ChatHandle.shared.adaptor = new ChatAdaptor(this);
        chatListView.setAdapter(ChatHandle.shared.adaptor);
        profile_image = findViewById(R.id.profile_image);
        username = findViewById(R.id.username);

        btn_send = findViewById(R.id.btn_send);
        textMsg = findViewById(R.id.text_msg);
        search = findViewById(R.id.searchView);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                doSearchFor(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                doSearchFor(newText);
                return false;
            }
        });
        intent = getIntent();

        username.setText(getIntent().getStringExtra("username"));

        Glide.with(MessageActivity.this).load(getIntent().getStringExtra("userimageUrl")).into(profile_image);


        btn_send.setOnClickListener(v -> {
            String mes = textMsg.getText().toString();
            if (!mes.equals("")) {
                textMsg.setText("");
                ChatHandle.shared.sendChat(mes);
                Toast.makeText(MessageActivity.this, mes, Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(MessageActivity.this, "T.T", Toast.LENGTH_SHORT).show();
        });


        ImageButton draw = (ImageButton) findViewById(R.id.more_option);
        draw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MessageActivity.this, DrawingActivity.class);
                startActivity(intent);
            }
        });

        List<MessageData> chatData = ChatHandle.shared.msgDao.getMessageFor(ChatHandle.shared.currentChatUser.deviceName);

        if (!chatData.isEmpty()) {
            for (MessageData chat : chatData) {
                ChatHandle.shared.adaptor.data.add(chat.messageContent);
            }
            ChatHandle.shared.adaptor.notifyDataSetChanged();
        }


    }


    private void doSearchFor(String query) {
        if (!query.isEmpty()) {
            List<MessageData> chatData = ChatHandle.shared.msgDao.searchMessageFor(ChatHandle.shared.currentChatUser.deviceName, "%" + query + "%");
            ChatHandle.shared.adaptor.data.clear();
            for (MessageData chat : chatData) {
                ChatHandle.shared.adaptor.data.add(chat.messageContent);
            }
            ChatHandle.shared.adaptor.notifyDataSetChanged();

        } else {
            List<MessageData> chatData = ChatHandle.shared.msgDao.getMessageFor(ChatHandle.shared.currentChatUser.deviceName);
            ChatHandle.shared.adaptor.data.clear();
            for (MessageData chat : chatData) {
                ChatHandle.shared.adaptor.data.add(chat.messageContent);
            }
            Log.i("thie", "empty");
            ChatHandle.shared.adaptor.notifyDataSetChanged();

        }
    }

}