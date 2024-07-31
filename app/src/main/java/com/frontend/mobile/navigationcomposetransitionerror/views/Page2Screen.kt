package com.frontend.mobile.navigationcomposetransitionerror.views

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.HapticFeedbackConstantsCompat

@Composable
fun Page2Screen() {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val sizeScale by animateFloatAsState(if (isPressed) 0.95f else 1f, label = "")
    val view = LocalView.current

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.Yellow)) {
        Text("Page 2 SCREEN")
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            modifier = Modifier.graphicsLayer(
                scaleX = sizeScale,
                scaleY = sizeScale
            ),
            interactionSource = interactionSource,
            onClick = {
                view.performHapticFeedback(HapticFeedbackConstantsCompat.VIRTUAL_KEY)
            },
        ) {
            Text(text = "Click me")
        }
    }
}