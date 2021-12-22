package com.alorma.tempcontacts.screen.temporal

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.dp
import com.alorma.tempcontacts.screen.add.AddContactSheet
import com.alorma.tempcontacts.screen.contacts.ContactsScreen
import dev.burnoo.cokoin.viewmodel.getViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TemporalScreen(
  temporalContactsViewModel: TemporalContactsViewModel = getViewModel(),
) {

  val coroutineScope = rememberCoroutineScope()
  val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Expanded)

  ModalBottomSheetLayout(
    sheetState = sheetState,
    sheetContent = {
      AddContactSheet(onAccountCreated = {
        coroutineScope.launch { sheetState.hide() }
      })
    },
    sheetShape = RoundedCornerShape(
      topStart = 16.dp,
      topEnd = 16.dp,
    ),
  ) {
    Scaffold(
      floatingActionButton = {
        FloatingActionButton(onClick = {
          coroutineScope.launch { sheetState.show() }
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
}

@Composable
fun ContactsList(temporalContacts: List<String>) {
  LazyColumn {
    items(temporalContacts) { contact ->
      Text(text = contact)
    }
  }
}