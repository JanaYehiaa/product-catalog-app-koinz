package com.example.productcatalog.core.common

import kotlinx.serialization.SerializationException
import retrofit2.HttpException
import java.io.IOException

 fun getErrorMessage(exception: Exception): String {
    return when (exception) {
        is IOException ->
            "No internet connection. Please check your connection."

        is SerializationException ->
            "We couldn't understand the data received from the server."

        is HttpException -> when (exception.code()) {
            in 500..599 ->
                "The server is currently unavailable. Please try again later."

            404 ->
                "The requested product was not found."

            in 400..499 ->
                "The request could not be completed."

            else ->
                "Something went wrong. Please try again."
        }

        else ->
            "Something went wrong. Please try again."
    }
}