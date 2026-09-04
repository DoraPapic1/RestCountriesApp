package org.unizd.rma.papic.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import org.unizd.rma.papic.data.remote.dto.Country

@Composable
fun CountryListScreen(
    viewModel: CountryListViewModel,
    onCountryClick: (String) -> Unit
) {
    when (val uiState = viewModel.state) {

        is CountryListState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is CountryListState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = uiState.message)
            }
        }

        is CountryListState.Success -> {
            CountryList(
                countries = uiState.countries,
                onCountryClick = onCountryClick
            )
        }
    }
}

@Composable
private fun CountryList(
    countries: List<Country>,
    onCountryClick: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Text(
            text = "Države svijeta",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(countries) { country ->

                CountryRow(
                    country = country,
                    onClick = {
                        country.codes?.alpha_2?.let { code ->
                            onCountryClick(code)
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun CountryRow(
    country: Country,
    onClick: () -> Unit
) {
    val countryName = country.names?.common ?: "Nepoznato ime"
    val region = country.region ?: "Nepoznata regija"
    val flagUrl = country.flag?.url_png

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .clickable(onClick = onClick),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            AsyncImage(
                model = flagUrl,
                contentDescription = "Zastava države $countryName",
                modifier = Modifier.size(60.dp)
            )

            Column {

                Text(
                    text = countryName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = region,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}