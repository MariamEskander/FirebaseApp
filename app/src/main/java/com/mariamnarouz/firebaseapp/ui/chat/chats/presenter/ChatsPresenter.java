package com.mariamnarouz.firebaseapp.ui.chat.chats.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mariamnarouz.firebaseapp.data.Singleton;
import com.mariamnarouz.firebaseapp.data.model.User;
import com.mariamnarouz.firebaseapp.ui.chat.chat_details.ChatDetailsActivity;
import com.mariamnarouz.firebaseapp.ui.chat.chats.ChatsActivity;
import com.mariamnarouz.firebaseapp.ui.chat.chats.ChatsViewModel;
import com.mariamnarouz.firebaseapp.data.model.Chat;
import com.mariamnarouz.firebaseapp.util.interactor.Interactor;
import com.mariamnarouz.firebaseapp.util.interactor.InteractorListener;
import com.mariamnarouz.firebaseapp.util.interactor.Result;

import java.util.ArrayList;

import nucleus.presenter.Presenter;

/**
 * Created by Mariam.Narouz on 12/19/2017.
 */

public class ChatsPresenter extends Presenter<ChatsActivity> implements InteractorListener {

    private ChatsViewModel viewModel;
    private ArrayList<Chat> chats = new ArrayList<>();
    private User mainUser ;


    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        if (viewModel == null) {
            viewModel = new ChatsViewModel();
        }

    }

    @Override
    protected void onTakeView(ChatsActivity activity) {
        super.onTakeView(activity);

        mainUser = Singleton.getInstance(activity).getUser();
        viewModel.setLoading(true);
        viewModel.setFirstOpen(true);
        activity.updateView(viewModel);
        getChats();
    }

    private void getChats() {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database.child("chats").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (viewModel.getData().size() == 0) {
                    chats = new ArrayList<>();
                    for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                        Chat chat = noteDataSnapshot.getValue(Chat.class);
                        assert chat != null;
                        if (chat.getReceiverId().equals(mainUser.getId()) || chat.getSenderId().equals(mainUser.getId())) {
                            chats.add(chat);
                        }
                    }
                    viewModel.setData(chats);
                    viewModel.setLoading(false);
                    viewModel.setNotifyDataChanged(true);
                    if (getView() != null)
                        getView().updateView(viewModel);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("getChats", "onCancelled");
                viewModel.setLoading(false);
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


    public void setFirstOpen(boolean firstOpen) {
        viewModel.setFirstOpen(false);
    }

    public void setNotifyDataChanged(boolean notifyDataChanged) {
        viewModel.setNotifyDataChanged(notifyDataChanged);
    }

    public void goToChat(int pos) {
        if (getView() != null) {
            Intent intent = new Intent(getView(), ChatDetailsActivity.class);
            if (chats.get(pos).getSenderId().equals(mainUser.getId())){
                intent.putExtra("userId", chats.get(pos).getReceiverId());
                intent.putExtra("userName", chats.get(pos).getReceiver());
            }else {
                intent.putExtra("userId", chats.get(pos).getSenderId());
                intent.putExtra("userName", chats.get(pos).getSender());
            }
            getView().startActivity(intent);
        }
    }
}

