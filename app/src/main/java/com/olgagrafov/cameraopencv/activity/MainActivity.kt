package com.olgagrafov.cameraopencv.activity

import android.Manifest.permission.*
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import com.olgagrafov.cameraopencv.ui.theme.CameraOpenCVTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.olgagrafov.cameraopencv.R
import com.olgagrafov.cameraopencv.composabls.AdjustLighting
import com.olgagrafov.cameraopencv.composabls.MainContent
import com.olgagrafov.cameraopencv.sensors.LightSensor

const val TAG = "CAMERA_OPEN_CV"
var lightSensor: LightSensor? = null
var PERMISSION_GRANTED = false
class MainActivity : ComponentActivity() {

    private val MAIN_WINDOW = 1
    private val LIGHT_WINDOW = 2

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        val allPermissionsGranted = permissions.entries.all { it.value }
        if (allPermissionsGranted) {
            // All permissions are granted
            PERMISSION_GRANTED = true
        } else {
            Toast.makeText(this, getString(R.string.you_must_grant_permissions), Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkPermissions(this)

        //Server that check Lux Optimal range: 20-1000
        lightSensor = LightSensor(context = this)

        setContent {
            CameraOpenCVTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val windowSwitch = remember { mutableStateOf(MAIN_WINDOW) }

                    when (windowSwitch.value) {
                        // MAIN_WINDOW - the first screen
                        MAIN_WINDOW -> MainContent(this ) { windowSwitch.value = LIGHT_WINDOW}
                        // If the Lux Optimal isn't in range you wil pass to the window that ask for check lighting
                        LIGHT_WINDOW -> AdjustLighting(this) { windowSwitch.value = MAIN_WINDOW }
                        else -> {
                            MainContent(this )  { windowSwitch.value = LIGHT_WINDOW }
                        }
                    }

                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        lightSensor?.startListening()
    }
    override fun onPause() {
        super.onPause()
        lightSensor?.stopListening()
    }

    override fun onDestroy() {
        super.onDestroy()
        lightSensor?.stopListening()
    }

    private fun checkPermissions(context: Context) {
        when {
            ContextCompat.checkSelfPermission(context,CAMERA) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(context, WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(context, READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED -> {
                // All permissions are granted
                PERMISSION_GRANTED = true
            }
            else -> {
                // Request the necessary permissions
                requestPermissionLauncher.launch(arrayOf(
                    WRITE_EXTERNAL_STORAGE,
                    READ_EXTERNAL_STORAGE,
                    CAMERA
                ))
            }
        }
    }
}

