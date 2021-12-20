package com.alorma.tempcontacts.data

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object DataModule {
  operator fun invoke() = module {
    factory { ContactsDatasource(androidContext()) }
  }
}