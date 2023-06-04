package com.olgagrafov.cameraopencv.composabls

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.olgagrafov.cameraopencv.R

@Composable
fun AdjustLighting(
    context: Context,
    onTry: () -> Unit,
){
    Box(Modifier.fillMaxSize())
    {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = context.getString(R.string.perform_test),
                style = MaterialTheme.typography.subtitle1,
            )
            Text(
                text = context.getString(R.string.adjust_lighting),
                style = MaterialTheme.typography.subtitle2,
                modifier = Modifier.padding(top = 24.dp, bottom = 70.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.home),
                contentDescription = "",
                modifier = Modifier.width(298.dp).height(205.dp)
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth().fillMaxHeight().padding(bottom = 5.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { onTry() },
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                modifier = Modifier.width(265.dp).height(65.dp)
            ) {
                Text(
                    text = context.getString(R.string.try_again),
                    style = MaterialTheme.typography.button
                )
            }
        }
    }
}