package com.olgagrafov.cameraopencv.activity

import android.Manifest.permission.CAMERA
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import com.olgagrafov.cameraopencv.ui.theme.CameraOpenCVTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.olgagrafov.cameraopencv.R
import com.olgagrafov.cameraopencv.composabls.AdjustLighting
import com.olgagrafov.cameraopencv.service.LightSensor

lateinit var lightSensor: LightSensor
lateinit var TAG: String
var MAIN_WINDOW = 1
var LIGHT_WINDOW = 2
var PERMISSION_GRANTED = false

class MainActivity : ComponentActivity() {

    private val REQUEST_PERMISSIONS = 1
    private val permissions = arrayOf(
        CAMERA,
        WRITE_EXTERNAL_STORAGE,
        READ_EXTERNAL_STORAGE
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkPermissions()

        TAG = this.resources.getString(R.string.tag)

        lightSensor = LightSensor(this)

        setContent {
            CameraOpenCVTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val windowSwitch = remember { mutableStateOf(MAIN_WINDOW) }

                    when (windowSwitch.value) {
                        MAIN_WINDOW -> MainContent(this, windowSwitch)
                        LIGHT_WINDOW -> AdjustLighting(this, windowSwitch)
                        else -> {
                            MainContent(this, windowSwitch)
                        }
                    }

                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        lightSensor.startListening()
    }

    override fun onPause() {
        super.onPause()
        lightSensor.stopListening()
    }

    override fun onDestroy() {
        super.onDestroy()
        lightSensor.stopListening()
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSIONS) {
            if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                PERMISSION_GRANTED = true
            } else {
                Log.i(TAG, "Permission denied")
            }
        }
    }

    private fun checkPermissions() {
        val permissionsToRequest = mutableListOf<String>()

        for (permission in permissions) {
            val result = ContextCompat.checkSelfPermission(this, permission)
            if (result != PackageManager.PERMISSION_GRANTED) {
                permissionsToRequest.add(permission)
            }
        }

        if (permissionsToRequest.isNotEmpty()) {
            val permissionsArray = permissionsToRequest.toTypedArray()

            ActivityCompat.requestPermissions(this, permissionsArray, REQUEST_PERMISSIONS)
        } else {
            PERMISSION_GRANTED = true
        }
    }
}

@Composable
fun MainContent(
    context: Context,
    windowSwitch: MutableState<Int>
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
                        if (lightSensor.isLuxInRange())
                            openCamera(context)
                        else
                            windowSwitch.value = LIGHT_WINDOW
                    }
                    else
                        Toast.makeText(context,  context.getString(R.string.permission_denied), Toast.LENGTH_SHORT ).show()
                   },
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                modifier = Modifier
                    .width(265.dp)
                    .height(65.dp)
                    .padding(start = 24.dp)) {
                Text(text = context.getString(R.string.launch_face_detector), style = MaterialTheme.typography.button)
                Image(
                    painter = painterResource(id = R.drawable.arrow),
                    contentDescription = "",
                    modifier = Modifier.padding(start = 5.dp, top=5.dp)
                )
            }
        }
    }
}

private fun openCamera (context: Context) {
    val myIntent = Intent(context, CameraView::class.java)
    context.startActivity(myIntent)
}

