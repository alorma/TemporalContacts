package com.alorma.tempcontacts.screen.contacts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alorma.tempcontacts.data.ContactsDatasource
import contacts.core.entities.Contact
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ContactsViewModel(
  private val contactsDatasource: ContactsDatasource,
) : ViewModel() {

  private val _contactsList: MutableStateFlow<List<Contact>> = MutableStateFlow(emptyList())
  val contactsList: StateFlow<List<Contact>>
    get() = _contactsList

  init {
    viewModelScope.launch {
      _contactsList.value = contactsDatasource.loadAllContacts()
    }
  }
}