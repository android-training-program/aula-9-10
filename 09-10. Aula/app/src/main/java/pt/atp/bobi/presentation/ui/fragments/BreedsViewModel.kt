package pt.atp.bobi.presentation.ui.fragments

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pt.atp.bobi.data.DogsAPIClient
import pt.atp.bobi.data.cb.DataRetrieved
import pt.atp.bobi.data.model.Breed
import pt.atp.bobi.data.persistence.DogModel
import pt.atp.bobi.data.persistence.DogRepository

private const val TAG = "BreedsViewModel"

class BreedsViewModel(
    private val repository: DogRepository
) : ViewModel(), DataRetrieved {

    private val _dogsViewModel = MutableLiveData<List<Breed>>()
    val dogsLiveData = _dogsViewModel

    init {
        repository.insert(DogModel("breafor", "group", 1, 50, "Fifi", "desconhecida", "medio"))
        repository.insert(DogModel("breafor", "group", 2, 50, "Kiko", "desconhecida", "medio"))
        repository.insert(DogModel("breafor", "group", 3, 51, "Luna", "desconhecida", "medio"))
    }

    fun loadDogs() {
        DogsAPIClient.getListOfBreeds(this)
    }

    fun loadDogsDatabase(): LiveData<List<DogModel>> {
        return repository.allDogs
    }

    //region DataRetrieved

    override fun onDataFetchedSuccess(breeds: List<Breed>) {
        Log.d(TAG, "onDataFetched Success | ${breeds.size} new breeds")
        _dogsViewModel.postValue(breeds)
    }

    override fun onDataFetchedFailed() {
        Log.e(TAG, "Unable to retrieve new data")
        _dogsViewModel.postValue(emptyList())
    }

    //endregion DataRetrieved
}

class BreedsViewModelFactory(
    private val repository: DogRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BreedsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BreedsViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}