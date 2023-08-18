package com.example.udemy_roompractice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.udemy_roompractice.databinding.SubscriberListItemBinding
import com.example.udemy_roompractice.db.Subscriber

class SubscriberRecyclerViewAdapter(private val onClickListener : (Subscriber) -> Unit) : RecyclerView.Adapter<SubscriberViewHolder>(){

    private val subscribers = ArrayList<Subscriber>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubscriberViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding : SubscriberListItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.subscriber_list_item, parent,false)

        return SubscriberViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return subscribers.size
    }

    override fun onBindViewHolder(holder: SubscriberViewHolder, position: Int) {
        holder.bindSubscriber(subscribers[position], onClickListener)
    }

    fun setList(subscribersParam : List<Subscriber>) {
        subscribers.clear()
        subscribers.addAll(subscribersParam)
    }

}

class SubscriberViewHolder(val binding: SubscriberListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindSubscriber(subscriber: Subscriber, onClickListener : (Subscriber) -> Unit) {
        binding.tvSubscriberName.text = subscriber.name
        binding.tvSubscriberEmail.text = subscriber.email

        binding.clSubscriberListItem.setOnClickListener {
            onClickListener(subscriber)
        }

    }

}