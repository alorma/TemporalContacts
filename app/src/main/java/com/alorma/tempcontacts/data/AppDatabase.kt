package com.alorma.tempcontacts.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
  entities = [DeleteUserEntity::class],
  version = 1,
)
@TypeConverters(LocalDateTimeConverter::class)
abstract class AppDatabase : RoomDatabase() {
  abstract fun deleteUserDao(): DeleteUsersDao
}