package com.jerryhanks.farimoneytest.data.api

import com.jerryhanks.farimoneytest.data.models.ApiResponse
import com.jerryhanks.farimoneytest.data.models.User
import com.jerryhanks.farimoneytest.data.models.UserMinimal
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Author: Jerry
 * Project: FairmoneyTest
 */
interface DummyApiService {

    /**
     * List dummy users
     *
     * @param limit: Limit of fetch  = 100 by default
     * */
    @GET("user")
    suspend fun users(@Query("limit") limit: Int = 100): ApiResponse<List<UserMinimal>>

    /**
     * Returns user details for a given user
     *
     * @param userId: Id of the user
     * */
    @GET("user/{userId}")
    suspend fun userDetails(@Path("userId") userId: String): User
}

