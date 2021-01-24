package com.jerryhanks.farimoneytest.data.models

import kotlinx.coroutines.flow.*
import timber.log.Timber

/**
 * Author: Jerry
 * Project: FairmoneyTest
 */

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline onFetchFailed: (Throwable) -> Unit = { Unit },
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
) = flow<Resource<ResultType>> {
    emit(Resource.Loading(null))
    val data = query().first()

    val flow = if (shouldFetch(data)) {
        emit(Resource.Loading(data))

        try {
            saveFetchResult(fetch())
            query().map { Resource.Success(it) }
        } catch (throwable: Throwable) {
            onFetchFailed(throwable)
            query().map { Resource.Error(throwable.message!!, it) }
        }
    } else {
        query().map { Resource.Success(it) }
    }

    emitAll(flow)
}

abstract class NetworkBoundResource<T> {
    fun asFlow(): Flow<Resource<T>> = flow {
        val flow = query()
            .onStart { emit(Resource.Loading<T>(null)) }
            .flatMapConcat { data ->
                if (shouldFetch(data)) {
                    emit(Resource.Loading(data))
                    Timber.d("DDD 1")
                    try {
                        saveFetchResult(fetch())
                        Timber.d("DDD 2")
                        query().map { Resource.Success(it) }
                    } catch (throwable: Throwable) {
                        onFetchFailed(throwable)
                        Timber.d("DDD 3 $throwable")
                        query().map { Resource.Error(throwable.message!!, it) }
                    }
                } else {
                    Timber.d("DDD 4")
                    query().map { Resource.Success(it) }
                }
            }

        emitAll(flow)
    }

    abstract fun query(): Flow<T>
    abstract suspend fun fetch(): T
    abstract suspend fun saveFetchResult(data: T)
    open fun onFetchFailed(throwable: Throwable) = Unit
    open suspend fun shouldFetch(data: T) = true

}


sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Loading -> "Loading[status=$data]"
            is Error -> "Error[exception=$message]"
        }
    }
}