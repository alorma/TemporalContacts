package com.alorma.tempcontacts.screen

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument

object Destinations {
  val CONTACTS: String = "contacts"
  val CREATE: String = "create"

  val EDIT_PARAM = "contactId"

  val EDIT_ARGUMENTS = listOf(
    navArgument(EDIT_PARAM) {
      nullable = true
      type = NavType.StringType
    }
  )

  val EDIT: String = "edit?$EDIT_PARAM={$EDIT_PARAM}"
  fun EDIT(contactId: Long): String = EDIT.replace("{$EDIT_PARAM}", contactId.toString())
  fun editParam(backStackEntry: NavBackStackEntry): Long {
    val argument = backStackEntry.arguments?.getString(EDIT_PARAM)
    return argument?.toLong() ?: error("No contact ID provided")
  }

  val SELECT_DATE: String = "select_date"
  val SELECT_DATE_RETURN_PARAM: String = "select_date_return_param"
}
