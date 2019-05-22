package ru.verkhovin.imagecompressor.converter

import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayInputStream

@Component
class MultipartFileConverter : Converter<MultipartFile> {
    override fun convert(source: MultipartFile) = ByteArrayInputStream(source.bytes)
}
