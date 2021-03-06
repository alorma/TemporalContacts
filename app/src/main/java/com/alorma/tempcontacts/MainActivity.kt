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
import com.alorma.tempcontacts.screen.add.AddContactScreen
import com.alorma.tempcontacts.screen.contacts.ContactsModule
import com.alorma.tempcontacts.screen.contacts.ContactsScreen
import com.alorma.tempcontacts.screen.date.SelectDateScreen
import com.alorma.tempcontacts.screen.edit.EditContactModule
import com.alorma.tempcontacts.screen.edit.EditContactScreen
import com.alorma.tempcontacts.screen.edit.EditContactViewModel
import com.alorma.tempcontacts.ui.theme.TempContactsTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionsRequired
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import contacts.core.Contacts
import dev.burnoo.cokoin.Koin
import dev.burnoo.cokoin.viewmodel.getViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.parameter.parametersOf

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
        DataModule(
          contacts = Contacts(
            context = activity,
            logger = contacts.core.log.AndroidLogger(
              tag = "Alorma-Contacts",
              redactMessages = !BuildConfig.DEBUG
            ))
        ),
        ContactsModule(),
        AddContactModule(),
        EditContactModule(),
      )
    },
    content = content,
  )
}

@Composable
fun AppWithNavigation() {
  val navController = rememberNavController()

  NavHost(navController, Destinations.CONTACTS) {
    composable(Destinations.CONTACTS) {
      ContactsScreen(navController)
    }
    composable(route = Destinations.CREATE) {
      AddContactScreen(navController = navController)
    }
    composable(
      route = Destinations.EDIT,
      arguments = Destinations.EDIT_ARGUMENTS,
    ) { backStackEntry ->
      val contactId = Destinations.editParam(backStackEntry = backStackEntry)

      val viewModel = getViewModel<EditContactViewModel>(
        parameters = { parametersOf(contactId) }
      )
      EditContactScreen(
        navController = navController,
        editContactViewModel = viewModel,
      )
    }
    composable(Destinations.SELECT_DATE) {
      SelectDateScreen(navController = navController)
    }
  }
}
