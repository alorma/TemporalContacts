package com.alorma.tempcontacts

import android.app.Application
import com.alorma.tempcontacts.data.DataModule
import com.alorma.tempcontacts.screen.add.AddContactModule
import com.alorma.tempcontacts.screen.contacts.ContactsModule
import com.alorma.tempcontacts.screen.temporal.TemporalModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TempContactsApplication : Application() {

  override fun onCreate() {
    super.onCreate()

    startKoin {
      androidContext(this@TempContactsApplication)
      modules(
        DataModule(),
        ContactsModule(),
        TemporalModule(),
        AddContactModule(),
      )
    }
  }
}