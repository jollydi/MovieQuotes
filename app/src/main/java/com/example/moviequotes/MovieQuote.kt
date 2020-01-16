package com.example.moviequotes

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.ServerTimestamp

data class MovieQuote(
    var quote: String = "",
    var movie: String = "",
    var isSelected: Boolean = false
) {
    @get:Exclude var id = ""
    @ServerTimestamp var lastTouched: Timestamp? = null

    companion object {
        const val LAST_TOUCHED_KEY = "lastTouched"
        fun fromSnapshot(snapshot: DocumentSnapshot): MovieQuote {
            val mq = snapshot.toObject(MovieQuote::class.java)!!
            mq.id = snapshot.id
            return mq
        }
    }
}