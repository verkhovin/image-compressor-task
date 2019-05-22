package ru.verkhovin.imagecompressor.converter

import org.springframework.stereotype.Component
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.util.Base64

@Component
class Base64Converter(private val base64Decoder: Base64.Decoder) : Converter<String> {
    override fun convert(source: String): InputStream {
        val decodedImage = base64Decoder.decode(source)
        return ByteArrayInputStream(decodedImage)
    }
}
