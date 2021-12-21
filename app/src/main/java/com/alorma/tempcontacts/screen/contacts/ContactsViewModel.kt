package com.alorma.tempcontacts.screen.contacts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alorma.tempcontacts.data.ContactsDatasource
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

  private val _filtersList: MutableStateFlow<List<Triple<String, DataField, Boolean>>> = MutableStateFlow(emptyList())
  val filtersList: StateFlow<List<Triple<String, DataField, Boolean>>>
    get() = _filtersList

  init {
    viewModelScope.launch {
      _filtersList.value = listOf(
        Triple("Address", Fields.Address.FormattedAddress, false),
        Triple("Email", Fields.Email.Address, false),
        Triple("Phone", Fields.Phone.Number, false),
      )
    }
    loadContacts()
  }

  private fun loadContacts() {
    viewModelScope.launch {
      val selectedFilters = _filtersList.value.filter { it.third }.map { filter -> filter.second }
      _contactsList.value = contactsDatasource.loadAllContacts(selectedFilters)
    }
  }

  fun filterChange(updatedFilter: DataField) {
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
