package ru.verkhovin.imagecompressor.api.dto

class CompressRequest(
    var base64: List<String> = emptyList(),
    var urls: List<String> = emptyList()
)
