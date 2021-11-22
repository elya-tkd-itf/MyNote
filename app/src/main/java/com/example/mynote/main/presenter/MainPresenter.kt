package com.example.mynote.main.presenter

import com.example.mynote.main.model.NoteModel
import com.example.mynote.main.view.NoteView

class MainPresenter(private val view: NoteView, private val model: NoteModel) : NotePresenter {
    override fun trySave(name: String, description: String) {
        if (name.isEmpty() || description.isEmpty()) {
            view.onAttemptSaveEmptyContent()
        } else if (model.saveNote(name, description)) {
            view.onSaveSuccessEvent()
        } else {
            view.onSaveErrorEvent()
        }
    }
}