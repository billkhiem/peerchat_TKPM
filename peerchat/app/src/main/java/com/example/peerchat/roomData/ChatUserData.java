package com.example.peerchat.roomData;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ChatUserData {
    @PrimaryKey
    public String id;

    public String name;
}
