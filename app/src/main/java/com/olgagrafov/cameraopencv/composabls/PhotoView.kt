package com.olgagrafov.cameraopencv.composabls

import android.content.Context
import android.graphics.Bitmap
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.olgagrafov.cameraopencv.R

@Composable
fun PhotoView(
    photoBitmap: Bitmap,
    context: Context,
    painter: Painter,
    color: Color,
    text: String,
    onDone: () -> Unit
) {
    val bt = photoBitmap.asImageBitmap()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter =  painter,
            contentDescription = "",
            modifier = Modifier.padding(top = 24.dp, bottom = 5.dp)
        )
        Text(
            text =  text,
            color = color,
            style = MaterialTheme.typography.caption,
        )
        if(bt != null)
            Image(
                bitmap = bt,
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        else
            Image(
                painter = painterResource(id = R.drawable.no_face),
                contentDescription = null,
                modifier = Modifier.width(360.dp).height(604.dp)//fillMaxSize()
            )
    }
  Box(modifier = Modifier.fillMaxSize()) {
        Button(
            onClick = { onDone( )},
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