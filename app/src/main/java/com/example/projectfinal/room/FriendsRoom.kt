package com.example.projectfinal.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [FriendData::class],
    version = 1
)

abstract class FriendsRoom : RoomDatabase() {
    abstract fun friendsDao(): FriendsDao

    companion object {

        @Volatile private var instance: FriendsRoom? = null
        private var LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance?: buildDatabase(context).also {

            }
        }

        private fun buildDatabase(context: Context)= Room.databaseBuilder(
            context.applicationContext,
            RoomDatabase::class.java,
            "FriendDatabase.db"
        ).build()
    }
}
