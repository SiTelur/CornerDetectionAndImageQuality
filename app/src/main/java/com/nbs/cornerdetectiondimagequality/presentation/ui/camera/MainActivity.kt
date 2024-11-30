package com.nbs.cornerdetectiondimagequality.presentation.ui.camera

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException

import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.nbs.cornerdetectiondimagequality.R
import com.nbs.cornerdetectiondimagequality.data.local.entity.HistoryActivity
import com.nbs.cornerdetectiondimagequality.databinding.ActivityMainBinding
import com.nbs.cornerdetectiondimagequality.helper.CornerDetectionHelper
import com.nbs.cornerdetectiondimagequality.helper.CornerDetectionHelper.ClassifierListener
import com.nbs.cornerdetectiondimagequality.presentation.component.ReminderFragment
import com.nbs.cornerdetectiondimagequality.presentation.component.ResultFragment
import com.nbs.cornerdetectiondimagequality.presentation.viewmodel.CameraViewModel
import com.nbs.cornerdetectiondimagequality.presentation.viewmodel.ViewModelFactory
import com.nbs.cornerdetectiondimagequality.utils.createCustomTempFile
import com.nbs.cornerdetectiondimagequality.utils.toBitmap
import org.tensorflow.lite.task.gms.vision.classifier.Classifications
import java.time.LocalDateTime

class MainActivity : AppCompatActivity() , ClassifierListener{
    private lateinit var activityMainBinding: ActivityMainBinding
    private var imageCapture: ImageCapture? = null
    private lateinit var cropOverlay: ImageView

    private lateinit var cornerDetectionHelper: CornerDetectionHelper

    private val cameraViewModel by viewModels<CameraViewModel>{
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        showModelReminder()

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
            putString(ResultFragment.Companion.IMAGE, image)
        }
        modalBottomSheet.arguments = bundle
        modalBottomSheet.show(supportFragmentManager, ResultFragment.TAG)
    }

    private fun showModelReminder() {
        val reminderModel = ReminderFragment()
        reminderModel.show(supportFragmentManager, ReminderFragment.TAG)
    }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            this,
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

    private fun startCamera() {

        cornerDetectionHelper = CornerDetectionHelper(context = this, imageClassifierListener = this)

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
                cameraProvider.bindToLifecycle(this, cameraSelector, preview,imageCapture)

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
                    cornerDetectionHelper.detectCorner(uri = output.savedUri)
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
                startCamera()
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
        inferenceTime: Long,
        uri: Uri?
    ) {
     runOnUiThread{
         val data = HistoryActivity(title = "Pendeteksian", pictureUri = uri.toString(), timestamp = LocalDateTime.now(), isSuccess = true )
         cameraViewModel.insertData(data)
         Log.d(TAG, "onResults: $data")
     }
    }


    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
        private const val TAG = "MainActivity"
    }
}