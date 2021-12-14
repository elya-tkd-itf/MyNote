package com.example.mynote.views

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mynote.R
import com.example.mynote.models.MainModel
import com.example.mynote.presentors.MainPresenter
import com.example.mynote.presentors.framework.NotePresenter
import com.example.mynote.views.framework.NoteView

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
        when (item.itemId) {
            R.id.save -> presenter?.onSaveIconClick(
                nameEditText.text.toString(),
                descriptionEditText.text.toString()
            )
            R.id.share -> presenter?.onShareIconClick(
                nameEditText.text.toString(),
                descriptionEditText.text.toString()
            )
            R.id.about -> presenter?.onAboutIconClick()
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

    override fun onAttemptShareEmptyContent() {
        showToast(getString(R.string.emptyShareMessage))
    }

    override fun startSendActivity(text: String) {
        startActivity(Intent(Intent.ACTION_SEND).apply {
            type = TEXT_PLAIN;
            putExtra(Intent.EXTRA_TEXT, text)
        })
    }

    override fun openAboutScreen() {
        startActivity(Intent(this, AboutActivity::class.java))
    }

    private fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }

    companion object {
        private const val TEXT_PLAIN = "text/plain";
    }
}
