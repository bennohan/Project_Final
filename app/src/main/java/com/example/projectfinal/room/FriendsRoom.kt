package com.example.projectfinal.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [FriendData::class],
    version = 1,
    exportSchema = false
)

abstract class FriendsRoom : RoomDatabase(), FriendsDao {
    abstract fun friendsDao(): FriendsDao

    companion object {

        @Volatile
        private var INSTANCE: FriendsRoom? = null

           fun getDatabase(context: Context): FriendsDao {
               val tempInstance = INSTANCE
               if (tempInstance !=null){
                   return tempInstance
               }

               synchronized(this){
                   val instance = Room.databaseBuilder(context.applicationContext, RoomDatabase ::class.java,"FriendsRom")
                       .fallbackToDestructiveMigration()
                       .build() as FriendsRoom
                   INSTANCE = instance
                   return instance
               }
           }
    }
}
