package com.alorma.tempcontacts.data

import android.accounts.Account
import contacts.async.commitWithContext
import contacts.async.findWithContext
import contacts.core.Contacts
import contacts.core.ContactsFields
import contacts.core.DataField
import contacts.core.Fields
import contacts.core.and
import contacts.core.asc
import contacts.core.entities.Contact
import contacts.core.entities.EmailEntity
import contacts.core.entities.EventDate
import contacts.core.entities.EventEntity
import contacts.core.entities.NewRawContact
import contacts.core.entities.PhoneEntity
import contacts.core.entities.toWhereString
import contacts.core.equalTo
import contacts.core.greaterThan
import contacts.core.isNotNullOrEmpty
import contacts.core.lessThan
import contacts.core.util.addEmail
import contacts.core.util.addEvent
import contacts.core.util.addPhone
import contacts.core.util.setName
import contacts.core.whereAnd
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Date

class ContactsDatasource(
  private val contacts: Contacts
) {

  suspend fun getSyncableAccounts(): List<Account> = withContext(Dispatchers.IO) {
    contacts.accounts(isProfile = true).query().accountsWithType("com.google")
  }

  suspend fun loadAllContacts(filters: List<DataField>): List<Contact> {
    return contacts
      .query()
      /*
      .where(
        (Fields.Event.Date greaterThan Date().toWhereString())
          and (Fields.Event.Type equalTo EventEntity.Type.CUSTOM)
          and (filters whereAnd { it.isNotNullOrEmpty() })
      )
       */
      .include(
        Fields.Contact.Id,
        Fields.Contact.DisplayNamePrimary,
        Fields.Email.Address,
        Fields.Phone.Number,
        Fields.Address.FormattedAddress,
        Fields.Event.Date,
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
      addPhone {
        number = phone
        type = PhoneEntity.Type.OTHER
      }
      addEmail {
        address = email
        type = EmailEntity.Type.OTHER
      }
      addEvent {
        date = EventDate.from(year = 2021, month = 11, dayOfMonth = 24)
        type = EventEntity.Type.CUSTOM
        label = "Temp delete"
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