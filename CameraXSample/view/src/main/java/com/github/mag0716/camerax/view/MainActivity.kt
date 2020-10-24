package com.github.mag0716.camerax.view

import android.Manifest
import android.Manifest.permission
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresPermission
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.view.CameraView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.github.mag0716.camerax.view.databinding.ActivityMainBinding
import java.io.File
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "CameraXSample(View)"
        private const val REQUEST_CAMERA_PERMISSION = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }

    private val executor = Executors.newSingleThreadExecutor()

    private lateinit var binding: ActivityMainBinding
    private lateinit var cameraView: CameraView
    private lateinit var changeLensFacingButton: Button
    private lateinit var captureButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        cameraView = findViewById(R.id.camera_view)
        changeLensFacingButton = findViewById(R.id.change_lens_facing)
        captureButton = findViewById(R.id.capture_button)

        @SuppressWarnings("MissingPermission") // allPermissionsGrantedでチェックしているのでwarningを抑制。FIXME:もっとスマートな実装を検討
        if (allPermissionsGranted()) {
            initCamera()
        } else {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CAMERA_PERMISSION
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            @SuppressWarnings("MissingPermission") // allPermissionsGrantedでチェックしているのでwarningを抑制。FIXME:もっとスマートな実装を検討
            if (allPermissionsGranted()) {
                initCamera()
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

    @RequiresPermission(permission.CAMERA)
    private fun initCamera() {
        cameraView.bindToLifecycle(this)
        updateChangeLensFacingButtonText()
        changeLensFacingButton.setOnClickListener {
            changeLensFacing()
        }
        val outputDirectory = filesDir
        if (outputDirectory != null && outputDirectory.exists()) {
            Log.d(TAG, "output directory : ${outputDirectory.path}")
            val outputFile =
                File("${outputDirectory.path}/output_${System.currentTimeMillis()}.png")
            val outputFileOptions = ImageCapture.OutputFileOptions.Builder(outputFile).build()
            captureButton.setOnClickListener {
                cameraView.takePicture(
                    outputFileOptions,
                    executor,
                    object : ImageCapture.OnImageSavedCallback {
                        override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                            val msg = "Photo capture succeeded: ${outputFile.absolutePath}"
                            runOnUiThread {
                                Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onError(exception: ImageCaptureException) {
                            val msg = "Photo capture failed : $exception"
                            runOnUiThread {
                                Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                            }
                        }
                    })
            }
        }
    }

    private fun updateChangeLensFacingButtonText() {
        changeLensFacingButton.text =
            when (cameraView.cameraLensFacing) {
                CameraSelector.LENS_FACING_FRONT -> "BACK"
                CameraSelector.LENS_FACING_BACK -> "FRONT"
                else -> ""
            }
    }

    private fun changeLensFacing() {
        cameraView.cameraLensFacing = when (cameraView.cameraLensFacing) {
            CameraSelector.LENS_FACING_FRONT -> CameraSelector.LENS_FACING_BACK
            CameraSelector.LENS_FACING_BACK -> CameraSelector.LENS_FACING_FRONT
            else -> CameraSelector.LENS_FACING_BACK
        }
        updateChangeLensFacingButtonText()
    }
}
