package com.alorma.tempcontacts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import com.alorma.tempcontacts.data.DataModule
import com.alorma.tempcontacts.screen.add.AddContactModule
import com.alorma.tempcontacts.screen.contacts.ContactsModule
import com.alorma.tempcontacts.screen.temporal.TemporalModule
import com.alorma.tempcontacts.screen.temporal.TemporalScreen
import com.alorma.tempcontacts.ui.theme.TempContactsTheme
import dev.burnoo.cokoin.Koin
import org.koin.android.ext.koin.androidContext

class MainActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      Koin(
        appDeclaration = {
          //androidLogger()
          androidContext(this@MainActivity.applicationContext)
          modules(
            DataModule(this@MainActivity),
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

@Composable
fun App() {
  TempContactsTheme {
    Scaffold {
      TemporalScreen()
    }
  }
}
