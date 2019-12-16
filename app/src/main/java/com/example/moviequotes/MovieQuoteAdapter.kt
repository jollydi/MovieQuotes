package com.example.moviequotes

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.dialog_add.view.*

class MovieQuoteAdapter(var context: Context) : RecyclerView.Adapter<MovieQuoteViewHolder>() {

    private val movieQuotes = ArrayList<MovieQuote>()

    override fun getItemCount() = movieQuotes.size

    override fun onCreateViewHolder(parent: ViewGroup, index: Int): MovieQuoteViewHolder {
        Log.d(Constants.TAG, "Creating VH")
        val view = LayoutInflater.from(context).inflate(R.layout.row_view, parent, false)
        return MovieQuoteViewHolder(view, this, context)
    }

    override fun onBindViewHolder(viewHolder: MovieQuoteViewHolder, index: Int) {
        viewHolder.bind(movieQuotes[index])
    }

    private fun add(movieQuote: MovieQuote) {
        movieQuotes.add(0, movieQuote)
        notifyItemInserted(0)
    }

    private fun remove(index: Int) {
        movieQuotes.removeAt(index)
        notifyItemRemoved(index)
    }

    private fun edit(position: Int, quote: String, movie: String) {
        movieQuotes[position].quote = quote
        movieQuotes[position].movie = movie
        notifyItemChanged(position)
    }

    fun showAddDialog(position: Int = -1) {
        val builder = AlertDialog.Builder(context)
        // Set options on builder
        builder.setTitle(
            if (position >= 0) {
                R.string.dialogTitleEdit
            } else {
                R.string.dialogTitleAdd
            }
        )
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_add, null, false)
        builder.setView(view)
        if (position >= 0) {
            view.quote_edit_text.setText(movieQuotes[position].quote)
            view.movie_edit_text.setText(movieQuotes[position].movie)
        }

        builder.setPositiveButton(android.R.string.ok) {_, _ ->
            val quote = view.quote_edit_text.text.toString()
            val movie = view.movie_edit_text.text.toString()
            if (position >= 0) {
                edit(position, quote, movie)
            } else {
                add(MovieQuote(quote, movie))
            }
        }
        builder.setNegativeButton(android.R.string.cancel, null)
        builder.setNeutralButton(context.getString(R.string.delete)) { _, _ ->
            remove(position)
        }
        builder.create().show()
    }

    fun selectMovieQuote(position: Int) {
        movieQuotes[position].isSelected = !movieQuotes[position].isSelected
        notifyItemChanged(position)
    }
}