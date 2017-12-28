package com.mariamnarouz.firebaseapp.ui.chat.chat_details;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mariamnarouz.firebaseapp.R;
import com.mariamnarouz.firebaseapp.data.Singleton;
import com.mariamnarouz.firebaseapp.data.model.Message;
import com.mariamnarouz.firebaseapp.data.model.User;

import java.util.ArrayList;

/**
 * Created by Mariam on 9/19/2017.
 */

public class ChatDetailsAdapter extends BaseAdapter {

    private ArrayList<Message> mData;
    private LayoutInflater inflater;
    private Context mcontext;
    private User user;

    ArrayList<String> dateArrayList = new ArrayList<>();
    String date = "";

    public ChatDetailsAdapter(Context context, ArrayList<Message> data) {
        user = Singleton.getInstance(context).getUser();
        inflater = LayoutInflater.from(context);
        this.mData = data;
        this.mcontext = context;

    }

    @Override
    public int getCount() {
        return this.mData.size();
    }

    @Override
    public Object getItem(int position) {
        return this.mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void add(Message item) {
        this.mData.add(item);
        notifyDataSetChanged();
    }

    public void addAll(ArrayList<Message> arrayList) {
        Log.i("adapter arrayList size", String.valueOf(arrayList.size()));
        for (Message chatView : arrayList) {
            add(chatView);
        }
        notifyDataSetChanged();
        Log.i("adapter size", String.valueOf(getCount()));
    }
    void resetAdapter() {
        this.mData = new ArrayList<>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = ((Activity) mcontext).getLayoutInflater();
        MessageHolder holder;


        if (convertView == null) {

            convertView = inflater.inflate(R.layout.chat_details_list_item, parent, false);
            holder = new MessageHolder();
            holder.receiver = (TextView) convertView.findViewById(R.id.receiver);
            holder.receiver_msg = (TextView) convertView.findViewById(R.id.receiver_msg);
            holder.receiver_time = (TextView) convertView.findViewById(R.id.receiver_time);
            holder.right = (LinearLayout) convertView.findViewById(R.id.right);
            holder.left = (LinearLayout) convertView.findViewById(R.id.left);
            holder.sender = (TextView) convertView.findViewById(R.id.sender);
            holder.sender_msg = (TextView) convertView.findViewById(R.id.sender_msg);
            holder.sender_time = (TextView) convertView.findViewById(R.id.sender_time);
            holder.delivered = (ImageView) convertView.findViewById(R.id.delivered);

            convertView.setTag(holder);
        } else {
            holder = (MessageHolder) convertView.getTag();
        }


        final Message message = this.mData.get(position);
        if (user != null && message.getUserId().equals(user.getId())) {
            holder.right.setVisibility(View.VISIBLE);
            holder.left.setVisibility(View.GONE);
            holder.receiver.setText("You");
            holder.receiver_msg.setText(message.getMessage());
        } else {
            holder.right.setVisibility(View.GONE);
            holder.left.setVisibility(View.VISIBLE);
            holder.sender.setText(message.getUserName());
            holder.sender_msg.setText(message.getMessage());
        }
        return convertView;
    }


    private static class MessageHolder {
        LinearLayout right;
        LinearLayout left;
        TextView receiver;
        TextView receiver_msg;
        TextView receiver_time;
        TextView sender;
        TextView sender_msg;
        TextView sender_time;
        ImageView delivered;

    }
}