package com.alorma.tempcontacts.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DeleteUsersDao {

  @Query("SELECT * FROM delete_user WHERE androidId = :androidId")
  suspend fun getContact(androidId: Long): DeleteUserEntity?

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun add(deleteUserEntity: DeleteUserEntity)
}