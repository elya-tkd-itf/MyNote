package com.example.mynote.main.view

interface NoteView {
    fun onSaveSuccessEvent()
    fun onSaveErrorEvent()
    fun onAttemptSaveEmptyContent()
    fun shareNote(name: String, description: String)
    fun openAboutScreen()
    fun takePicture()
}