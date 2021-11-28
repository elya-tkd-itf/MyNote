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

    private fun isEmpty(name: String, description: String): Boolean{
        return name.isEmpty() || description.isEmpty()
    }

    override fun shareNote(name: String, description: String, context: Context) {
        if (isEmpty(name, description)){
            view.onAttemptShareEmptyContent()
        } else {
            view.startMyActivity(Intent(Intent.ACTION_SEND).apply {
                type = context.getString(R.string.plain_text)
                putExtra(Intent.EXTRA_TEXT, "$name\n$description")
            })
        }
    }

    override fun openAboutScreen(context: Context) {
        view.startMyActivity(Intent(context, AboutActivity::class.java))
    }

    override fun takePicture(context: Context) {
        if (!checkPermissions(context)) {
            requestPermissions(context)
        } else {
            dispatchTakePictureIntent()
        }
    }

    private fun checkPermissions(context: Context): Boolean {
        val permissionState = ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        )
        return permissionState == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions(context: Context) {
        val shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(
            context as Activity, Manifest.permission.CAMERA
        )
        if (shouldProvideRationale) {
            view.showToast(context.getString(R.string.allow_camera))
        } else {
            view.showToast(context.getString(R.string.requesting_permission))
            // previously and checked "Never ask again".
            ActivityCompat.requestPermissions(
                context, arrayOf(Manifest.permission.CAMERA),
                REQUEST_PERMISSIONS_REQUEST_CODE
            )
        }
    }

    private fun dispatchTakePictureIntent() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            view.startMyActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            Log.e(TAG, e.toString())
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String?>,
        grantResults: IntArray, context: Context
    ) {
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            when {
                grantResults.isEmpty() -> {
                    Log.i(TAG, "User interaction was cancelled.")
                }
                grantResults[0] == PackageManager.PERMISSION_GRANTED -> {
                    dispatchTakePictureIntent()
                }
                else -> {
                    Log.i(TAG, "Permission denied.")
                }
            }
        }
    }

    companion object {
        private val TAG = "MainPresenter"
        private val REQUEST_IMAGE_CAPTURE = 1
        private val REQUEST_PERMISSIONS_REQUEST_CODE = 34
    }
}