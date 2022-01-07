package com.alorma.tempcontacts.screen.edit

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import dev.burnoo.cokoin.viewmodel.getViewModel

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
  }
}