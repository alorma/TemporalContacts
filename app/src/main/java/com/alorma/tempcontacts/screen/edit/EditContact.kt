package com.alorma.tempcontacts.screen.edit

import contacts.core.entities.Contact

data class EditContact(
  val contact: Contact,
  val scheduled: Boolean,
)
