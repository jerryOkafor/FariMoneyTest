package com.example.testapp.data.respository

import com.jerryhanks.farimoneytest.data.api.DummyApiService
import com.jerryhanks.farimoneytest.data.db.AppDatabase
import com.jerryhanks.farimoneytest.data.models.NetworkBoundResource
import com.jerryhanks.farimoneytest.data.models.Resource
import com.jerryhanks.farimoneytest.data.models.User
import com.jerryhanks.farimoneytest.data.models.UserMinimal
import kotlinx.coroutines.flow.*
import javax.inject.Inject


/**
 * Author: Jerry
 * Project: FairmoneyTest
 */
class DummyApiRepository
@Inject constructor(
    private val apiService: DummyApiService,
    private val db: AppDatabase

) : DummyApiDataSource {
    override fun users(forceReload: Boolean): Flow<Resource<List<UserMinimal>>> {
        return object : NetworkBoundResource<List<UserMinimal>>() {

            override fun query(): Flow<List<UserMinimal>> = db.userDao().getUsers()

            override suspend fun fetch(): List<UserMinimal> {
                db.userDao().deleteAllUsers()
                return apiService.users().data
            }

            override suspend fun saveFetchResult(data: List<UserMinimal>) =
                db.userDao().updateAllUsers(data.map { it.toUserEntity() })

            override suspend fun shouldFetch(data: List<UserMinimal>) =
                data.isNullOrEmpty() || forceReload

        }.asFlow()

    }


    override fun userDetails(userId: String): Flow<Resource<User>> {
        return object : NetworkBoundResource<User>() {
            override fun query(): Flow<User> =
                db.userDao().getUser(userId).filterNotNull().map { it.toUser() }

            override suspend fun fetch(): User = apiService.userDetails(userId)

            override suspend fun saveFetchResult(data: User) =
                db.userDao().insert(data.toUserEntity())

            override suspend fun shouldFetch(data: User) = data.isDetailFetched().not()

        }.asFlow()

    }
}