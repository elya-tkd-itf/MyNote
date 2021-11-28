package com.example.mynote.main.presenter

import android.content.Context

interface NotePresenter {
    fun trySave(name: String, description: String)
    fun shareNote(name: String, description: String, context: Context)
    fun openAboutScreen(context: Context)
    fun takePicture(context: Context)
    fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String?>,
        grantResults: IntArray, context: Context
    )
}