package com.example.udemy_roompractice

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.udemy_roompractice.db.Subscriber
import com.example.udemy_roompractice.db.SubscriberRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SubscriberViewModel(private val subscriberRepository: SubscriberRepository) : ViewModel() {


    val allSubscribers = subscriberRepository.allSubscribers

    val etSubscriberName = MutableLiveData<String>()
    val etSubscriberEmail = MutableLiveData<String>()

    val btnSaveOrUpdateText = MutableLiveData<String>()
    val btnClearAllOrDeleteText = MutableLiveData<String>()

    init {
        btnSaveOrUpdateText.value = "Save"
        btnClearAllOrDeleteText.value = "Clear"
    }

    fun onBtnSaveOrUpdateClicked() {

        val name = etSubscriberName.value!!
        val email = etSubscriberEmail.value!!

        val thisSubscriber = Subscriber(0, name, email)
        insert(thisSubscriber)

        etSubscriberName.value = ""
        etSubscriberEmail.value = ""

    }

    fun onBtnClearAllOrDeleteClicked() {
        clearAll()
    }

    fun insert(subscriber: Subscriber) {
        viewModelScope.launch(Dispatchers.IO) {
            subscriberRepository.insert(subscriber)
        }
    }

    fun update(subscriber: Subscriber) {
        viewModelScope.launch(Dispatchers.IO) {
            subscriberRepository.update(subscriber)
        }
    }

    fun delete(subscriber: Subscriber) {
        viewModelScope.launch(Dispatchers.IO) {
            subscriberRepository.delete(subscriber)
        }
    }

    fun clearAll() {
        viewModelScope.launch(Dispatchers.IO) {
            subscriberRepository.deleteAll()
        }
    }
}