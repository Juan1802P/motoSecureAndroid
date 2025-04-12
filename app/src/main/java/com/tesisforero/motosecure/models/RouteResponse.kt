package com.tesisforero.motosecure.models

data class RouteResponse(
    val routes: List<Route>
)

data class Route(
    val duration: String,
    val distanceMeters: Int,
    val polyline: Polyline?
)

data class Polyline(
    val encodedPolyline: String
)
