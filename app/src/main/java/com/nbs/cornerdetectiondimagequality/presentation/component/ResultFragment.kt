package com.nbs.cornerdetectiondimagequality.presentation.component

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
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