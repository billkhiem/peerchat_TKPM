package com.example.peerchat.roomData;

import android.graphics.Bitmap;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class RoomData {
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "imei")
    public String imei;

    public String name;

    public int maxMember = -1;

    //List users in room

    //List message in room

//    public String imagePath;

//    @Ignore
//    public Bitmap image;
}
