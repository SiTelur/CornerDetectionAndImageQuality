package com.nbs.cornerdetectiondimagequality.presentation.component

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nbs.cornerdetectiondimagequality.R
import com.nbs.cornerdetectiondimagequality.databinding.FragmentReminderBinding
import com.nbs.cornerdetectiondimagequality.databinding.FragmentResultBinding
import com.nbs.cornerdetectiondimagequality.presentation.ui.camera.MainActivity

class ReminderFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentReminderBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = BottomSheetDialog ( requireContext() )

        binding = FragmentReminderBinding.inflate(layoutInflater)
        bottomSheetDialog.setContentView(binding.root)

        binding.btnTakePhoto.setOnClickListener{ dismiss() }

        return bottomSheetDialog
    }

    companion object {
        const val TAG = "ReminderFragment"
    }
}