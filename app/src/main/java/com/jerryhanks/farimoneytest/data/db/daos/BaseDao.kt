package com.jerryhanks.farimoneytest.data.db.daos

import androidx.room.Insert
import androidx.room.OnConflictStrategy

/**
 * Author: Jerry
 * Project: FairmoneyTest
 */

interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg obj: T)
}