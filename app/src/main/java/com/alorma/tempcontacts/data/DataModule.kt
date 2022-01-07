package com.alorma.tempcontacts.data

import androidx.room.Room
import contacts.core.Contacts
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object DataModule {
  operator fun invoke(contacts: Contacts) = module {
    factory { ContactsDatasource(contacts) }

    factory {
      Room.databaseBuilder(
        androidContext(),
        AppDatabase::class.java, "delete-user-db"
      ).build()
    }

    factory { get<AppDatabase>().deleteUserDao() }
  }
}