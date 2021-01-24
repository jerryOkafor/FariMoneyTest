package com.jerryhanks.farimoneytest.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import com.jerryhanks.fairmoneytest.MainCoroutineRule
import com.jerryhanks.fairmoneytest.TestUtils
import com.jerryhanks.fairmoneytest.runBlockingTest
import com.jerryhanks.farimoneytest.data.db.AppDatabase
import com.jerryhanks.farimoneytest.data.db.daos.UserDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.firstOrNull
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/**
 * Author: Jerry
 * Project: FairmoneyTest
 */

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class UsersDaoTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var appDatabase: AppDatabase
    private lateinit var usersDao: UserDao

    @Before
    @Throws(IOException::class)
    fun initDB() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        appDatabase =
            Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
                .allowMainThreadQueries().build()
        usersDao = appDatabase.userDao()
    }

    @Test
    fun insertUsersSavesData() = mainCoroutineRule.runBlockingTest {

        val users = TestUtils.mockUsers().map { it.toUserEntity() }
        usersDao.insertAll(users)

        //read
        val firstUser = usersDao.getUsers().firstOrNull()

        assertThat(firstUser).isNotNull()
        assertThat(firstUser).isNotNull()
        assertThat(firstUser?.size).isEqualTo(12)
        assertThat(firstUser?.first()?.id).isEqualTo(TestUtils.FIRST_USER_ID)
    }

    @Test
    fun deleteAllUserClearsDbRecord() = mainCoroutineRule.runBlockingTest {
        val users = TestUtils.mockUsers().map { it.toUserEntity() }
        usersDao.insertAll(users)

        //read
        val firstRead = usersDao.getUsers().firstOrNull()

        //Verify we have saved data
        assertThat(firstRead?.size).isEqualTo(12)

        //delete
        usersDao.deleteAllUsers()

        //read
        val secondRead = usersDao.getUsers().firstOrNull()

        //verify empty
        assertThat(secondRead).isEmpty()
    }

    @Test
    fun getUserReturnsUserRecord() = mainCoroutineRule.runBlockingTest {
        val users = TestUtils.mockUsers().map { it.toUserEntity() }
        usersDao.insertAll(users)

        val user = usersDao.getUser(TestUtils.FIRST_USER_ID).firstOrNull()

        assertThat(user).isNotNull()
        assertThat(user?.id).isEqualTo(TestUtils.FIRST_USER_ID)
        assertThat(user?.email).isEqualTo(TestUtils.FIRST_USER_EMAIL)
    }

    @After
    @Throws(IOException::class)
    fun closeDB() {
        appDatabase.close()
    }
}