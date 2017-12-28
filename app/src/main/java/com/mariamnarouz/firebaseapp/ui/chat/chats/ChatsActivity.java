package com.mariamnarouz.firebaseapp.ui.chat.chats;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.util.Log;
import android.widget.ProgressBar;


import com.mariamnarouz.firebaseapp.R;
import com.mariamnarouz.firebaseapp.ui.MainActivity;
import com.mariamnarouz.firebaseapp.ui.chat.add_chat.AddChatActivity;
import com.mariamnarouz.firebaseapp.ui.chat.chats.presenter.ChatsPresenter;
import com.mariamnarouz.firebaseapp.data.model.Chat;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusAppCompatActivity;

/**
 * Created by Mariam.Narouz on 12/19/2017.
 */

@RequiresPresenter(ChatsPresenter.class)
public class ChatsActivity extends NucleusAppCompatActivity<ChatsPresenter> implements GoToChat{


    @BindView(R.id.activity_chats_btn_add_chat)
    Button activity_chats_btn_add_chat;

    @BindView(R.id.activity_chats_rv_chats)
    RecyclerView fragment_coming_rv_chats;

    @BindView(R.id.activity_chats_pb)
    ProgressBar activity_chats_pb;

    @BindView(R.id.activity_chats_txt_no_results)
    TextView activity_chats_txt_no_results;

    private ChatsAdapter chatsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);
        ButterKnife.bind(this);

        activity_chats_btn_add_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChatsActivity.this, AddChatActivity.class));
            }
        });

        chatsAdapter = new ChatsAdapter(this, new ArrayList<Chat>());
        fragment_coming_rv_chats.setAdapter(chatsAdapter);

        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(this);
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        fragment_coming_rv_chats.setLayoutManager(mLinearLayoutManagerVertical);
        fragment_coming_rv_chats.setItemAnimator(new DefaultItemAnimator());
    }



    public void updateView(ChatsViewModel viewModel) {
        if (viewModel.isFirstOpen()){
            fragment_coming_rv_chats.setVisibility(View.GONE);
            activity_chats_txt_no_results.setVisibility(View.GONE);
            activity_chats_pb.setVisibility(View.VISIBLE);
            if (chatsAdapter != null)
            chatsAdapter.resetAdapter();
            getPresenter().setFirstOpen(false);

        }else {

            if (viewModel.isLoading()){
                activity_chats_pb.setVisibility(View.VISIBLE);
            }else {
                activity_chats_pb.setVisibility(View.GONE);
            }
            if (viewModel.isNotifyDataChanged()){
                if (viewModel.getData().size() > 0 ) {
                    fragment_coming_rv_chats.setVisibility(View.VISIBLE);
                    setData(viewModel.getData());
                }else {
                    fragment_coming_rv_chats.setVisibility(View.GONE);
                    activity_chats_txt_no_results.setVisibility(View.VISIBLE);
                }
                Log.i(" activity", "size= " + viewModel.getData().size());
                getPresenter().setNotifyDataChanged(false);
            }

        }
    }

    public void setData(ArrayList<Chat> data) {
            Log.i("adapter", "count => " + chatsAdapter.getItemCount());
          chatsAdapter.addAll(data);
    }

    @Override
    public void goToChat(int pos) {
        getPresenter().goToChat(pos);
    }
}
