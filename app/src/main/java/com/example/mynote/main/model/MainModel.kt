package com.example.mynote.main.model

import android.util.Log

class MainModel : NoteModel {
    override fun saveNote(name: String, description: String): Boolean {
        Log.d(TAG, "saveNote:$name, $description")
        return true
    }

    companion object {
        private const val TAG = "MainModel"
    }
}