package com.alorma.tempcontacts.screen.add

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class AddContactViewModel : ViewModel() {

  var nameValue by mutableStateOf<String?>(null)
  var phoneValue by mutableStateOf<String?>(null)
  var emailValue by mutableStateOf<String?>(null)

  fun onSave() {
    Log.i("Alorma", nameValue.orEmpty())
    Log.i("Alorma", phoneValue.orEmpty())
    Log.i("Alorma", emailValue.orEmpty())
  }
}