package com.alorma.tempcontacts.screen.base

import androidx.navigation.NavController

fun <T> NavController.getArgument(key: String): T? {
  val value = currentBackStackEntry
    ?.savedStateHandle
    ?.get<T>(key)

  currentBackStackEntry
    ?.savedStateHandle
    ?.clearSavedStateProvider(key)

  return value
}