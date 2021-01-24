package com.jerryhanks.farimoneytest.data

import com.google.common.truth.Truth.assertThat
import com.google.gson.GsonBuilder
import com.jerryhanks.farimoneytest.data.api.DummyApiService
import com.jerryhanks.farimoneytest.utils.LocalDateTimeConverter
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime

/**
 * Author: Jerry
 * Project: FairmoneyTest
 */

@RunWith(JUnit4::class)
class DummyApiServiceTest {

    private val usersJson = """
        {
          "data": [
            {
              "id": "0F8JIqi4zwvb77FGz6Wt",
              "lastName": "Fiedler",
              "firstName": "Heinz-Georg",
              "email": "heinz-georg.fiedler@example.com",
              "title": "mr",
              "picture": "https://randomuser.me/api/portraits/men/81.jpg"
            },
            {
              "id": "0P6E1d4nr0L1ntW8cjGU",
              "picture": "https://randomuser.me/api/portraits/women/74.jpg",
              "lastName": "Hughes",
              "email": "katie.hughes@example.com",
              "title": "miss",
              "firstName": "Katie"
            },
            {
              "id": "1Lkk06cOUCkiAsUXFLMN",
              "title": "mr",
              "lastName": "Aasland",
              "firstName": "Vetle",
              "picture": "https://randomuser.me/api/portraits/men/97.jpg",
              "email": "vetle.aasland@example.com"
            },
            {
              "id": "1OuR3CWOEsfISTpFxsG7",
              "picture": "https://randomuser.me/api/portraits/men/66.jpg",
              "lastName": "Vasquez",
              "email": "dylan.vasquez@example.com",
              "title": "mr",
              "firstName": "Dylan"
            },
            {
              "id": "1pRsh5nXDIH3pjEOZ17A",
              "lastName": "Vicente",
              "title": "miss",
              "firstName": "Margarita",
              "email": "margarita.vicente@example.com",
              "picture": "https://randomuser.me/api/portraits/women/5.jpg"
            },
            {
              "id": "3JAf8R85oIlxXd58Piqk",
              "email": "joey.oliver@example.com",
              "title": "mr",
              "firstName": "Joey",
              "lastName": "Oliver",
              "picture": "https://randomuser.me/api/portraits/men/61.jpg"
            },
            {
              "id": "5aZRSdkcBOM6j3lkWEoP",
              "picture": "https://randomuser.me/api/portraits/women/50.jpg",
              "email": "lilja.lampinen@example.com",
              "lastName": "Lampinen",
              "firstName": "Lilja",
              "title": "ms"
            },
            {
              "id": "5tVxgsqPCjv2Ul5Rc7gw",
              "email": "abigail.liu@example.com",
              "lastName": "Liu",
              "title": "miss",
              "picture": "https://randomuser.me/api/portraits/women/83.jpg",
              "firstName": "Abigail"
            },
            {
              "id": "6wy6UNkZueJfIUfq88d5",
              "picture": "https://randomuser.me/api/portraits/women/32.jpg",
              "firstName": "Melanie",
              "email": "melanie.pilz@example.com",
              "title": "miss",
              "lastName": "Pilz"
            },
            {
              "id": "7DbXNPWlNDR4QYVvFZjr",
              "email": "evan.carlson@example.com",
              "firstName": "Evan",
              "picture": "https://randomuser.me/api/portraits/men/80.jpg",
              "lastName": "Carlson",
              "title": "mr"
            },
            {
              "id": "8RQd4OVqvmV0I4UlWETQ",
              "email": "kitty.steward@example.com",
              "title": "ms",
              "firstName": "Kitty",
              "picture": "https://randomuser.me/api/portraits/women/78.jpg",
              "lastName": "Steward"
            },
            {
              "id": "8UfTdB7ctWt3Fl87d88Q",
              "firstName": "Vanessa",
              "picture": "https://randomuser.me/api/portraits/women/33.jpg",
              "email": "vanessa.ramos@example.com",
              "lastName": "Ramos",
              "title": "ms"
            },
            {
              "id": "8YL1aG0vwRBXTzeZ0jRC",
              "picture": "https://randomuser.me/api/portraits/women/85.jpg",
              "firstName": "Olaí",
              "email": "olai.gomes@example.com",
              "title": "mrs",
              "lastName": "Gomes"
            },
            {
              "id": "9N03J6vQj6MFq2UpUanW",
              "email": "constance.bourgeois@example.com",
              "lastName": "Bourgeois",
              "firstName": "Constance",
              "title": "miss",
              "picture": "https://randomuser.me/api/portraits/women/87.jpg"
            },
            {
              "id": "CNYttp1Jrgg3I2zfSeS4",
              "email": "kenneth.carter@example.com",
              "picture": "https://randomuser.me/api/portraits/men/40.jpg",
              "lastName": "Carter",
              "firstName": "Kenneth",
              "title": "mr"
            }
          ],
          "total": 100,
          "page": 0,
          "limit": 50,
          "offset": 0
        }
    """.trimIndent()
    private val userDetailsJson = """
        {
          "id": "VqOy7pso6gmeEKnEEhob",
          "location": {
            "timezone": "-10:00",
            "city": "اسلام‌شهر",
            "country": "Iran",
            "state": "کرمان",
            "street": "8721, رسالت"
          },
          "dateOfBirth": "1960-06-15T00:43:59.672Z",
          "gender": "male",
          "firstName": "محمدپارسا",
          "lastName": "سهيلي راد",
          "title": "mr",
          "email": "mhmdprs.shylyrd@example.com",
          "registerDate": "2020-03-17T13:17:34.518Z",
          "phone": "033-69287019",
          "picture": "https://randomuser.me/api/portraits/men/21.jpg"
        }
    """.trimIndent()

    private val usersResponse = MockResponse()
        .addHeader("Content-Type", "application/json; charset=utf-8")
        .addHeader("Cache-Control", "no-cache")
        .setBody(usersJson)

    private val userDetailsResponse = MockResponse()
        .addHeader("Content-Type", "application/json; charset=utf-8")
        .addHeader("Cache-Control", "no-cache")
        .setBody(userDetailsJson)


    private val dispatcher = object : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/user?limit=100" -> {
                    usersResponse.setResponseCode(200)
                }
                "/user/VqOy7pso6gmeEKnEEhob" -> {
                    userDetailsResponse.setResponseCode(200)
                }
                else -> MockResponse().setResponseCode(404)
            }
        }

    }

    private val mockWebServer = MockWebServer()
    private lateinit var apiService: DummyApiService


    @Before
    fun setUp() {
        mockWebServer.dispatcher = dispatcher
        mockWebServer.start()

        val gson =
            GsonBuilder().registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeConverter())
                .create()

        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(DummyApiService::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    //Todo: Add more tests

    @Test
    fun `DummyApiService return list of users`() = runBlocking {
        val usersResponse = apiService.users()
        val users = usersResponse.data
        assert(users.size == 15)

        val firstUser = users.first()
        val lastUser = users.last()

        assertThat(firstUser.id).isEqualTo("0F8JIqi4zwvb77FGz6Wt")
        assertThat(lastUser.id).isEqualTo("CNYttp1Jrgg3I2zfSeS4")
    }

    @Test
    fun `DummyApiService return details of a user`() = runBlocking {
        val userResponse = apiService.userDetails("VqOy7pso6gmeEKnEEhob")

        assertThat(userResponse).isNotNull()

        assertThat(userResponse.email).isEqualTo("mhmdprs.shylyrd@example.com")
        assertThat(userResponse.firstName).isEqualTo("محمدپارسا")
        assertThat(userResponse.lastName).isEqualTo("سهيلي راد")
        assertThat(userResponse.gender).isEqualTo("male")
        assertThat(userResponse.phone).isEqualTo("033-69287019")
    }
}