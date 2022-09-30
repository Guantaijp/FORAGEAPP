package dev.guantel.forageapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Forageable::class], version = 2, exportSchema = false)

abstract class ForageDatabase : RoomDatabase(){
    abstract fun forageableDao(): ForageableDao


    companion object{
        private var INSTANCE: ForageDatabase? = null

        fun getDatabase(context:Context): ForageDatabase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ForageDatabase::class.java,
                    "forageable_database"
                ) .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}