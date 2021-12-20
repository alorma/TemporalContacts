package com.alorma.tempcontacts

import android.app.Application
import com.alorma.tempcontacts.data.DataModule
import com.alorma.tempcontacts.screen.contacts.ContactsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TempContactsApplication : Application() {

  override fun onCreate() {
    super.onCreate()

    startKoin {
      androidContext(this@TempContactsApplication)
      modules(
        DataModule(),
        ContactsModule()
      )
    }
  }
}