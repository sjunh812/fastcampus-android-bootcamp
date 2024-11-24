package com.sjhstudio.compose.movieapp.ui.models

sealed class DialogText {

    abstract var text: String?
    abstract var id: Int?

    class Default() : DialogText() {

        override var text: String? = null
        override var id: Int? = null

        constructor(text: String) : this() {
            this.text = text
        }

        constructor(id: Int) : this() {
            this.id = id
        }
    }

    class Rating() : DialogText() {

        override var text: String? = null
        override var id: Int? = null
        var rating: Float? = 0.0F

        constructor(text: String, rating: Float) : this() {
            this.text = text
            this.rating = rating
        }

        constructor(id: Int, rating: Float) : this() {
            this.id = id
            this.rating = rating
        }
    }
}