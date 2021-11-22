package com.example.mynote.main.view

interface NoteView {
    fun onSaveSuccessEvent()
    fun onSaveErrorEvent()
    fun onAttemptSaveEmptyContent()
}