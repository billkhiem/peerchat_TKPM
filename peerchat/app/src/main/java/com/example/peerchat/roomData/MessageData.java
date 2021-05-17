package com.example.peerchat.roomData;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.security.Timestamp;

@Entity
public class MessageData {
    public MessageData() {
    }

    public MessageData(String messageContent, String senderId, long timestamp) {
        this.messageContent = messageContent;
        this.senderId = senderId;
        this.timestamp = timestamp;
    }


    @PrimaryKey(autoGenerate = true)
    public int id;

    public long timestamp;

    public String senderId;

    public String messageContent;
}
