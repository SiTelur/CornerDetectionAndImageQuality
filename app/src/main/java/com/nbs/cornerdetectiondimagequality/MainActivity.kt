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
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException

import androidx.camera.core.Preview
import androidx.camera.core.resolutionselector.AspectRatioStrategy
import androidx.camera.core.resolutionselector.ResolutionSelector
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import com.nbs.cornerdetectiondimagequality.databinding.ActivityMainBinding
import com.nbs.cornerdetectiondimagequality.helper.CornerDetectionHelper
import com.nbs.cornerdetectiondimagequality.helper.CornerDetectionHelper.ClassifierListener
import com.nbs.cornerdetectiondimagequality.utils.createCustomTempFile
import org.tensorflow.lite.task.gms.vision.classifier.Classifications
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() , ClassifierListener{
    private lateinit var activityMainBinding: ActivityMainBinding
    private var imageCapture: ImageCapture? = null
    private lateinit var cropOverlay: ImageView

    private lateinit var imageClassifierHelper: CornerDetectionHelper

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

        imageClassifierHelper = CornerDetectionHelper(context = this, imageClassifierListener = this)

        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            val resolutionSelector = ResolutionSelector.Builder()
                .setAspectRatioStrategy(AspectRatioStrategy.RATIO_16_9_FALLBACK_AUTO_STRATEGY)
                .build()
            val imageAnalyzer = ImageAnalysis.Builder()
                .setResolutionSelector(resolutionSelector)
                .setTargetRotation(activityMainBinding.previewCamera.display.rotation)
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888)
                .build()
            imageAnalyzer.setAnalyzer(Executors.newSingleThreadExecutor()) { image ->
                imageClassifierHelper.detect(image)
            }

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

    override fun onError(error: String) {
        runOnUiThread {
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResults(
        results: List<Classifications>?,
        inferenceTime: Long
    ) {
        Log.d(TAG, "onResults: $results and $inferenceTime")
    }


    companion object {
        private const val REQUIRED_PERMISSION = android.Manifest.permission.CAMERA
        private const val TAG = "MainActivity"
    }
}