package com.mariamnarouz.firebaseapp.ui.chat.chats;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mariamnarouz.firebaseapp.R;
import com.mariamnarouz.firebaseapp.data.model.Chat;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mariam.Narouz on 12/20/2017.
 */

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.MeetingViewHolder> {

    protected ArrayList<Chat> mItems;
    protected LayoutInflater mInflater;
    private Context mContext;
    private GoToChat goToChat;



    public ChatsAdapter(Context context, ArrayList<Chat> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.mItems = data;
        try {
            goToChat = (GoToChat) context;
        }catch (Exception e){
            //exception
        }

    }

    @Override
    public MeetingViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = mInflater.inflate(R.layout.chats_list_item, viewGroup, false);
        final MeetingViewHolder holder = new MeetingViewHolder(view);
        return holder;

    }

    public void setItems(ArrayList<Chat> items) {
        mItems = items;
    }

    @Override
    public void onBindViewHolder(MeetingViewHolder viewHolder, int position) {
        final Chat chat = mItems.get(position);
        viewHolder.setData(chat, position);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void add(Chat item) {
        mItems.add(item);
        notifyItemInserted(mItems.size() - 1);
    }

    public void addAll(ArrayList<Chat> arrayList) {
        for (Chat chat: arrayList) {
            add(chat);
        }
        notifyDataSetChanged();

    }

    void resetAdapter() {
        this.mItems = new ArrayList<>();

    }


    private void setUpText(TextView txt, String s) {
        txt.setText(s);
    }

    class MeetingViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.sender)
        TextView sender;


        int mPosition;


        MeetingViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void setData(Chat d, final int position) {
            Log.i("data", position + "");
            setUpText(this.sender, d.getSender());
            this.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToChat.goToChat(position);
                }
            });
            this.mPosition = position;
        }

    }
}
