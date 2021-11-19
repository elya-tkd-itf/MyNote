package com.example.mynote.main

interface NoteView {
    fun onSaveSuccessEvent()
    fun onSaveErrorEvent()
    fun onAttemptSaveEmptyContent()
}