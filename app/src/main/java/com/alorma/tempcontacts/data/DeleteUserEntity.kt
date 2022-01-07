package com.alorma.tempcontacts.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
  tableName = "delete_user",
  indices = [
    Index(value = ["androidId"], unique = true),
  ]
)
data class DeleteUserEntity(
  @ColumnInfo(name = "androidId") val androidId: Long,
  @ColumnInfo(name = "androidLookupKey") val androidLookupKey: String? = null
) {
  @PrimaryKey(autoGenerate = true) var id: Long = 0L
}