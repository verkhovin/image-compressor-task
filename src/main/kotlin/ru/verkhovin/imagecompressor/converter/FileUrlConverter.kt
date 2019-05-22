package ru.verkhovin.imagecompressor.converter

import org.springframework.stereotype.Component
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

@Component
class FileUrlConverter : Converter<String> {
    override fun convert(source: String): InputStream {
        val connection = URL(source).openConnection() as HttpURLConnection
        connection.addRequestProperty("User-Agent", "Mozilla/4.0")
        return connection.inputStream
    }
}
