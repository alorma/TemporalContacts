package com.alorma.tempcontacts

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.alorma.tempcontacts.data.DataModule
import com.alorma.tempcontacts.screen.add.AddContactModule
import com.alorma.tempcontacts.screen.contacts.ContactsModule
import com.alorma.tempcontacts.screen.temporal.TemporalModule
import com.alorma.tempcontacts.screen.temporal.TemporalScreen
import com.alorma.tempcontacts.ui.theme.TempContactsTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionsRequired
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import contacts.core.Contacts
import dev.burnoo.cokoin.Koin
import org.koin.android.ext.koin.androidContext

class MainActivity : ComponentActivity() {

  @OptIn(ExperimentalPermissionsApi::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      AppWithPermissions {
        Koin(
          appDeclaration = {
            //androidLogger()
            androidContext(this@MainActivity.applicationContext)
            modules(
              DataModule(Contacts(this@MainActivity)),
              ContactsModule(),
              TemporalModule(),
              AddContactModule(),
            )
          },
          content = { App() }
        )
      }

    }
  }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AppWithPermissions(
  content: @Composable () -> Unit
) {
  val permissionsState = rememberMultiplePermissionsState(
    listOf(
      Manifest.permission.WRITE_CONTACTS,
      Manifest.permission.READ_CONTACTS,
      Manifest.permission.GET_ACCOUNTS,
    )
  )

  PermissionsRequired(
    multiplePermissionsState = permissionsState,
    permissionsNotGrantedContent = {
      LaunchedEffect(permissionsState) {
        permissionsState.launchMultiplePermissionRequest()
      }
    },
    permissionsNotAvailableContent = { },
    content = content
  )
}

@Composable
fun App() {
  TempContactsTheme {
    Scaffold {
      TemporalScreen()
    }
  }
}
