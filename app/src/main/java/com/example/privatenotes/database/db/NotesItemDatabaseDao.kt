package com.example.privatenotes.database.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.privatenotes.database.entity.NotesItem


@Dao
interface NotesItemDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(item:NotesItem)

    @Update
    fun update(item:NotesItem)

    @Delete
    fun delete(item:NotesItem)

    @Query("SELECT * From NotesDatabaseTable")
    fun getAllItemNotesList() : LiveData<List<NotesItem>>



}