package com.villadevs.busschedule.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.villadevs.busschedule.database.schedule.Schedule
import com.villadevs.busschedule.databinding.ListBusStopItemBinding
import java.text.SimpleDateFormat
import java.util.Date

class BusStopAdapter(val onItemClicked: (Schedule) -> Unit)
    : ListAdapter<Schedule, BusStopAdapter.BusStopViewHolder>(DiffCallback){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusStopViewHolder {
        val viewHolder = BusStopViewHolder(ListBusStopItemBinding.inflate(LayoutInflater.from( parent.context), parent, false))

       /* viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            onItemClicked(getItem(position))
        }*/
        return viewHolder
    }

    override fun onBindViewHolder(holder: BusStopViewHolder, position: Int) {
        //holder.bind(getItem(position))

        val currentStop = getItem(position)
        holder.bind(currentStop)

        holder.itemView.setOnClickListener {
            onItemClicked(currentStop)
        }

    }


    class BusStopViewHolder(private var binding: ListBusStopItemBinding):RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SimpleDateFormat")
        fun bind(schedule: Schedule) {
            binding.tvStopName.text = schedule.stopName
            binding.tvArrivalTime.text = SimpleDateFormat(
                "h:mm a").format(Date(schedule.arrivalTime.toLong() * 1000))
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Schedule>() {
            override fun areItemsTheSame(oldItem: Schedule, newItem: Schedule): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Schedule, newItem: Schedule): Boolean {
                return oldItem == newItem
            }
        }
    }


}