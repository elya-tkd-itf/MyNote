package com.example.mynote.main.view

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.mynote.R
import com.example.mynote.about.AboutActivity
import com.example.mynote.main.model.MainModel
import com.example.mynote.main.presenter.MainPresenter
import com.example.mynote.main.presenter.NotePresenter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), NoteView {

    private var presenter: NotePresenter? = null

    private lateinit var nameEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var photoView: ImageView
    private lateinit var photoButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        presenter = MainPresenter(this, MainModel())
    }

    private fun initViews() {
        nameEditText = findViewById(R.id.nameText)
        descriptionEditText = findViewById(R.id.descriptionText)
        photoView = findViewById(R.id.photoView)
        photoButton = findViewById(R.id.photoButton)
        photoButton.setOnClickListener { takePicture() }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save -> presenter?.trySave(
                nameEditText.text.toString(),
                descriptionEditText.text.toString()
            )
            R.id.share -> shareNote(
                nameEditText.text.toString(),
                descriptionEditText.text.toString()
            )
            R.id.about -> openAboutScreen()
        }
        return true
    }

    override fun startMyActivity(intent: Intent) {
        startActivity(intent)
    }

    override fun startMyActivityForResult(intent: Intent, result: Int) {
        startActivityForResult(intent, result)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter = null
    }

    override fun onSaveSuccessEvent() {
        showToast(getString(R.string.successSaveMessage))
    }

    override fun onSaveErrorEvent() {
        showToast(getString(R.string.errorSaveMessage))
    }

    override fun onAttemptSaveEmptyContent() {
        showToast(getString(R.string.emptySaveMessage))
    }

    override fun onAttemptShareEmptyContent() {
        showToast(getString(R.string.emptyShareMessage))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            photoView.setImageBitmap(imageBitmap)
            photoView.layoutParams = LinearLayout.LayoutParams(photoView.width, 900)
        }
    }

    override fun shareNote(name: String, description: String) {
        if (isEmpty(name, description)) {
            onAttemptShareEmptyContent()
        } else {
            startMyActivity(Intent(Intent.ACTION_SEND).apply {
                type = context.getString(R.string.plain_text)
                putExtra(Intent.EXTRA_TEXT, "$name\n$description")
            })
        }
    }

    override fun openAboutScreen() {
        startMyActivity(Intent(this, AboutActivity::class.java))
    }

    override fun takePicture() {
        if (!checkPermissions()) {
            requestPermissions()
        } else {
            dispatchTakePictureIntent()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String?>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            when {
                grantResults.isEmpty() -> Log.i(TAG, "User interaction was cancelled.")
                grantResults[0] == PackageManager.PERMISSION_GRANTED -> dispatchTakePictureIntent()
                else -> Log.i(TAG, "Permission denied.")
            }
        }
    }

    private fun dispatchTakePictureIntent() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startMyActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            Log.e(TAG, e.toString())
        }
    }

    private fun checkPermissions(): Boolean {
        val permissionState = ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        )
        return permissionState == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        val shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(
            this, Manifest.permission.CAMERA
        )
        if (shouldProvideRationale) {
            showToast(getString(R.string.allow_camera))
        } else {
            showToast(getString(R.string.requesting_permission))
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.CAMERA),
                REQUEST_PERMISSIONS_REQUEST_CODE
            )
        }
    }

    override fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }

    companion object {
        private val TAG = "MainActivity"
        private val REQUEST_IMAGE_CAPTURE = 1
        private val REQUEST_PERMISSIONS_REQUEST_CODE = 34
    }
}
