package org.aystudios.skincare

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

@Composable
fun getAppNavigator() = LocalNavigator.currentOrThrow

@Composable
fun getAppRootNavigator() = generateSequence(getAppNavigator()) { it.parent }.lastOrNull()
