package com.example.moviequotes

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.dialog_add.view.*

class MovieQuoteAdapter(var context: Context) : RecyclerView.Adapter<MovieQuoteViewHolder>() {

    private val movieQuotes = ArrayList<MovieQuote>()

    private val quotesRef = FirebaseFirestore
        .getInstance()
        .collection(Constants.QUOTES_COLLECTION)

    init {
        quotesRef
            .orderBy(MovieQuote.LAST_TOUCHED_KEY, Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    Log.e(Constants.TAG, "Listen error: $exception")
                    return@addSnapshotListener
                }
                for (docChange in snapshot!!.documentChanges) {
                    val mq = MovieQuote.fromSnapshot(docChange.document)
                    when (docChange.type) {
                        DocumentChange.Type.ADDED -> {
                            movieQuotes.add(0, mq)
                            notifyItemInserted(0)
                        }
                        DocumentChange.Type.REMOVED -> {
                            val pos = movieQuotes.indexOfFirst { mq.id == it.id }
                            movieQuotes.removeAt(pos)
                            notifyItemRemoved(pos)
                        }
                        DocumentChange.Type.MODIFIED -> {
                            val pos = movieQuotes.indexOfFirst { mq.id == it.id }
                            movieQuotes[pos] = mq
                            notifyItemChanged(pos)
                        }
                    }
                }
            }
    }

    override fun getItemCount() = movieQuotes.size

    override fun onCreateViewHolder(parent: ViewGroup, index: Int): MovieQuoteViewHolder {
        Log.d(Constants.TAG, "Creating VH")
        val view = LayoutInflater.from(context).inflate(R.layout.row_view, parent, false)
        return MovieQuoteViewHolder(view, this, context)
    }

    override fun onBindViewHolder(viewHolder: MovieQuoteViewHolder, index: Int) {
        viewHolder.bind(movieQuotes[index])
    }

    fun add(movieQuote: MovieQuote) {
        quotesRef.add(movieQuote)
    }

    private fun remove(index: Int) {
        quotesRef.document(movieQuotes[index].id).delete()
    }

    private fun edit(position: Int, quote: String, movie: String) {
        movieQuotes[position].quote = quote
        movieQuotes[position].movie = movie
        quotesRef.document(movieQuotes[position].id).set(movieQuotes[position])
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

        builder.setPositiveButton(android.R.string.ok) { _, _ ->
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
        quotesRef.document(movieQuotes[position].id).set(movieQuotes[position])
    }
}