package dev.guantel.forageapp

import androidx.lifecycle.*
import dev.guantel.forageapp.data.Forageable
import dev.guantel.forageapp.data.ForageableDao
import kotlinx.coroutines.launch

class ForageableViewModel(

    private val forageableDao: ForageableDao
): ViewModel() {

    val allForageable: LiveData<List<Forageable>> = forageableDao.getForageables().asLiveData()

    fun getForageableById(id: Long): LiveData<Forageable> {
        return forageableDao.getForageable(id).asLiveData()
    }

    fun addForeageable(
        name:String,
        address:String,
        inSeason:Boolean,
        notes:String
    ){
        val newForageable = getNewForeageableEntry(
            name,
            address,
            inSeason,
            notes)
            insertForeageable(newForageable)
    }

    private fun getNewForeageableEntry(
        name:String,
        address:String,
        inSeason:Boolean,
        notes:String
    ): Forageable {
    return Forageable(
        name = name,
        address = address,
        inSeason = inSeason,
        notes = notes
    )
    }
    private fun insertForeageable(forageable: Forageable){

        viewModelScope.launch {
            forageableDao.insert(forageable)
        }

}
    fun updateForeageable(
        id:Long,
        name:String,
        address:String,
        inSeason:Boolean,
        notes:String
    ){
        val updatedForageable = getUpdatedForeageableEntry(
            id,
            name,
            address,
            inSeason,
            notes)
        updateForeageable(updatedForageable)

    }

    private fun updateForeageable(forageable: Forageable) {
    viewModelScope.launch {
        forageableDao.update(forageable)
    }
    }

    private fun getUpdatedForeageableEntry(
        id: Long,
        name:String,
        address:String,
        inSeason:Boolean,
        notes:String

    ):Forageable {
        return Forageable(
            id= id,
            name = name,
            address = address,
            inSeason = inSeason,
            notes = notes)
    }

    fun deleteForeageable(forageable: Forageable){
        viewModelScope.launch {
            forageableDao.delete(forageable)
        }

    }

    fun isValidEntry(name: String, address: String): Boolean {
        return name.isNotBlank() && address.isNotBlank()
    }


}
class ForageableViewModelFactory(private val forageableDao: ForageableDao)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ForageableViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ForageableViewModel(forageableDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}