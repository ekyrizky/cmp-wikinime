package com.ekyrizky.wikinime

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.ekyrizky.wikinime.app.App
import com.ekyrizky.wikinime.di.initKoin

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "wikinime",
        ) {
            App()
        }
    }
}