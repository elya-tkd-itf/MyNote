package com.example.mynote.main.model

interface NoteModel {
    fun saveNote(name: String, description: String): Boolean
}