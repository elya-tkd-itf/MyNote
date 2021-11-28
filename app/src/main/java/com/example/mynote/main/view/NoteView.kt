package com.example.mynote.main.view

import android.content.Intent

interface NoteView {
    fun onSaveSuccessEvent()
    fun onSaveErrorEvent()
    fun onAttemptSaveEmptyContent()
    fun onAttemptShareEmptyContent()
    fun shareNote(name: String, description: String)
    fun openAboutScreen()
    fun takePicture()
    fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String?>,
        grantResults: IntArray
    )

    fun showToast(text: String)
    fun startMyActivity(intent: Intent)
    fun startMyActivityForResult(intent: Intent, result: Int)
}