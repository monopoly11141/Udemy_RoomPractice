package com.example.udemy_roompractice.db

import com.example.udemy_roompractice.db.Subscriber
import com.example.udemy_roompractice.db.SubscriberDAO

class SubscriberRepository(private val subscriberDAO : SubscriberDAO) {

    val allSubscribers = subscriberDAO.getAllSubscribers()

    suspend fun insert(subscriber : Subscriber) : Long {
        return subscriberDAO.insertSubscriber(subscriber)
    }

    suspend fun update(subscriber : Subscriber) : Int{
        return subscriberDAO.updateSubscriber(subscriber)
    }

    suspend fun delete(subscriber : Subscriber) {
        subscriberDAO.deleteSubscriber(subscriber)
    }

    suspend fun deleteAll() {
        subscriberDAO.deleteAllSubscribers()
    }

}