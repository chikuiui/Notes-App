package com.example.notetakingappkotlin.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notetakingappkotlin.room.NoteRepository

class NoteViewModelFactory(val app : Application,private val noteRepository: NoteRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if(modelClass.isAssignableFrom(NoteViewModel::class.java)){
//            return NoteViewModel(app,noteRepository) as T
//        }
//        throw IllegalArgumentException("Unknown View Model class")
          return NoteViewModel(app,noteRepository) as T
    }
}