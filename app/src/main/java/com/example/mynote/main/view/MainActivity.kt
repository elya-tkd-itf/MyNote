package com.example.mynote.main.view

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mynote.R
import com.example.mynote.main.model.MainModel
import com.example.mynote.main.presenter.MainPresenter
import com.example.mynote.main.presenter.NotePresenter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), NoteView {

    private var presenter: NotePresenter? = null

    private lateinit var nameEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var photoView: ImageView

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
        findViewById<FloatingActionButton>(R.id.photoButton).setOnClickListener {
            presenter?.takePicture(this)
        }
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
            R.id.share -> presenter?.shareNote(
                nameEditText.text.toString(),
                descriptionEditText.text.toString(),
                this
            )
            R.id.about -> presenter?.openAboutScreen(this)
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

    override fun onAttemptShareEmptyContent(){
        showToast(getString(R.string.emptyShareMessage))
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        presenter?.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            photoView.setImageBitmap(imageBitmap)
            photoView.layoutParams = LinearLayout.LayoutParams(photoView.width, 900)
        }
    }

    override fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }

    companion object {
        private val TAG = "MainActivity"
        private val REQUEST_IMAGE_CAPTURE = 1
    }
}
