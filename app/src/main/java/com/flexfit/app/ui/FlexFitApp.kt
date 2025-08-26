package com.flexfit.app.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.filled.TrackChanges
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.flexfit.app.ui.screens.GoalsScreen
import com.flexfit.app.ui.screens.HomeScreen
import com.flexfit.app.ui.screens.ProfileScreen
import com.flexfit.app.ui.screens.ProgressScreen
import com.flexfit.app.ui.screens.WorkoutsScreen

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home", "Home", Icons.Default.Home)
    object Workouts : Screen("workouts", "Workouts", Icons.Default.FitnessCenter)
    object Progress : Screen("progress", "Progress", Icons.Default.ShowChart)
    object Goals : Screen("goals", "Goals", Icons.Default.TrackChanges)
    object Profile : Screen("profile", "Profile", Icons.Default.Person)
}

@Composable
fun FlexFitApp() {
    val navController = rememberNavController()
    
    Scaffold(
        bottomBar = {
            FlexFitBottomNavigation(navController = navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen()
            }
            composable(Screen.Workouts.route) {
                WorkoutsScreen()
            }
            composable(Screen.Progress.route) {
                ProgressScreen()
            }
            composable(Screen.Goals.route) {
                GoalsScreen()
            }
            composable(Screen.Profile.route) {
                ProfileScreen()
            }
        }
    }
}

@Composable
fun FlexFitBottomNavigation(navController: NavHostController) {
    val items = listOf(
        Screen.Home,
        Screen.Workouts,
        Screen.Progress,
        Screen.Goals,
        Screen.Profile
    )
    
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    
    NavigationBar {
        items.forEach { screen ->
            NavigationBarItem(
                icon = { 
                    Icon(
                        imageVector = screen.icon, 
                        contentDescription = screen.title
                    ) 
                },
                label = { Text(screen.title) },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                }
            )
        }
    }
}