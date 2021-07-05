package com.example.baruintro.model

import java.util.*

data class StudentModel (
    var id: Int = getAutoId(),
    var name: String = "",
    var email: String = "",
    var date: String = ""
){
    companion object{
        fun getAutoId(): Int {
            val random = Random()
            return random.nextInt(100)
        }
    }
}