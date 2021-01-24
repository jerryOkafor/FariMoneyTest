package com.example.testapp.data.respository

import com.jerryhanks.farimoneytest.data.models.Resource
import com.jerryhanks.farimoneytest.data.models.User
import com.jerryhanks.farimoneytest.data.models.UserMinimal
import kotlinx.coroutines.flow.Flow

/**
 * Author: Jerry
 * Project: FairmoneyTest
 */
interface DummyApiDataSource {

    /**
     * List dummy users
     *
     * @param limit: Limit of fetch  = 100 by default
     * */
    fun users(forceReload: Boolean): Flow<Resource<List<UserMinimal>>>

    /**
     * Returns user details for a given user
     *
     * @param userId: Id of the user
     * */
    fun userDetails(userId: String): Flow<Resource<User>>
}