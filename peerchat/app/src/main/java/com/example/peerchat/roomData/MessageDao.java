package com.example.peerchat.roomData;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MessageDao {
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        void insertAll(MessageData... msgs);

        @Delete
        void delete(MessageData msg);

        @Update
        public void updateUsers(MessageData... msgs);

        @Query("SELECT * FROM MessageData")
        List<MessageData> getAll();

        @Query("DELETE FROM MessageData")
        void deleteAll();

        @Query("SELECT * FROM MessageData where senderId = :id ORDER BY timestamp ASC")
        List<MessageData> getMessageFor(String id);

        @Query("SELECT * FROM MessageData where senderId = :id AND messageContent LIKE :text ORDER BY timestamp ASC")
        List<MessageData> searchMessageFor(String id, String text);
//        @Query("SELECT * FROM user WHERE age > :minAge")
//        public User[] loadAllUsersOlderThan(int minAge);
    }
