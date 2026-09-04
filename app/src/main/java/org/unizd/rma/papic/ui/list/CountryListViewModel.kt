package org.unizd.rma.papic.ui.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.unizd.rma.papic.data.CountryRepository

class CountryListViewModel(
    private val repository: CountryRepository = CountryRepository()
) : ViewModel() {

    var state: CountryListState by mutableStateOf<CountryListState>(
        CountryListState.Loading
    )
        private set

    init {
        loadCountries()
    }

    private fun loadCountries() {
        viewModelScope.launch {
            try {
                val countries = repository.getCountries()
                    .sortedBy {
                        it.names?.common ?: ""
                    }

                state = CountryListState.Success(countries)

            } catch (e: Exception) {

                state = CountryListState.Error(
                    e.message ?: "Došlo je do greške"
                )
            }
        }
    }
}