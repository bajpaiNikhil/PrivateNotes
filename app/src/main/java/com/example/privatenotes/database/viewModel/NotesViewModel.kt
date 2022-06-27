package com.example.privatenotes.database.viewModel

import androidx.lifecycle.ViewModel
import com.example.privatenotes.database.entity.NotesItem
import com.example.privatenotes.database.repository.NotesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel(private val repository: NotesRepository) : ViewModel() {

    fun upsert(item:NotesItem) = CoroutineScope(Dispatchers.IO).launch {
        repository.upsert(item)
    }

    fun delete(item:NotesItem) = CoroutineScope(Dispatchers.IO).launch {
        repository.delete(item)
    }

    fun update(item:NotesItem) = CoroutineScope(Dispatchers.IO).launch {
        repository.update(item)
    }

    fun getAllNotesListItem() = repository.getAllNotesListItem()

}