package com.alorma.tempcontacts.screen.add

import android.accounts.Account
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alorma.tempcontacts.data.ContactsDatasource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AddContactViewModel(
  private val contactsDatasource: ContactsDatasource
) : ViewModel() {

  var nameValue by mutableStateOf<String?>("tempcontacts")
  var phoneValue by mutableStateOf<String?>("646433541")
  var emailValue by mutableStateOf<String?>("tempo@contacts.com")

  private val _accounts: MutableStateFlow<Account?> = MutableStateFlow(null)
  val accounts: StateFlow<Account?>
    get() = _accounts

  init {
    viewModelScope.launch {
      _accounts.value = contactsDatasource.getSyncableAccounts().first()
    }
  }

  fun onSave() {
    checkNotNull(nameValue)

    viewModelScope.launch {
      contactsDatasource.create(
        name = nameValue!!,
        phone = phoneValue,
        email = emailValue,
        account = _accounts.value,
      )
    }
  }
}