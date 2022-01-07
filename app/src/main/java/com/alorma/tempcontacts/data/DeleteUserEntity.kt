package com.alorma.tempcontacts.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(
  tableName = "delete_user",
  indices = [
    Index(value = ["androidId"], unique = true),
  ],
)
data class DeleteUserEntity(
  @ColumnInfo(name = "androidId") val androidId: Long,
  @ColumnInfo(name = "androidLookupKey") val androidLookupKey: String? = null,
  @ColumnInfo(name = "delete_date") val deleteDate: LocalDateTime,
) {
  @PrimaryKey(autoGenerate = true) var id: Long = 0L
}