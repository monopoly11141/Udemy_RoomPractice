package com.example.udemy_roompractice

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.udemy_roompractice.databinding.ActivityMainBinding
import com.example.udemy_roompractice.db.Subscriber
import com.example.udemy_roompractice.db.SubscriberDatabase
import com.example.udemy_roompractice.db.SubscriberRepository

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var subscriberViewModel: SubscriberViewModel
    private lateinit var adapter : SubscriberRecyclerViewAdapter
    private val TAG = this.javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val subscriberDAO = SubscriberDatabase.getInstance(application).subscriberDAO
        val subscriberRepository = SubscriberRepository(subscriberDAO)
        val subscriberViewModelFactory = SubscriberViewModelFactory(subscriberRepository)
        subscriberViewModel = ViewModelProvider(this, subscriberViewModelFactory).get(SubscriberViewModel::class.java)

        binding.subscriberViewModel = subscriberViewModel
        binding.lifecycleOwner = this

        initSubscriberRecyclerView()

        subscriberViewModel.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let {toastMessage ->
                Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun initSubscriberRecyclerView() {

        binding.rvSubscriber.layoutManager = LinearLayoutManager(this)
        adapter = SubscriberRecyclerViewAdapter({clickedSubscriber : Subscriber -> onSubscriberItemClick(clickedSubscriber)})
        binding.rvSubscriber.adapter = adapter
        displaySubscriberList()

    }

    private fun displaySubscriberList() {

        subscriberViewModel.allSubscribers.observe(this, Observer { subscriberList ->
            Log.i(TAG, subscriberList.toString())
            adapter.setList(subscriberList)
            adapter.notifyDataSetChanged()
        })

    }

    private fun onSubscriberItemClick(subscriber : Subscriber) {
        //Toast.makeText(this, "Subscriber ${subscriber.name} clicked", Toast.LENGTH_SHORT).show()
        subscriberViewModel.initUpdateAndDelete(subscriber)
    }

}