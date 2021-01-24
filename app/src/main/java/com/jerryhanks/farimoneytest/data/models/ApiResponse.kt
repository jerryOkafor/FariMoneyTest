package com.jerryhanks.farimoneytest.data.models

/**
 * Author: Jerry
 * Project: FairmoneyTest
 */
data class ApiResponse<T>(
    val data: T,
    val total: Int,
    val page: Int,
    val limit: Int,
    val offset: Int
)