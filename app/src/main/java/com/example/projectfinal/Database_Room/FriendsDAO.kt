package com.example.projectfinal.Database_Room

import androidx.room.*

@Dao
interface FriendsDAO {

    @Insert
    suspend fun addFriend(friendData: FriendData)

    @Update
    suspend fun editFriend(friendData: FriendData)

    @Delete
    suspend fun deleteFriend(friendData: FriendData)

    @Query("SELECT * FROM frienddata")
    suspend fun getFriend(): List<FriendData>
}