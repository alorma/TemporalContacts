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
  fun editParam(backStackEntry: NavBackStackEntry): Long? = backStackEntry.arguments?.getString(EDIT_PARAM)?.toLong()
}