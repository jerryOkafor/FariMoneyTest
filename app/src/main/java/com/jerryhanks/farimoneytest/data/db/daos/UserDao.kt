package com.jerryhanks.farimoneytest.data.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.jerryhanks.farimoneytest.data.db.entities.UserEntity
import com.jerryhanks.farimoneytest.data.models.UserMinimal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

/**
 * Author: Jerry
 * Project: FairmoneyTest
 */
@Dao
abstract class UserDao : BaseDao<UserEntity> {

    @Transaction
    open suspend fun updateAllUsers(users: List<UserEntity>) {
        deleteAllUsers()
        insertAll(users = users)
    }

    @Insert
    abstract suspend fun insertAll(users: List<UserEntity>)

    @Query("DELETE FROM users")
    abstract suspend fun deleteAllUsers()

    @Query("SELECT id, title, lastName, email, firstName ,picture FROM users ORDER BY firstName ASC")
    abstract fun getUsers(): Flow<List<UserMinimal>>

    @Query("SELECT * FROM users WHERE id=:userId")
    abstract fun getUser(userId: String): Flow<UserEntity>

    /**
     * Get new data only when we have specific change
     * */
    fun getUserUntilChanged(userId: String) = getUser(userId).distinctUntilChanged()
}
