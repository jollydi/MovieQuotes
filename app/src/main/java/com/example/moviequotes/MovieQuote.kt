package com.example.moviequotes

import com.google.firebase.firestore.DocumentSnapshot

data class MovieQuote(
    var quote: String = "",
    var movie: String = "",
    var isSelected: Boolean = false) {
    companion object {
        fun fromSnapshot(snapshot: DocumentSnapshot): MovieQuote {
            val mq = snapshot.toObject(MovieQuote::class.java)!!
            return mq
        }
    }
}