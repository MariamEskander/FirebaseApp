package com.mariamnarouz.firebaseapp.ui.chat.chats;


import com.mariamnarouz.firebaseapp.data.model.Chat;
import java.util.ArrayList;

/**
 * Created by Mariam.Narouz on 12/19/2017.
 */

public class ChatsViewModel {
    private boolean firstOpen = true;
    private boolean loading = true;
    private boolean notifyDataChanged = false;
    private ArrayList<Chat> data = new ArrayList<>();



    public boolean isFirstOpen() {
        return firstOpen;
    }

    public void setFirstOpen(boolean firstOpen) {
        this.firstOpen = firstOpen;
    }


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

    public ArrayList<Chat> getData() {
        return data;
    }

    public void setData(ArrayList<Chat> data) {
        this.data.clear();
        this.data = data;
    }
}
