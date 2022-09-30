package dev.guantel.forageapp.data


import androidx.room.*
import dev.guantel.forageapp.data.Forageable
import kotlinx.coroutines.flow.Flow

@Dao
interface ForageableDao {

    @Query("SELECT * FROM forageable_database")
    fun getForageables(): Flow<List<Forageable>>

    @Query("SELECT * FROM forageable_database WHERE id = :id")
    fun getForageable(id: Long): Flow<Forageable>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(forageable: Forageable)

    @Update
    suspend fun update(forageable: Forageable)

    @Delete
    suspend fun delete(forageable: Forageable)
}