package com.example.privatenotes.database.repository

import com.example.privatenotes.database.db.NotesItemDatabase
import com.example.privatenotes.database.entity.NotesItem

class NotesRepository(
    private val db : NotesItemDatabase
) {
    suspend fun upsert(item: NotesItem) = db.getNotesItemDatabaseDao().upsert(item)

    suspend fun delete(item:NotesItem) = db.getNotesItemDatabaseDao().delete(item)

    suspend fun update(item: NotesItem) = db.getNotesItemDatabaseDao().update(item)

    fun getAllNotesListItem() = db.getNotesItemDatabaseDao().getAllItemNotesList()

}