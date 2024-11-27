package com.sjhstudio.compose.movieapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import com.sjhstudio.compose.movieapp.R

private val spoqaBold = FontFamily(
    Font(R.font.spoqa_han_sans_neo_bold, FontWeight.Bold)
)
private val spoqaRegular = FontFamily(
    Font(R.font.spoqa_han_sans_neo_regular, FontWeight.Normal)
)
private val spoqaThin = FontFamily(
    Font(R.font.spoqa_han_sans_neo_thin, FontWeight.Thin)
)

val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = spoqaBold,
        fontSize = 60.sp,
    ),
    displayMedium = TextStyle(
        fontFamily = spoqaBold,
        fontSize = 45.sp,
    ),
    displaySmall = TextStyle(
        fontFamily = spoqaRegular,
        fontSize = 36.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = spoqaBold,
        fontSize = 32.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = spoqaBold,
        fontSize = 28.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = spoqaRegular,
        fontSize = 24.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = spoqaBold,
        fontSize = 22.sp
    ),
    titleMedium = TextStyle(
        fontFamily = spoqaBold,
        fontSize = 18.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = spoqaRegular,
        fontSize = 14.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = spoqaRegular,
        fontSize = 16.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = spoqaRegular,
        fontSize = 14.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = spoqaThin,
        fontSize = 12.sp
    ),
    labelLarge = TextStyle(
        fontFamily = spoqaRegular,
        fontSize = 14.sp
    ),
    labelMedium = TextStyle(
        fontFamily = spoqaRegular,
        fontSize = 12.sp
    ),
    labelSmall = TextStyle(
        fontFamily = spoqaThin,
        fontSize = 10.sp
    )
)

val Typography.titleLarge24: TextStyle
    @Composable get() = titleLarge.copy(
        fontSize = 24.sp
    )

val Typography.button: TextStyle
    @Composable get() = titleMedium

val Typography.dialogButton: TextStyle
    @Composable get() = titleMedium

val Typography.underlinedDialogButton: TextStyle
    @Composable get() = titleMedium.copy(
        textDecoration = TextDecoration.Underline
    )

val Typography.underlinedButton: TextStyle
    @Composable get() = titleMedium.copy(
        textDecoration = TextDecoration.Underline
    )