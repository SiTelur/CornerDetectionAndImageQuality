package com.nbs.cornerdetectiondimagequality

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nbs.cornerdetectiondimagequality.databinding.FragmentResultBinding

class ResultFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentResultBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog: BottomSheetDialog =
            BottomSheetDialog(
                requireContext()
            )

        binding = FragmentResultBinding.inflate(layoutInflater)
        bottomSheetDialog.setContentView(binding.root)

        val data =  arguments?.getString(IMAGE)
        binding.imageView.setImageURI(Uri.parse(data))

        binding.btnRetake.setOnClickListener{
            dismiss()
        }
        return bottomSheetDialog
    }


    companion object {
        const val TAG = "ResultFragment"
        const val IMAGE = "ImageResultImage"
    }
}