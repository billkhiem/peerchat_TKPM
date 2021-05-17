package com.example.peerchat.roomData;

import android.graphics.Bitmap;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"deviceAdress"},
        unique = true)})

public class UserData {
    public UserData() {}
    public UserData(String name, String device, String mac) {
        this.name = name;
        this.deviceName = device;
        this.mac = mac;
    }
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;

    public String deviceName;

    @ColumnInfo(name = "deviceAdress")
    public String mac;

}
