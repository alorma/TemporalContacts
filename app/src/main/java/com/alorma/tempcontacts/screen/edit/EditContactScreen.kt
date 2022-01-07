package com.alorma.tempcontacts.screen.edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.alorma.tempcontacts.screen.base.Maverick

@Composable
fun EditContactScreen(
  navController: NavController,
  editContactViewModel: EditContactViewModel,
) {

  Scaffold(
    topBar = {
      TopAppBar(
        navigationIcon = {
          IconButton(onClick = { navController.navigateUp() }) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
          }
        },
        title = { Text("Edit contact") }
      )
    }
  ) {

    val contactInfo by editContactViewModel.contactInfo.collectAsState()

    when (contactInfo) {
      is Maverick.Loading,
      Maverick.Uninitialized -> FullLoadingState()
      is Maverick.Fail -> ErrorState()
      is Maverick.Success -> {
        val success = contactInfo as Maverick.Success<EditContact>
        SuccessState(success.value)
      }
    }
  }
}

@Composable
fun SuccessState(editContact: EditContact) {
  Column(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.spacedBy(8.dp)
  ) {
    if (editContact.scheduled) {
      Text(text = "Planned")
    } else {
      Text(text = "Not planned")
    }
    Text(text = editContact.contact.toString())
  }
}

@Composable
fun ErrorState() {
  Box(
    modifier = Modifier.fillMaxSize(),
    contentAlignment = Alignment.Center,
  ) {
    Text(text = "Error")
  }
}

@Composable
fun FullLoadingState() {
  Box(
    modifier = Modifier.fillMaxSize(),
    contentAlignment = Alignment.Center,
  ) {
    CircularProgressIndicator()
  }
}
