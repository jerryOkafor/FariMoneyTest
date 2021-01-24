package com.jerryhanks.fairmoneytest

import androidx.lifecycle.*
import com.jerryhanks.farimoneytest.data.models.Resource
import com.jerryhanks.farimoneytest.data.models.User
import com.jerryhanks.farimoneytest.data.models.UserMinimal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Author: Jerry
 * Project: FairmoneyTest
 */
class OneTimeObserver<T>(private val handler: (T) -> Unit) : Observer<T>, LifecycleOwner {
    private val lifecycle = LifecycleRegistry(this)

    init {
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    override fun getLifecycle(): Lifecycle = lifecycle

    override fun onChanged(t: T) {
        handler(t)
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    }
}

fun <T> LiveData<T>.observeOnce(onChangeHandler: (T) -> Unit) {
    val observer = OneTimeObserver(handler = onChangeHandler)
    observe(observer, observer)
}

object TestUtils {
    const val FIRST_USER_ID = "first_user_xyz"
    const val FIRST_USER_EMAIL = "firstuser@domain.com"

    const val LAST_USER_ID = "last_user_xyz"
    const val LAST_USER_EMAIL = "lastuser@domain.com"

    fun mockUsers(): List<User> {
        val firstUser = User(id = FIRST_USER_ID, email = FIRST_USER_EMAIL)
        val lastUser = User(id = LAST_USER_ID, email = LAST_USER_EMAIL)

        val users = (1..10).map {
            User(
                id = " user_$it",
                email = "user${it}@gmail.com",
                firstName = "User $it"
            )
        }.toMutableList()

        users.add(0, firstUser)
        users.add(lastUser)
        return users
    }

    fun mockflow(): Flow<Resource<List<UserMinimal>>> = flow {
        val firstUser = UserMinimal(id = FIRST_USER_ID, email = FIRST_USER_EMAIL)
        val lastUser = UserMinimal(id = LAST_USER_ID, email = LAST_USER_EMAIL)

        val users = (1..10).map {
            UserMinimal(
                id = " user_$it",
                email = "user${it}@gmail.com",
                firstName = "User $it"
            )
        }.toMutableList()

        users.add(0, firstUser)
        users.add(lastUser)

        val resource: Resource<List<UserMinimal>> = Resource.Success(data = users)
        emit(resource)
    }
}