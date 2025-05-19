package com.ekyrizky.wikinime

import androidx.compose.ui.window.ComposeUIViewController
import com.ekyrizky.wikinime.app.App
import com.ekyrizky.wikinime.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }