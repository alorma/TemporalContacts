package com.alorma.tempcontacts.screen.add

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.burnoo.cokoin.viewmodel.getViewModel

@Composable
fun AddContactSheet(
  navController: NavController,
  addContactViewModel: AddContactViewModel = getViewModel(),
) {

  val accountCreated by addContactViewModel.accountCreated.collectAsState()
  if (accountCreated) {
    navController.navigateUp()
  }

  Scaffold {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {

      val account by addContactViewModel.accounts.collectAsState()

      AnimatedVisibility(visible = account != null) {
        TextField(
          label = { Text(text = "Account") },
          modifier = Modifier.fillMaxWidth(),
          value = account?.name.orEmpty(),
          enabled = false,
          onValueChange = { },
        )
      }

      TextField(
        label = { Text(text = "Name") },
        modifier = Modifier.fillMaxWidth(),
        value = addContactViewModel.nameValue.orEmpty(),
        onValueChange = { addContactViewModel.nameValue = it },
      )
      TextField(
        label = { Text(text = "Phone") },
        modifier = Modifier.fillMaxWidth(),
        value = addContactViewModel.phoneValue.orEmpty(),
        onValueChange = { addContactViewModel.phoneValue = it },
      )
      TextField(
        label = { Text(text = "Email") },
        modifier = Modifier.fillMaxWidth(),
        value = addContactViewModel.emailValue.orEmpty(),
        onValueChange = { addContactViewModel.emailValue = it },
      )

      Spacer(modifier = Modifier.weight(1f))

      TextButton(
        modifier = Modifier.fillMaxWidth(),
        onClick = { addContactViewModel.onSave() }
      ) {
        Text(text = "SAVE")
      }
    }
  }
}