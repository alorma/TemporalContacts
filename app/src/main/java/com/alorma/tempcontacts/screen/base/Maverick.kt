package com.alorma.tempcontacts.screen.base

sealed class Maverick<out T> {

  object Uninitialized : Maverick<Nothing>()
  data class Loading<T>(val value: T? = null) : Maverick<T>()
  data class Success<T>(val value: T) : Maverick<T>()
  data class Fail(val throwable: Throwable) : Maverick<Nothing>()
}