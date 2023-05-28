package com.olgagrafov.cameraopencv.composabls

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.olgagrafov.cameraopencv.activity.MAIN_WINDOW
import com.olgagrafov.cameraopencv.R

@Composable
fun AdjustLighting(
    context: Context,
    windowSwitch: MutableState<Int>
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
                modifier = Modifier.padding(24.dp)
            )
            Text(
                text = context.getString(R.string.adjust_lighting),
                style = MaterialTheme.typography.subtitle2,
                modifier = Modifier.padding(24.dp) //, start = 24.dpend = 24.dp,top = 20.dp, bottom = 50.dp))
            )
            Image(
                painter = painterResource(id = R.drawable.home),
                contentDescription = "",
                modifier = Modifier.width(400.dp).height(270.dp).padding(top = 50.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { windowSwitch.value = MAIN_WINDOW },
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                    modifier = Modifier.width(354.dp).height(88.dp).padding(start = 24.dp)
                ) {
                    Text(
                        text = context.getString(R.string.try_again),
                        style = MaterialTheme.typography.button
                    )
                }
            }
        }
    }
}