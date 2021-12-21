package com.alorma.tempcontacts.screen.temporal

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object TemporalModule {

  operator fun invoke() = module {
    viewModel { TemporalContactsViewModel(get()) }
  }
}