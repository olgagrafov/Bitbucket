package com.olgagrafov.cameraopencv.composabls

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.olgagrafov.cameraopencv.R
import com.olgagrafov.cameraopencv.activity.CameraActivity
import com.olgagrafov.cameraopencv.activity.PERMISSION_GRANTED
import com.olgagrafov.cameraopencv.activity.lightSensor

@Composable
fun MainContent(
    context: Context,
    checkLighting: () -> Unit,
) {
    Box(Modifier.fillMaxSize())
    {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "",
                modifier = Modifier
                    .wrapContentSize(Alignment.TopStart)
                    .width(101.dp)
                    .height(23.dp)
                    .padding(start = 24.dp, bottom = 5.dp)
            )
            Divider (color = Color.White, modifier = Modifier
                .height(1.dp)
                .width(103.dp))
            Text(text = context.getString(R.string.welcome), style = MaterialTheme.typography.h2, modifier = Modifier.padding(start = 24.dp, top = 50.dp))
            Text(text = context.getString(R.string.bio_eye_user), style = MaterialTheme.typography.h1,modifier = Modifier.padding(start = 24.dp, top = 5.dp))
            Text(text = context.getString(R.string.small_demo), style = MaterialTheme.typography.body1, modifier = Modifier.padding(start = 24.dp, top = 5.dp, bottom = 80.dp))
            Button(
                onClick = {
                    if (PERMISSION_GRANTED) {
                        if (lightSensor!!.isLuxInRange())
                            openCamera(context)
                        else
                            checkLighting()
                    }
                    else
                        Toast.makeText(context, context.getString(R.string.permission_denied), Toast.LENGTH_SHORT ).show()
                },
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                modifier = Modifier
                    .width(300.dp)
                    .height(65.dp)
                    .padding(start = 24.dp)) {
                Text(text = context.getString(R.string.launch_face_detector), style = MaterialTheme.typography.button)
                Image(
                    painter = painterResource(id = R.drawable.arrow),
                    contentDescription = "",
                    modifier = Modifier.padding(start = 5.dp, top=5.dp).width(40.dp)
                )
            }
        }
    }
}

private fun openCamera (context: Context) {
    val myIntent = Intent(context, CameraActivity::class.java)
    context.startActivity(myIntent)
}
