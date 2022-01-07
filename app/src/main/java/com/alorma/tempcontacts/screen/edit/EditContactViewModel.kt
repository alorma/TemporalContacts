package com.alorma.tempcontacts.screen.edit

import androidx.lifecycle.ViewModel
import com.alorma.tempcontacts.data.ContactsDatasource

class EditContactViewModel(
  private val contactsDatasource: ContactsDatasource,
  private val contactId: Long,
) : ViewModel() {

}