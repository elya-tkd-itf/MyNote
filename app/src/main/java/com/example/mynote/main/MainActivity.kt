package com.example.mynote.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import com.example.mynote.R

class MainActivity : AppCompatActivity(), NoteView{

    private var presenter: MainPresenter? = null

    private lateinit var nameEditText: EditText
    private lateinit var descriptionEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        presenter = MainPresenter(this)
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
        if (item.itemId == R.id.save){
            presenter?.trySave(
                nameEditText.text.toString(),
                descriptionEditText.text.toString()
            )
        }
        return true
    }

    override fun onDestroy(){
        super.onDestroy()
        presenter?.view = null
    }
    fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
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
}
