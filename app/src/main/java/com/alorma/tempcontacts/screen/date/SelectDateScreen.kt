package com.alorma.tempcontacts.screen.date

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.alorma.tempcontacts.screen.Destinations
import java.time.LocalDateTime

@Composable
fun SelectDateScreen(
  navController: NavController
) {
  Box(
    modifier = Modifier.fillMaxSize(),
    contentAlignment = Alignment.Center,
  ) {
    Button(onClick = {
      navController.previousBackStackEntry
        ?.savedStateHandle
        ?.set(Destinations.SELECT_DATE_RETURN_PARAM, LocalDateTime.now().plusDays(1))
      navController.popBackStack()
    }) {
      Text(text = "Schedule")
    }
  }
}