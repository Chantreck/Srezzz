package com.chantreck.srez

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chantreck.srez.databinding.ItemTaskBinding
import java.time.Instant

class RecyclerAdapter : ListAdapter<Task, RecyclerAdapter.ViewHolder>(DIFF) {
    private companion object {
        val DIFF = object : DiffUtil.ItemCallback<Task>() {
            override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemTaskBinding.bind(view)

        fun bind(task: Task) {
            when (task.status.id) {
                1 -> {
                    binding.root.setBackgroundResource(R.drawable.yellow)
                    binding.status.text = "Not completed yet"
                }
                2 -> {
                    binding.root.setBackgroundResource(R.drawable.red)
                    binding.status.text = "Not completed yet"
                }
                else -> {
                    binding.root.setBackgroundResource(R.drawable.green)
                    binding.status.text = "Completed in ${task.actual_duration} minutes"
                }
            }

            binding.title.text = task.title
            binding.time.text = "${task.estimated_duration} min."
            binding.deadline.text = task.deadline
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = getItem(position)
        holder.bind(task)
    }
}