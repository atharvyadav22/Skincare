package org.aystudios.skincare.ui.theme

import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color


val AppPrimaryColor = Color(0xFFFF4428)       // Main brand color, primary actions/buttons
val AppBackgroundColor = Color(0xFFF4F4F7)   // Main screen background
val AppOnBackgroundColor = Color(0xFF000000)        // Icons, main text on light backgrounds
val AppSurfaceColor = Color(0xFFFFFFFF)        // Cards, surfaces, secondary backgrounds
val AppSecondaryColor = Color(0xFFFF7961)    // Slightly lighter for secondary buttons / accents
val AppOnPrimaryColor = Color(0xFFFFFFFF)    // Text/icons on primary buttons
val AppRedColor = Color(0xFFFF3B30)           // Price strikethrough / errors
val AppYellowColor = Color(0xFFFFD700)        // Rating stars / highlights


val AppColorScheme = lightColorScheme(
    primary = AppPrimaryColor,         // Main buttons, active elements
    onPrimary = AppOnPrimaryColor,     // Text/icons on primary
    secondary = AppSecondaryColor,     // Secondary actions, badges
    background = AppBackgroundColor,   // Main screen background
    onBackground = AppOnBackgroundColor, // Text/icons on background
    surface = AppSurfaceColor,         // Cards, sheets
    onSurface = AppOnBackgroundColor,         // Text/icons on cards/surfaces
    error = AppRedColor,                   // Errors / strikethrough price
    onError = AppSurfaceColor              // Text/icons on error
)