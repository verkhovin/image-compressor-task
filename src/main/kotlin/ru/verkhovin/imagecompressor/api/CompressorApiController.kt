package ru.verkhovin.imagecompressor.api

import org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE
import org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import ru.verkhovin.imagecompressor.api.dto.CompressRequest
import ru.verkhovin.imagecompressor.api.dto.Response
import ru.verkhovin.imagecompressor.component.RequestHandler

@RestController
class CompressorApiController(private val requestHandler: RequestHandler) {
    @PostMapping("/compress", produces = [APPLICATION_JSON_UTF8_VALUE], consumes = [MULTIPART_FORM_DATA_VALUE])
    fun compressMultiPart(@RequestParam("files") files: Array<MultipartFile>) =
        try {
            Response.of(requestHandler.handleMultipartRequest(files))
        } catch (e: Exception) {
            Response.error("Failed to process multipart/form-data request: ${e.message}")
        }


    @PostMapping("/compress", produces = [APPLICATION_JSON_UTF8_VALUE], consumes = [APPLICATION_JSON_UTF8_VALUE])
    fun compressJson(@RequestBody compressRequest: CompressRequest) =
        try {
            Response.of(requestHandler.handleJsonRequest(compressRequest))
        } catch (e: Exception) {
            Response.error("Failed to process application/json request: ${e.message}")
        }
}
