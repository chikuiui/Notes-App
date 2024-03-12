package com.example.notetakingappkotlin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.notetakingappkotlin.MainActivity
import com.example.notetakingappkotlin.R
import com.example.notetakingappkotlin.adapter.NoteRecyclerAdapter
import com.example.notetakingappkotlin.databinding.FragmentNewNoteBinding
import com.example.notetakingappkotlin.room.Note
import com.example.notetakingappkotlin.viewModel.NoteViewModel


class NewNoteFragment : Fragment(R.layout.fragment_new_note) {

    private var _binding : FragmentNewNoteBinding? = null
    private val binding get() = _binding!!

    private lateinit var notesViewModel : NoteViewModel
    private lateinit var mView : View


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentNewNoteBinding.inflate(inflater,container,false)
        activity?.title = "Create a New Note"
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notesViewModel = (activity as MainActivity).noteViewModel
        mView = view
    }


    private fun saveNote(view : View){
        val noteTitle : String = binding.editNoteTitle.text.toString().trim()
        val noteBody : String = binding.editNoteBody.text.toString().trim()

        if(noteTitle.isNotEmpty()){
            val note = Note(0,noteTitle,noteBody)
            notesViewModel.addNote(note)
            Toast.makeText(mView.context,"Note added successfully!",Toast.LENGTH_LONG).show()
            // after adding the note we are going back to home fragment.
            view.findNavController().navigate(R.id.action_newNoteFragment_to_homeFragment)
        }else{
            Toast.makeText(mView.context,"Please enter the note title.",Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.new_note_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.save){
           saveNote(mView)
        }
        return super.onOptionsItemSelected(item)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }









}