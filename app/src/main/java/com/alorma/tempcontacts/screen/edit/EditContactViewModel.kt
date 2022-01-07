package com.alorma.tempcontacts.screen.edit

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alorma.tempcontacts.data.ContactsDatasource
import com.alorma.tempcontacts.data.DeleteUsersDao
import kotlinx.coroutines.launch

class EditContactViewModel(
  private val contactsDatasource: ContactsDatasource,
  private val deleteUsersDao: DeleteUsersDao,
  private val contactId: Long,
) : ViewModel() {

  init {
    viewModelScope.launch {
      val deleteUser = deleteUsersDao.getContact(contactId)
      val contact = contactsDatasource.loadContact(contactId)
      Log.i("Alorma", "Contact exists: ${deleteUser != null}")
      Log.i("Alorma", contact.toString())
    }
  }

}