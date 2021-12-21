package com.alorma.tempcontacts

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.alorma.tempcontacts.screen.temporal.TemporalScreen
import com.alorma.tempcontacts.ui.theme.TempContactsTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionsRequired
import com.google.accompanist.permissions.rememberMultiplePermissionsState

class MainActivity : ComponentActivity() {

  @OptIn(ExperimentalPermissionsApi::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      TempContactsTheme {
        Scaffold {
          var doNotShowRationale by remember { mutableStateOf(false) }

          val contactsPermissionState = rememberMultiplePermissionsState(
            listOf(
              Manifest.permission.WRITE_CONTACTS,
              Manifest.permission.READ_CONTACTS,
            )
          )
          PermissionsRequired(
            multiplePermissionsState = contactsPermissionState,
            permissionsNotGrantedContent = {
              if (doNotShowRationale) {
                Text(text = "Not granted")
              } else {
                Button(onClick = { contactsPermissionState.launchMultiplePermissionRequest() }) {
                  Text(text = "Read contacts")
                }
              }
            },
            permissionsNotAvailableContent = {
              Text(text = "Denied")
            },
          ) {
            TemporalScreen()
          }
        }
      }
    }
  }
}
