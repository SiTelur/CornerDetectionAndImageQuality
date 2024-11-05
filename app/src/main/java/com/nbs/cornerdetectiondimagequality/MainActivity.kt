package com.nbs.cornerdetectiondimagequality

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.OrientationEventListener
import android.view.Surface
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.nbs.cornerdetectiondimagequality.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        if (!allPermissionsGranted()){
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }else {
            startCamera()
        }

        activityMainBinding.cameraButton.setOnClickListener{
            showModalSheet()
        }
    }

    private fun showModalSheet() {
        val modalBottomSheet = ResultFragment()
        modalBottomSheet.show(supportFragmentManager, ResultFragment.TAG)
    }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            this,
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    private var cameraSelector : CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
       cameraProviderFuture.addListener({
           val cameraProvider = cameraProviderFuture.get()
           val preview = Preview.Builder()
               .build()
               .also {
                   it.surfaceProvider = activityMainBinding.previewCamera.surfaceProvider
               }
           try {
               cameraProvider.unbindAll()
               cameraProvider.bindToLifecycle(this,cameraSelector,preview)

           } catch (e : Exception){
               Toast.makeText(
                   this,
                   "Gagal memunculkan kamera.",
                   Toast.LENGTH_SHORT
               ).show()
               Log.e(TAG, "startCamera: ${e.message}")
           }
       }, ContextCompat.getMainExecutor(this))
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

    companion object {
        private const val REQUIRED_PERMISSION = android.Manifest.permission.CAMERA
        private const val TAG = "MainActivity"
    }
}