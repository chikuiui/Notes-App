package com.example.notetakingappkotlin.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


/*
  NOTE -> This is just a template code to create database and i will learn perfectly on coroutines and multithreading.

  Volatile -> this annotation will marks the JVM backing field of the annotated property as volatile
           -> Rights to this field are immediately visible to other threads like any change to INSTANCE
              will immediately visible to other threads.

  Any() -> it is a function which added as extension to iterable and map interfaces which take a higher order
           function as param to predicate the condition and return boolean as true
        -> If any of the items in the list,set,map confirms that condition otherwise it will return false.

  synchronized -> used when we deal with multithreading and prevent unsynchronized data b/w threads
               -> It ensure we have only one instance of this database called NoteDatabase.


 */


@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase(){

    abstract fun getNoteDao() : NoteDAO

    // this ine works on prev. project but in this project i will pass database as parameter not dao
//    abstract val noteDAO : NoteDAO

    companion object{
        @Volatile
        private var INSTANCE : NoteDatabase ?= null



        operator fun invoke(context : Context) = INSTANCE ?:
            synchronized(Any()){
                INSTANCE ?: createDatabase(context).also{
                    INSTANCE = it
                }
            }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,NoteDatabase::class.java,"note_db").build()
    }
}