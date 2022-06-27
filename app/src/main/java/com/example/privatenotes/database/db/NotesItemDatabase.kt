package com.example.privatenotes.database.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.privatenotes.database.entity.NotesItem



@Database(
    entities = [NotesItem::class] ,
    version = 1
)
abstract class NotesItemDatabase : RoomDatabase() {

    abstract fun getNotesItemDatabaseDao() : NotesItemDatabaseDao

    companion object{

        @Volatile
        private var instance : NotesItemDatabase? = null
        private var lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock){
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context:Context) =
            Room.databaseBuilder(context.applicationContext ,
            NotesItemDatabase::class.java ,
            "NotesItemDatabase.db").build()
    }

}