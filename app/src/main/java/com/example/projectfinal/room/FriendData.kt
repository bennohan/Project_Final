package com.example.projectfinal.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FriendData (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val name: String,
    val school: String,
    val github: String,
    val photoProfile: String
        )
