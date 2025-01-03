package com.sjhstudio.compose.movieapp.ui.theme.color

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

data class MyColors(
    val colorScheme: ColorScheme,
    val tertiary: Color = colorScheme.primary,
    val onPrimaryAlt: Color = colorScheme.onPrimary,
    val success: Color = Color.Green,
    val checked: Color = Color.White,
    val unchecked: Color = Color.White,
    val checkmark: Color = colorScheme.primary,
    val disabledSecondary: Color = colorScheme.secondary.copy(alpha = 0.5f),
    val textFieldBackground: Color = Color.LightGray,
    val textFieldBackgroundContainer: Color = Color.DarkGray,
    val launcherScreenBackground: Color = colorScheme.primary,
    val progressItemColor: Color = Color.Black
) {

//    val primary: Color get() = colorScheme.primary
//    val primaryContainer: Color get() = colorScheme.primaryContainer
//    val secondary: Color get() = colorScheme.secondary
//    val secondaryContainer: Color get() = colorScheme.secondaryContainer
//    val background: Color get() = colorScheme.background
//    val surface: Color get() = colorScheme.surface
//    val error: Color get() = colorScheme.error
//    val onPrimary: Color get() = colorScheme.onPrimary
//    val onSecondary: Color get() = colorScheme.onSecondary
//    val onBackground: Color get() = colorScheme.onBackground
//    val onSurface: Color get() = colorScheme.onSurface
//    val onError: Color get() = colorScheme.onError
//    val isLight: Boolean get() = true
}

val ColorScheme.tertiary: Color @Composable get() = primary
val ColorScheme.onPrimaryAlt: Color @Composable get() = onPrimary
val ColorScheme.success: Color @Composable get() = Color.Green
val ColorScheme.checked: Color @Composable get() = Color.White
val ColorScheme.unchecked: Color @Composable get() = Color.White
val ColorScheme.checkmark: Color @Composable get() = primary
val ColorScheme.disabledSecondary: Color @Composable get() = secondary.copy(alpha = 0.5f)
val ColorScheme.textFieldBackground: Color @Composable get() = Color.LightGray
val ColorScheme.textFieldBackgroundContainer: Color @Composable get() = Color.DarkGray
val ColorScheme.progressItemColor: Color @Composable get() = Color.Black
