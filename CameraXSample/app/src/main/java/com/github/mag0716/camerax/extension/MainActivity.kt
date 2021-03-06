package com.github.mag0716.camerax.extension

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.extensions.BeautyPreviewExtender
import androidx.camera.extensions.BokehPreviewExtender
import androidx.camera.extensions.HdrPreviewExtender
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.common.util.concurrent.ListenableFuture
import java.io.File
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "CameraXExtension"
        private const val REQUEST_CAMERA_PERMISSION = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }

    private val executor = Executors.newSingleThreadExecutor()

    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    private lateinit var previewView: PreviewView
    private lateinit var captureButton: ImageButton

    private var camera: Camera? = null
    private var imageCapture: ImageCapture? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        previewView = findViewById(R.id.previewView)
        previewView.implementationMode = PreviewView.ImplementationMode.PERFORMANCE
        captureButton = findViewById(R.id.capture_button)

        if (allPermissionsGranted()) {
            previewView.post { startCamera() }
        } else {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CAMERA_PERMISSION
            )
        }
        captureButton.setOnClickListener {
            val file = File(externalMediaDirs.first(), "${System.currentTimeMillis()}.jpg")
            val outputOptions = ImageCapture.OutputFileOptions.Builder(file)
                .build()
            imageCapture?.takePicture(outputOptions,
                executor,
                object : ImageCapture.OnImageSavedCallback {
                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                        val msg = "Photo capture succeeded: ${file.absolutePath}"
                        previewView.post {
                            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onError(exception: ImageCaptureException) {
                        val msg = "Photo capture failed : $exception"
                        previewView.post {
                            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                        }
                    }
                })
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (allPermissionsGranted()) {
                previewView.post { startCamera() }
            } else {
                Toast.makeText(
                    this,
                    "Permissions not granted by user.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun allPermissionsGranted(): Boolean {
        for (permission in REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(
                    this, permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }

    private fun startCamera() {
        val cameraSelector =
            CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build()

        // setup preview
        val previewBuilder = Preview.Builder().apply {
            setTargetAspectRatio(AspectRatio.RATIO_16_9)
        }
        checkExtensionAvailability(previewBuilder, cameraSelector)
        val bokehPreviewExtender = BokehPreviewExtender.create(previewBuilder)
        if (bokehPreviewExtender.isExtensionAvailable(cameraSelector)) {
            Log.d(TAG, "Bokeh is available.")
            bokehPreviewExtender.enableExtension(cameraSelector)
        }
        val preview = previewBuilder.build()
        preview.setSurfaceProvider(
            previewView.surfaceProvider
        )

        // setup imagecapture
        imageCapture = ImageCapture.Builder().apply {
            setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
        }.build()

        val cameraProvider = cameraProviderFuture.get()
        camera = cameraProvider.bindToLifecycle(
            this,
            cameraSelector,
            preview,
            imageCapture
        )
    }

    private fun checkExtensionAvailability(
        previewBuilder: Preview.Builder,
        cameraSelector: CameraSelector
    ) {
        val bokehPreviewExtender = BokehPreviewExtender.create(previewBuilder)
        val hdrPreviewExtender = HdrPreviewExtender.create(previewBuilder)
        val beautyPreviewExtender = BeautyPreviewExtender.create(previewBuilder)

        Log.d(
            TAG,
            """
                bokeh : ${bokehPreviewExtender.isExtensionAvailable(cameraSelector)}
                HDR: ${hdrPreviewExtender.isExtensionAvailable(cameraSelector)}
                Beauty: ${beautyPreviewExtender.isExtensionAvailable(cameraSelector)}
                """
        )
    }
}
