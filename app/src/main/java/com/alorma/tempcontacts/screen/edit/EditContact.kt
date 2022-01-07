package com.alorma.tempcontacts.screen.edit

import contacts.core.entities.Contact
import java.time.LocalDateTime

data class EditContact(
  val contact: Contact,
  val scheduled: Boolean,
  val scheduleDate: LocalDateTime? = null,
)
