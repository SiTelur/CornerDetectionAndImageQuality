package com.nbs.cornerdetectiondimagequality

import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.OrientationEventListener
import android.view.Surface
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.nbs.cornerdetectiondimagequality.databinding.ActivityMainBinding
import com.nbs.cornerdetectiondimagequality.utils.createCustomTempFile

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding
    private var imageCapture: ImageCapture? = null
    private lateinit var cropOverlay: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        } else {
            startCamera()
        }

        activityMainBinding.cameraButton.setOnClickListener {
takePicture()
        }

        cropOverlay = ImageView(this).apply {
            setImageResource(R.drawable.rectangle_shape)
            setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        }


    }

    private fun showModalSheet(image : String) {
        val modalBottomSheet = ResultFragment()
        val bundle = Bundle().apply {
            putString(ResultFragment.IMAGE, image)
        }
        modalBottomSheet.arguments = bundle
        modalBottomSheet.show(supportFragmentManager, ResultFragment.TAG)
    }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            this,
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.surfaceProvider = activityMainBinding.previewCamera.surfaceProvider
                }
            imageCapture = ImageCapture.Builder().build()
            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)

            } catch (e: Exception) {
                Toast.makeText(
                    this,
                    "Gagal memunculkan kamera.",
                    Toast.LENGTH_SHORT
                ).show()
                Log.e(TAG, "startCamera: ${e.message}")
            }
        }, ContextCompat.getMainExecutor(this))
    }


    private fun takePicture() {
        val imageCapture = imageCapture ?: return
        val photoFile = createCustomTempFile(application)
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    showModalSheet(output.savedUri.toString())
                }
                override fun onError(exc: ImageCaptureException) {
                    Toast.makeText(
                        this@MainActivity,
                        "Gagal mengambil gambar.",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.e(TAG, "onError: ${exc.message}")
                }
            }
        )
    }




    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Log.d(TAG, "Permission request granted")
            } else {
                Log.e(TAG, "Permission request denied")
            }
        }

    private val orientationEventListener by lazy {
        object : OrientationEventListener(this) {
            override fun onOrientationChanged(orientation: Int) {
                if (orientation == ORIENTATION_UNKNOWN) {
                    return
                }
                val rotation = when (orientation) {
                    in 45 until 135 -> Surface.ROTATION_270
                    in 135 until 225 -> Surface.ROTATION_180
                    in 225 until 315 -> Surface.ROTATION_90
                    else -> Surface.ROTATION_0
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        orientationEventListener.enable()
    }

    private fun imageProxyToBitmap(imageProxy: ImageProxy): Bitmap {
        val buffer = imageProxy.planes[0].buffer
        val bytes = ByteArray(buffer.remaining())
        buffer.get(bytes)
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }

    private fun cropImage(original: Bitmap): Bitmap {
        // Get the overlay dimensions relative to the preview
        val previewRatio = activityMainBinding.previewCamera.width.toFloat() / activityMainBinding.previewCamera.height
        val imageRatio = original.width.toFloat() / original.height

        // Calculate crop dimensions
        val cropWidth = original.width * 0.8f  // 80% of width
        val cropHeight = cropWidth / 3.18f     // Maintain aspect ratio shown in image

        // Calculate start points to center the crop
        val startX = (original.width - cropWidth) / 2
        val startY = (original.height - cropHeight) / 2

        return Bitmap.createBitmap(
            original,
            startX.toInt(),
            startY.toInt(),
            cropWidth.toInt(),
            cropHeight.toInt()
        )
    }


    companion object {
        private const val REQUIRED_PERMISSION = android.Manifest.permission.CAMERA
        private const val TAG = "MainActivity"
    }
}