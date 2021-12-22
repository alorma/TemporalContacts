package com.alorma.tempcontacts.data

import contacts.core.Contacts
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object DataModule {
  operator fun invoke(contacts: Contacts) = module {
    factory { ContactsDatasource(androidContext(), contacts) }
  }
}