package com.mariamnarouz.firebaseapp.ui.chat.chat_details;

import com.mariamnarouz.firebaseapp.data.model.Message;
import java.util.ArrayList;

/**
 * Created by Mariam on 9/19/2017.
 */

public class ChatDetailsViewModel {
    private String id = "" , name = "" , chatId = "";
    private boolean progressBar = true;

    private ArrayList<Message> data  = new ArrayList<>();
    private ArrayList<Message> addedMessages  = new ArrayList<>();
    private boolean notifyDataChanged = false;

    private boolean editTextError = false;

    private Message messageAdded = null;
    private boolean removeText = false;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public void setName(String name) {
        this.name = name;
    }


    public boolean isEditTextError() {
        return editTextError;
    }

    public void setEditTextError(boolean editTextError) {
        this.editTextError = editTextError;
    }



    public boolean isProgressBar() {
        return progressBar;
    }

    public void setProgressBar(boolean progressBar) {
        this.progressBar = progressBar;
    }



    public boolean isNotifyDataChanged() {
        return notifyDataChanged;
    }

    public void setNotifyDataChanged(boolean notifyDataChanged) {
        this.notifyDataChanged = notifyDataChanged;
    }

    public void removeByPosition(int id){
        this.data.remove(id);
    }

    public void setData(ArrayList<Message> data) {
        this.data.clear();
        this.data.addAll(data);
    }

    public void addData(Message dta){
        this.data.add(dta);
    }
    public ArrayList<Message> getData() {
        return data;
    }


    public void setMessageAdded(Message dta){
        this.messageAdded = dta;

    }

    public ArrayList<Message> getAddedMessages() {
        return addedMessages;
    }

    public void setAddedMessages(ArrayList<Message> added) {
        this.addedMessages.clear();
        this.addedMessages = added;
        if (added.size()>0)
            this.data.addAll(added);
    }

    public Message getMessageAdded(){
        return this.messageAdded;
    }

    public boolean isRemoveText() {
        return removeText;
    }

    public void setRemoveText(boolean removeText) {
        this.removeText = removeText;
    }
}
