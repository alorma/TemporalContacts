package com.alorma.tempcontacts.screen.edit

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alorma.tempcontacts.data.ContactsDatasource
import kotlinx.coroutines.launch

class EditContactViewModel(
  private val contactsDatasource: ContactsDatasource,
  private val contactId: Long,
) : ViewModel() {

  init {
    viewModelScope.launch {
      val contact = contactsDatasource.loadContact(contactId)
      Log.i("Alorma", contact.toString())
    }
  }

}