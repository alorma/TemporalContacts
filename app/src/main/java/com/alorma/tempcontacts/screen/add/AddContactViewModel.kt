package com.alorma.tempcontacts.screen.add

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alorma.tempcontacts.data.ContactsDatasource
import kotlinx.coroutines.launch

class AddContactViewModel(
  private val contactsDatasource: ContactsDatasource
) : ViewModel() {

  var nameValue by mutableStateOf<String?>("tempcontacts")
  var phoneValue by mutableStateOf<String?>("637098531")
  var emailValue by mutableStateOf<String?>("tempo@contacts.com")

  fun onSave() {
    checkNotNull(nameValue)

    viewModelScope.launch {
      val result = contactsDatasource.create(nameValue!!, phoneValue, emailValue)

      Log.i("Alorma", "Created ID: $result")
    }
  }
}