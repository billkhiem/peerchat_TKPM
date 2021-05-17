package com.example.peerchat.roomData;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDaoInterface {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(UserData... users);

    @Delete
    void delete(UserData user);

    @Update
    public void updateUsers(UserData... users);

    @Query("SELECT * FROM UserData")
    List<UserData> getAll();

    @Query("DELETE FROM UserData")
    void deleteAll();


//    @Query("SELECT * FROM UserData")
//    UserData getUserWithName(string );


//    public class NameTuple {
//        @ColumnInfo(name = "first_name")
//        public String firstName;
//
//        @ColumnInfo(name = "last_name")
//        @NonNull
//        public String lastName;
//    }


//    @Query("SELECT first_name, last_name FROM user")
//    public List<NameTuple> loadFullName();

//    @Query("SELECT * FROM user WHERE age > :minAge")
//    public User[] loadAllUsersOlderThan(int minAge);

//  @Query("SELECT * FROM user WHERE age BETWEEN :minAge AND :maxAge")
//  public User[] loadAllUsersBetweenAges(int minAge, int maxAge);
//

//    @Query("SELECT * FROM user WHERE first_name LIKE :search " +
//            "OR last_name LIKE :search")
//    public List<User> findUserWithName(String search);

//@Query("SELECT * FROM user WHERE region IN (:regions)")
//public List<User> loadUsersFromRegions(List<String> regions);
}
