package com.nbs.cornerdetectiondimagequality.presentation.ui.dashboard.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.nbs.cornerdetectiondimagequality.R
import com.nbs.cornerdetectiondimagequality.databinding.FragmentAllBinding
import com.nbs.cornerdetectiondimagequality.presentation.component.HistoryAdapter
import com.nbs.cornerdetectiondimagequality.presentation.component.HistoryLoadStateAdapter
import com.nbs.cornerdetectiondimagequality.presentation.viewmodel.DashboardViewModel
import com.nbs.cornerdetectiondimagequality.presentation.viewmodel.ViewModelFactory

class AllFragment : Fragment() {

    private var _binding :  FragmentAllBinding? = null
    private val binding get() = _binding!!

    private val historyViewModel by viewModels<DashboardViewModel>{
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = HistoryAdapter()
        binding.recyclerViewAll.adapter = adapter.withLoadStateFooter(footer = HistoryLoadStateAdapter())
        binding.recyclerViewAll.layoutManager = LinearLayoutManager(requireContext())

        historyViewModel.getAllHistory.observe(viewLifecycleOwner){ data ->
            adapter.submitData(lifecycle,data)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}