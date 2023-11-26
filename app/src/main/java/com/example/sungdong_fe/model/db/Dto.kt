package com.example.sungdong_fe.model.db

class Dto {
    data class User(
        val id: Int,
        val token: String,
    )
    data class LoginRequest(
        val username: String,
        val password: String
    )
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
        val ycoordinate: String,
        val openingTime: String,
        val closingTime: String,
        val hasEvent : Boolean
    )
    data class Event(
        val placeId: Int,
        val placeName: String,
        val name: String,
        val endDate: String,
        val url: String
    )
    data class Marker(
        val id: Int,
        val name: String,
        val openingTime: String,
        val closingTime: String,
        val hasEvent: Boolean,
        val xcoordinate: String,
        val ycoordinate: String,
    )
    data class Footprint(
        val id : Int,
        val name: String,
        val isVisited: Boolean
    )
    data class PathRequest(
        val startX: Float,
        val startY: Float,
        val angle: Int?,
        val speed: Int?,
        val endPoiId: String?,
        val endX: Float,
        val endY: Float,
        val passList: String?,
        val reqCoordType: String?,
        val startName: String,
        val endName: String,
        val searchOption: String?,
        val CoordType: String?,
        val sort: String?
    )
}