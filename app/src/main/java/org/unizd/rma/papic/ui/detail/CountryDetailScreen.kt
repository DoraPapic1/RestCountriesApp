package org.unizd.rma.papic.ui.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import org.unizd.rma.papic.data.remote.dto.Country
import org.unizd.rma.papic.ui.list.CountryListState
import org.unizd.rma.papic.ui.list.CountryListViewModel
import java.text.NumberFormat
import java.util.Locale

@Composable
fun CountryDetailScreen(
    cca2: String,
    viewModel: CountryListViewModel,
    onBack: () -> Unit
) {
    val state = viewModel.state

    if (state is CountryListState.Success) {

        val country = state.countries.find {
            it.codes?.alpha_2 == cca2
        }

        if (country != null) {
            CountryDetailContent(
                country = country,
                onBack = onBack
            )
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Država nije pronađena.")
            }
        }

    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Podaci nisu dostupni.")
        }
    }
}

@Composable
private fun CountryDetailContent(
    country: Country,
    onBack: () -> Unit
) {
    val scrollState = rememberScrollState()

    val countryName = country.names?.common ?: "Nepoznato ime"
    val region = country.region ?: "Nepoznata regija"
    val capital = country.capitals?.firstOrNull()?.name ?: "Nema podataka"

    val population = country.population?.let {
        NumberFormat.getInstance(Locale.getDefault()).format(it)
    } ?: "Nema podataka"

    val countryCode = country.codes?.alpha_2 ?: "Nema podataka"
    val flagUrl = country.flag?.url_png

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Detalji države",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            )
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                AsyncImage(
                    model = flagUrl,
                    contentDescription = "Zastava države $countryName",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                )

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                Text(
                    text = countryName,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                DetailRow(
                    label = "Kod države",
                    value = countryCode
                )

                DetailRow(
                    label = "Regija",
                    value = region
                )

                DetailRow(
                    label = "Glavni grad",
                    value = capital
                )

                DetailRow(
                    label = "Populacija",
                    value = population
                )
            }
        }

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Button(
            onClick = onBack
        ) {
            Text("Natrag")
        }
    }
}

@Composable
private fun DetailRow(
    label: String,
    value: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {

        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(
            modifier = Modifier.height(2.dp)
        )

        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}