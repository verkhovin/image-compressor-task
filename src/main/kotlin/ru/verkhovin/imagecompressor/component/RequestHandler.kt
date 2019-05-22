package ru.verkhovin.imagecompressor.component

import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import ru.verkhovin.imagecompressor.api.dto.CompressRequest
import ru.verkhovin.imagecompressor.converter.Base64Converter
import ru.verkhovin.imagecompressor.converter.FileUrlConverter
import ru.verkhovin.imagecompressor.converter.MultipartFileConverter
import java.util.Base64

@Service
class RequestHandler(
    private val multipartFileConverter: MultipartFileConverter,
    private val base64Converter: Base64Converter,
    private val fileUrlConverter: FileUrlConverter,
    private val imageResizingComponent: ImageResizingComponent,
    private val base64Encoder: Base64.Encoder
) {
    fun handleMultipartRequest(files: Array<MultipartFile>) =
        files.asSequence()
            .map(multipartFileConverter::convert)
            .map(imageResizingComponent::resizeImage)
            .map(this::convertImageToBase64)
            .toList()

    fun handleJsonRequest(compressRequest: CompressRequest) =
        with(compressRequest) {
            base64.map(base64Converter::convert) + urls.map(fileUrlConverter::convert)
        }.asSequence()
            .map(imageResizingComponent::resizeImage)
            .map(this::convertImageToBase64)
            .toList()


    private fun convertImageToBase64(image: ByteArray) = base64Encoder.encodeToString(image)
}
