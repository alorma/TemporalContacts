package com.alorma.tempcontacts.screen.temporal

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.alorma.tempcontacts.screen.Destinations
import com.alorma.tempcontacts.screen.contacts.ContactsScreen
import dev.burnoo.cokoin.viewmodel.getViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TemporalScreen(
  navController: NavController,
  temporalContactsViewModel: TemporalContactsViewModel = getViewModel(),
) {
  Scaffold(
    floatingActionButton = {
      FloatingActionButton(onClick = {
        navController.navigate(Destinations.CREATE) {
          popUpTo(Destinations.TEMPORALS)
        }
      }) {
        Icon(imageVector = Icons.Default.Add, contentDescription = null)
      }
    },
  ) {
    ContactsScreen()
    /*
      val temporalContacts by temporalContactsViewModel.temporalContacts.collectAsState()
      ContactsList(temporalContacts)
      */
  }
}

@Composable
fun ContactsList(temporalContacts: List<String>) {
  LazyColumn {
    items(temporalContacts) { contact ->
      Text(text = contact)
    }
  }
}