package com.example.mynote.presentors.framework

interface NotePresenter {
    fun onSaveIconClick(name: String, description: String)

    fun onShareIconClick(name: String, description: String)

    fun onAboutIconClick()
}