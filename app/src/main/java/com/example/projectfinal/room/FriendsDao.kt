package com.example.projectfinal.room

import androidx.room.*

@Dao
interface FriendsDao {
    @Insert
    fun addFriend(friendData: FriendData)

    @Update
     fun editFriend(friendData: FriendData)

    @Delete
     fun deleteFriend(friendData: FriendData)

    @Query("SELECT * FROM FriendData")
    fun getFriend(): List<FriendData>

    @Query("SELECT * FROM FriendData WHERE name LIKE :searchQuery OR school LIKE :searchQuery OR github LIKE :searchQuery OR github LIKE :searchQuery")
    fun getsearchDatabase(searchQuery: String): List<FriendData>
}