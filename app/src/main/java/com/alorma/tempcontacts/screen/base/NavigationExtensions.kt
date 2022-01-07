package com.alorma.tempcontacts.screen.base

import androidx.navigation.NavController

fun <T> NavController.setArgument(key: String, value: T) {
  previousBackStackEntry
    ?.savedStateHandle
    ?.set(key, value)
}

fun <T> NavController.getArgument(key: String): T? {
  val value = currentBackStackEntry
    ?.savedStateHandle
    ?.get<T>(key)

  currentBackStackEntry
    ?.savedStateHandle
    ?.clearSavedStateProvider(key)

  return value
}