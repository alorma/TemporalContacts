package com.alorma.tempcontacts.data

import android.accounts.Account
import contacts.async.commitWithContext
import contacts.async.findWithContext
import contacts.core.Contacts
import contacts.core.ContactsFields
import contacts.core.DataField
import contacts.core.Fields
import contacts.core.asc
import contacts.core.entities.Contact
import contacts.core.entities.EmailEntity
import contacts.core.entities.NewRawContact
import contacts.core.entities.PhoneEntity
import contacts.core.isNotNullOrEmpty
import contacts.core.util.addEmail
import contacts.core.util.addPhone
import contacts.core.util.setName
import contacts.core.whereAnd
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ContactsDatasource(
  private val contacts: Contacts
) {

  suspend fun getSyncableAccounts(): List<Account> = withContext(Dispatchers.IO) {
    contacts.accounts(isProfile = true).query().accountsWithType("com.google")
  }

  suspend fun loadAllContacts(filters: List<DataField>): List<Contact> {
    return contacts
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
      .findWithContext()
  }

  suspend fun create(
    name: String,
    phone: String?,
    email: String?,
    account: Account? = null,
  ): Long? {
    val rawContact = NewRawContact().apply {
      setName { displayName = name }
      if (phone != null) {
        addPhone {
          number = phone
          type = PhoneEntity.Type.OTHER
        }
      }
      if (email != null) {
        addEmail {
          address = email
          type = EmailEntity.Type.OTHER
        }
      }
    }
    return contacts
      .insert()
      .forAccount(account)
      .rawContacts(rawContact)
      .commitWithContext()
      .rawContactId(rawContact)
  }
}