package com.alorma.tempcontacts.data

import android.app.Activity
import contacts.core.Contacts
import org.koin.dsl.module

object DataModule {
  operator fun invoke(activity: Activity) = module {
    factory { Contacts(activity) }
    factory { ContactsDatasource(get()) }
  }
}