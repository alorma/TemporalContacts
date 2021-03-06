package com.alorma.tempcontacts.screen.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alorma.tempcontacts.data.ContactsDatasource
import com.alorma.tempcontacts.data.DeleteUserEntity
import com.alorma.tempcontacts.data.DeleteUsersDao
import com.alorma.tempcontacts.screen.base.Maverick
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class EditContactViewModel(
  private val contactsDatasource: ContactsDatasource,
  private val deleteUsersDao: DeleteUsersDao,
  private val contactId: Long,
) : ViewModel() {

  private val _accountCreated: MutableStateFlow<Boolean> = MutableStateFlow(false)
  val accountCreated: StateFlow<Boolean>
    get() = _accountCreated

  private val _contactInfo: MutableStateFlow<Maverick<EditContact>> = MutableStateFlow(Maverick.Uninitialized)
  val contactInfo: StateFlow<Maverick<EditContact>>
    get() = _contactInfo

  private val _scheduleDate: MutableStateFlow<LocalDateTime?> = MutableStateFlow(null)
  val scheduleDate: StateFlow<LocalDateTime?>
    get() = _scheduleDate

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
        if (deleteUser != null) {
          _scheduleDate.value = deleteUser.deleteDate
        }
        _contactInfo.value = Maverick.Success(editContact)
      } catch (e: Exception) {
        _contactInfo.value = Maverick.Fail(e)
      }
    }
  }

  fun setSelectedDate(selectedDate: LocalDateTime) {
    _scheduleDate.value = selectedDate
  }

  fun save() {
    viewModelScope.launch {
      val date = _scheduleDate.value
      if (date != null) {
        val entity = DeleteUserEntity(
          androidId = contactId,
          deleteDate = date
        )
        deleteUsersDao.add(entity)
        _accountCreated.value = true
      }
    }
  }
}