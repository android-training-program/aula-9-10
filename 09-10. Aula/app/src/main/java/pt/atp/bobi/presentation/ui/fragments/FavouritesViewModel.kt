package pt.atp.bobi.presentation.ui.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pt.atp.bobi.data.model.Breed
import pt.atp.bobi.data.persistence.Dog
import pt.atp.bobi.data.persistence.DogRepository

private const val TAG = "FavouritesViewModel"

class FavouritesViewModel(
    private val repository: DogRepository
) : ViewModel() {

    private val _dogsViewModel = MutableLiveData<List<Breed>>()
    val dogsLiveData = _dogsViewModel

    fun favBreed(breed: Breed) {
        repository.deleteById(breed.id) {
            updateDogs()
        }
    }

    fun loadDogs() {
        updateDogs()
    }

    private fun updateDogs() {
        repository.getDogs { dogs ->

            dogs.map(::dogToBreed)
                .let {
                    _dogsViewModel.postValue(it)
                }
        }
    }

    private fun dogToBreed(dog: Dog): Breed {
        return Breed(
            bredFor = dog.bredFor,
            bredGroup = dog.bredGroup,
            id = dog.id,
            lifeSpan = dog.lifeSpan,
            name = dog.name,
            origin = dog.origin,
            temperament = dog.temperament,
            height = null,
            weight = null,
            fav = true,
        )
    }
}

class FavouritesViewModelFactory(
    private val repository: DogRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavouritesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavouritesViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}