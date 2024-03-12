package com.example.notetakingappkotlin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notetakingappkotlin.MainActivity
import com.example.notetakingappkotlin.R
import com.example.notetakingappkotlin.adapter.NoteRecyclerAdapter
import com.example.notetakingappkotlin.databinding.FragmentHomeBinding
import com.example.notetakingappkotlin.room.Note
import com.example.notetakingappkotlin.viewModel.NoteViewModel


class HomeFragment : Fragment(R.layout.fragment_home) , SearchView.OnQueryTextListener{


    private var _binding  : FragmentHomeBinding?  = null
    private val binding get() = _binding!!

    private lateinit var notesViewModel: NoteViewModel
    private lateinit var noteAdapter : NoteRecyclerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notesViewModel = (activity as MainActivity).noteViewModel
        setUpRecyclerView()
        binding.fabAddNote.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_newNoteFragment).apply {  }
        }
    }

    private fun setUpRecyclerView() {
        noteAdapter = NoteRecyclerAdapter()

        binding.recyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            adapter = noteAdapter
        }
        // activity?.let -> only used when activity is not null.
        activity?.let {
            notesViewModel.getAllNotes().observe(
                viewLifecycleOwner
            ) { note ->
                noteAdapter.differ.submitList(note)
                updateUi(note)
            }
        }
    }

    private fun updateUi(note: List<Note>?){
        if (note != null) {
            if(note.isNotEmpty()){
                binding.cardView.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
            }else{
                binding.cardView.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE
            }
        }
    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.home_menu,menu)

        val menuSearch = menu.findItem(R.id.search).actionView as SearchView
        menuSearch.isSubmitButtonEnabled = false
        menuSearch.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchNote(query)
        }
        return false
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if(query != null){
            searchNote(query)
        }
        return true
    }

    private fun searchNote(query : String){
        val searchQuery = "%$query"
        notesViewModel.searchNote(searchQuery).observe(this
        ) { list -> noteAdapter.differ.submitList(list) }
    }

    override fun onDestroy() {
        super.onDestroy()
        // To avoid memory leaks or usage.
        _binding = null
    }



}