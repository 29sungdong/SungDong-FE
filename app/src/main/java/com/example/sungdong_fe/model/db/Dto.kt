package com.example.sungdong_fe.model.db

class Dto {
    data class PlaceInfo(
        val id: Int,
        val name: String,
        val image: String,
        val address: String,
        val tel: String,
        val openingTime: String,
        val closingTime: String
    )
    data class PlaceLocation(
        val id: Int,
        val name: String,
        val xcoordinate: String,
        val ycoordinate: String
    )
}