package com.mariamnarouz.firebaseapp.ui.chat.add_chat;

import com.mariamnarouz.firebaseapp.data.model.User;

import java.util.ArrayList;

/**
 * Created by Mariam.Narouz on 12/27/2017.
 */

public class AddChatViewModel {
    private boolean loading = true;
    private boolean notifyDataChanged = false;
    private ArrayList<String> data = new ArrayList<>();


    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }

    public boolean isNotifyDataChanged() {
        return notifyDataChanged;
    }

    public void setNotifyDataChanged(boolean notifyDataChanged) {
        this.notifyDataChanged = notifyDataChanged;
    }

    public ArrayList<String> getData() {
        return data;
    }

    public void setData(ArrayList<User> arrayList) {
        this.data.clear();
        for (User user : arrayList)
        this.data.add(user.getName()) ;
    }
}
