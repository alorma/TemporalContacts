package com.alorma.tempcontacts.screen.add

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object AddContactModule {

  operator fun invoke() = module {
    viewModel { params -> AddContactViewModel(get(), params.get()) }
  }
}