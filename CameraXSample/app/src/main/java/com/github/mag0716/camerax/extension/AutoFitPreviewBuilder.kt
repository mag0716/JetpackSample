package com.github.mag0716.camerax.extension

import android.util.Size
import android.view.Display
import android.view.Surface
import android.view.TextureView
import androidx.camera.core.Preview
import androidx.camera.core.PreviewConfig
import java.lang.ref.WeakReference

class AutoFitPreviewBuilder private constructor(
    config: PreviewConfig,
    viewFinderRef: WeakReference<TextureView>,
    bufferRotation: (Int) -> Unit,
    private val scaledSize: (Int, Int) -> Unit
) {
    companion object {
        private fun getDisplaySurfaceRotation(display: Display?) = when (display?.rotation) {
            Surface.ROTATION_0 -> 0
            Surface.ROTATION_90 -> 90
            Surface.ROTATION_180 -> 180
            Surface.ROTATION_270 -> 270
            else -> null
        }
    }

    val preview: Preview

    private var viewFinderRotation: Int
    private var bufferSize: Size = Size(0, 0)
    private var viewFinderSize: Size = Size(0, 0)
    private val viewFinderDisplayId: Int

    init {
        val viewFinder = checkNotNull(viewFinderRef.get())

        viewFinderDisplayId = viewFinder.display.displayId
        viewFinderRotation = getDisplaySurfaceRotation(viewFinder.display) ?: 0

        preview = Preview(config)
    }


}