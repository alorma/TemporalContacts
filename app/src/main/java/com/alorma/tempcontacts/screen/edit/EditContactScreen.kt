package com.alorma.tempcontacts.screen.edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
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
import com.alorma.tempcontacts.screen.Destinations
import com.alorma.tempcontacts.screen.base.Maverick
import com.alorma.tempcontacts.screen.base.getArgument
import java.time.LocalDateTime

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

    val selectedDateParam: LocalDateTime? = navController.getArgument(Destinations.SELECT_DATE_RETURN_PARAM)

    if (selectedDateParam != null) {
      editContactViewModel.setSelectedDate(selectedDateParam)
    }

    val contactInfo by editContactViewModel.contactInfo.collectAsState()

    when (contactInfo) {
      is Maverick.Loading,
      Maverick.Uninitialized -> FullLoadingState()
      is Maverick.Fail -> ErrorState()
      is Maverick.Success -> {
        val success = contactInfo as Maverick.Success<EditContact>
        SuccessState(
          editContact = success.value,
          onSchedule = { navController.navigate(Destinations.SELECT_DATE) }
        )
      }
    }
  }
}

@Composable
fun SuccessState(
  editContact: EditContact,
  onSchedule: () -> Unit,
) {
  Column(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.spacedBy(8.dp)
  ) {
    if (editContact.scheduled && editContact.scheduleDate != null) {
      Text(text = "Planned: ${editContact.scheduleDate.toString()}")
    } else {
      Text(text = "Not planned")
    }
    Text(text = editContact.contact.toString())

    Button(onClick = { onSchedule() }) {
      Text(text = "Schedule")
    }
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
