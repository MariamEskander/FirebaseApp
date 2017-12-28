package com.mariamnarouz.firebaseapp.ui.chat.chat_details;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;


import com.mariamnarouz.firebaseapp.R;
import com.mariamnarouz.firebaseapp.ui.chat.chat_details.presenter.ChatDetailsPresenter;
import com.mariamnarouz.firebaseapp.data.model.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusAppCompatActivity;


/**
 * Created by Mariam on 9/19/2017.
 */
@RequiresPresenter(ChatDetailsPresenter.class)
public class ChatDetailsActivity extends NucleusAppCompatActivity<ChatDetailsPresenter> {


    @BindView(R.id.relative_layout)
    RelativeLayout relativeLayout;

    @BindView(R.id.list)
    ListView message_listView;


    @BindView(R.id.enterMsgEditTxt)
    EditText enterMsgEditTxt;

    @BindView(R.id.sendmsg)
    ImageView sendmsg;

    ChatDetailsAdapter adapter;
    private ProgressDialog pDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_details);
        ButterKnife.bind(this);

        adapter = new ChatDetailsAdapter(this, new ArrayList<Message>());
        message_listView.setAdapter(adapter);

        sendmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().send_message(enterMsgEditTxt.getText().toString());
            }
        });

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
    }


    public void showDialog() {
        pDialog.show();
    }

    public void hideDialog() {
        pDialog.dismiss();
    }

    public void updateView(ChatDetailsViewModel viewModel) {

        if (viewModel.isProgressBar()) {
            showDialog();
        } else {
            hideDialog();

        if (viewModel.isNotifyDataChanged()) {
            if (viewModel.getMessageAdded()==null) {
                if(viewModel.getAddedMessages().size()>0) {
                    Log.i("activity", "getAddedMessages size= " + viewModel.getAddedMessages().size());
                    setAddedChats(viewModel.getAddedMessages());
                    getPresenter().setAddedMessages();
                }else {
                    setChats(viewModel.getData());
                    Log.i("activity", "size= " + viewModel.getData().size());
                }

            }else {
                addMessage(viewModel.getMessageAdded());
                getPresenter().setMessageAdded();

            }
            getPresenter().setNotifyDataChanged(false);

        }
        if (viewModel.isEditTextError()) {
            showEditTextError();
            getPresenter().setEditTextError(false);
        }
        if (viewModel.isRemoveText()){
            enterMsgEditTxt.setText("");
            getPresenter().setRemoveText();

        }

    }
    }

    private void showEditTextError() {
        enterMsgEditTxt.requestFocus();
        enterMsgEditTxt.setError("Enter message");
    }

    public void setChats(final ArrayList<Message> data) {
        Log.i("setChats", "size " + data.size());
        adapter = new ChatDetailsAdapter(this, data);
        message_listView.setAdapter(adapter);
        message_listView.post(new Runnable() {
            @Override
            public void run() {
                message_listView.setSelection(data.size() - 1);
            }
        });

    }

    public void setAddedChats(final ArrayList<Message> data) {
        Log.i("setChats", "size " + data.size());
        message_listView.post(new Runnable() {
            @Override
            public void run() {
                message_listView.setSelection(adapter.getCount() - 1);
            }
        });

    }

    public void addMessage(final Message data) {
        Log.i("addMessage", "addMessage ");
        adapter.add(data);
        message_listView.post(new Runnable() {
            @Override
            public void run() {
                message_listView.setSelection(adapter.getCount() - 1);
            }
        });
    }


}



