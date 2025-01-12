package com.sjhstudio.compose.movieapp.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavDirections

fun NavController.safeNavigation(direction: NavDirections) {
    currentDestination?.getAction(direction.actionId)?.run { navigate(direction) }
}