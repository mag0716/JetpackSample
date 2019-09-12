package com.github.mag0716.camerax.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.ImageCapture
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.github.mag0716.camerax.view.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "CameraXSample(View)"
        private const val REQUEST_CAMERA_PERMISSION = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

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

    private fun initCamera() {
        binding.cameraView.bindToLifecycle(this)
        val outputDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        if (outputDirectory != null && outputDirectory.exists()) {
            Log.d(TAG, "output directory : ${outputDirectory.path}")
            val outputFile =
                File("${outputDirectory.path}/output_${System.currentTimeMillis()}.png")
            binding.captureButton.setOnClickListener {
                binding.cameraView.takePicture(
                    outputFile,
                    object : ImageCapture.OnImageSavedListener {
                        override fun onImageSaved(file: File) {
                            Log.d(TAG, "onImageSaved : ${file.path}")
                        }

                        override fun onError(
                            useCaseError: ImageCapture.UseCaseError,
                            message: String,
                            cause: Throwable?
                        ) {
                            Log.e(TAG, "onError : $message", cause)
                        }

                    })
            }
        }
    }
}
