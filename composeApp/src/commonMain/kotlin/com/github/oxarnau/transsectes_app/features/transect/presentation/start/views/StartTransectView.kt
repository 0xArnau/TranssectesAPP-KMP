package com.github.oxarnau.transsectes_app.features.transect.presentation.start.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.github.oxarnau.transsectes_app.features.transect.presentation.records.views.TopAppBar
import dev.jordond.compass.Coordinates
import dev.jordond.compass.Priority
import dev.jordond.compass.geolocation.Geolocator
import dev.jordond.compass.geolocation.GeolocatorResult
import dev.jordond.compass.geolocation.LocationRequest
import dev.jordond.compass.geolocation.TrackingStatus
import dev.jordond.compass.geolocation.isPermissionDeniedForever
import dev.jordond.compass.geolocation.mobile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartTransectView(
    navController: NavHostController,
) {
    val scrollBehavior =
        TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    val snackbarHostState = remember { SnackbarHostState() }

    val geoLocator = remember { Geolocator.mobile() }

    // Declare coordinates as MutableStateFlow
    val coordinates: MutableStateFlow<Coordinates?> =
        remember { MutableStateFlow(null) }

    // Start tracking the user's location
    LaunchedEffect(Unit) {
        geoLocator.startTracking(LocationRequest(priority = Priority.HighAccuracy))
        geoLocator.trackingStatus.collectLatest { status ->
            println("Status: $status")
            when (status) {
                is TrackingStatus.Idle -> {
                    println("Idle")
                }

                is TrackingStatus.Tracking -> {
                    println("Tracking")
                }

                is TrackingStatus.Update -> {
                    coordinates.update { status.location.coordinates }
                }

                is TrackingStatus.Error -> {
                    val error: GeolocatorResult.Error = status.cause
                    println("TRACKING ERROR: $error")
                    // Show the permissions settings screen
                    val permissionDeniedForever =
                        error.isPermissionDeniedForever()
                    println("TRACKING PERMISSION DENIED: $permissionDeniedForever")
                }
            }
        }
    }

    // Observing the coordinates state reactively
    val coordinateState = coordinates.collectAsState()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                { navController.popBackStack() },
                scrollBehavior,
                "Start/Stop transect"
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
        ) {
            Text("Coordinates: ${coordinateState.value?.let { "${it.latitude}, ${it.longitude}" } ?: "Not available"}")
        }
    }
}
