package com.alorma.tempcontacts.screen.temporal

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import com.alorma.tempcontacts.screen.add.AddContactSheet
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TemporalScreen(
  temporalContactsViewModel: TemporalContactsViewModel = getViewModel(),
) {

  val coroutineScope = rememberCoroutineScope()
  val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

  ModalBottomSheetLayout(
    sheetState = sheetState,
    sheetContent = { AddContactSheet() },
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
      val temporalContacts by temporalContactsViewModel.temporalContacts.collectAsState()
      LazyColumn {
        items(temporalContacts) { contact ->
          Text(text = contact)
        }
      }
    }
  }
}