package com.example.mynote.main.presenter

interface NotePresenter {
    fun trySave(name: String, description: String)
}