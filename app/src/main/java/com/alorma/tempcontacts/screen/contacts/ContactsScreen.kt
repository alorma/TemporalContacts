package com.alorma.tempcontacts.screen.contacts

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import contacts.core.AbstractDataFieldSet
import contacts.core.DataField
import contacts.core.entities.Contact
import contacts.core.util.emailList
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ContactsScreen(
  viewModel: ContactsViewModel = getViewModel(),
) {
  val filters by viewModel.filtersList.collectAsState()
  val contacts by viewModel.contactsList.collectAsState()

  LazyColumn {
    stickyHeader {
      ContactFilters(
        filters = filters,
        onFilterChange = { filter -> viewModel.filterChange(filter) }
      )
    }
    items(contacts, key = { contact -> contact.id }) { contact ->
      ContactView(contact)
    }
  }
}

@Composable
fun ContactFilters(
  filters: List<Triple<String, AbstractDataFieldSet<DataField>, Boolean>>,
  onFilterChange: (AbstractDataFieldSet<DataField>) -> Unit,
) {
  LazyRow(
    modifier = Modifier.fillMaxWidth()
  ) {
    items(filters) { filter ->
      ContactFilter(
        text = filter.first,
        enabled = filter.third,
        onClick = { onFilterChange(filter.second) },
      )
    }
  }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ContactFilter(
  text: String,
  enabled: Boolean,
  onClick: () -> Unit,
) {
  val stateColor = if (enabled) {
    MaterialTheme.colors.primary
  } else {
    MaterialTheme.colors.onSurface
  }
  Surface(
    modifier = Modifier
      .padding(8.dp)
      .wrapContentSize(),
    border = BorderStroke(
      width = 1.dp,
      color = stateColor.copy(alpha = 0.24f)
    ),
    shape = CircleShape,
    onClick = { onClick() },
  ) {
    Row(
      modifier = Modifier
        .padding(vertical = 8.dp, horizontal = 12.dp)
        .wrapContentSize(),
      horizontalArrangement = Arrangement.Center,
      verticalAlignment = Alignment.CenterVertically,
    ) {

      Text(
        text = text,
        color = stateColor.copy(alpha = 0.60f)
      )
      Spacer(modifier = Modifier.width(16.dp))
      Icon(
        imageVector = Icons.Default.CheckCircle,
        contentDescription = null,
        tint = stateColor.copy(alpha = 0.36f)
      )
    }
  }
}

@Composable
fun ContactView(contact: Contact) {
  Surface(
    modifier = Modifier.fillMaxWidth(),
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      Text(text = contact.id.toString())

      contact.displayNamePrimary?.let { primaryName ->
        Text(text = primaryName)
      }
      if (!contact.emailList().isNullOrEmpty()) {
        val primaryEmail = contact.emailList().firstOrNull { email -> email.isPrimary }?.address
        if (primaryEmail != null) {
          Text(text = primaryEmail)
        }
      }
    }
  }
}