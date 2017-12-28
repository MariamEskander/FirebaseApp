package com.mariamnarouz.firebaseapp.data.model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Mariam.Narouz on 12/27/2017.
 */
@IgnoreExtraProperties
public class Chat {
    private String chatId ;
    private String sender;
    private String senderId;
    private String receiverId;
    private String receiver;

    public Chat() {
    }
    public Chat(String chatId, String sender, String senderId, String receiver, String receiverId) {
        this.chatId = chatId;
        this.sender = sender;
        this.senderId = senderId;
        this.receiver = receiver;
        this.receiverId = receiverId;
    }



    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
}
