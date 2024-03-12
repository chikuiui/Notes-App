package com.example.notetakingappkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.notetakingappkotlin.databinding.ActivityMainBinding
import com.example.notetakingappkotlin.room.NoteDatabase
import com.example.notetakingappkotlin.room.NoteRepository
import com.example.notetakingappkotlin.viewModel.NoteViewModel
import com.example.notetakingappkotlin.viewModel.NoteViewModelFactory

class MainActivity : AppCompatActivity() {

    /*
        Key Component For this Project
       -> MVVM Architecture
       -> View Model
       -> Room Database (for local data storage)
       -> Navigation Components
       -> SafeArgs
       -> SearchView
       -> DataBinding
       -> CardView
       -> RecyclerView

     */

    private lateinit var binding : ActivityMainBinding

    lateinit var noteViewModel : NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        setUpViewModel()
    }

    private fun setUpViewModel() {
        val noteRepository = NoteRepository(NoteDatabase(this))
        val factory = NoteViewModelFactory(application,noteRepository)
        noteViewModel = ViewModelProvider(this,factory).get(NoteViewModel::class.java)
    }


}