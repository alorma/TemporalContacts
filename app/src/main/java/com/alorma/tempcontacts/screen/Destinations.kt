package com.alorma.tempcontacts.screen

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument

object Destinations {
  val CONTACTS: String = "contacts"
  val CREATE: String = "create?contactId={contactId}"


  val CREATE_ARGUMENTS = listOf(
    navArgument("contactId") {
      nullable = true
      type = NavType.StringType
    }
  )

  fun EDIT(contactId: Long): String = CREATE.replace("{contactId}", contactId.toString())
  fun editParam(backStackEntry: NavBackStackEntry): Long? = backStackEntry.arguments?.getString("contactId")?.toLong()
}