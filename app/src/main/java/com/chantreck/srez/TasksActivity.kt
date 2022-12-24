package com.chantreck.srez

import android.app.AlertDialog
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chantreck.srez.Network.BASE_URL
import com.chantreck.srez.databinding.ActivityTasksBinding
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TasksActivity : AppCompatActivity(R.layout.activity_tasks) {
    private val binding by lazy { ActivityTasksBinding.inflate(layoutInflater) }
    private val api = Network.retrofit.create(RequestInterface::class.java)
    private var tasks: MutableList<Task> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.inProgressRecycler.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        binding.newRecycler.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        binding.completedRecycler.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)

        updateAvatar()
        setRecyclers()
    }

    private fun updateAvatar() {
        api.getUserInfo().enqueue(object : Callback<UserInfoResponse> {
            override fun onResponse(
                call: Call<UserInfoResponse>,
                response: Response<UserInfoResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        val avatar = BASE_URL + it.data.avatar
                        Glide.with(this@TasksActivity).load(avatar).circleCrop().into(binding.avatar)
                    }
                } else {
                    showDialog("Something went wrong")
                }
            }

            override fun onFailure(call: Call<UserInfoResponse>, t: Throwable) {
                showDialog("Something went wrong")
            }
        })
    }

    private fun setRecyclers() {
        api.getTasks().enqueue(object : Callback<TaskResponse> {
            override fun onResponse(
                call: Call<TaskResponse>,
                response: Response<TaskResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        tasks.addAll(it.data)
                        initRecyclers()
                    }
                } else {
                    showDialog("Something went wrong")
                }
            }

            override fun onFailure(call: Call<TaskResponse>, t: Throwable) {
                showDialog("Something went wrong")
            }
        })
    }

    private fun initRecyclers() {
        val inProgressAdapter = RecyclerAdapter()
        binding.inProgressRecycler.adapter = inProgressAdapter
        inProgressAdapter.submitList(tasks.filter { it.status_id == 2 })

        val newAdapter = RecyclerAdapter()
        binding.newRecycler.adapter = newAdapter
        newAdapter.submitList(tasks.filter { it.status_id == 1 })

        val completedAdapter = RecyclerAdapter()
        binding.completedRecycler.adapter = completedAdapter
        completedAdapter.submitList(tasks.filter { it.status_id == 3 })
    }

    private fun showDialog(text: String) {
        AlertDialog.Builder(this).apply {
            setTitle(text)
            setPositiveButton("OK", null)
        }.show()
    }
}