package com.example.mynote.presentors

import com.example.mynote.models.framework.NoteModel
import com.example.mynote.presentors.framework.NotePresenter
import com.example.mynote.views.framework.NoteView

class MainPresenter(private val view: NoteView, private val model: NoteModel) : NotePresenter {

    override fun onSaveIconClick(name: String, description: String) {
        when {
            isEmpty(name, description) -> view.onAttemptSaveEmptyContent()
            model.saveNote(name, description) -> view.onSaveSuccessEvent()
            else -> view.onSaveErrorEvent()
        }
    }

    override fun onShareIconClick(name: String, description: String) {
        if (isEmpty(name, description)) view.onAttemptShareEmptyContent()
        else view.startSendActivity("$name\n$description")
    }

    override fun onAboutIconClick() {
        view.openAboutScreen()
    }

    private fun isEmpty(name: String, description: String): Boolean =
        name.isEmpty() || description.isEmpty()

    companion object {
        private const val TAG = "MainPresenter"
    }
}