package com.ufes.automobile.ui.garage

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun GarageScreen(
    modifier: Modifier,
    viewModel: GarageViewModel = hiltViewModel()
) {
    val vehicles by viewModel.vehicles.collectAsState()
}