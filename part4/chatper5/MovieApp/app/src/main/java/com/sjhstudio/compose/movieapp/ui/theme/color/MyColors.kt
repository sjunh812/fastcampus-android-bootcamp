package com.sjhstudio.compose.movieapp.ui.theme.color

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color

data class MyColors(
    val colorScheme: ColorScheme,
    val tertiary: Color = colorScheme.primary,
    val launcherScreenBackground: Color = colorScheme.primary,
)

val ColorScheme.onPrimaryAlt: Color @ReadOnlyComposable get() = onPrimary
val ColorScheme.success: Color @ReadOnlyComposable get() = Color.Green
val ColorScheme.checked: Color @ReadOnlyComposable get() = Color.White
val ColorScheme.unchecked: Color @ReadOnlyComposable get() = Color.White
val ColorScheme.checkmark: Color @ReadOnlyComposable get() = primary
val ColorScheme.disabledSecondary: Color @ReadOnlyComposable get() = secondary.copy(alpha = 0.5f)
val ColorScheme.textFieldBackground: Color @ReadOnlyComposable get() = Color.LightGray
val ColorScheme.textFieldBackgroundContainer: Color @ReadOnlyComposable get() = Color.DarkGray
val ColorScheme.progressItemColor: Color @ReadOnlyComposable get() = Color.Black

// Font
val ColorScheme.defaultFont: Color @ReadOnlyComposable get() = Color.Black
val ColorScheme.descriptionFont: Color @ReadOnlyComposable get() = Color.Gray