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
        fontFamily = spoqaRegular,
        fontSize = 60.sp,
    ),
    displayMedium = TextStyle(
        fontFamily = spoqaRegular,
        fontSize = 45.sp,
    ),
    displaySmall = TextStyle(
        fontFamily = spoqaRegular,
        fontSize = 36.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = spoqaRegular,
        fontSize = 32.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = spoqaRegular,
        fontSize = 28.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = spoqaRegular,
        fontSize = 24.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = spoqaRegular,
        fontSize = 22.sp
    ),
    titleMedium = TextStyle(
        fontFamily = spoqaRegular,
        fontSize = 16.sp,
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
        fontFamily = spoqaRegular,
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
        fontFamily = spoqaRegular,
        fontSize = 11.sp
    )
)

val Typography.titleLarge24: TextStyle
    @Composable get() = titleLarge.copy(
        fontSize = 24.sp
    )

val Typography.dialogButton: TextStyle
    @Composable get() = labelLarge.copy(
        fontSize = 18.sp
    )

val Typography.underlinedDialogButton: TextStyle
    @Composable get() = labelLarge.copy(
        fontSize = 18.sp,
        textDecoration = TextDecoration.Underline
    )

val Typography.underlinedButton: TextStyle
    @Composable get() = labelLarge.copy(
        fontSize = 18.sp,
        textDecoration = TextDecoration.Underline
    )