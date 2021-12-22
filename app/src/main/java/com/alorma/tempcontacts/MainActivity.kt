package com.alorma.tempcontacts

import android.Manifest
import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alorma.tempcontacts.data.DataModule
import com.alorma.tempcontacts.screen.Destinations
import com.alorma.tempcontacts.screen.add.AddContactModule
import com.alorma.tempcontacts.screen.add.AddContactSheet
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
      TempContactsTheme {
        AppWithPermissions {
          AppWithDependencies(this@MainActivity) {
            AppWithNavigation()
          }
        }
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
fun AppWithDependencies(
  activity: Activity,
  content: @Composable () -> Unit,
) {
  Koin(
    appDeclaration = {
      //androidLogger()
      androidContext(activity.applicationContext)
      modules(
        DataModule(Contacts(activity)),
        ContactsModule(),
        TemporalModule(),
        AddContactModule(),
      )
    },
    content = content,
  )
}

@Composable
fun AppWithNavigation() {
  val navController = rememberNavController()

  NavHost(navController, Destinations.TEMPORALS) {
    composable(Destinations.TEMPORALS) {
      TemporalScreen(navController)
    }
    composable(Destinations.CREATE) {
      AddContactSheet(navController)
    }
  }
}
