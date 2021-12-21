package com.alorma.tempcontacts.data

import android.content.Context
import contacts.core.Contacts
import contacts.core.ContactsFields
import contacts.core.DataField
import contacts.core.Fields
import contacts.core.asc
import contacts.core.entities.Contact
import contacts.core.isNotNullOrEmpty
import contacts.core.whereAnd
import contacts.core.whereOr
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ContactsDatasource(
  private val context: Context
) {
  suspend fun loadAllContacts(filters: List<DataField>): List<Contact> = withContext(Dispatchers.IO) {
    Contacts(context)
      .query()
      .where(filters whereAnd { it.isNotNullOrEmpty() })
      .include(
        Fields.Contact.Id,
        Fields.Contact.DisplayNamePrimary,
        Fields.Email.Address,
        Fields.Phone.Number,
        Fields.Address.FormattedAddress,
      )
      .orderBy(ContactsFields.DisplayNamePrimary.asc())
      .find()
  }
}