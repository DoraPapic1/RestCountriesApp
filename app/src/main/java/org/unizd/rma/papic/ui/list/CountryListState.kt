package org.unizd.rma.papic.ui.list

import org.unizd.rma.papic.data.remote.dto.Country

sealed interface CountryListState {
    object Loading : CountryListState
    data class Error(val message: String) : CountryListState
    data class Success(val countries: List<Country>) : CountryListState
}
