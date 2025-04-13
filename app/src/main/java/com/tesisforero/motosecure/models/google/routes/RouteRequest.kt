package com.tesisforero.motosecure.models.google.routes

data class LatLng(
    val latitude: Double,
    val longitude: Double
)

data class Location(
    val latLng: LatLng
)

data class OriginOrDestination(
    val location: Location
)

data class RouteModifiers(
    val avoidTolls: Boolean,
    val avoidHighways: Boolean,
    val avoidFerries: Boolean
)

data class RouteRequest(
    val origin: OriginOrDestination,
    val destination: OriginOrDestination,
    val travelMode: String = "DRIVE",
    val routingPreference: String = "TRAFFIC_AWARE",
    val computeAlternativeRoutes: Boolean = false,
    val routeModifiers: RouteModifiers = RouteModifiers(false, false, false),
    val languageCode: String = "es-PE",
    val units: String = "METRIC"
)
