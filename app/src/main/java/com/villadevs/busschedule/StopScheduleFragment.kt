package com.villadevs.busschedule

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.villadevs.busschedule.adapter.BusStopAdapter
import com.villadevs.busschedule.databinding.FragmentFullScheduleBinding
import com.villadevs.busschedule.databinding.FragmentStopScheduleBinding
import com.villadevs.busschedule.viewmodel.BusScheduleViewModel
import com.villadevs.busschedule.viewmodel.BusScheduleViewModelFactory
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class StopScheduleFragment : Fragment() {

    private val sharedViewModel: BusScheduleViewModel by activityViewModels {
        BusScheduleViewModelFactory((activity?.application as BusScheduleApplication).database.scheduleDao())
    }

    companion object {
        var STOP_NAME = "stopName"
    }

    private var _binding: FragmentStopScheduleBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView

    //private var param1: String? = null
    private lateinit var stopName: String
    //private var stopName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            //param1 = it.getString(ARG_PARAM1)
            //stopName = it.getString(STOP_NAME)
            stopName = it.getString(STOP_NAME).toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentStopScheduleBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.rvRecyclerViewStop
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val busStopAdapter = BusStopAdapter({})
        recyclerView.adapter = busStopAdapter

        lifecycle.coroutineScope.launch {
            sharedViewModel.scheduleForStopName(stopName).collect() {
                busStopAdapter.submitList(it)
            }
        }


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}