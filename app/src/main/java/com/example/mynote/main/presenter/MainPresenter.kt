package com.example.mynote.main.presenter

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.MediaStore
import android.util.Log
import androidx.core.app.ActivityCompat
import com.example.mynote.R
import com.example.mynote.about.AboutActivity
import com.example.mynote.main.model.NoteModel
import com.example.mynote.main.view.NoteView

class MainPresenter(private val view: NoteView, private val model: NoteModel) : NotePresenter {

    override fun trySave(name: String, description: String) {
        when {
            isEmpty(name, description) -> {
                view.onAttemptSaveEmptyContent()
            }
            model.saveNote(name, description) -> {
                view.onSaveSuccessEvent()
            }
            else -> {
                view.onSaveErrorEvent()
            }
        }
    }

    private fun isEmpty(name: String, description: String): Boolean =
        name.isEmpty() || description.isEmpty()


    companion object {
        private val TAG = "MainPresenter"
        private val REQUEST_IMAGE_CAPTURE = 1
        private val REQUEST_PERMISSIONS_REQUEST_CODE = 34
    }
}