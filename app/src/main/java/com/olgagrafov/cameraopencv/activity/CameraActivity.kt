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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
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
import coil.compose.rememberImagePainter

class CameraActivity : ComponentActivity() {
    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService
    private var shouldShowCamera: MutableState<Boolean> = mutableStateOf(true)
    private lateinit var imageBitmap: Bitmap
    private lateinit var photoUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Load Open CV SDK
        System.loadLibrary("opencv_java4")

        //this hardcode is a temp data for create the next Summary window
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
                            clickDone = ::handleDone,
                            onError = { Log.e(TAG, "View error:", it) }
                        )
                       startTimer()
                } else {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background
                    ) {
                        //The next Summary screen will be displayed if we get an image and click "done" or the camera closes after 0.5 minutes
                        if (showSessionSummary || (!::imageBitmap.isInitialized))
                            SessionSummary(this, sTotal, sDetected, sNoDetected)
                        //The photo will be displayed on the photo screen if we make one
                        else
                            PhotoView(
                                photoPainter = rememberImagePainter(photoUri),
                                context = this,
                                painter =  painterResource(id = if (face.idDetected) R.drawable.face_detected else R.drawable.no_face),
                                color = if (face.idDetected) Color(red = 51, green = 211, blue = 123) else Color(red = 255, green = 106, blue = 124),
                                text =  if (face.idDetected) getString(R.string.detected) else  getString(R.string.no_face)
                            ) { showSessionSummary = true }

                    }
                }
            }
        }
        outputDirectory = getOutputDirectory()
        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    //Timer runs for 0.5 minutes, then camera closes
    private fun startTimer() {
        val scope = CoroutineScope(Dispatchers.Main)
        scope.launch {
            delay(30000)
            shouldShowCamera.value = false
            handleDone()
        }
    }

    //As soon as the "Done" button is clicked or the timer ends, this fun will be launched
    private fun handleDone() {
        Log.i("lunch", "handleDone")
    }

    private fun handleImageCapture(uri: Uri) {
        photoUri = uri
        var bitmap: Bitmap? = createBit(this, uri)
        Log.i("bit: ", bitmap.toString())
        bitmap?.let { convertBitmapToMat(it) }

// TODO Send bitmap  to C++

        shouldShowCamera.value = false

    }

    //TODO save CSV from C++

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

    //It's fun to convert a photo from an image to a CV using OpenCV SDK
    private fun convertBitmapToMat(bitmap: Bitmap) {
        val mat = Mat(bitmap.height, bitmap.width, CvType.CV_8UC1)
        Utils.bitmapToMat(bitmap, mat)
        Log.i(TAG, mat.elemSize().toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }
}