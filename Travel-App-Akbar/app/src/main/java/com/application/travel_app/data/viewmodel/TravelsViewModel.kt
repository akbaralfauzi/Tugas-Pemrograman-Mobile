package com.application.travel_app.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.application.travel_app.data.database.Travel
import com.application.travel_app.data.database.TravelDao
import com.application.travel_app.data.database.TravelDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class TravelsViewModel(application: Application) : AndroidViewModel(application) {

    private val dao: TravelDao
    private val _allTravels = MutableLiveData<List<Travel>>()
    private val _getTravel = MutableLiveData<Travel>()

    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    val allTravels: LiveData<List<Travel>> = _allTravels
    val getTravel: LiveData<Travel> = _getTravel

    init {
        dao = TravelDatabase.getInstance(application).travelsDao()
    }

    fun getTravels() {
        viewModelScope.launch(Dispatchers.IO) {
            _allTravels.postValue(dao.getAllTravels())
        }
    }

    fun getTravel(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _getTravel.postValue(dao.getTravel(id))
        }
    }

    fun addTravel(travel: Travel) {
        viewModelScope.launch(Dispatchers.IO) {
            executorService.execute {dao.insertTravel(travel)}
        }
        getTravels()
    }

    fun deleteTravel(travel: Travel) {
        viewModelScope.launch(Dispatchers.IO) {
            executorService.execute {dao.deleteTravel(travel)}
        }
        getTravels()
    }

    fun updateTravel(id: Int, donaturName: String, tglDonasi: String, category: String, amount: String) {
        viewModelScope.launch(Dispatchers.IO) {
            executorService.execute {
                dao.updateTravel(id, donaturName, tglDonasi, category, amount)
            }
        }
        getTravels()
    }
}