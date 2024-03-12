package com.example.notetakingappkotlin.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notetakingappkotlin.room.Note
import com.example.notetakingappkotlin.room.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(app: Application, private val noteRepository: NoteRepository) :
    AndroidViewModel(app) {

        // The below three fun are coroutines and launch on background thread.
        fun addNote(note : Note) = viewModelScope.launch {
            noteRepository.insertNote(note)
        }

        fun updateNote(note : Note) = viewModelScope.launch {
            noteRepository.updateNote(note)
        }

        fun deleteNote(note : Note) = viewModelScope.launch {
            noteRepository.deleteNote(note)
        }

        fun getAllNotes() = noteRepository.getAllNotes()
        fun searchNote(query : String?) = noteRepository.searchNote(query)
}