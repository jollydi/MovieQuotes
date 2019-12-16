package com.example.moviequotes

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_view.view.*

class MovieQuoteViewHolder : RecyclerView.ViewHolder {

    val quoteTextView: TextView = itemView.quote_text_view
    val movieTextView: TextView = itemView.movie_text_view
    val cardView: CardView = itemView.card_view
    lateinit var context: Context

    constructor(itemView: View, adapter: MovieQuoteAdapter, context: Context): super(itemView) {
        this.context = context
        itemView.setOnClickListener {
            adapter.showAddDialog(adapterPosition)
        }
        itemView.setOnLongClickListener {
            adapter.selectMovieQuote(adapterPosition)
            true
        }
    }

    fun bind(movieQuote: MovieQuote) {
        quoteTextView.text = movieQuote.quote
        movieTextView.text = movieQuote.movie
        if (movieQuote.isSelected) {
            val color = ContextCompat.getColor(context, R.color.colorAccent)
            cardView.setCardBackgroundColor(color)
        } else {
            cardView.setCardBackgroundColor(Color.WHITE)
        }
    }
}