package com.example.projectfinal.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FriendData (
    var name: String,
    var school: String,
    var github: String,
    var photoProfile: String
    ){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
