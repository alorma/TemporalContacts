package com.alorma.tempcontacts.screen.add

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import org.koin.androidx.compose.getViewModel

@Composable
fun AddContactSheet(
  addContactViewModel: AddContactViewModel = getViewModel(),
) {
  Text(text = "Hello sheet")
}