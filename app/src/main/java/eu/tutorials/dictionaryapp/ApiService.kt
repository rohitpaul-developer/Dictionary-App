package eu.tutorials.dictionaryapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private val retrofit = Retrofit.Builder()
    .baseUrl("https://api.dictionaryapi.dev")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val dictionaryService = retrofit.create(ApiService::class.java)

interface ApiService{
    @GET("/api/v2/entries/en/{word}")
    suspend fun getMeanings(
        @Path("word") word : String
    ):Results
}