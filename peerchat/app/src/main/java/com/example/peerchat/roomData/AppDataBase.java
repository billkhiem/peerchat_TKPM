package com.example.peerchat.roomData;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {UserData.class, MessageData.class}, version = 3)
public abstract class AppDataBase extends RoomDatabase {
    public abstract UserDaoInterface userDaoInterface();
    public abstract MessageDao messageDao();


}