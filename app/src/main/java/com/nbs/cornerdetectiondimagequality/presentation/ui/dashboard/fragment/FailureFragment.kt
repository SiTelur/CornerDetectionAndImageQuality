package com.nbs.cornerdetectiondimagequality.presentation.ui.dashboard.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.nbs.cornerdetectiondimagequality.R
import com.nbs.cornerdetectiondimagequality.databinding.FragmentFailureBinding
import com.nbs.cornerdetectiondimagequality.presentation.component.HistoryAdapter
import com.nbs.cornerdetectiondimagequality.presentation.component.HistoryLoadStateAdapter
import com.nbs.cornerdetectiondimagequality.presentation.viewmodel.DashboardViewModel
import com.nbs.cornerdetectiondimagequality.presentation.viewmodel.ViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class FailureFragment : Fragment() {
    private var _binding : FragmentFailureBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<DashboardViewModel>{
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFailureBinding.inflate(layoutInflater,container,false    )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = HistoryAdapter()
        binding.recyclerViewFailed.adapter = adapter.withLoadStateFooter(footer = HistoryLoadStateAdapter())
        binding.recyclerViewFailed.layoutManager = LinearLayoutManager(requireContext())

        lifecycleScope.launch{
            viewModel.getFailureHistory.collectLatest {  data ->
                adapter.submitData(data)
            }
        }
    }

}