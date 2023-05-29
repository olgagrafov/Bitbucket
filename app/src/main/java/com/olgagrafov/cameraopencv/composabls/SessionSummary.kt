package com.olgagrafov.cameraopencv.composabls

import android.app.Activity
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.olgagrafov.cameraopencv.R

@Composable
fun SessionSummary(
    context: Context,
    sTotal: String,
    sDetected: String,
    sNoDetected: String
){
    Box(Modifier.fillMaxSize())
    {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = context.getString(R.string.session_summary),
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.padding(24.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 50.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Column()
                {
                    Text(
                        text = sTotal,
                        style = MaterialTheme.typography.h3,
                        modifier = Modifier.padding(24.dp)
                    )
                    Text(
                        text = sDetected,
                        style = MaterialTheme.typography.h3,
                        color  = Color(red = 51, green = 211, blue = 123),
                        modifier = Modifier.padding(24.dp)
                    )
                    Text(
                        text = sNoDetected,
                        style = MaterialTheme.typography.h3,
                        color  = Color(red = 255, green = 106, blue = 124),
                        modifier = Modifier.padding(24.dp)
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { (context as Activity).finish() },
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                    modifier = Modifier.width(265.dp).height(65.dp).padding(bottom = 5.dp)
                ) {
                    Text(
                        text = context.getString(R.string.start_over),
                        style = MaterialTheme.typography.button
                    )
                }
            }
        }
    }
}

