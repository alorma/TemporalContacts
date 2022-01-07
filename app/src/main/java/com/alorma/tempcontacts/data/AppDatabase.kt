package com.alorma.tempcontacts.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DeleteUserEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
  abstract fun deleteUserDao(): DeleteUsersDao
}