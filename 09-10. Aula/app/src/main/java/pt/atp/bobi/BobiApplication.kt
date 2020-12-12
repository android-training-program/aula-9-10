package pt.atp.bobi

import android.app.Application
import pt.atp.bobi.data.persistence.DogRepository
import pt.atp.bobi.data.persistence.KennelDatabase

class BobiApplication : Application() {

    val database by lazy { KennelDatabase.getDatabase(this) }
    val repository by lazy { DogRepository(database.dogDao()) }

}