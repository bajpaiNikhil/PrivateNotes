package com.example.privatenotes.database.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "NotesDatabaseTable")
data class NotesItem (

        @ColumnInfo(name = "NotesTitle")
        val notesTitle : String ,

        @ColumnInfo(name = "NotesDescription")
        val notesDescription : String ,

        @ColumnInfo(name = "NotesCreatedAt")
        val notesCreatedAt : String ,

        @ColumnInfo(name ="NotesUpdatedAt")
        val notesUpdatedAt : String
) : Parcelable {
        @IgnoredOnParcel
        @PrimaryKey(autoGenerate = true)
        var notesPk : Int? = null
}