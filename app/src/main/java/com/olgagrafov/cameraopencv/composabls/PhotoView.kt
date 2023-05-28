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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.olgagrafov.cameraopencv.R
import com.olgagrafov.cameraopencv.model.Face

@Composable
fun PhotoView(
    photoBitmap: Bitmap,
    face: Face,
    context: Context,
    showSessionSummary: () -> Unit
) {
    val bt = photoBitmap.asImageBitmap()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter =  painterResource(id = if (face.idDetected) R.drawable.face_detected else R.drawable.no_face),
            contentDescription = "",
            modifier = Modifier.padding(top = 5.dp)
        )
        Text(
            text =  if (face.idDetected) context.getString(R.string.detected) else  context.getString(R.string.no_face),
            color = if (face.idDetected) Color(red = 51, green = 211, blue = 123) else Color(red = 255, green = 106, blue = 124),
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
                modifier = Modifier.fillMaxSize()
            )


    }

    Box(modifier = Modifier.fillMaxSize()) {
        Button(
            onClick = { showSessionSummary() },
            shape = RoundedCornerShape(33.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
            modifier = Modifier.width(190.dp).height(88.dp).align(Alignment.BottomCenter).padding(bottom = 5.dp)
        ) {
            Text(
                text = context.getString(R.string.done),
                style = MaterialTheme.typography.button
            )
        }
    }
}