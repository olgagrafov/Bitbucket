package com.olgagrafov.cameraopencv.ui.theme

import android.content.res.Resources.Theme
import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.olgagrafov.cameraopencv.R

// Set of Material typography styles to start with
val Nunitorans = FontFamily (
        Font(R.font.nunitorans_regular)
    )
val Typography = Typography(
    h1 = TextStyle(
        fontFamily = Nunitorans,
        fontWeight = FontWeight.W700,
        fontSize = 40.sp,
        color = Color.White
    ),
    h2 = TextStyle(
        fontFamily = Nunitorans,
        fontWeight = FontWeight.W300,
        fontSize = 24.sp,
        color = Color.White
    ),
    h3 = TextStyle(
        fontFamily = Nunitorans,
        fontWeight = FontWeight.W700,
        fontSize = 16.sp,
        color = Color.White
    ),
    subtitle1 = TextStyle(
        fontFamily = Nunitorans,
        fontWeight = FontWeight.W700,
        fontSize = 24.sp,
        color = Color.White,
        textAlign = TextAlign.Center
    ),
    subtitle2 = TextStyle(
        fontFamily = Nunitorans,
        fontWeight = FontWeight.W400,
        fontSize = 18.sp,
        color = Color.White,
        textAlign = TextAlign.Center
    ),
    caption = TextStyle(
        fontFamily = Nunitorans,
        fontWeight = FontWeight.W700,
        fontSize = 36.sp,
        color = Color.White,
        textAlign = TextAlign.Center
    ),
    body1 = TextStyle(
        fontFamily = Nunitorans,
        fontWeight = FontWeight.W300,
        fontSize = 24.sp,
        color = Color(0xFF9E9E9E)
    ),
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W700,
        fontSize = 18.sp,
        color = Color.Black
    ),
    /* Other default text styles to override

    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)