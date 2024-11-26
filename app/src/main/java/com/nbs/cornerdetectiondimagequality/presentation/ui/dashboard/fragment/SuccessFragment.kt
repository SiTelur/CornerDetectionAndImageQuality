package com.nbs.cornerdetectiondimagequality.presentation.ui.dashboard.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.nbs.cornerdetectiondimagequality.R
import com.nbs.cornerdetectiondimagequality.databinding.FragmentSuccessBinding
import com.nbs.cornerdetectiondimagequality.presentation.component.HistoryAdapter
import com.nbs.cornerdetectiondimagequality.presentation.component.HistoryLoadStateAdapter
import com.nbs.cornerdetectiondimagequality.presentation.viewmodel.DashboardViewModel
import com.nbs.cornerdetectiondimagequality.presentation.viewmodel.ViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SuccessFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SuccessFragment : Fragment() {
    private var _binding : FragmentSuccessBinding? = null
    private val binding get() = _binding!!

    private val historyViewModel by viewModels<DashboardViewModel>{
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSuccessBinding.inflate(layoutInflater,container,false)
        return binding.root
    }
    val adapter = HistoryAdapter()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewSuccess.adapter = adapter.withLoadStateFooter(footer = HistoryLoadStateAdapter())
        binding.recyclerViewSuccess.layoutManager = LinearLayoutManager(requireContext())

       lifecycleScope.launch{
           historyViewModel.getSuccessHistory().collectLatest {  data ->
               adapter.submitData(data)
           }
       }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}