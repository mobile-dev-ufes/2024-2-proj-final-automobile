package com.ufes.automobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.ufes.automobile.ui.navigation.NavGraph
import com.ufes.automobile.ui.theme.AutoMobileTheme
import dagger.hilt.android.AndroidEntryPoint

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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AutoMobileTheme {
                val navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }
    }
}