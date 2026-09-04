package org.unizd.rma.papic

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.unizd.rma.papic.ui.detail.CountryDetailScreen
import org.unizd.rma.papic.ui.list.CountryListScreen
import org.unizd.rma.papic.ui.list.CountryListViewModel

@Composable
fun AppNav() {
    val navController = rememberNavController()

    val listViewModel: CountryListViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "country_list"
    ) {
        composable(route = "country_list") {
            CountryListScreen(
                viewModel = listViewModel,
                onCountryClick = { cca2 ->
                    navController.navigate("country_detail/$cca2")
                }
            )
        }

        composable(
            route = "country_detail/{cca2}",
            arguments = listOf(
                navArgument("cca2") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val cca2 = backStackEntry.arguments?.getString("cca2") ?: ""

            CountryDetailScreen(
                cca2 = cca2,
                viewModel = listViewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
