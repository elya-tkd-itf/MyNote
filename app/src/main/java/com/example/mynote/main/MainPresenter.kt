package com.example.mynote.main

class MainPresenter(var view: NoteView?) {

    fun trySave(name: String, description: String) {
        if (name.isEmpty() || description.isEmpty()){
            view?.onAttemptSaveEmptyContent()
        } else {
            view?.onSaveSuccessEvent()
        }
    }
}