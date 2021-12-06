package com.example.mynote.views.framework

interface NoteView {
    fun onSaveSuccessEvent()

    fun onSaveErrorEvent()

    fun onAttemptSaveEmptyContent()

    fun onAttemptShareEmptyContent()

    fun openAboutScreen()

    fun startSendActivity(text: String)
}