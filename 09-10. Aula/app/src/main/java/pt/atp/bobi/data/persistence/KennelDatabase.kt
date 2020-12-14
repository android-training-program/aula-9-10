package pt.atp.bobi.data.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Database(entities = [Dog::class], version = 1, exportSchema = false)
abstract class KennelDatabase : RoomDatabase() {

    abstract fun dogDao(): DogDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: KennelDatabase? = null

        fun getDatabase(context: Context): KennelDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    KennelDatabase::class.java,
                    "dog_database"
                ).build()

                INSTANCE = instance

                // return instance
                instance
            }
        }

        val databaseWriteExecutor: ExecutorService = Executors.newFixedThreadPool(2)
    }
}