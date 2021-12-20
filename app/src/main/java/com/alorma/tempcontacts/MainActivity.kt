package com.alorma.tempcontacts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.alorma.tempcontacts.ui.theme.TempContactsTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.rememberPermissionState
import contacts.core.Contacts
import contacts.core.ContactsFields
import contacts.core.Fields
import contacts.core.asc
import contacts.core.entities.Contact
import contacts.core.util.emailList

class MainActivity : ComponentActivity() {

  @OptIn(ExperimentalPermissionsApi::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      TempContactsTheme {
        Scaffold {
          var doNotShowRationale by remember { mutableStateOf(false) }

          val contactsPermissionState = rememberPermissionState(android.Manifest.permission.READ_CONTACTS)
          PermissionRequired(
            permissionState = contactsPermissionState,
            permissionNotGrantedContent = {
              if (doNotShowRationale) {
                Text(text = "Not granted")
              } else {
                Button(onClick = { contactsPermissionState.launchPermissionRequest() }) {
                  Text(text = "Read contacts")
                }
              }
            },
            permissionNotAvailableContent = { Text(text = "Denied") },
          ) {
            ContactsList()
          }
        }
      }
    }
  }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ContactsList() {
  val context = LocalContext.current

  val contacts = Contacts(context)
    .query()
    .include(
      Fields.Contact.Id,
      Fields.Contact.DisplayNamePrimary,
      Fields.Email.Address,
    )
    .orderBy(ContactsFields.DisplayNamePrimary.asc())
    .find()
  LazyColumn {
    stickyHeader {
      Text(text = "Contacts: ${contacts.size}")
    }
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