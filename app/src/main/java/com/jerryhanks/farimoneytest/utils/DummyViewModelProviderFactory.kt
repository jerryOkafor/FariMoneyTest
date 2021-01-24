package com.jerryhanks.farimoneytest.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

/**
 * Author: Jerry
 * Project: FairmoneyTest
 */

@Singleton
class DummyViewModelProviderFactory @Inject
@Suppress
constructor(val creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>) :
    ViewModelProvider.Factory {
//    https://stackoverflow.com/questions/44638878/binding-into-map-with-kclass-type


    @Suppress(
        "UNCHECKED_CAST",
        "TooGenericExceptionCaught",
        "TooGenericExceptionThrown",
        "ThrowingNewInstanceOfSameException"
    )
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        var creator: Provider<out ViewModel>? = creators[modelClass]
        if (creator == null) {
            for ((key, value) in creators) {
                if (modelClass.isAssignableFrom(key)) {
                    creator = value
                    break
                }
            }
        }
        if (creator == null) {
            throw IllegalArgumentException("unknown model call $modelClass")
        }

        try {
            return creator.get() as T
        } catch (e: RuntimeException) {
            throw RuntimeException(e)
        }
    }
}