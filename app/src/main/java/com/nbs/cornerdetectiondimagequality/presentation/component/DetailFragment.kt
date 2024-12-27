package com.nbs.cornerdetectiondimagequality.presentation.component

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.nbs.cornerdetectiondimagequality.data.local.entity.HistoryEntity
import com.nbs.cornerdetectiondimagequality.databinding.FragmentDetailBinding
import com.nbs.cornerdetectiondimagequality.presentation.component.adapter.HistoryAdapter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val status = arguments?.getString(ARG_STATUS)
        val score = arguments?.getDouble(ARG_SCORE)?.times(100)?.toInt()
        val timestamp = arguments?.getString(ARG_DATE)
        val imageUri = arguments?.getString(ARG_PICTURE_URI)?.toUri()

        binding.apply {
            detailImgResult.setImageURI(imageUri)
            detailTvStatus.text = status?.trimIndent()
            detailTvScore.text = "${score.toString().trimIndent()}%"
            detailTvTimestamp.text = timestamp?.trimIndent()
        }
    }

    companion object {
        private const val ARG_STATUS = "status"
        private const val ARG_SCORE = "score"
        private const val ARG_DATE = "date"
        private const val ARG_PICTURE_URI = "pictureUri"

        fun newInstance(activity: HistoryEntity): DetailFragment {
            val fragment = DetailFragment()
            val bundle = Bundle().apply {
                putString(ARG_STATUS, activity.title)
                putDouble(ARG_SCORE, activity.score)
                putString(ARG_DATE, DateToString(activity.timestamp))
                putString(ARG_PICTURE_URI, activity.pictureUri)
            }
            fragment.arguments = bundle
            return fragment
        }

        private fun DateToString(localDateTime: LocalDateTime): String {
            val formatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy", Locale.getDefault())
            return localDateTime.format(formatter)
        }
    }

}