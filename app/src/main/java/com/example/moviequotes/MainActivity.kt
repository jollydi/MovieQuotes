package com.example.moviequotes

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.dialog_add.*
import kotlinx.android.synthetic.main.dialog_add.view.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            Log.d(Constants.TAG, "You want a quote")
//            updateQuote(MovieQuote("Everything Is Awesome!", "The LEGO Movie"))
            showAddDialog()
        }
    }

    private fun showAddDialog() {
        val builder = AlertDialog.Builder(this)
        // Set options on builder
        builder.setTitle(R.string.dialogTitle)

        // Content is a message, view, or a list of items
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_add, null, false)
        builder.setView(view)
        builder.setPositiveButton(android.R.string.ok) {_, _ ->
            val quote = view.quote_edit_text.text.toString()
            val movie = view.movie_edit_text.text.toString()
            updateQuote(MovieQuote(quote, movie))
        }
        builder.setNegativeButton(android.R.string.cancel, null)
        builder.create().show()
    }

    private fun updateQuote(movieQuote: MovieQuote) {
        quote_text_view.text = movieQuote.quote
        movie_text_view.text = movieQuote.movie
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> {
//                startActivity(Intent(Settings.ACTION_SETTINGS))
//                getWhichSettings()
                true
            }
            R.id.action_increase_size -> {
                changeSize(4)
                true
            }
            R.id.action_decrease_size -> {
                changeSize(-4)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
//
//    private fun getWhichSettings() {
//        val builder =
//    }

    private fun changeSize(delta: Int) {
        var currentSize = quote_text_view.textSize / resources.displayMetrics.scaledDensity
        currentSize += delta
        quote_text_view.textSize = currentSize
        movie_text_view.textSize = currentSize
    }
}
