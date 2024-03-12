package com.example.notetakingappkotlin.room

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "notes")
@Parcelize
data class Note(
    @PrimaryKey(autoGenerate = true) val id : Int,
    val noteTitle : String,
    val noteBody : String
) : Parcelable


/*  what is Parcelable ?
   -> its an interface that a class can implement to be passed within an intent from an activity
   -> This way, transporting data from one activity to another one.
   -> By Default parcelable override two functions but we don't need it thanks to kotlin by annotating with @Parcelize
   @Parcelize -> we can directly access methods without overriding them.
 */