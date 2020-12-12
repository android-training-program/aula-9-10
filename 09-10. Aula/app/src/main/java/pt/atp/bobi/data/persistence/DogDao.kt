package pt.atp.bobi.data.persistence

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DogDao {

    @Query("SELECT * FROM Dog ORDER BY name ASC")
    fun getAlphabetizedDogs(): LiveData<List<DogModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(dog: DogModel)

    @Query("DELETE FROM dog")
    fun deleteAll()

}