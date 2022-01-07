package com.alorma.tempcontacts.screen.edit

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object EditContactModule {

  operator fun invoke() = module {
    viewModel { params -> EditContactViewModel(get(), params.get()) }
  }
}