package com.mariamnarouz.firebaseapp.ui.chat.add_chat.presenter;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mariamnarouz.firebaseapp.data.Singleton;
import com.mariamnarouz.firebaseapp.data.model.User;
import com.mariamnarouz.firebaseapp.ui.chat.add_chat.AddChatActivity;
import com.mariamnarouz.firebaseapp.ui.chat.add_chat.AddChatViewModel;
import com.mariamnarouz.firebaseapp.util.interactor.Interactor;
import com.mariamnarouz.firebaseapp.util.interactor.InteractorListener;
import com.mariamnarouz.firebaseapp.util.interactor.Result;

import java.util.ArrayList;

import nucleus.presenter.Presenter;

/**
 * Created by Mariam.Narouz on 12/27/2017.
 */

public class AddChatPresenter extends Presenter<AddChatActivity> implements InteractorListener {

    private AddChatViewModel viewModel;
    ArrayList<User> arrayList;
    private User mainUser ;


    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        if (viewModel == null) {
            viewModel = new AddChatViewModel();
        }

    }

    @Override
    protected void onTakeView(AddChatActivity activity) {
        super.onTakeView(activity);

        mainUser = Singleton.getInstance(activity).getUser();
        viewModel.setLoading(true);
        activity.updateView(viewModel);

        getChats();
    }

    private void getChats() {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList = new ArrayList<>();
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    User user = noteDataSnapshot.getValue(User.class);
                    assert user != null;
                    if (!user.getId().equals(mainUser.getId()))
                     arrayList.add(user);
                }
                viewModel.setData(arrayList);
                viewModel.setLoading(false);
                viewModel.setNotifyDataChanged(true);
                if (getView() != null)
                    getView().updateView(viewModel);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("addChat", "onCancelled");
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



    public void setNotifyDataChanged(boolean notifyDataChanged) {
        viewModel.setNotifyDataChanged(notifyDataChanged);
    }

    public String getUserId(int position){
        return arrayList.get(position).getId();
    }

    public String getUserName(int position) {
        return arrayList.get(position).getName();
    }
}

