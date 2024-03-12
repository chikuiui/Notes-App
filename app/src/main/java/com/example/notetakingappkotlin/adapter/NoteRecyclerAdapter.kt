package com.example.notetakingappkotlin.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.notetakingappkotlin.databinding.NoteItemBinding
import com.example.notetakingappkotlin.fragments.HomeFragmentDirections
import com.example.notetakingappkotlin.room.Note
import java.util.*

class NoteRecyclerAdapter : RecyclerView.Adapter<NoteRecyclerAdapter.NoteViewHolder>() {


    /* this is utility class that can calculate the diff b/w two list and output a list of
       update operations that converts the first list into the second one.

       -> It will calculate updates for recyclerView adapter and display things when we need
          to insert,delete,update the recyclerView items it will make operations more-n-more
          fast and handle them smoothly.
     */
    private val differCallback = object : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
                    && oldItem.noteBody == newItem.noteBody
                    && oldItem.noteTitle == newItem.noteTitle
        }

        // if an item is similar to new one then we will return new one == old one
        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }

    /*  Why we use this differ?
        -> since we learned before that in order to use recyclerView we can use onBindView holder
           and make refresh all the visible area on the recyclerView,redraw them,but it cannot
           fire the recycler animation and has many-many problems inside the onBindView
        -> if i use partial refresh like -> adapter.setDataNotifyChange()
        -> This will give you specific item position to refresh but here comes the problem.

           What if you have a whole list you need to refresh?
           Do i have to calculate all the positions you need to refresh? = NO
         ->Thats why we use DiffUtility and we are going to use the async list differ
           * diffUtil item callback
           * async list handle all the data synchronization
       */

    val differ = AsyncListDiffer(this, differCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            NoteItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }


    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currNote = differ.currentList[position]

        holder.binding.textNoteTitle.text = currNote.noteTitle
        holder.binding.textNoteBody.text = currNote.noteBody

        // to generate random color
        val random =  Random()
        val color  = Color.argb(255,random.nextInt(256),random.nextInt(256),random.nextInt(256))
        holder.binding.ibColor.setBackgroundColor(color)

        holder.itemView.setOnClickListener {
            val direction = HomeFragmentDirections.actionHomeFragmentToUpdateNoteFragment(currNote)
            it.findNavController().navigate(direction)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    class NoteViewHolder(val binding: NoteItemBinding) : RecyclerView.ViewHolder(binding.root)

}