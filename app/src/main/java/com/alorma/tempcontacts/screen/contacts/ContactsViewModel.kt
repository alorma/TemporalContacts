package com.alorma.tempcontacts.screen.contacts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alorma.tempcontacts.data.ContactsDatasource
import contacts.core.AbstractDataFieldSet
import contacts.core.DataField
import contacts.core.Fields
import contacts.core.entities.Contact
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ContactsViewModel(
  private val contactsDatasource: ContactsDatasource,
) : ViewModel() {
  private val _contactsList: MutableStateFlow<List<Contact>> = MutableStateFlow(emptyList())

  val contactsList: StateFlow<List<Contact>>
    get() = _contactsList
  private val _filtersList: MutableStateFlow<List<Triple<String, AbstractDataFieldSet<DataField>, Boolean>>> = MutableStateFlow(emptyList())

  val filtersList: StateFlow<List<Triple<String, AbstractDataFieldSet<DataField>, Boolean>>>
    get() = _filtersList

  init {
    viewModelScope.launch {
      _filtersList.value = listOf(
        Triple("Email", Fields.Email, false),
        Triple("Phone", Fields.Phone, false),
      )
    }
    loadContacts()
  }

  private fun loadContacts() {
    viewModelScope.launch {
      _contactsList.value = contactsDatasource.loadAllContacts(
        _filtersList.value.map { filter -> filter.second }
      )
    }
  }

  fun filterChange(updatedFilter: AbstractDataFieldSet<DataField>) {
    _filtersList.update { current ->
      current.map { filter ->
        if (filter.second == updatedFilter) {
          filter.copy(third = !filter.third)
        } else {
          filter
        }
      }
    }
    loadContacts()
  }
}
