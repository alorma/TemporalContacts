package com.alorma.tempcontacts.screen.edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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

  val accountCreated by editContactViewModel.accountCreated.collectAsState()
  if (accountCreated) {
    navController.navigateUp()
  }

  val selectedDateParam: LocalDateTime? = navController.getArgument(Destinations.SELECT_DATE_RETURN_PARAM)
  if (selectedDateParam != null) {
    editContactViewModel.setSelectedDate(selectedDateParam)
  }

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
    Column {
      val contactInfo by editContactViewModel.contactInfo.collectAsState()

      when (contactInfo) {
        is Maverick.Loading,
        Maverick.Uninitialized -> FullLoadingState()
        is Maverick.Fail -> ErrorState()
        is Maverick.Success -> {
          val success = contactInfo as Maverick.Success<EditContact>
          SuccessState(
            modifier = Modifier.fillMaxWidth(),
            editContact = success.value,
          )
          val scheduleDate by editContactViewModel.scheduleDate.collectAsState()
          if (scheduleDate != null) {
            Text(text = scheduleDate.toString())
            Spacer(modifier = Modifier.weight(1f))
            Button(onClick = { editContactViewModel.save() }) {
              Text(text = "Save")
            }
          } else {
            Spacer(modifier = Modifier.weight(1f))
            Button(onClick = { navController.navigate(Destinations.SELECT_DATE) }) {
              Text(text = "Schedule")
            }
          }
        }
      }
    }
  }
}

@Composable
fun SuccessState(
  modifier: Modifier = Modifier,
  editContact: EditContact,
) {
  Column(
    modifier = modifier,
    verticalArrangement = Arrangement.spacedBy(8.dp)
  ) {
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
