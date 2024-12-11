package com.nbs.cornerdetectiondimagequality.presentation.ui.detail

import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.nbs.cornerdetectiondimagequality.R
import com.nbs.cornerdetectiondimagequality.data.local.entity.HistoryEntity
import com.nbs.cornerdetectiondimagequality.databinding.ActivityDetailBinding
import com.nbs.cornerdetectiondimagequality.presentation.viewmodel.DashboardViewModel
import com.nbs.cornerdetectiondimagequality.presentation.viewmodel.ViewModelFactory
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel by viewModels<DashboardViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra("id", 0)
        supportActionBar?.title = getString(R.string.detail_action_bar_title)
        viewModel.detailHistory.observe(this) { history ->
            val manufacturer = Build.MANUFACTURER
            val model = Build.MODEL
            val device = "$manufacturer $model"
            val score = history.score.times(100).toInt()

            binding.apply {
                showIcon(history.isSuccess)
                detailTvStatus.text = getString(R.string.detail_status, history.title)
                detailTvScore.text = getString(R.string.detail_threshold, score.toString())
                detailTvTimestamp.text = DateToString(history.timestamp)
                detailTvDevice.text = device
                detailImgResult.setImageURI(history.pictureUri.toUri())
            }
        }

        viewModel.getDetailHistory(id)
    }

    private fun DateToString(localDateTime: LocalDateTime): String {
        val formatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy", Locale.getDefault())
        return localDateTime.format(formatter)
    }

    private fun showIcon(status: Boolean) {
        binding.apply {
            if (status) {
                binding.imageView8.setImageDrawable(
                    ContextCompat.getDrawable(
                        this@DetailActivity,
                        R.drawable.success_icon
                    )
                )
            } else {
                binding.imageView8.setImageDrawable(
                    ContextCompat.getDrawable(
                        this@DetailActivity,
                        R.drawable.failed_icon
                    )
                )
            }
        }
    }
}