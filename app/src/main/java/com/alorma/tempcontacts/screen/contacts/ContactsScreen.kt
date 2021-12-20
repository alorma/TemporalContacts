package com.alorma.tempcontacts.screen.contacts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import contacts.core.entities.Contact
import contacts.core.util.emailList
import org.koin.androidx.compose.getViewModel

@Composable
fun ContactsScreen(
  viewModel: ContactsViewModel = getViewModel(),
) {

  val contacts by viewModel.contactsList.collectAsState()
  LazyColumn {
    items(contacts, key = { contact -> contact.id }) { contact ->
      ContactView(contact)
    }
  }
}

@Composable
fun ContactView(contact: Contact) {
  Surface(
    modifier = Modifier.fillMaxWidth(),
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      Text(text = contact.id.toString())

      contact.displayNamePrimary?.let { primaryName ->
        Text(text = primaryName)
      }
      if (!contact.emailList().isNullOrEmpty()) {
        val primaryEmail = contact.emailList().firstOrNull { email -> email.isPrimary }?.address
        if (primaryEmail != null) {
          Text(text = primaryEmail)
        }
      }
    }
  }
}