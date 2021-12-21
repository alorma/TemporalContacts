package com.alorma.tempcontacts.screen.temporal

import androidx.lifecycle.ViewModel
import com.alorma.tempcontacts.data.ContactsDatasource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TemporalContactsViewModel(
  private val contactsDatasource: ContactsDatasource,
) : ViewModel() {

  // A contact: 2545

  private val _temporalContacts: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())

  val temporalContacts: StateFlow<List<String>>
    get() = _temporalContacts

}