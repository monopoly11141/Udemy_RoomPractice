package com.example.udemy_roompractice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.udemy_roompractice.db.Subscriber
import com.example.udemy_roompractice.db.SubscriberRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SubscriberViewModel(private val subscriberRepository: SubscriberRepository) : ViewModel() {


    val allSubscribers = subscriberRepository.allSubscribers
    private var isUpdateOrDelete = false
    private lateinit var subscriberToUpdateOrDelete: Subscriber

    val etSubscriberName = MutableLiveData<String>()
    val etSubscriberEmail = MutableLiveData<String>()

    val btnSaveOrUpdateText = MutableLiveData<String>()
    val btnClearAllOrDeleteText = MutableLiveData<String>()

    private val statusMessage= MutableLiveData<Event<String>>()
    val message : LiveData<Event<String>>
        get() = statusMessage

    init {
        btnSaveOrUpdateText.value = "Save"
        btnClearAllOrDeleteText.value = "Clear"
    }

    fun onBtnSaveOrUpdateClicked() {

        if (isUpdateOrDelete) {
            subscriberToUpdateOrDelete.name = etSubscriberName.value!!
            subscriberToUpdateOrDelete.email = etSubscriberEmail.value!!
            update(subscriberToUpdateOrDelete)
        } else {
            val name = etSubscriberName.value!!
            val email = etSubscriberEmail.value!!

            val thisSubscriber = Subscriber(0, name, email)
            insert(thisSubscriber)

            etSubscriberName.value = ""
            etSubscriberEmail.value = ""
        }


    }

    fun onBtnClearAllOrDeleteClicked() {
        if (isUpdateOrDelete) {
            delete(subscriberToUpdateOrDelete)
        } else {
            clearAll()
        }
    }

    private fun insert(subscriber: Subscriber) {
        viewModelScope.launch(Dispatchers.IO) {
            val insertedRowId = subscriberRepository.insert(subscriber)

            withContext(Dispatchers.Main) {
                if(insertedRowId > -1) {
                    statusMessage.value = Event("Subscriber Inserted successfully at row $insertedRowId")
                } else {
                    statusMessage.value = Event("Error Occurred during Insert!")
                }
            }
        }
    }

    private fun update(subscriber: Subscriber) {
        viewModelScope.launch(Dispatchers.IO) {
            val updatedRowCount = subscriberRepository.update(subscriber)

            withContext(Dispatchers.Main) {
                if(updatedRowCount > 0) {
                    etSubscriberName.value = ""
                    etSubscriberEmail.value = ""

                    isUpdateOrDelete = false

                    btnSaveOrUpdateText.value = "Save"
                    btnClearAllOrDeleteText.value = "Clear All"

                    statusMessage.value = Event("Subscriber Updated successfully in $updatedRowCount rows.")
                }else {
                    statusMessage.value = Event("Error Occurred during Update!")
                }
            }
        }
    }

    fun delete(subscriber: Subscriber) {
        viewModelScope.launch(Dispatchers.IO) {
            subscriberRepository.delete(subscriber)

            withContext(Dispatchers.Main) {
                etSubscriberName.value = ""
                etSubscriberEmail.value = ""

                isUpdateOrDelete = false

                btnSaveOrUpdateText.value = "Save"
                btnClearAllOrDeleteText.value = "Clear All"

                statusMessage.value = Event("Subscriber Deleted successfully.")
            }
        }
    }

    fun clearAll() {
        viewModelScope.launch(Dispatchers.IO) {
            subscriberRepository.deleteAll()

            withContext(Dispatchers.Main) {
                statusMessage.value = Event("All Subscribers Deleted successfully.")
            }
        }
    }

    fun initUpdateAndDelete(subscriber: Subscriber) {
        etSubscriberName.value = subscriber.name
        etSubscriberEmail.value = subscriber.email

        isUpdateOrDelete = true
        subscriberToUpdateOrDelete = subscriber

        btnSaveOrUpdateText.value = "Update"
        btnClearAllOrDeleteText.value = "Delete"
    }
}