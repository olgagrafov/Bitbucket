package com.olgagrafov.cameraopencv.composabls

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.ImagePainter
import com.olgagrafov.cameraopencv.R

@Composable
fun PhotoView(
    photoPainter: ImagePainter,
    context: Context,
    painter: Painter,
    color: Color,
    text: String,
    onDone: () -> Unit,

) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painter,
            contentDescription = "",
            modifier = Modifier.padding(top = 24.dp, bottom = 5.dp)
        )
        Text(
            text = text,
            color = color,
            style = MaterialTheme.typography.caption,
        )
         Image(
            painter = photoPainter,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
         )
    }
  Box(modifier = Modifier.fillMaxSize()) {
        Button(
            onClick = { onDone() },
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
            modifier = Modifier.width(143.dp).height(65.dp).align(Alignment.BottomCenter).padding(bottom = 5.dp)
        ) {
            Text(
                text = context.getString(R.string.done),
                style = MaterialTheme.typography.button
            )
        }
    }
}