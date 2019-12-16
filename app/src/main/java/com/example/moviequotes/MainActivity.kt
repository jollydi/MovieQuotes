package com.example.moviequotes

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.dialog_add.*
import kotlinx.android.synthetic.main.dialog_add.view.*

class MainActivity : AppCompatActivity() {

    lateinit var adapter: MovieQuoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            Log.d(Constants.TAG, "You want a quote")
            adapter.showAddDialog()
        }

        adapter = MovieQuoteAdapter(this)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)
        recycler_view.adapter = adapter

    }

    private fun showDeleteDialog() {
//        val builder = AlertDialog.Builder(this)
//        builder.setTitle(R.string.dialogDeleteTitle)
//        builder.setMessage(getString(R.string.confirm_delete_message))
//        builder.setPositiveButton(android.R.string.ok) {_, _ ->
//            updateQuote(MovieQuote(getString(R.string.defaultQuote), getString(R.string.defaultMovie)))
//        }
//        builder.setNegativeButton(android.R.string.cancel, null)
//        builder.create().show()
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
                getWhichSettings()
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
            R.id.action_clear -> {
                showDeleteDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getWhichSettings() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Which settings?")
        builder.setItems(R.array.settings_types) {_, index ->
            val settingsType = when (index) {
                0 -> Settings.ACTION_SOUND_SETTINGS
                1 -> Settings.ACTION_SEARCH_SETTINGS
                else -> Settings.ACTION_SETTINGS
            }
            startActivity(Intent(settingsType))
        }
        builder.create().show()
    }
//
//    private fun getWhichSettings() {
//        val builder =
//    }

    private fun changeSize(delta: Int) {
//        var currentSize = quote_text_view.textSize / resources.displayMetrics.scaledDensity
//        currentSize += delta
//        quote_text_view.textSize = currentSize
//        movie_text_view.textSize = currentSize
    }
}
