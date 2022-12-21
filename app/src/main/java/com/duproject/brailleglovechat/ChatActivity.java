package com.duproject.brailleglovechat;

import java.io.IOException;
import java.util.ArrayList;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

public class ChatActivity extends AppCompatActivity {

    public final int MESSAGE_READ = 1;

    ArrayList<Message> chat = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chat);

        mRecyclerView = findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ChatAdapter(chat);
        mRecyclerView.setAdapter(mAdapter);

        MainActivity.mHandler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case MESSAGE_READ:
                        byte[] readBuf = (byte[]) msg.obj;
                        String readString = new String(readBuf, 0, msg.arg1);
                        chat.add(new Message(readString, 1));
                        mAdapter.notifyDataSetChanged();
                        mRecyclerView.scrollToPosition(chat.size() - 1);
                        break;
                }
            }
        };
    }

    public void sendMessage(View view) {
        EditText editText = findViewById(R.id.text_edit);
        String message = editText.getText().toString();
        MainActivity.mConnectedThread.write(message);
        editText.setText(null);
        chat.add(new Message(message, 0));
        mAdapter.notifyDataSetChanged();
        mRecyclerView.scrollToPosition(chat.size() - 1);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (MainActivity.mConnectedThread != null) {
            MainActivity.mConnectedThread.cancel();
            try {
                MainActivity.btSocket.close();
            } catch (IOException e2) {
                finish();
            }
        }
    }

}