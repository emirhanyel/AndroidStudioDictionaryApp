package com.example.dictionaryapp.retrofit_part

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private const val BASE_URL_WORD = "https://api.dictionaryapi.dev/"
    private const val BASE_URL_SYNONYM = "https://api.datamuse.com/"

    private var retrofit: Retrofit? = null
    private var retrofitV2: Retrofit? = null

    fun getClientWord(): Retrofit {
        if (retrofit == null)
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL_WORD)
                .addConverterFactory(GsonConverterFactory.create()) //retrofit will understand as a converter GSON converter will be used
                .build()

        return retrofit as Retrofit
    }

    fun getClientSynonym(): Retrofit {
        if (retrofitV2 == null)
            retrofitV2 = Retrofit.Builder()
                .baseUrl(BASE_URL_SYNONYM)
                .addConverterFactory(GsonConverterFactory.create()) //retrofit will understand as a converter GSON converter will be used
                .build()

        return retrofitV2 as Retrofit
    }
}