package com.example.thegazette.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.thegazette.R

// 1. Оголошуємо сімейство шрифту Unbounded
val UnboundedFontFamily = FontFamily(
    Font(R.font.unbounded_regular, FontWeight.Normal),
    Font(R.font.unbounded_medium, FontWeight.Medium),
    Font(R.font.unbounded_semibold, FontWeight.SemiBold),
    Font(R.font.unbounded_bold, FontWeight.Bold)
)

// 2. Базовий стиль із шрифтом Unbounded
private val defaultTextStyle = TextStyle(
    fontFamily = UnboundedFontFamily
)

// 3. Глобальна типографіка для Material 3
val Typography = Typography(
    displayLarge = defaultTextStyle.copy(fontSize = 57.sp, lineHeight = 64.sp, fontWeight = FontWeight.Normal),
    displayMedium = defaultTextStyle.copy(fontSize = 45.sp, lineHeight = 52.sp, fontWeight = FontWeight.Normal),
    displaySmall = defaultTextStyle.copy(fontSize = 36.sp, lineHeight = 44.sp, fontWeight = FontWeight.Normal),

    headlineLarge = defaultTextStyle.copy(fontSize = 32.sp, lineHeight = 40.sp, fontWeight = FontWeight.SemiBold),
    headlineMedium = defaultTextStyle.copy(fontSize = 28.sp, lineHeight = 36.sp, fontWeight = FontWeight.SemiBold),
    headlineSmall = defaultTextStyle.copy(fontSize = 24.sp, lineHeight = 32.sp, fontWeight = FontWeight.SemiBold),

    titleLarge = defaultTextStyle.copy(fontSize = 22.sp, lineHeight = 28.sp, fontWeight = FontWeight.Normal),
    titleMedium = defaultTextStyle.copy(fontSize = 16.sp, lineHeight = 24.sp, fontWeight = FontWeight.Normal),
    titleSmall = defaultTextStyle.copy(fontSize = 14.sp, lineHeight = 20.sp, fontWeight = FontWeight.Normal),

    bodyLarge = defaultTextStyle.copy(fontSize = 16.sp, lineHeight = 24.sp, fontWeight = FontWeight.Normal),
    bodyMedium = defaultTextStyle.copy(fontSize = 14.sp, lineHeight = 20.sp, fontWeight = FontWeight.Normal),
    bodySmall = defaultTextStyle.copy(fontSize = 12.sp, lineHeight = 16.sp, fontWeight = FontWeight.Normal),

    labelLarge = defaultTextStyle.copy(fontSize = 14.sp, lineHeight = 20.sp, fontWeight = FontWeight.Normal),
    labelMedium = defaultTextStyle.copy(fontSize = 12.sp, lineHeight = 16.sp, fontWeight = FontWeight.Normal),
    labelSmall = defaultTextStyle.copy(fontSize = 11.sp, lineHeight = 16.sp, fontWeight = FontWeight.Normal)
)