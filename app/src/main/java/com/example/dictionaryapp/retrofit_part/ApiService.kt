package com.example.dictionaryapp.retrofit_part

import com.example.dictionaryapp.data_part.Synonym
import com.example.dictionaryapp.data_part.Word
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("api/v2/entries/en/{word}")
    fun getDefinitions(@Path("word") word: String): Call<List<Word>>

    @GET("words")
    fun getSynonyms(@Query("rel_syn") word: String): Call<List<Synonym>>
}