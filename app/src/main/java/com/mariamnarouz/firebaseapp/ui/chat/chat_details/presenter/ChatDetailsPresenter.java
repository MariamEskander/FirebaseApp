package com.mariamnarouz.firebaseapp.ui.chat.chat_details.presenter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.mariamnarouz.firebaseapp.data.Singleton;
import com.mariamnarouz.firebaseapp.ui.chat.chat_details.ChatDetailsActivity;
import com.mariamnarouz.firebaseapp.ui.chat.chat_details.ChatDetailsViewModel;
import com.mariamnarouz.firebaseapp.data.model.Chat;
import com.mariamnarouz.firebaseapp.data.model.Message;
import com.mariamnarouz.firebaseapp.data.model.User;
import com.mariamnarouz.firebaseapp.util.interactor.Interactor;
import com.mariamnarouz.firebaseapp.util.interactor.InteractorListener;
import com.mariamnarouz.firebaseapp.util.interactor.Result;

import java.util.ArrayList;

import nucleus.presenter.Presenter;


/**
 * Created by Mariam on 9/19/2017.
 */

public class ChatDetailsPresenter extends Presenter<ChatDetailsActivity> implements InteractorListener {

    private ChatDetailsViewModel viewModel;
    private ArrayList<Message> arrayList = new ArrayList<>() , arrayList2 = new ArrayList<>();
    private User user;
    DatabaseReference database;

    @Override
    protected void onCreate(@Nullable Bundle savedState) {
        super.onCreate(savedState);
        if (viewModel == null) {
            viewModel = new ChatDetailsViewModel();
        }

    }

    @Override
    protected void onTakeView(final ChatDetailsActivity activity) {
        super.onTakeView(activity);

        Log.i("here" , "ontakeview");
        user = Singleton.getInstance(activity).getUser();
        viewModel.setId(activity.getIntent().getStringExtra("userId"));
        viewModel.setName(activity.getIntent().getStringExtra("userName"));
        viewModel.setData(new ArrayList<Message>());
        viewModel.setProgressBar(true);
        activity.updateView(viewModel);
        getChats();
    }


    private void getChats() {
        database = FirebaseDatabase.getInstance().getReference();
        database.child("chats").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList = new ArrayList<>();
                if (viewModel.getData().size() == 0 && viewModel.getMessageAdded() == null) {
                    Log.i("count", "" + dataSnapshot.getChildrenCount());
                    if (dataSnapshot.getChildrenCount() == 0) {
                        DatabaseReference myRef = database.child("chats");
                        String chatId = myRef.push().getKey();
                        Chat chat = new Chat(chatId, viewModel.getName(), viewModel.getId(),user.getName(), user.getId());
                        myRef.child(chatId).setValue(chat);
                        viewModel.setChatId(chatId);
                        viewModel.setProgressBar(false);
                    } else {
                        for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                            Chat chat = noteDataSnapshot.getValue(Chat.class);
                            assert chat != null;
                            if (chat.getReceiverId().equals(user.getId()) && chat.getSenderId().equals(viewModel.getId())
                                    || chat.getSenderId().equals(user.getId()) && chat.getReceiverId().equals(viewModel.getId())) {
                                viewModel.setChatId(chat.getChatId());
                                for (DataSnapshot dd : noteDataSnapshot.child("messages").getChildren()) {
                                    Message mm = dd.getValue(Message.class);
                                    arrayList.add(mm);
                                }

                            }
                        }
                        if (viewModel.getChatId().equals("")) {
                            DatabaseReference myRef = database.child("chats");
                            String chatId = myRef.push().getKey();
                            Chat chat = new Chat(chatId, viewModel.getName(), viewModel.getId(),user.getName(), user.getId());
                            myRef.child(chatId).setValue(chat);
                            viewModel.setChatId(chatId);
                        }
                        viewModel.setData(arrayList);
                        viewModel.setProgressBar(false);
                        viewModel.setNotifyDataChanged(true);
                    }

                    if (getView() != null)
                        getView().updateView(viewModel);
                }else if (viewModel.getData().size() != 0 && viewModel.getMessageAdded() == null){
                    arrayList2 = new ArrayList<>();
                    int c = 0;
                    for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                        Chat chat = noteDataSnapshot.getValue(Chat.class);
                        assert chat != null;
                        if (chat.getReceiverId().equals(user.getId()) && chat.getSenderId().equals(viewModel.getId())
                                || chat.getSenderId().equals(user.getId()) && chat.getReceiverId().equals(viewModel.getId())) {
                            viewModel.setChatId(chat.getChatId());
                            for (DataSnapshot dd : noteDataSnapshot.child("messages").getChildren()) {
                                Message mm = dd.getValue(Message.class);
                                c++;
                                if (viewModel.getData().size() < c) {
                                    arrayList2.add(mm);
                                }
                            }

                        }
                    }

                    viewModel.setAddedMessages(arrayList2);
                    viewModel.setProgressBar(false);
                    viewModel.setNotifyDataChanged(true);

                    if (getView() != null)
                        getView().updateView(viewModel);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("chatDetails", "onCancelled");
                viewModel.setProgressBar(false);
                viewModel.setNotifyDataChanged(false);
                if (getView() != null)
                    getView().updateView(viewModel);
            }

        });

    }

    @Override
    public void dropView() {
        super.dropView();

    }


    @Override
    public void onTaskStarted(Interactor interactor) {
    }

    @Override
    public void onTaskFinished(Interactor interactor, Result result) {

    }

    @Override
    public void onTaskProgress(Interactor interactor, int progress) {

    }

    @Override
    public void onTaskCanceled(Interactor interactor) {

    }


    public void setEditTextError(boolean error) {
        viewModel.setEditTextError(error);
    }

    public void setRemoveText() {
        viewModel.setRemoveText(false);
    }

    public void setNotifyDataChanged(boolean notifyDataChanged) {
        viewModel.setNotifyDataChanged(notifyDataChanged);
    }
    public void setMessageAdded() {
        viewModel.setMessageAdded(null);
    }

    public void send_message(String s) {

        if (s.equals("") || s.replaceAll(" ", "").equals("")) {
            viewModel.setEditTextError(true);
            if (getView() != null)
                getView().updateView(viewModel);
        } else {
            Message m = new Message(s, user.getName(), user.getId());
            DatabaseReference myRef = database.child("chats").child(viewModel.getChatId()).child("messages");
            String msgId = myRef.push().getKey();
            myRef.child(msgId).setValue(m);
            viewModel.setMessageAdded(m);
            viewModel.setNotifyDataChanged(true);
            viewModel.setRemoveText(true);
            if (getView() != null)
                getView().updateView(viewModel);
        }
    }

    public void setAddedMessages() {
        viewModel.setAddedMessages(new ArrayList<Message>());
    }
}

