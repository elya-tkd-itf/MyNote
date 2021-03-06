package com.example.mynote.main.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import com.example.mynote.R
import com.example.mynote.main.model.MainModel
import com.example.mynote.main.presenter.MainPresenter
import com.example.mynote.main.presenter.NotePresenter

class MainActivity : AppCompatActivity(), NoteView {

    private var presenter: NotePresenter? = null

    private lateinit var nameEditText: EditText
    private lateinit var descriptionEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        presenter = MainPresenter(this, MainModel())
    }

    private fun initViews() {
        nameEditText = findViewById(R.id.nameText)
        descriptionEditText = findViewById(R.id.descriptionText)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.save) {
            presenter?.trySave(
                nameEditText.text.toString(),
                descriptionEditText.text.toString()
            )
        }
        return true
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

    private fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }
}
