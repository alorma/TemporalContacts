package com.alorma.tempcontacts.data

import android.content.Context
import contacts.core.Contacts
import contacts.core.ContactsFields
import contacts.core.Fields
import contacts.core.asc
import contacts.core.entities.Contact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ContactsDatasource(
  private val context: Context
) {
  suspend fun loadAllContacts(): List<Contact> = withContext(Dispatchers.IO) {
    Contacts(context)
      .query()
      .include(
        Fields.Contact.Id,
        Fields.Contact.DisplayNamePrimary,
        Fields.Email.Address,
      )
      .orderBy(ContactsFields.DisplayNamePrimary.asc())
      .find()
  }
}