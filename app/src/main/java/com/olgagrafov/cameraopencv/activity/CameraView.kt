package com.olgagrafov.cameraopencv.activity

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment.DIRECTORY_DOWNLOADS
import android.os.Environment.getExternalStoragePublicDirectory
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.olgagrafov.cameraopencv.R
import com.olgagrafov.cameraopencv.composabls.CameraView
import com.olgagrafov.cameraopencv.composabls.PhotoView
import com.olgagrafov.cameraopencv.composabls.SessionSummary
import com.olgagrafov.cameraopencv.ui.theme.CameraOpenCVTheme
import kotlinx.coroutines.*
import org.opencv.android.Utils
import org.opencv.core.CvType
import org.opencv.core.Mat
import java.io.File
import java.io.FileNotFoundException
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class CameraView : ComponentActivity() {
    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService
    private var shouldShowCamera: MutableState<Boolean> = mutableStateOf(true)
    private lateinit var imageBitmap: Bitmap


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        System.loadLibrary("opencv_java4")

        val face = com.olgagrafov.cameraopencv.model.Face(true)
        val falseFace = com.olgagrafov.cameraopencv.model.Face(false)
        val faces = ArrayList<com.olgagrafov.cameraopencv.model.Face>()
        faces.add(face)
        faces.add(face)
        faces.add(face)
        faces.add(face)
        faces.add(falseFace)
        faces.add(falseFace)
        faces.add(falseFace)
        var det = 0
        var noDet = 0
        faces.forEach {
            if (it.idDetected)
                det++
            else
                noDet++
        }
        val iSize = faces.size
        val sTotal = getString(R.string.total).plus(": $iSize")
        val sDetected = getString(R.string.detected).plus(": $det")
        val sNoDetected = getString(R.string.no_face).plus(": $noDet")


        setContent {
            var showSessionSummary by remember { mutableStateOf(false) }

            CameraOpenCVTheme {
                if (shouldShowCamera.value) {
                    CameraView(
                        outputDirectory = outputDirectory,
                        executor = cameraExecutor,
                        onImageCaptured = ::handleImageCapture,
                        onError = { Log.e(TAG, "View error:", it) }
                    )
                    startTimer()
                } else {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background
                    ) {
                        if (showSessionSummary)
                            SessionSummary(this, sTotal, sDetected, sNoDetected)
                        else
                            PhotoView(
                                photoBitmap = imageBitmap,
                                face = falseFace,
                                context = this
                            ) { showSessionSummary = true }
                    }
                }
            }
        }
        outputDirectory = getOutputDirectory()
        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    private fun startTimer() {
        val scope = CoroutineScope(Dispatchers.Main)
        scope.launch {
            delay(30000)
            shouldShowCamera.value = false
        }
    }

    private fun handleImageCapture(uri: Uri) {
        var bitmap: Bitmap? = createBit(this, uri)
        bitmap?.let { convertBitmapToMat(it) }

// TODO Send bitmap  to C++

        shouldShowCamera.value = false

    }

    private fun getOutputDirectory(): File {
        val downloadsDirectory = getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS)
        val mediaDir = downloadsDirectory?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }
//        val mediaDir = externalMediaDirs.firstOrNull()?.let {
//            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
//        }

        return if (mediaDir != null && mediaDir.exists()) mediaDir else filesDir
    }

    private fun createBit(context: Context, savedUri: Uri): Bitmap? {
        var bitmap: Bitmap? = null

        bitmap = try {
            BitmapFactory.decodeStream(
                context
                    .contentResolver.openInputStream(savedUri)
            )
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            return null
        }

        if (bitmap != null) {
            imageBitmap = bitmap
        }

        return bitmap
    }

    private fun convertBitmapToMat(bitmap: Bitmap) {
        val mat = Mat(bitmap.height, bitmap.width, CvType.CV_8UC1)
        Utils.bitmapToMat(bitmap, mat)
       // Log.i(TAG, mat.elemSize().toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }
}