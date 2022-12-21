package com.duproject.brailleglovechat;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder>{

    private ArrayList<Message> chat;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private View mView;
        private ViewHolder(View v) {
            super(v);
            mView = v;
        }
    }


    public ChatAdapter(ArrayList<Message> chat) {
        this.chat = chat;
    }

    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v;
        if (viewType == 0)
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.text_view_sent, parent, false);
         else
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.text_view_received, parent, false);


        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        View mView= holder.mView;
        TextView mTextView = mView.findViewById(R.id.textview);
        mTextView.setText(chat.get(position).getMessage());
    }

    @Override
    public int getItemCount() {
        return chat.size();
    }

    @Override
    public int getItemViewType(int position) {
        return chat.get(position).getType();
    }
}