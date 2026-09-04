package org.unizd.rma.papic.data.remote

import org.unizd.rma.papic.data.remote.dto.CountriesResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface RestCountriesApi {

    @GET(
        "countries/v5" +
                "?response_fields=names.common,codes.alpha_2,capitals,population,region,flag.url_png" +
                "&limit=100"
    )
    suspend fun getAllCountries(
        @Header("Authorization") authorization: String
    ): CountriesResponse
}