package pt.atp.bobi.data.persistence

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DogDao {

    @Query("SELECT * FROM Dog")
    fun getDogs(): List<Dog>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(dog: Dog)

    @Query("SELECT * FROM dog WHERE id=:id ")
    fun findDogById(id: String): LiveData<Dog>

    @Query("DELETE FROM Dog WHERE id = :id")
    fun deleteById(id: String)

    @Query("DELETE FROM dog")
    fun deleteAll()

}