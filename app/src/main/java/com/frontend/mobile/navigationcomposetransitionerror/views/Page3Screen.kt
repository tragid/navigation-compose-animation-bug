package com.frontend.mobile.navigationcomposetransitionerror.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun Page3Screen() {
    Column(modifier = Modifier.fillMaxSize().background(Color.Red)) {
        Text("Page 3 SCREEN")
    }
}