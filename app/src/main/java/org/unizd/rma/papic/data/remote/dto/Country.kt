package org.unizd.rma.papic.data.remote.dto

data class CountriesResponse(
    val data: CountriesData?
)

data class CountriesData(
    val objects: List<Country>?
)

data class Country(
    val names: Names?,
    val codes: Codes?,
    val capitals: List<Capital>?,
    val population: Long?,
    val region: String?,
    val flag: Flag?
)

data class Names(
    val common: String?
)

data class Codes(
    val alpha_2: String?
)

data class Capital(
    val name: String?
)

data class Flag(
    val url_png: String?
)