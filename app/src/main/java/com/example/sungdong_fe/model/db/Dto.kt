package com.example.sungdong_fe.model.db

class Dto {
    data class Place(
        val id: Int,
        val name: String,
        val image: String,
        val address: String,
        val tel: String,
        val openingTime: String,
        val closingTime: String
    )
    data class TmapErrorCode(
        val id: Int,
        val category: String,
        val code: Int,
        val message: String
    )
}