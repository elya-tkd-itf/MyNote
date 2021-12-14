package com.example.mynote.models.framework

interface NoteModel {
    fun saveNote(name: String, description: String): Boolean
}