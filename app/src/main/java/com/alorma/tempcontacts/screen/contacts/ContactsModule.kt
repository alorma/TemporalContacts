package com.alorma.tempcontacts.screen.contacts

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ContactsModule {

  operator fun invoke() = module {
    viewModel { ContactsViewModel(get()) }
  }
}