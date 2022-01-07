package com.alorma.tempcontacts.screen.edit

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alorma.tempcontacts.data.ContactsDatasource
import com.alorma.tempcontacts.data.DeleteUsersDao
import com.alorma.tempcontacts.screen.base.Maverick
import contacts.core.entities.Contact
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Exception
import java.time.LocalDateTime

class EditContactViewModel(
  private val contactsDatasource: ContactsDatasource,
  private val deleteUsersDao: DeleteUsersDao,
  private val contactId: Long,
) : ViewModel() {
  private val _contactInfo: MutableStateFlow<Maverick<EditContact>> = MutableStateFlow(Maverick.Uninitialized)

  val contactInfo: StateFlow<Maverick<EditContact>>
    get() = _contactInfo
  init {
    viewModelScope.launch {
      _contactInfo.value = Maverick.Loading()

      try {
        val deleteUser = deleteUsersDao.getContact(contactId)
        val contact = contactsDatasource.loadContact(contactId)

        val editContact = EditContact(
          contact = contact,
          scheduled = deleteUser != null
        )
        _contactInfo.value = Maverick.Success(editContact)
      } catch (e: Exception) {
        _contactInfo.value = Maverick.Fail(e)
      }
    }
  }

  fun setSelectedDate(selectedDate: LocalDateTime) {
    _contactInfo.update { maverick ->
      if (maverick is Maverick.Success) {
        maverick.copy(
          maverick.value.copy(
            scheduled = true,
            scheduleDate = selectedDate
          )
        )
      } else {
        maverick
      }
    }
  }

}