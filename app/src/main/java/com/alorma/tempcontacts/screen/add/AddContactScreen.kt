package com.alorma.tempcontacts.screen.add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.getViewModel

@Composable
fun AddContactSheet(
  addContactViewModel: AddContactViewModel = getViewModel(),
) {
  Surface(
    modifier = Modifier.wrapContentSize(),
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {

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

      Spacer(modifier = Modifier.height(16.dp))

      Button(onClick = { addContactViewModel.onSave() }) {
        Text(text = "SAVE")
      }
    }

  }
}