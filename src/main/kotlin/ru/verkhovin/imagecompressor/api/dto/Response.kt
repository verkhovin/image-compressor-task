package ru.verkhovin.imagecompressor.api.dto

class Response(
    val imagesBase64: List<String> = emptyList(),
    val status: ResponseStatus? = null,
    val errorMessage: String? = null
) {
    companion object {
        fun of(imagesBase64: List<String>) = Response(imagesBase64, ResponseStatus.OK)
        fun error(message: String) = Response(emptyList(), ResponseStatus.ERROR, message)
    }
}

