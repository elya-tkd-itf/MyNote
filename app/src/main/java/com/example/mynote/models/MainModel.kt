package com.example.mynote.models

import android.util.Log
import com.example.mynote.models.framework.NoteModel

class MainModel : NoteModel {
    override fun saveNote(name: String, description: String): Boolean {
        Log.d(TAG, "saveNote:$name, $description")
        return true
    }

    companion object {
        private const val TAG = "MainModel"
    }
}