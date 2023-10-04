package pe.edu.moviecompose.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import pe.edu.moviecompose.ui.heroes.HeroDetail
import pe.edu.moviecompose.ui.heroes.HeroSearch

@Composable
fun Home(){
    val navController = rememberNavController()
    
    NavHost(navController = navController, startDestination = Routes.HeroSearch.route){
        composable(Routes.HeroSearch.route){
            HeroSearch(){id->
                navController.navigate(route = "${Routes.Detail.route}/$id")
            }
        }

        composable(
            route = Routes.Detail.routeWithArgument,
            arguments = listOf(navArgument(Routes.Detail.argument ){type = NavType.StringType})
        ){backStackEntry->
            val id = backStackEntry.arguments?.getString("id") as String
            HeroDetail(id)
        }
    }
}

sealed class Routes(val route: String){
    object HeroSearch: Routes("HeroSearch")
    object Detail: Routes("HeroDetail"){
        const val routeWithArgument = "HeroDetail/{id}"
        const val argument = "id"
    }
}