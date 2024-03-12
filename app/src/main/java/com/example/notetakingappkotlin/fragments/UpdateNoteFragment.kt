package com.example.notetakingappkotlin.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notetakingappkotlin.MainActivity
import com.example.notetakingappkotlin.R
import com.example.notetakingappkotlin.adapter.NoteRecyclerAdapter
import com.example.notetakingappkotlin.databinding.FragmentUpdateNoteBinding
import com.example.notetakingappkotlin.room.Note
import com.example.notetakingappkotlin.viewModel.NoteViewModel


class UpdateNoteFragment : Fragment(R.layout.fragment_update_note) {

    private var _binding : FragmentUpdateNoteBinding? = null
    private val binding get() = _binding!!


    private lateinit var notesViewModel : NoteViewModel
    private lateinit var currNote : Note
    private val args : UpdateNoteFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentUpdateNoteBinding.inflate(inflater,container,false)
        activity?.title = "Update a Note"
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notesViewModel = (activity as MainActivity).noteViewModel
        currNote = args.note!!

        binding.editNoteTitleUpdate.setText(currNote.noteTitle)
        binding.editNoteBodyUpdate.setText(currNote.noteBody)

        binding.fabDone.setOnClickListener {
            val title : String = binding.editNoteTitleUpdate.text.toString().trim()
            val body : String = binding.editNoteBodyUpdate.text.toString().trim()

            if(title.isNotEmpty()){
                val note = Note(currNote.id,title,body)
                notesViewModel.updateNote(note)
                view.findNavController().navigate(R.id.action_updateNoteFragment_to_homeFragment)
            }else{
                Toast.makeText(context,"Please enter the note title.",Toast.LENGTH_LONG).show()
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    private fun deleteNote(){
        AlertDialog.Builder(activity).apply {
            setTitle("Delete a Note")
            setMessage("You want to delete this Note?")
            setPositiveButton("Delete"){_,_ ->
                notesViewModel.deleteNote(currNote)
                view?.findNavController()?.navigate(R.id.action_updateNoteFragment_to_homeFragment)
            }
            setNegativeButton("Cancel",null)
        }.create().show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.update_note_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.delete){
            deleteNote()
        }
        return super.onOptionsItemSelected(item)
    }





}