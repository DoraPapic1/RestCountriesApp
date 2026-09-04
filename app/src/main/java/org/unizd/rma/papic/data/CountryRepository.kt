package org.unizd.rma.papic.data

import okhttp3.OkHttpClient
import org.unizd.rma.papic.BuildConfig
import org.unizd.rma.papic.data.remote.RestCountriesApi
import org.unizd.rma.papic.data.remote.dto.Country
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class CountryRepository {

    private val api: RestCountriesApi

    init {
        val client = OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.restcountries.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(RestCountriesApi::class.java)
    }

    suspend fun getCountries(): List<Country> {
        val response = api.getAllCountries(
            authorization = "Bearer ${BuildConfig.REST_COUNTRIES_API_KEY}"
        )

        return response.data?.objects ?: emptyList()
    }
}