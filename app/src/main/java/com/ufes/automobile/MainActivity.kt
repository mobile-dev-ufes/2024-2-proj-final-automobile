package com.ufes.automobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.ufes.automobile.ui.navigation.NavGraph
import com.ufes.automobile.ui.navigation.Route
import com.ufes.automobile.ui.theme.AutoMobileTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * The main entry point of the application.
 *
 * This activity serves as the host for the application's composable UI and handles the initial setup
 * of the navigation graph. It uses Hilt for dependency injection.
 *
 * The `onCreate` method is responsible for:
 * 1. Enabling edge-to-edge display for immersive UI experience.
 * 2. Setting the content of the activity to a composable function.
 * 3. Initializing the navigation controller.
 * 4. Launching the main navigation graph defined in `NavGraph`.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AutoMobileTheme {
                val navController = rememberNavController()
                val startDestination = if (firebaseAuth.currentUser != null) {
                    Route.GarageScreen.route
                } else {
                    Route.LoginScreen.route
                }
                NavGraph(navController = navController, startDestination = startDestination)
            }
        }
    }
}