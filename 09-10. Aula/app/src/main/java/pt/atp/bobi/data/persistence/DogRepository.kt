package pt.atp.bobi.data.persistence

import androidx.lifecycle.LiveData

class DogRepository(private val dogDao: DogDao) {

    val allDogs : LiveData<List<DogModel>> = dogDao.getAlphabetizedDogs()

    fun insert(dog : DogModel){
        KennelDatabase
            .databaseWriteExecutor
            .execute {
                dogDao.insert(dog)
            }
    }
}