package com.villadevs.busschedule

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.villadevs.busschedule.adapter.BusStopAdapter
import com.villadevs.busschedule.databinding.FragmentFullScheduleBinding
import com.villadevs.busschedule.viewmodel.BusScheduleViewModel
import com.villadevs.busschedule.viewmodel.BusScheduleViewModelFactory
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


private const val ARG_PARAM1 = "param1"


class FullScheduleFragment : Fragment() {

    private val sharedViewModel: BusScheduleViewModel by activityViewModels {
        BusScheduleViewModelFactory((activity?.application as BusScheduleApplication).database.scheduleDao())
    }

    private var _binding: FragmentFullScheduleBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentFullScheduleBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    @OptIn(DelicateCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.rvRecyclerViewFull
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val busStopAdapter = BusStopAdapter {
            val action = FullScheduleFragmentDirections.actionFullScheduleFragmentToStopScheduleFragment(stopName = it.stopName)
            //view.findNavController().navigate(action)
            this.findNavController().navigate(action)
        }
        recyclerView.adapter = busStopAdapter

        lifecycle.coroutineScope.launch {
            sharedViewModel.fullSchedule().collect() {
                busStopAdapter.submitList(it)
            }
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}