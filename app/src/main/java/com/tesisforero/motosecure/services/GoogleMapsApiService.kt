package com.tesisforero.motosecure.services

import com.tesisforero.motosecure.models.RouteRequest
import com.tesisforero.motosecure.models.RouteResponse
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface GoogleMapsApiService {
    @POST("directions/v2:computeRoutes")
    @Headers(
        "Content-Type: application/json",
        "X-Goog-FieldMask: routes.duration,routes.distanceMeters,routes.polyline.encodedPolyline"
    )
    suspend fun getRoute(
        @Body routeRequest: RouteRequest
    ): RouteResponse
}
